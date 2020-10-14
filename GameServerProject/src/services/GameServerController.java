package services;

import exeptions.UnknownIdException;
import gameAlgo.GameBoard;
import gameAlgo.IGameAlgo;

public class GameServerController {
	 private GamesService games_service;
	
	public GameServerController(int capacity) {
		games_service=new GamesService(capacity);	
	}
    
	public int getCapacity() {
		return games_service.getCapacity();
	}
    
	public int newGame(String gametype,String opponent) {
		return(games_service.newGame(gametype, opponent));
	}
	
	public void endGame(Integer gameId) throws UnknownIdException{
		games_service.endGame(gameId);
	}
	
	public IGameAlgo.GameState updateMove(Integer gameId,GameBoard.GameMove playerMove) throws UnknownIdException{
		return(games_service.updateMove(gameId,playerMove));
	}
	
	public char[][] computerStartGame(Integer gameId) throws UnknownIdException{
		return(games_service.computerStartGame(gameId));
	}
	
	public char[][] getBoardState(Integer gameId) throws UnknownIdException {
		return(games_service.getBoardState(gameId));	
	}
}
