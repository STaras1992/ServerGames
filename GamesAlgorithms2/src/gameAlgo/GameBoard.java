package gameAlgo;

import games.TicTacTow.BoardSigns;

public abstract class GameBoard implements IGameAlgo {
	protected char[][] board;
	/*
	 * Create clear board.. 
	 */
	public GameBoard(int rowLenght,int colLenght) {
		board=new char[rowLenght][colLenght];
		for(int i=0;i<rowLenght;i++)
			for(int j=0;j<rowLenght;j++)
				board[i][j]=BoardSigns.BLANK.getSign();	
		if(rowLenght>3) {//start positions for kid and bunny
			board[0][0]=games.CatchTheBunny.BoardSigns.PLAYER.getSign();
		    board[rowLenght/2][colLenght/2]=games.CatchTheBunny.BoardSigns.COMPUTER.getSign();
		}
	}
	
	public abstract IGameAlgo.GameState getGameState(GameBoard.GameMove move);
	public abstract char[][] getBoardState();
	public abstract boolean updatePlayerMove(GameBoard.GameMove move);
	public abstract void	calcComputerMove();
    /*
     * Coordinates of move ,and score data for min max algorithm
     */
	public static class GameMove {
		private int row;
		private int column;
		private int score;
		
		public GameMove() {
			
		}
		
		public GameMove(int row,int column) {
		this.row=row;
		this.column=column;
	    }
		
		public GameMove(GameMove move) {
			this.row=move.getRow();
			this.column=move.getColumn();
		}
		
		public int getScore() {
			return score;
		}
		
		public void setScore(int sc) {
			this.score=sc;
			
		}
		
		public int getColumn() {
			return this.column;
		}
		
		public int getRow() {
			return this.row;
		}
	}
}
