package games;

import java.util.Random;

public class TicTacTowRandom extends TicTacTow {
	/*
	 * Constructors
	 */
	public TicTacTowRandom(int rowLenght, int colLenght) {
		super(rowLenght,colLenght);
	}
	public TicTacTowRandom() {
		super(3,3);
	}
  /*
   * random legal computer move calculation.
   */
	public void calcComputerMove() {
	    Random random=new Random();
	    //find to random numbers 0-2
	    int rand1=Math.abs(random.nextInt()%3);
	    int rand2=Math.abs(random.nextInt()%3);
	    //while move coordinates is illegal change random numbers
	    while(board[rand1][rand2]==TicTacTow.BoardSigns.PLAYER.getSign()||board[rand1][rand2]==TicTacTow.BoardSigns.COMPUTER.getSign()) {
		    rand1=Math.abs(random.nextInt()%3);
		    rand2=Math.abs(random.nextInt()%3);    
	    }
	    //move is legal,set last move and do the move .
	    setLastMove(new GameMove(rand1,rand2));
	    board[rand1][rand2]=TicTacTow.BoardSigns.COMPUTER.getSign();
	}
}

