package games;

import java.util.Random;




public class CatchTheBunnyRandom extends CatchTheBunny {
	/*
	 * Constructor
	 */
	public CatchTheBunnyRandom(int rowLenght,int colLenght,int maxSteps) {
		super(rowLenght, colLenght,maxSteps);
	}
	/*
	 * calculate random move
	 */
	public void calcComputerMove() {
		int rand_col=-1; 
		int rand_row=-1;
		GameMove comp_move=new GameMove();
		/*
		 * find coordinates of computer.
		 */
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++)
				if(board[i][j]==CatchTheBunny.BoardSigns.COMPUTER.getSign()) {
					comp_move=new GameMove(i,j);
					board[i][j]=CatchTheBunny.BoardSigns.BLANK.getSign();
				}
		}
        /*
         * Find random legal move for computer
         */
		while(rand_row<0||rand_row>8||rand_col<0||rand_col>8) {	
			
	    Random random=new Random();
	    //if 0,we change row
	    if((random.nextInt()%2)==0) {
	       rand_row=Math.abs(random.nextInt()%2);
	       rand_col=comp_move.getColumn();
	    
	        if(rand_row==0) 
	    	    rand_row=comp_move.getRow()+1; 
	        else 
	    	    rand_row=comp_move.getRow()-1;
	    }
	    else {//if 1 ,we change column
	    	
	    rand_col=Math.abs(random.nextInt()%2);    
	    rand_row=comp_move.getRow();
	    if(rand_col==0) 
	    	rand_col=comp_move.getColumn()+1; 
	    else 
	    	rand_col=comp_move.getColumn()-1;
	    }
	    //if move is illegal,do the same
	    if((rand_row>0&&rand_row<9&&rand_col>0&&rand_col<9) && board[rand_row][rand_col]==CatchTheBunny.BoardSigns.PLAYER.getSign()) rand_row=-1;
		}
		
		//If move is legal,do the move
		board[rand_row][rand_col]=CatchTheBunny.BoardSigns.COMPUTER.getSign();
		//set move as last move made.
		this.setLastMove(new GameMove(rand_row,rand_col));
		}
//	}
}
