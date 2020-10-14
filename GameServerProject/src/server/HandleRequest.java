package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import exeptions.UnknownIdException;
import gameAlgo.GameBoard.GameMove;
import gameAlgo.IGameAlgo;
import gameAlgo.IGameAlgo.GameState;
import services.GameServerController;

public class HandleRequest implements Runnable {
	private Socket socket;
	private GameServerController controller;
	private JSONObject messegeFromClient;
	private JSONObject messegeFromServer;
	private String type;
	private Scanner reader;
	private PrintWriter writer;
	
	public HandleRequest(Socket s,GameServerController controller) {
		this.socket=s;
		this.controller=controller;
		try {
			writer=new PrintWriter(socket.getOutputStream());
			reader= new Scanner(socket.getInputStream());
			messegeFromClient=new JSONObject();
			messegeFromServer=new JSONObject();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		/*
		 * While game is running wait for commands from client,when done,interrupt and close connection.
		 */
		while(!Thread.currentThread().isInterrupted()) {
                    //if was action on client side read message 
        			try {
        				if(reader.hasNextLine()) {
						   messegeFromClient =(JSONObject) new JSONParser().parse(reader.nextLine());
        				   type=(String)messegeFromClient.get("type");
        				}
					} catch (Exception e) {
						e.printStackTrace();
					} 

			       
		    /*
		     * for specific type of command read all data and call correct method of controller.
		     * after receive data from controller,create JSON message and send to client.
		     */
			switch (type) {
			
			    case "New-Game":
			    	/*
			    	 * read data from JSON and call method newGame
			    	 */
				    int ID=controller.newGame((String)messegeFromClient.get("game"),(String)messegeFromClient.get("opponent"));
				    /*
				     * write respond JSON to client
				     */
				    messegeFromServer.put("type","New-Game");
				    messegeFromServer.put("ID",ID);
				    
				    //if id=-1 server is full. Send response and close connection.
				    if(ID==-1) {
						writer.println(messegeFromServer.toJSONString());
				        writer.flush();			   
						Thread.currentThread().interrupt();
				    	break;
				    }
				    
				    
				    //server is not full,convert board from char[][] to char[] and send information to client.			    
				    int i=0;
				    char[] chars;
				    
				    if(((String)messegeFromClient.get("game")).equals("Tic Tac Toe"))
				          chars=new char[9];
				    else
				    	chars=new char[81];	
				    
				    try {				    	
				    char[][] charboard=controller.getBoardState(ID);			    
				    
				    for (char[] cs : charboard) 
						for (char ch :cs) {
							chars[i]=ch;						
							++i;
						}					
					messegeFromServer.put("board",new String(chars));					
				    }
			     	catch (UnknownIdException e) {
				    	e.printStackTrace();
				    }
				    
				    //send JSON messege 
					writer.println(messegeFromServer.toJSONString());
			        writer.flush();
			        type=null;
				break;
				
			case "Update-Move":
				
				IGameAlgo.GameState state=null;
				//read JSON and call update move
				try {				
					state=controller.updateMove(Math.toIntExact((long)messegeFromClient.get("ID")),new GameMove(Math.toIntExact((long)messegeFromClient.get("row")),Math.toIntExact((long)messegeFromClient.get("col"))));
				} catch (UnknownIdException e) {
					e.printStackTrace();
				}
				/*
				 * if illegal move write JSON whit board state is null
				 */
				if(state==GameState.ILLEGAL_PLAYER_MOVE)
				 {
					messegeFromServer.put("type","Update-Move");
					messegeFromServer.put("ID",Math.toIntExact((long)messegeFromClient.get("ID")));
					messegeFromServer.put("state",0);
					messegeFromServer.put("board",null);		
				}	
					/*
					 * if returned state is legal move, write JSON whit board states.
					 */
				 else{
						messegeFromServer.put("type","Update-Move");
						messegeFromServer.put("ID",Math.toIntExact((long)messegeFromClient.get("ID")));
						messegeFromServer.put("state",state.ordinal());
						
						//convert board  to char[]
					    i=0;
					    try {
					    char[][] board=controller.getBoardState(Math.toIntExact((long)messegeFromClient.get("ID")));	
					    if(board.length==3)
					          chars=new char[9];
					    else
					    	chars=new char[81];					    
					    				    	
					        for (char[] cs : board) 
							    for (char ch :cs) {
								    chars[i]=ch;						
								    ++i;
							    }	
						messegeFromServer.put("board",new String(chars));
						}catch(UnknownIdException e){
							e.printStackTrace();
						}
					}
				
				writer.println(messegeFromServer.toJSONString());
		        writer.flush();
		        /*
		         * if game state is end of game call "end game" (increment capacity) and  close connection
		         */
		        if(state==GameState.PLAYER_LOST || state==GameState.PLAYER_WON || state==GameState.TIE) {
		        	try {
		        		controller.endGame(Math.toIntExact((long)messegeFromClient.get("ID")));
						Thread.currentThread().interrupt();
					} catch (Exception e) {
						e.printStackTrace();
					}
		        	break;
		        }
		        	
		        type=null;
				break;
				
				
			case "Start-Game":
				/*
				 * Calculate computer move,and write information to JSON.
				 */
				try {
				//write JSON respond
				messegeFromServer.put("type","Start-Game");
				messegeFromServer.put("ID",messegeFromClient.get("ID"));
				
				//convert board  to char[]
			    i=0;		  
			    char[][] board=controller.computerStartGame(Math.toIntExact((long)messegeFromClient.get("ID")));
			    if(board.length==3)
			          chars=new char[9];
			    else
			    	chars=new char[81];					    
			    				    	
			        for (char[] cs : board) 
					    for (char ch :cs) {
						    chars[i]=ch;						
						    ++i;
					    }	
			        
				messegeFromServer.put("board",new String(chars));
				}catch(Exception e) {
					e.printStackTrace();
				}
				//send information
				writer.println(messegeFromServer.toJSONString());
		        writer.flush();
		        type=null;
				break;
				
				
			case "Stop-Game":
				/*
				 * Call end game ,and close connection
				 */
				try {
					controller.endGame(Math.toIntExact((long)messegeFromClient.get("ID")));
					Thread.currentThread().interrupt();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
				
			default:
				System.out.println("TYPE= BAD COMMAND ");
				break;
			}		
        } 
		/*
		 * close connection,and close scanners.
		 */
		try {
			socket.close();
			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
