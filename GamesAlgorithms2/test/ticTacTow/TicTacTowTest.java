package ticTacTow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gameAlgo.GameBoard.GameMove;
import gameAlgo.IGameAlgo.GameState;
import games.CatchTheBunny.BoardSigns;
import games.TicTacTow;
import games.TicTacTowRandom;
import games.TicTacTowSmart;

class TicTacTowTest {
	
	
    
	@Test
	void test_getBoard() {
		TicTacTow tictac =new TicTacTowRandom(3,3);
		char[][] expected=new char[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				expected[i][j]=TicTacTow.BoardSigns.BLANK.getSign();
		/*
		 * return is not null
		 */
		assertNotNull(tictac.getBoardState());
		/*
		 * return clear board
		 */
		assertArrayEquals(expected, tictac.getBoardState());
		/*
		 * return not empty board
		 */
		tictac.updatePlayerMove(new GameMove(1,1));
		expected[1][1]=TicTacTow.BoardSigns.PLAYER.getSign();
		assertArrayEquals(expected, tictac.getBoardState());		
	}
	
	@Test
	void testIllegalUpdateMove() {
		TicTacTow tic=new TicTacTowRandom(3,3);
		char[][] board=new char[3][3];
		/*
		 * out of the bounds return false test
		 */
		GameMove illegalMove1=new GameMove(-1,1);
		GameMove illegalMove2=new GameMove(1,-1);
		GameMove illegalMove3=new GameMove(4,1);
		GameMove illegalMove4=new GameMove(1,4);
		
		assertFalse(tic.updatePlayerMove(illegalMove1));
		assertFalse(tic.updatePlayerMove(illegalMove2));
		assertFalse(tic.updatePlayerMove(illegalMove3));
		assertFalse(tic.updatePlayerMove(illegalMove4));
		/*
		 * spot is catched by computer return false
		 */
		
		tic.calcComputerMove();
		board=tic.getBoardState();
		int i=0;
		int j=0;
		for(;i<3;i++)
			for(;j<3;j++)
				if(board[i][j]==BoardSigns.COMPUTER.getSign())//find coordinats of spot with computer sign
					break;
		assertFalse(tic.updatePlayerMove(new GameMove(i,j)));
		/*
		 * spot is catched by player return false
		 */
		tic.updatePlayerMove(new GameMove(0,0));
		assertFalse(tic.updatePlayerMove(new GameMove(0,0)));
	}
	
	@Test
	void testLegalUpdateMove() {
		TicTacTow tictac=new TicTacTowRandom(3,3);
		assertTrue(tictac.updatePlayerMove(new GameMove(0,0)));
	}
	
   	@Test
     void testGetState() {
		TicTacTow tictac =new TicTacTowRandom(3,3);
		
		tictac.calcComputerMove();
		
		/*
		 * Player lost
		 */
		tictac=new TicTacTowRandom(3,3);
		//create computer wins situation.
		tictac.calcComputerMove();
		tictac.calcComputerMove();
		tictac.calcComputerMove();
		tictac.calcComputerMove();
		tictac.calcComputerMove();
		tictac.calcComputerMove();
		assertEquals(GameState.PLAYER_LOST,tictac.getGameState(tictac.getLastMove()));
		
		/*
		 * Player won.
		 */
		tictac=new TicTacTowRandom(3,3);
		tictac.updatePlayerMove(new GameMove(0,0));
		tictac.updatePlayerMove(new GameMove(0,1));
		tictac.updatePlayerMove(new GameMove(0,2));
		
		assertEquals(GameState.PLAYER_WON,tictac.getGameState(tictac.getLastMove()));
		/*
		 * Game is in progress
		 */
		tictac=new TicTacTowRandom(3,3);
		tictac.updatePlayerMove(new GameMove(0,2));
		//in progress after player move
		assertEquals(GameState.IN_PROGRESS,tictac.getGameState(tictac.getLastMove()));
		tictac.calcComputerMove();
		//in progress after computer move.
		assertEquals(GameState.IN_PROGRESS,tictac.getGameState(tictac.getLastMove()));
		/*
		 * Create tie situation and check it.
		 */
		tictac=new TicTacTowRandom(3,3);
		tictac.updatePlayerMove(new GameMove(0,0));
		tictac.updatePlayerMove(new GameMove(0,2));
		tictac.updatePlayerMove(new GameMove(1,1));
		tictac.updatePlayerMove(new GameMove(2,0));
		tictac.updatePlayerMove(new GameMove(2,2));
		tictac.calcComputerMove();
		tictac.calcComputerMove();
		tictac.calcComputerMove();
		tictac.calcComputerMove();
		//tie?
		assertEquals(GameState.TIE,tictac.getGameState(tictac.getLastMove()));
		
	}
   	@Test
   	void testCalcComputerMoveRandom() {
   	   TicTacTow tictac=new TicTacTowRandom(3,3);
   	   //create clear board
   	   char[][] originalBoard=new char[3][3];
       boolean notequal=false;
   	   tictac.calcComputerMove();
       //check if board is changed
   	   for(int i=0;i<3;i++)
   		   for(int j=0;j<3;j++)
   			   if(originalBoard[i][j]!=(tictac.getBoardState())[i][j]) {
   				   notequal=true;
   				   originalBoard[i][j]=(tictac.getBoardState())[i][j];
   			   }
   	   assertTrue(notequal);
   	   notequal=false;
   	   //test that board changed with different move;
   	   tictac.calcComputerMove();
   	   
   	   for(int i=0;i<3;i++)
   		   for(int j=0;j<3;j++)
   			   if(originalBoard[i][j]!=(tictac.getBoardState())[i][j]) {
   				   notequal=true;
   			   }
   	   assertTrue(notequal);
   	}
    
   	@Test
   	void testCalcComputerMoveSmart() {
    	   TicTacTow tictac=new TicTacTowSmart(3,3);
       	   GameMove smartMove;
       	   //create situation that computer needs to defend and do smart move.
       	   tictac.updatePlayerMove(new GameMove(0,0));
       	   tictac.updatePlayerMove(new GameMove(1,1));
       	   smartMove=new GameMove(2,2);
       	   tictac.calcComputerMove();
       	   assertNotEquals(smartMove,tictac.getLastMove());
   	}

}


