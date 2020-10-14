package games;


import java.util.ArrayList;


import gameAlgo.GameBoard;

public class TicTacTowSmart extends TicTacTow {
	/*
	 * Constructors
	 */
	public TicTacTowSmart(int rowLenght,int colLenght) {
		super(rowLenght, colLenght);
	}
	public TicTacTowSmart() {
		super(3,3);
	}
	/*
	 * Calculate best move for not to lose using min max algorithm function.
	 */
	public void calcComputerMove() {
        GameBoard.GameMove move=new GameMove(); //temp for move coordinats and score.
        TicTacTow.BoardSigns player=BoardSigns.COMPUTER; //starts with computer move 
        move =minimax(this.board,player);             //minimax algorithm.
        setLastMove(move);
        this.board[move.getRow()][move.getColumn()]=BoardSigns.COMPUTER.getSign();
		}
	/*
	 * check if tree signs in a row exist.
	 */
	private boolean winning(char[][] board, games.TicTacTow.BoardSigns player){
		  if(
		    (board[0][0] == player.getSign() && board[0][1] == player.getSign() && board[0][2] == player.getSign()) ||
		    (board[1][0] == player.getSign() && board[1][1] == player.getSign() && board[1][2] == player.getSign()) ||
		    (board[2][0] == player.getSign() && board[2][1] == player.getSign() && board[2][2] == player.getSign()) ||
		    (board[0][0] == player.getSign() && board[1][0] == player.getSign() && board[2][0] == player.getSign()) ||
		    (board[0][1] == player.getSign() && board[1][1] == player.getSign() && board[2][1] == player.getSign()) ||
		    (board[0][2] == player.getSign() && board[1][2] == player.getSign() && board[2][2] == player.getSign()) ||
		    (board[0][0] == player.getSign() && board[1][1] == player.getSign() && board[2][2] == player.getSign()) ||
		    (board[0][2] == player.getSign() && board[1][1] == player.getSign() && board[2][0] == player.getSign())
		    ) 
		      return true; 
		  else 
		      return false;
		}	
	/*
	 * create array of empty places on the board [0,1,2,3,4,5,6,7,8].
	 */
	private ArrayList<Integer> emptyIndices(char[][] board){
		  ArrayList<Integer> list=new ArrayList<>();
		  for(int i=0;i<3;i++)
			  for(int j=0;j<3;j++)
				  if(board[i][j]==TicTacTow.BoardSigns.BLANK.getSign()) list.add(i*3+j);//transfer i,j index to i integer index 0 to 8
		  return list;
		}
	/*
	 * min max function
	 */
	private GameBoard.GameMove minimax(char[][] newBoard,games.TicTacTow.BoardSigns player){ 
		ArrayList<Integer> availSpots = emptyIndices(newBoard);
		  ArrayList<GameBoard.GameMove> moves=new ArrayList<>();
		  
		  //if after player move player win(has 3 in a row) return -10 (on the move object temp) 
	  if (winning ( newBoard,BoardSigns.PLAYER)){
		  GameBoard.GameMove temp = new GameBoard.GameMove();
		  temp.setScore(-10);
		  return temp;
	  }
	      //if after player move player win(has 3 in a row) return 10 (on the move object temp)
	  else if (winning (newBoard,BoardSigns.COMPUTER)){
		  GameBoard.GameMove temp= new GameBoard.GameMove();
		  temp.setScore(10);
		  return temp;
	  }
	     //if no one wins return 0.
	  else if (availSpots.size() == 0){
		  GameBoard.GameMove temp= new GameBoard.GameMove();
		  temp.setScore(0);
		  return temp;
	  }
	  
	  
	  for (int i = 0; i < availSpots.size(); i++){ 
		    GameBoard.GameMove move=new GameBoard.GameMove(availSpots.get(i)/3,availSpots.get(i)%3);//transfer integer index i to i,j.
		    newBoard[availSpots.get(i)/3][availSpots.get(i)%3]=player.getSign();//do move on availble empty spot.
		    //check foe every empty spot min max and coollect points to object move.
		    if (player == BoardSigns.COMPUTER){
		    	GameBoard.GameMove result = minimax(newBoard,TicTacTow.BoardSigns.PLAYER);
		        move.setScore(result.getScore());
		      }
		      else{
		    	GameBoard.GameMove result = minimax(newBoard,TicTacTow.BoardSigns.COMPUTER);
		    	move.setScore(result.getScore());
		      }
		    newBoard[availSpots.get(i)/3][availSpots.get(i)%3]=TicTacTow.BoardSigns.BLANK.getSign();//clear checked spot.
		    moves.add(move);//add move with score to array of moves.
	  }
	  
	  int bestMove=0;
	  //find best move (best score) from array of moves.
	  if(player == TicTacTow.BoardSigns.COMPUTER){
	    int bestScore = -10000;
	    for(int i = 0; i < moves.size(); i++){
	      if(moves.get(i).getScore() > bestScore){
	        bestScore = moves.get(i).getScore();
	        bestMove = i;
	      }
	    }
	  }
	  else{
	    int bestScore = 10000;
	    for(int i = 0; i < moves.size(); i++){
	      if(moves.get(i).getScore() < bestScore){
	        bestScore = moves.get(i).getScore();
	        bestMove = i;
	      }
	    }
	  }

	  return moves.get(bestMove);//return best move;
	}
	
}


