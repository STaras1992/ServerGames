package controller;

import java.beans.PropertyChangeEvent;
import org.json.simple.JSONObject;
import model.Model;
import view.View;

public class GamesController implements Controller {
	
	private Model model;
	private View view;
	
	/*
	 * Constructor
	 */
	public GamesController(Model model,View view) {
		this.model=model;
		this.view=view;		
	}
	
	/*
	 * firePropertyChange was called
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		
		switch (evt.getPropertyName()) {
		
		/*
		 * Model response for View request
		 */
		case "response":
			
			JSONObject message=(JSONObject) evt.getNewValue();
			//If 
			if(Math.toIntExact((long)message.get("ID"))==-1) {
				break;
			}
            String board=(String) message.get("board");
            if(board==null) {          	
            	view.updateViewGameMove(Math.toIntExact((long)message.get("state")),null);
            	break;
            }
            char[] charboard=board.toCharArray();
            Character[] array=new Character[board.length()];
            int i=0;
            for (char ch : charboard) {
				array[i]=ch;
				++i;
			}
            
            
			/*
			 * for each type of response call View method.
			 */
			if(message.get("type").equals("New-Game")) {			
				view.updateViewNewGame(array);
				break;
			}
			if(message.get("type").equals("Update-Move")) {					
				view.updateViewGameMove(Math.toIntExact((long)message.get("state")),array);
				break;
			}
			if(message.get("type").equals("Start-Game")) {					
				view.updateViewGameMove(1,array);
				break;
			}
			
			break;
		/*	
		 * New game request
		 */
		case "new game":
			String str[]=(String[])evt.getNewValue();
		    model.newGame(str[0],str[1]);
		    break;
		/*  
		 * Update move request
		 */
		case "update move":
			int row=Character.getNumericValue((((String)evt.getNewValue()).charAt(0)));//newValue="row,col";
			int col=Character.getNumericValue((((String)evt.getNewValue()).charAt(2)));		
			model.updatePlayerMove(row,col);
			break;
		/*	
		 * Start game request
		 */
		case "start game":
			model.startGame();
			break;
		/*	
		 * Stop game request
		 */
		case "stop game":
			model.stopGame();
			break;
		    
		default:
			break;
		}				
	}		
}
