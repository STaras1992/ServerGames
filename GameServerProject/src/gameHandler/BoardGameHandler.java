package gameHandler;

import gameAlgo.GameBoard;
import gameAlgo.GameBoard.GameMove;
import gameAlgo.IGameAlgo;
import gameAlgo.IGameAlgo.GameState;

public class BoardGameHandler {
	
	private IGameAlgo member;
	public BoardGameHandler(IGameAlgo game) {
		this.member=game;
	}
	public char[][] computerStartGame(){
		member.calcComputerMove();
		return member.getBoardState();
		
	}
	
	public char[][] getBoardState(){
		return member.getBoardState();
		
	}
	public IGameAlgo.GameState playOneRound(GameMove playerMove){
		if(!member.updatePlayerMove(playerMove))
			return GameState.ILLEGAL_PLAYER_MOVE;//if update move return false
		if(!(member.getGameState(playerMove)).equals(GameState.IN_PROGRESS))
			return member.getGameState(playerMove);		
		member.calcComputerMove();
		return member.getGameState(playerMove);
	}
	

}
