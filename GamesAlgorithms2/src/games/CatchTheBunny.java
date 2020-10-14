package games;

import gameAlgo.GameBoard;

import gameAlgo.IGameAlgo;
import gameAlgo.GameBoard.GameMove;

public abstract class CatchTheBunny extends GameBoard implements IGameAlgo {
	private int max_steps;//max number of available steps.
	protected GameMove lastMove;
	
	//Constructor
	public CatchTheBunny(int rowLenght,int colLenght,int maxSteps) {
		super(rowLenght, colLenght);
		this.max_steps=maxSteps;
		this.lastMove=new GameMove();
	}
	
	public GameMove getLastMove() {
		return lastMove;
	}
	
	public void setLastMove(GameMove lastMove) {
		this.lastMove =new GameMove(lastMove);
	}
	
	public int getMax_steps() {
		return max_steps;
	}
	
	public void setMax_steps(int max_steps) {
		this.max_steps = max_steps;
	}
	
	public abstract void calcComputerMove();

	/*
	 * get status of the game.
	 */
	public IGameAlgo.GameState getGameState(GameBoard.GameMove move){
		/*
		 * last move was players move
		 */
		if(board[move.getRow()][move.getColumn()]==BoardSigns.PLAYER.getSign()) {
	        //Check if Bunny on the board.
			for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				if(board[i][j]==CatchTheBunny.BoardSigns.COMPUTER.getSign()) {
				   //if bunny on the board and all steps used player lost
                   if(max_steps==0) 
                	   return GameState.PLAYER_LOST;
                   //bunny on the board but player has more steps
                   else
				       return GameState.IN_PROGRESS;
				}
			//Bunny doesn't exist on the board,player won.
			return GameState.PLAYER_WON;		
		}
		else
			return GameState.IN_PROGRESS;			
	}	
	
	public char[][] getBoardState(){
		return board;
	}
	
	/*
	 * Updates player move
	 */
	public boolean updatePlayerMove(GameBoard.GameMove move) {
		//If out of bounds or move is on the same spot.illegal move 
		if(move.getRow()>8||move.getRow()<0||move.getColumn()<0||move.getColumn()>8 ||this.board[move.getRow()][move.getColumn()]==CatchTheBunny.BoardSigns.PLAYER.getSign())  {
			return false; 
		}
		//if try to go on diagonal.illegal move
		if( !((move.getRow()<8 && board[move.getRow()+1][move.getColumn()]==BoardSigns.PLAYER.getSign())
			||(move.getRow()>0 && board[move.getRow()-1][move.getColumn()]==BoardSigns.PLAYER.getSign())
			||(move.getColumn()<8 && board[move.getRow()][move.getColumn()+1]==BoardSigns.PLAYER.getSign())
			||(move.getColumn()>0 && board[move.getRow()][move.getColumn()-1]==BoardSigns.PLAYER.getSign()))) {
			return false;
		}
		
             
			for(int i=0;i<9;i++) 
				for(int j=0;j<9;j++)
					if(board[i][j]==CatchTheBunny.BoardSigns.PLAYER.getSign())
						board[i][j]=CatchTheBunny.BoardSigns.BLANK.getSign();
					
		//do legal move
		setLastMove(move);
		--max_steps;
		this.board[move.getRow()][move.getColumn()]=CatchTheBunny.BoardSigns.PLAYER.getSign();
		return true;
		}	
	/*
	 * computer 'B' player 'K' .
	 */
	public static enum BoardSigns {
		BLANK('-'),COMPUTER('B'),PLAYER('K');
		char sign;
		BoardSigns(char ch){
			sign=ch;
		}
		public char getSign() {
				return sign;
		}}

}

