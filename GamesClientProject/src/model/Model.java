package model;

public interface Model {
	
	public void newGame(String gameType,String opponentType) ;
	public void updatePlayerMove(int row,int col);
	public void startGame();
	public void stopGame();
}
