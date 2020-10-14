package util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import services.GameServerController;



public class CLI implements Runnable {
	private Scanner input;
	private PrintWriter output;
	private PropertyChangeSupport changes;
	/*
	 * Constructor
	 */
	public CLI(InputStream in,OutputStream out) {
		try {
			input=new Scanner(in);
			output=new PrintWriter(out);	
			changes=new PropertyChangeSupport(this);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
    /*
     * run CLI,wait for commands
     */
	public void run() {	
    	System.out.println("Please enter server command");
    	/*
    	 * CLI runs all the time to receive commands from user. 
    	 */
    	while(true) 
    	{
    		if(input.hasNextLine()) { //if received new command 
     			   String command=input.nextLine();
                   if(command.startsWith("GAME_SERVER_CONFIG")||command.equals("START")||command.equals("SHUTDOWN")) {
                    	changes.firePropertyChange(new PropertyChangeEvent(this,"input",null,command));	
                    	writeResponse(command);
                   }			
    		}
    	}	
		
	}
    
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
    	changes.addPropertyChangeListener(pcl);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
    	changes.removePropertyChangeListener(pcl); 	
    }
    /*
     * Write response for command
     */
    public void writeResponse(String command) {

		if(command.startsWith("GAME_SERVER_CONFIG")) {
			output.println("Server configured");
			output.flush();
			return;
		}
		if(command.equals("START")) {
			output.println("Server started");
			output.flush();
			return;
		}
		if(command.equals("SHUTDOWN")){
			output.println("Server is offline!");
			output.flush();
			return;
		}
		output.print("Not a valid command");   	
    }
    
}
