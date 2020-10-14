package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GamesModel implements Model{
	private PropertyChangeSupport propertyChangeSupport;
	private GamesClient gamesClient;
	private JSONObject send_message;
	private String last_response;//last message comes from server,string format
	private JSONObject receive_message;//last message comes from server ,JSON format
	private int gameID;
	/*
	 * Constructor
	 */
	public GamesModel(){
		propertyChangeSupport = new PropertyChangeSupport(this);
		gamesClient=new GamesClient(34567);//local host
		send_message=new JSONObject();
		receive_message=new JSONObject();
		last_response=new String();
		gameID=-2;
	}
	/*
	 * Add listeners
	 */
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	/*
	 * Send request for new game
	 */
	public void newGame(String gameType, String opponentType) {
		send_message=new JSONObject();
		
		send_message.put("type","New-Game");
			if(gameType.equals("Catch The Bunny")) 
				send_message.put("game","Catch The Bunny");
			   else 
				   send_message.put("game","Tic Tac Toe");
			
			   if(opponentType.equals("Random"))
				   send_message.put("opponent","Random");
			     else
			    	 send_message.put("opponent","Smart");
			   //Create connection whit server
				   gamesClient=new GamesClient(34567);
			       gamesClient.connectToServer();
			       
            //receive response from server 
			last_response=gamesClient.sendMessage(send_message.toJSONString(),true);			
			try {
				receive_message =(JSONObject) new JSONParser().parse(last_response);
				//if response id=-1 server is full,close connection
				if(Math.toIntExact((long)receive_message.get("ID"))==-1) {
			     	gamesClient.closeConnection();
			     	return;
				}
				
				gameID=Math.toIntExact((long)receive_message.get("ID"));		     	
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this,"response",null,receive_message));
	}

	/*
	 * Update player move request 
	 */
	public  void updatePlayerMove(int row, int col) {	
		
		//write message to server 	
		send_message=new JSONObject();
		send_message.put("type","Update-Move");
		send_message.put("ID",gameID);
		send_message.put("row",row);
		send_message.put("col",col);
		
		//receive response
		last_response=gamesClient.sendMessage(send_message.toJSONString(),true);
		try {
			receive_message =(JSONObject) new JSONParser().parse(last_response);
			
			//if response is end of game(win,lose,tie) close connection
			if(Math.toIntExact((long)receive_message.get("state"))==2 || Math.toIntExact((long)receive_message.get("state"))==3 || Math.toIntExact((long)receive_message.get("state"))==4) {
				gamesClient.closeConnection();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this,"response",null,receive_message));	
	}
	
	/*
	 * Start game request
	 */
	public void startGame() {
		//write message
		send_message=new JSONObject();
		send_message.put("type","Start-Game");
		send_message.put("ID",gameID);
		
		//receive response
		last_response=gamesClient.sendMessage(send_message.toJSONString(),true);
		
		try {
			receive_message =(JSONObject) new JSONParser().parse(last_response);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this,"response",null,receive_message));
		
	}
	/*
	 * Stop game request
	 */
	public void stopGame() {
		send_message=new JSONObject();
		send_message.put("type","Stop-Game");
		send_message.put("ID",gameID);
		
		gamesClient.sendMessage(send_message.toJSONString(),false);
		gamesClient.closeConnection();
	}

}
