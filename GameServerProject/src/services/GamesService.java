package services;

import java.util.HashMap;
import java.util.Map;

import exeptions.UnknownIdException;
import gameAlgo.GameBoard;
import gameAlgo.IGameAlgo;
import gameHandler.BoardGameHandler;
import games.CatchTheBunnyRandom;
import games.CatchTheBunnySmart;
import games.TicTacTowRandom;
import games.TicTacTowSmart;

public class GamesService {
	Map<Integer, BoardGameHandler> games;
	private int capacity;
	
	public GamesService(int capacity) {
		games=new HashMap<Integer,BoardGameHandler>();//create hash map whit key that is ID of the specific game,and value is object of BoardGame
		this.capacity=capacity;	//max number of games sever can reeceive
	}
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int newGame(String gametype,String opponent) {
		if(capacity==0) return -1;//if reached max number of games return -1.
		
		//if choose game is Catch the bunny.Add game to the map and decrement capacity.
		if(gametype.equals("Catch The Bunny")) {
			if(opponent.equals("Random"))
		        games.put(capacity--,new BoardGameHandler(new CatchTheBunnyRandom(9,9,15)));
			else 
				games.put(capacity--,new BoardGameHandler(new CatchTheBunnySmart(9,9,15)));
			
		}
		//if TIC TAC
		if(gametype.equals("Tic Tac Toe")) {
			if(opponent.equals("Random"))
				games.put(capacity--,new BoardGameHandler(new TicTacTowRandom(3,3)));
			else
				games.put(capacity--,new BoardGameHandler(new TicTacTowSmart(3,3)));
		}
		return capacity+1;//return ID of the game.
	}
	
	public IGameAlgo.GameState updateMove(Integer gameId,GameBoard.GameMove playerMove) throws UnknownIdException{
		BoardGameHandler game=(BoardGameHandler) games.get(gameId);
		return game.playOneRound(playerMove);
	}
	
	public char[][] getBoardState(Integer gameId) throws UnknownIdException {
		BoardGameHandler game=(BoardGameHandler) games.get(gameId);
		return game.getBoardState();
		
	}
	public char[][] computerStartGame(Integer gameId) throws UnknownIdException{
		BoardGameHandler game=(BoardGameHandler) games.get(gameId);
		return game.computerStartGame();
	}
	
	public void endGame(Integer gameId) throws UnknownIdException{
		games.remove(gameId);
		++capacity;//if some game ended increment capacity,we can add new game.
	}

}
