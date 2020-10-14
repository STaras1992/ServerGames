package view;

public interface View {
	
	public void start();
	public void updateViewNewGame(Character[] board);
	public void updateViewGameMove(int gameState,Character[] board);
  
}
