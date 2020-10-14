package gameAlgo;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;





/*
 * FOR TESTS
 */

public class Main {
	
}



	
//	public static void main(String[] args) {
//        System.out.println("Choose game: Enter '1' for TicTacTow, '2' for CatchTheBunny...");
//        Scanner scan=new Scanner(System.in);
//        int x=scan.nextInt();
//		
//		if(x==2) {
//		//CATCH THE BUNNY TEST
//		CatchTheBunny catch_the_bunny;
//		System.out.println("For random enter '1',for smart enter '2'..");
//		int y=scan.nextInt();
//		if(y==1)
//			catch_the_bunny=new CatchTheBunnyRandom(9,9,20);
//		else
//		    catch_the_bunny=new CatchTheBunnySmart(9,9,20); 
//		catch_the_bunny.getBoardState();
//		Scanner sc=new Scanner(System.in);
//	    int step_row=0;
//	    int temp_step_row=0;
//	    int step_col=0;
//	    int temp_step_col=0;
//	    char input;
//		System.out.println("Please enter row of your first step:");
//		step_row=sc.nextInt();
//		temp_step_row=step_row;
//		System.out.println("Please enter column of your first step:");
//		step_col=sc.nextInt();
//		temp_step_col=step_col;
//		GameBoard.GameMove move=new GameBoard.GameMove(step_row,step_col);
//		catch_the_bunny.updatePlayerMove(move);
//		catch_the_bunny.calcComputerMove();
//		catch_the_bunny.getBoardState();
//		while (true)
//		{
//			System.out.println("Please enter W for move up,S for move down,A for move left,D for move right:");
//			input=sc.next().charAt(0);
//			if(input=='w'||input=='W') 
//				temp_step_row=step_row-1;
//			else 
//			if(input=='s'||input=='S') {
//				temp_step_row=step_row+1;
//			}
//			else 
//			if(input=='a'||input=='A') {
//				temp_step_col=step_col-1;
//			}
//			else 
//			if(input=='d'||input=='D') {
//				temp_step_col=step_col+1;
//			}
//			move = new GameBoard.GameMove(temp_step_row,temp_step_col);
//			//if(catch_the_bunny.updatePlayerMove(move))
//			if(catch_the_bunny.getGameState(move)==GameState.IN_PROGRESS)
//			{
//                catch_the_bunny.updatePlayerMove(move);
//				catch_the_bunny.board[step_row][step_col]=BoardSigns.BLANK.getSign();
//				catch_the_bunny.calcComputerMove();
//				
//				step_row=temp_step_row;
//				step_col=temp_step_col;
//			}
//			else {
//		if(catch_the_bunny.getGameState(move)==GameState.ILLEGAL_PLAYER_MOVE) {
//			System.out.println("Illegal player move");
//			temp_step_row=step_row;
//			temp_step_col=step_col;
//		    continue;
//		}
//		if(catch_the_bunny.getGameState(move)==GameState.PLAYER_LOST) {
//		    System.out.println("Player lost");
//		    break;
//		}
//		System.out.println("Player won");
//		break;
//			}
//			catch_the_bunny.getBoardState();
//		}
//				sc.close();
//		}
//		else {
//				
////		TICTAC TEST
//			TicTacTow tictac;
//			System.out.println("For random enter '1',for smart enter '2'..");
//			int y=scan.nextInt();
//			if(y==1)
//				tictac=new TicTacTowRandom(3,3);
//			else
//				tictac=new TicTacTowSmart(3,3); 
//		tictac.getBoardState();
//		System.out.println("Player sign is:"+TicTacTow.BoardSigns.PLAYER.getSign());
//	    System.out.println("Computer sign is:"+TicTacTow.BoardSigns.COMPUTER.getSign());
//	    Scanner sc=new Scanner(System.in);
//	    int step_row;
//	    int step_col;
//		GameBoard.GameMove move=new GameBoard.GameMove();
//		while(true) {
//		System.out.println("Please enter row of your step:");
//		step_row=sc.nextInt();
//		System.out.println("Please enter column of your step:");
//		step_col=sc.nextInt();
//		move=new GameBoard.GameMove(step_row,step_col);
//		if(tictac.getGameState(move)==GameState.ILLEGAL_PLAYER_MOVE) {
//			System.out.println("Illegal player move");
//			continue;
//		}
//		if(tictac.getGameState(move)==GameState.IN_PROGRESS) {
//			System.out.println("In progress");
//			tictac.updatePlayerMove(move);
//			tictac.calcComputerMove();
//			if(tictac.getGameState(move)==GameState.PLAYER_LOST) {
//				System.out.println("PLAYER LOSS");
//				break;
//			}
//				
//			tictac.getBoardState();
//		}
//		else {
//			System.out.println(tictac.getGameState(move).toString());
//			break;
//		}
//		}
//		sc.close();	
//	}
//		scan.close();
//	}}

//}


//public class Client implements Runnable {
//
//	private Socket socket;
//	private Scanner client_reader;
//	private Scanner server_reader;
//	private PrintWriter server_writer;
//	JSONObject messege;
//	
//	public Client() {
//		messege = new JSONObject();
//		try {
//			socket=new Socket(InetAddress.getLocalHost(),34567);
//			client_reader=new Scanner(System.in);
//			server_reader=new Scanner(socket.getInputStream());
//			server_writer=new PrintWriter(socket.getOutputStream(),true);	
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//	}
//	
//	public static void main(String[] args) {
//		   Client client=new Client();
//		   new Thread(client,"CLIENT").start();
//		   
//		}
//
//	@Override
//	public void run() {
//		
//		System.out.println(Thread.currentThread().getName()+" run");
//	  
//	  messege.put("type","New-Game");
//      System.out.println("Choose game: Enter '1' for TicTacTow, '2' for CatchTheBunny...");
//      Scanner scan=new Scanner(System.in);
//      int x=scan.nextInt();
//		if(x==2) {
//		   messege.put("game","Catch The Bunny");
//		   System.out.println("For random enter '1',for smart enter '2'..");
//		   int y=scan.nextInt();
//		   if(y==1)
//			     messege.put("opponent","Random");
//		     else
//		    	 messege.put("opponent","Smart");
//		}
//		else {
//			messege.put("game","Tic Tac Toe");
//			System.out.println("For random enter '1',for smart enter '2'..");
//			int y=scan.nextInt();
//			if(y==1)
//				messege.put("opponent","Random");
//			else
//				messege.put("opponent","Smart");
//		}
//		server_writer.print(messege.toJSONString());
//        server_writer.flush();
//        System.out.println("messege sent:"+messege.toJSONString());   
//        try {
//			Thread.currentThread();
//			Thread.sleep(3000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}  
//       	
//        messege=receiveMessege();
//        
//        System.out.println("Messege from server received");
//        
//	 try {
//		socket.close();
//		client_reader.close();
//		server_reader.close();
//		server_writer.close();
//		scan.close();
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	
//}
//	private synchronized JSONObject receiveMessege() {
//		JSONObject mesg=null;
//		while(!server_reader.hasNext());
//		try {
//		mesg =(JSONObject) new JSONParser().parse (server_reader.nextLine());
//	} catch (ParseException e) {
//		e.printStackTrace();
//	}
//	return mesg;	
//	}
//
//}

