package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import org.json.simple.JSONObject;

public class GamesClient {
	private Socket socket;
	private int serverPort;
	private Scanner server_reader;
	private PrintWriter server_writer;
	/*
	 * Constructor
	 */
	public GamesClient(int serverPort){
		
		this.serverPort=serverPort;
	}
	
    /*
     * Connect to server and initialize scanners
     */
	public void connectToServer() {
		try {
			socket=new Socket(InetAddress.getLocalHost(),serverPort);
			server_reader=new Scanner(socket.getInputStream());
			server_writer=new PrintWriter(socket.getOutputStream(),true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /*
     * Close connections
     */
	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * Send messege to server 
	 */
	public  String sendMessage(String message,boolean hasResponse) {
		
		server_writer.println(message);  
    	server_writer.flush();	
    	
        try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        //if type of request has response wait for response ,else return null.
		if(hasResponse==true) {
			while(!server_reader.hasNextLine()) {} //System.out.println("wait for response");
			String str=server_reader.nextLine();
			return str;
		}
		else 
			return null;	
	}

}
