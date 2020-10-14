package server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import services.GameServerController;

public class Server implements PropertyChangeListener,Runnable {
	private ServerSocket serverSocket;
	private Socket socket;
	private GameServerController controller=null;
	private ExecutorService executor=null;
	private int port;
	/*
	 * Constructor
	 */
    public Server(int port) {
           this.port=port;
           if(controller==null)//default controller
           controller=new GameServerController(1);
           if(executor==null)//default executor
           executor=Executors.newFixedThreadPool(2);
	}
    /*
     * If default capacity changes set controller and executor whit new sizes(capacity
     */
    public void setController(GameServerController controller) {
		this.controller = controller;
	}
    public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	
	/*
	 * Run server
	 */
	public void run() {
		/*
		 * Wait for clients to connect servers.Create new thread for each client.
		 */
		try {
			serverSocket=new ServerSocket(port);
			while(true) {
			     socket=serverSocket.accept();			     
			     executor.execute(new HandleRequest(socket,controller));//run new client request in new thread
			     //Thread.currentThread().sleep(500);
			}
		}
		catch (Exception e) {
	     e.printStackTrace();   
		}	
	}


	/*
	 * If CLI fires property change (new server command from user) do task.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		
		String command=new String((String)evt.getNewValue());
	
		if(command.equals("START")) {//start server in new thread
			new Thread(this).start();
			System.out.println("Starting server...");
		}
		if(command.equals("SHUTDOWN")){//destroy all threads,and shutdown server
			executor.shutdown();
			System.out.println("Shutdown server...");
		}
		if(command.startsWith("GAME_SERVER_CONFIG")) {//set maximum number of games server can receive.
			int cap=0;
			char[] ch=new char[30];
			
			if(command.length()==18)//if command "GAME SERVER CONFIG" without capacity.default capacity=1.
				cap=1;
			else {    //if command with capacity.	
			ch=command.toCharArray();
			int n=1;
			for(int i=command.indexOf('}')-1;i>command.indexOf('{');i--) { //check capacity number in command {capacity}
				cap+=Character.getNumericValue(ch[i])*n;
				n=n*10;
			}}
			//setCapacity(cap);//
			setController(new GameServerController(cap));//change default capacity(max number of games) for controller.
			setExecutor(Executors.newFixedThreadPool(cap+1));//change capacity for executor pool.+1 for new game that returns ID=-1 and close connection after that.
		}
			
		}
	}


