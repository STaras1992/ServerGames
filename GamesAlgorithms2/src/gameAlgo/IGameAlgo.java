package gameAlgo;

public interface IGameAlgo {
	
	public IGameAlgo.GameState getGameState(GameBoard.GameMove move);
	public char[][] getBoardState();
	public boolean updatePlayerMove(GameBoard.GameMove move);
	public void	calcComputerMove();
	
	public static enum GameState {
		ILLEGAL_PLAYER_MOVE, 
		IN_PROGRESS, 
		PLAYER_LOST, 
		PLAYER_WON, 
		TIE; 
	}
    
}
