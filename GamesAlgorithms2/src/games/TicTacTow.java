package games;

import gameAlgo.*;


public abstract class TicTacTow extends GameBoard{
	protected GameMove lastMove;

	/*
	 * Constructors
	 */
	public TicTacTow(int rowLenght,int colLenght) {
		super(rowLenght,colLenght);
		this.lastMove=new GameMove();
	}
	public TicTacTow() {
		super(3,3);
		this.lastMove=new GameMove();
	}
	
	public GameMove getLastMove() {
		return lastMove;
	}
	
	public void setLastMove(GameMove lastMove) {
		this.lastMove =new GameMove(lastMove);
	}
	
	public abstract void calcComputerMove();
	
	
	
	/*
	 * return game status
	 */
	public IGameAlgo.GameState getGameState (GameBoard.GameMove move){
		/*
		 * If last move made by computer,check if computer wins.
		 */
		if(this.board[lastMove.getRow()][lastMove.getColumn()]==BoardSigns.COMPUTER.getSign()) {
			  if(
		    (board[0][0] == BoardSigns.COMPUTER.getSign() && board[0][1] == BoardSigns.COMPUTER.getSign() && board[0][2] == BoardSigns.COMPUTER.getSign()) ||
		    (board[1][0] == BoardSigns.COMPUTER.getSign() && board[1][1] == BoardSigns.COMPUTER.getSign() && board[1][2] == BoardSigns.COMPUTER.getSign()) ||
		    (board[2][0] == BoardSigns.COMPUTER.getSign() && board[2][1] == BoardSigns.COMPUTER.getSign() && board[2][2] == BoardSigns.COMPUTER.getSign()) ||
		    (board[0][0] == BoardSigns.COMPUTER.getSign() && board[1][0] == BoardSigns.COMPUTER.getSign() && board[2][0] == BoardSigns.COMPUTER.getSign()) ||
		    (board[0][1] == BoardSigns.COMPUTER.getSign() && board[1][1] == BoardSigns.COMPUTER.getSign() && board[2][1] == BoardSigns.COMPUTER.getSign()) ||
		    (board[0][2] == BoardSigns.COMPUTER.getSign() && board[1][2] == BoardSigns.COMPUTER.getSign() && board[2][2] == BoardSigns.COMPUTER.getSign()) ||
		    (board[0][0] == BoardSigns.COMPUTER.getSign() && board[1][1] == BoardSigns.COMPUTER.getSign() && board[2][2] == BoardSigns.COMPUTER.getSign()) ||
		    (board[0][2] == BoardSigns.COMPUTER.getSign() && board[1][1] == BoardSigns.COMPUTER.getSign() && board[2][0] == BoardSigns.COMPUTER.getSign())
		    )
				  return GameState.PLAYER_LOST;						  
		}
		/*
		 * If last move made by player,check if player wins.
		 */
		else {
			  if(
		    (board[0][0] == BoardSigns.PLAYER.getSign() && board[0][1] == BoardSigns.PLAYER.getSign() && board[0][2] == BoardSigns.PLAYER.getSign()) ||
		    (board[1][0] == BoardSigns.PLAYER.getSign() && board[1][1] == BoardSigns.PLAYER.getSign() && board[1][2] == BoardSigns.PLAYER.getSign()) ||
		    (board[2][0] == BoardSigns.PLAYER.getSign() && board[2][1] == BoardSigns.PLAYER.getSign() && board[2][2] == BoardSigns.PLAYER.getSign()) ||
		    (board[0][0] == BoardSigns.PLAYER.getSign() && board[1][0] == BoardSigns.PLAYER.getSign() && board[2][0] == BoardSigns.PLAYER.getSign()) ||
		    (board[0][1] == BoardSigns.PLAYER.getSign() && board[1][1] == BoardSigns.PLAYER.getSign() && board[2][1] == BoardSigns.PLAYER.getSign()) ||
		    (board[0][2] == BoardSigns.PLAYER.getSign() && board[1][2] == BoardSigns.PLAYER.getSign() && board[2][2] == BoardSigns.PLAYER.getSign()) ||
		    (board[0][0] == BoardSigns.PLAYER.getSign() && board[1][1] == BoardSigns.PLAYER.getSign() && board[2][2] == BoardSigns.PLAYER.getSign()) ||
		    (board[0][2] == BoardSigns.PLAYER.getSign() && board[1][1] == BoardSigns.PLAYER.getSign() && board[2][0] == BoardSigns.PLAYER.getSign())
		    )
				  return GameState.PLAYER_WON;	
		}
		/*
		 * No one wins,check if in progress or game ends with tie.
		 */
		
		  //check if exist empty spots. 
			for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(board[i][j]==TicTacTow.BoardSigns.BLANK.getSign()) 
				   return GameState.IN_PROGRESS;
			return GameState.TIE;			
	}
	
	/*
	 * return current board status,Player and CPU positions..
	 */
	public char[][] getBoardState(){
		return board;
	}
	
		
	/*
	 * update player move if legal.
	 */
	public boolean updatePlayerMove(GameBoard.GameMove move) {
		
		
		/*
		 * move is illegal,return false.
		 */
		if(move.getRow()>2||move.getRow()<0||move.getColumn()>2||move.getColumn()<0||this.board[move.getRow()][move.getColumn()]!=TicTacTow.BoardSigns.BLANK.getSign()) {
			return false; 
		}
		else {
		/*
		 * legal move,return true.
		 */
		setLastMove(move);
		this.board[move.getRow()][move.getColumn()]=TicTacTow.BoardSigns.PLAYER.getSign();
		return true;
		}
	}

	
	
	
	/*
	 * sign 'O' for Computer.Sign 'X' for player.
	 */
	public static enum BoardSigns {
		BLANK('-'),COMPUTER('O'),PLAYER('X');
		char sign;
		BoardSigns(char ch){
			sign=ch;
		}
		public char getSign() {
				return sign;
		}

	}

}
