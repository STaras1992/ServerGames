package catchTheBunny;

import static org.junit.jupiter.api.Assertions.*;

import java.security.acl.LastOwnerException;

import org.junit.jupiter.api.Test;

import gameAlgo.GameBoard.GameMove;
import gameAlgo.IGameAlgo.GameState;
import games.CatchTheBunny;
import games.CatchTheBunny.BoardSigns;
import games.CatchTheBunnyRandom;


class CatchTheBunnyTest {

	@Test
	void testGetBoard() {
		CatchTheBunny catchTheBunny =new CatchTheBunnyRandom(9,9,10);
		char[][] expected=new char[9][9];
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				expected[i][j]=CatchTheBunny.BoardSigns.BLANK.getSign();
		expected[0][0]=BoardSigns.PLAYER.getSign();
		expected[4][4]=BoardSigns.COMPUTER.getSign();
		/*
		 * return is not null
		 */
		assertNotNull(catchTheBunny.getBoardState());
		/*
		 * return start board
		 */
		assertArrayEquals(expected, catchTheBunny.getBoardState());
		/*
		 * return not empty board
		 */
		catchTheBunny.updatePlayerMove(new GameMove(0,1));
		expected[0][1]=CatchTheBunny.BoardSigns.PLAYER.getSign();
		expected[0][0]=BoardSigns.BLANK.getSign();
		assertArrayEquals(expected, catchTheBunny.getBoardState());
	}
	
	@Test
	void testIllegalUpdateMove() {
		
	    CatchTheBunny catchthebunny=new CatchTheBunnyRandom(9,9,10);
	    /*
	     * out of the bounds return false test
	    */
	    GameMove illegalMove1=new GameMove(-1,1);
	    GameMove illegalMove2=new GameMove(1,-1);
	    GameMove illegalMove3=new GameMove(9,1);
	    GameMove illegalMove4=new GameMove(1,9);
	
	    assertFalse(catchthebunny.updatePlayerMove(illegalMove1));
	    assertFalse(catchthebunny.updatePlayerMove(illegalMove2));
	    assertFalse(catchthebunny.updatePlayerMove(illegalMove3));
	    assertFalse(catchthebunny.updatePlayerMove(illegalMove4));

	
	/*
	 * spot is catched by player return false
	 */
	
	catchthebunny.updatePlayerMove(new GameMove(0,0));
	assertFalse(catchthebunny.updatePlayerMove(new GameMove(0,0)));
}
	@Test
	void testLegalUpdateMove() {
		CatchTheBunny catchthebunny=new CatchTheBunnyRandom(9, 9, 10);
		assertTrue(catchthebunny.updatePlayerMove(new GameMove(0,1)));
	}
	
	@Test
	void testGetState() {
		CatchTheBunny catchthebunny =new CatchTheBunnyRandom(9, 9, 10);
		/*
		 * game is in progress test
		 */
		catchthebunny.calcComputerMove();
		catchthebunny.updatePlayerMove(new GameMove(0,1));
		assertEquals(GameState.IN_PROGRESS,catchthebunny.getGameState(catchthebunny.getLastMove()));
		/*
		 * Computer is won.Make 10 moves and don't catch the bunny.bunny :[4][4] Kid:[0][0]
		 */
		catchthebunny=new CatchTheBunnyRandom(9,9,10);
        for(int i=1;i<9;i++)
        	catchthebunny.updatePlayerMove(new GameMove(0,i));
        catchthebunny.updatePlayerMove(new GameMove(1,8));
        catchthebunny.updatePlayerMove(new GameMove(2,8));
		assertEquals(GameState.PLAYER_LOST,catchthebunny.getGameState(catchthebunny.getLastMove()));
		/*
		 * Player is won,catch the bunny!bunny :[4][4] Kid:[0][0].
		 */
		catchthebunny=new CatchTheBunnyRandom(9,9,10);
		for(int i=1;i<=4;i++)
		catchthebunny.updatePlayerMove(new GameMove(0,i));
		for(int i=1;i<=4;i++)
		catchthebunny.updatePlayerMove(new GameMove(i,4));//kid=bunny=[4][4]
		
		assertEquals(GameState.PLAYER_WON,catchthebunny.getGameState(catchthebunny.getLastMove()));
        /*
         * no tie option
         */
	}
	
	@Test
	void calcComputerMoveRandom () {
		CatchTheBunny catchthebunny=new CatchTheBunnyRandom(9,9,10);
	   	   //create clear board
	   	   char[][] originalBoard=new char[9][9];
	       boolean notequal=false;
	   	   catchthebunny.calcComputerMove();
	       //check if board is changed
	   	   for(int i=0;i<9;i++)
	   		   for(int j=0;j<9;j++)
	   			   if(originalBoard[i][j]!=(catchthebunny.getBoardState())[i][j]) {
	   				   notequal=true;
	   				   originalBoard[i][j]=(catchthebunny.getBoardState())[i][j];
	   			   }
	   	   assertTrue(notequal);
	   	   notequal=false;
	   	   //test that board changed with different move;
	   	   catchthebunny.calcComputerMove();
	   	   
	   	   for(int i=0;i<9;i++)
	   		   for(int j=0;j<9;j++)
	   			   if(originalBoard[i][j]!=(catchthebunny.getBoardState())[i][j]) {
	   				   notequal=true;
	   			   }
	   	   assertTrue(notequal);
	}

	@Test
	void testCalcComputerMoveSmart() {
		CatchTheBunny catchthebunny=new CatchTheBunnyRandom(9,9,10);
	   	   //create clear board
	   	   char[][] originalBoard=new char[9][9];
	       boolean notequal=false;
	   	   catchthebunny.calcComputerMove();
	       //check if board is changed
	   	   for(int i=0;i<9;i++)
	   		   for(int j=0;j<9;j++)
	   			   if(originalBoard[i][j]!=(catchthebunny.getBoardState())[i][j]) {
	   				   notequal=true;
	   				   originalBoard[i][j]=(catchthebunny.getBoardState())[i][j];
	   			   }
	   	   assertTrue(notequal);
	   	   notequal=false;
	   	   //test that board changed with different move;
	   	   catchthebunny.calcComputerMove();
	   	   
	   	   for(int i=0;i<9;i++)
	   		   for(int j=0;j<9;j++)
	   			   if(originalBoard[i][j]!=(catchthebunny.getBoardState())[i][j]) {
	   				   notequal=true;
	   			   }
	   	   assertTrue(notequal);		
	}
}


