package games;

public class CatchTheBunnySmart extends CatchTheBunny {
	
	public CatchTheBunnySmart(int rowLenght,int colLenght,int maxSteps) {
		super(rowLenght, colLenght,maxSteps);
	}
	
	
	/*
	 * calculate best computer move.
	 * distance=2:players on diagonal or there is 2 spots between(up,down,left,right).
	 * distance is minimum number of steps between player and computer.Algorith all the time 
	 * do move that keep the bunny on distance at least 2 from player,and also try to stay on the central square 3x3.
	 */
	public void calcComputerMove() {
		int distance;
		GameMove player_move=new GameMove();
		GameMove comp_move=new GameMove();
		
		//find positions of the players
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++) {
				if(board[i][j]==CatchTheBunny.BoardSigns.PLAYER.getSign())
					player_move=new GameMove(i,j);
				if(board[i][j]==CatchTheBunny.BoardSigns.COMPUTER.getSign()) {
					comp_move=new GameMove(i,j);
				}
			}

			/*
			 * find empty spot in square 3x3 where distance from player spot will be at least 2.After that free previous spot.
			 */
		    for(int k=comp_move.getRow()-1;k<=comp_move.getRow()+1;k++)//check left right spot
		    	for(int n=comp_move.getColumn()-1;n<=comp_move.getColumn()+1;n++) {	//up,down spot
		    		
		    		distance=Math.abs((player_move.getRow()-k))+Math.abs(player_move.getColumn()-n);//distance between candidate for 
		    		                                                                                //computer move spot and Player.
		    		
		    		if(board[k][n]==BoardSigns.BLANK.getSign()  && distance>=2 && k>2&&k<6&&n>2&&n<6) {//if spot clear,distance to player at least 2
		    			                                                                               //and spot is not out of bounds (3x3)square.
	
		    			board[k][n]=BoardSigns.COMPUTER.getSign();//do the move		
		    			board[comp_move.getRow()][comp_move.getColumn()]=BoardSigns.BLANK.getSign();//clear previus computer spot
		    			this.setLastMove(new GameMove(k,n));//set as last made move 
		    			return;
		    		}
		    	}
	}

}
