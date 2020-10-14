package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class GamesView implements View,ActionListener{
    private PropertyChangeSupport property;
    private String[] new_game_parametrs;
    private JButton last_move=null;       //last button on the board was clicked.
    private JButton[] tictac_buttns;      //tictac board.
    private JButton[] catchTheBunny_buttns;//catch the bunny board.
    private JFrame tictacFrame=null;
    private JFrame catchTneBunnyFrame=null;
    private JFrame mainMenuFrame=null;
    private RunningGame currentGame;
    private boolean fullFlag;//true if server is full(max number of games).
	/*
	 * Constructor
	 */
	public GamesView() {
		property=new PropertyChangeSupport(this);
		new_game_parametrs=new String[2];
		catchTheBunny_buttns=new JButton[81];	
		fullFlag=false;
		currentGame=RunningGame.NO_GAME;
	}
	
	/*
	 * View starts his work
	 */
	public void start() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGui();
				
			}
		});
		
	}
    /*
     * Add listeners ,in our case Controller.
     */
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		property.addPropertyChangeListener(propertyChangeListener);
	}
    
    /*
     * User (client) clicked on the board.
     */
	public void actionPerformed(ActionEvent e) {
		JButton button=(JButton)(e.getSource());
		
		if(currentGame==RunningGame.TIC_TAC_TOE) {
		    button.setText("X");
		    button.removeActionListener(this); 
		}
		else {
			button.setText("K");
			last_move=button;
		}
		property.firePropertyChange(new PropertyChangeEvent(this,"update move",null,e.getActionCommand()));
		
	}
	
	@Override
	public void updateViewNewGame(Character[] board) {
		   fullFlag=false;//server is not full
		   
		   if(board.length==9) {
           createTicTacGui();
           currentGame=RunningGame.TIC_TAC_TOE;
           for (int i = 0; i < board.length; i++) 
        	   if(board[i].equals('X'))
        		   tictac_buttns[i].setText("X");
        	   else
        		   if(board[i].equals('O')) {
        			   tictac_buttns[i].setText("O"); 
        			   tictac_buttns[i].removeActionListener(this);
        		   }
		   }
		   else {
			   createCatchTheBunnyGui();
			   currentGame=RunningGame.CATCH_THE_BUNNY;
	           for (int i = 0; i < board.length; i++) {
	        	   if(board[i].equals('K')) {
	        		       catchTheBunny_buttns[i].setText("K");	        		       
	        	   }
	        	   if(board[i].equals('B'))
	        			   catchTheBunny_buttns[i].setText("B"); 
	        	   if(board[i].equals('-'))
	        		       catchTheBunny_buttns[i].setText(" "); 	        		  
	           }		   
		   }
	}

	@Override
	public void updateViewGameMove(int gameState, Character[] board) {
		
		switch (gameState) {
		/*
		 * Illegal move
		 */
		case 0:
			if(currentGame==RunningGame.TIC_TAC_TOE)
			  JOptionPane.showMessageDialog(tictacFrame,"ILLEGAL MOVE");
			else {
				if(last_move.getText().equals(" "))
					last_move.setText("B");
				else {
				int count=0;
				for (int j=0;j<81;j++) 
					if(catchTheBunny_buttns[j].getText().equals("K")) {
						++count;
						if(count==2) {
							last_move.setText(" ");
							break;
						}					
					}	
				}			
				JOptionPane.showMessageDialog(catchTneBunnyFrame,"ILLEGAL MOVE");
			}          
			break;
        /*
         * Game is in progress
         */
		case 1:
			if(board.length==9) {
	        for (int i = 0; i < board.length; i++) 
	      	   if(board[i].equals('X')) {
	      		   tictac_buttns[i].setText("X");
	      		   last_move=tictac_buttns[i];
	      	   }
	      	   else
	      		   if(board[i].equals('O')) {
	      			   tictac_buttns[i].setText("O"); 
	      			   tictac_buttns[i].removeActionListener(this);
	      		   }
			}else {
		           for (int i = 0; i < board.length; i++) {
		        	   if(board[i].equals('K')) {
		        		       last_move=catchTheBunny_buttns[i];
		        		       catchTheBunny_buttns[i].setText("K");	        		       
		        	   }
		        	   if(board[i].equals('B'))
		        			   catchTheBunny_buttns[i].setText("B"); 
		        	   if(board[i].equals('-'))
		        		       catchTheBunny_buttns[i].setText(" "); 	        		  
		           }
			}
	        break;
	        
       /*
        * Player lost
	    */
		case 2:
			if(board.length==9) {
	        for (int i = 0; i < board.length; i++) 
		      	   if(board[i].equals('X')) {
		      		   tictac_buttns[i].setText("X");
		      		   last_move=tictac_buttns[i];
		      	   }
		      	   else
		      		   if(board[i].equals('O'))
		      			   tictac_buttns[i].setText("O"); 
			JOptionPane.showMessageDialog(tictacFrame,"You lost!!!");
			tictacFrame.dispose();
			last_move=null;
			currentGame=RunningGame.NO_GAME;
			}else {
		           for (int i = 0; i < board.length; i++) {
		        	   if(board[i].equals('K')) {
		        		       catchTheBunny_buttns[i].setText("K");	        		       
		        	   }
		        	   if(board[i].equals('B'))
		        			   catchTheBunny_buttns[i].setText("B"); 
		        	   if(board[i].equals('-'))
		        		       catchTheBunny_buttns[i].setText(" "); 	        		  
		           }
					JOptionPane.showMessageDialog(catchTneBunnyFrame,"You lost!!!");
					catchTneBunnyFrame.dispose();
					last_move=null;
					currentGame=RunningGame.NO_GAME;
			}
		      		   
			break;
		/*
		 * 	Player won
		 */
		case 3:
			if(board.length==9) {
		        for (int i = 0; i < board.length; i++) 
			      	   if(board[i].equals('X')) {
			      		   tictac_buttns[i].setText("X");
			      		   last_move=tictac_buttns[i];
			      	   }
			      	   else
			      		   if(board[i].equals('O'))
			      			   tictac_buttns[i].setText("O"); 
				JOptionPane.showMessageDialog(tictacFrame,"You Won!!!!!!");
				tictacFrame.dispose();
				last_move=null;
				currentGame=RunningGame.NO_GAME;
				}else {
			           for (int i = 0; i < board.length; i++) {
			        	   if(board[i].equals('K')) {
			        		       catchTheBunny_buttns[i].setText("K");	        		       
			        	   }
			        	   if(board[i].equals('B'))
			        			   catchTheBunny_buttns[i].setText("B"); 
			        	   if(board[i].equals('-'))
			        		       catchTheBunny_buttns[i].setText(" "); 	        		  
			           }
						JOptionPane.showMessageDialog(catchTneBunnyFrame,"You Won!!!!!!");
						catchTneBunnyFrame.dispose();
						last_move=null;
						currentGame=RunningGame.NO_GAME;
				}

			break;
		/*
		 * TIE	
		 */
		case 4:
			if(board.length==9) {
		        for (int i = 0; i < board.length; i++) 
			      	   if(board[i].equals('X')) {
			      		   tictac_buttns[i].setText("X");
			      		   last_move=tictac_buttns[i];
			      	   }
			      	   else
			      		   if(board[i].equals('O'))
			      			   tictac_buttns[i].setText("O"); 
				JOptionPane.showMessageDialog(tictacFrame,"TIE!!!!!!");
				tictacFrame.dispose();
				last_move=null;
				currentGame=RunningGame.NO_GAME;
				}else {
			           for (int i = 0; i < board.length; i++) {
			        	   if(board[i].equals('K')) {
			        		       catchTheBunny_buttns[i].setText("K");	        		       
			        	   }
			        	   if(board[i].equals('B'))
			        			   catchTheBunny_buttns[i].setText("B"); 
			        	   if(board[i].equals('-'))
			        		       catchTheBunny_buttns[i].setText(" "); 	        		  
			           }
						JOptionPane.showMessageDialog(catchTneBunnyFrame,"TIE!!!!!!");
						catchTneBunnyFrame.dispose();
						last_move=null;
						currentGame=RunningGame.NO_GAME;
				}
			break;
			
		default:
			break;
		}

	}
	
	protected void createGui() {
		
		mainMenuFrame=new JFrame("Main");
		mainMenuFrame.setFont(new Font("Arial", Font.BOLD, 18));
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuFrame.setBounds(300, 50, 1280, 900);
		JPanel contentPane = (JPanel) mainMenuFrame.getContentPane();
		contentPane.setBackground(new Color(255, 255, 240));
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		mainMenuFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBackground(new Color(175, 238, 238));
		panel.setBounds(0, 0, 287, 844);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(GamesView.class.getResource("/x_red2.png")));
		lblNewLabel.setBounds(0, 136, 287, 229);
		panel.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(0, 515, 287, 229);
		panel.add(label_1);
		label_1.setIcon(new ImageIcon(GamesView.class.getResource("/o_red.png")));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(175, 238, 238));
		panel_1.setBounds(971, 0, 287, 844);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(GamesView.class.getResource("/bunny2.png")));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(62, 500, 167, 229);
		panel_1.add(label_2);
		
		JLabel label = new JLabel("");
		label.setBackground(new Color(175, 238, 238));
		label.setBounds(0, 93, 287, 256);
		panel_1.add(label);
		label.setIcon(new ImageIcon(GamesView.class.getResource("/boy1.png")));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel labelChooseGame = new JLabel("CHOOSE GAME");
		labelChooseGame.setHorizontalAlignment(SwingConstants.CENTER);
		labelChooseGame.setForeground(Color.RED);
		labelChooseGame.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 40));
		labelChooseGame.setBounds(376, 85, 472, 51);
		contentPane.add(labelChooseGame);
		
		JLabel lblChooseOpponent = new JLabel("CHOOSE OPPONENT");
		lblChooseOpponent.setHorizontalAlignment(SwingConstants.CENTER);
		lblChooseOpponent.setForeground(Color.RED);
		lblChooseOpponent.setFont(new Font("Gill Sans Ultra Bold", Font.BOLD, 40));
		lblChooseOpponent.setBounds(363, 422, 522, 51);
		contentPane.add(lblChooseOpponent);
		
		JRadioButton choose_tictac = new JRadioButton("Tic Tac Toe");
		choose_tictac.setSelected(true);
		choose_tictac.setHorizontalAlignment(SwingConstants.LEFT);
		choose_tictac.setFont(new Font("Arial Black", Font.BOLD, 25));
		choose_tictac.setBackground(new Color(255, 255, 240));
		choose_tictac.setBounds(480, 217, 241, 29);
		contentPane.add(choose_tictac);
		
		JRadioButton choose_catchbunny = new JRadioButton("Catch The Bunny");
		choose_catchbunny.setHorizontalAlignment(SwingConstants.LEFT);
		choose_catchbunny.setFont(new Font("Arial Black", Font.BOLD, 25));
		choose_catchbunny.setBackground(new Color(255, 255, 240));
		choose_catchbunny.setBounds(480, 278, 332, 29);
		contentPane.add(choose_catchbunny);
		
		JRadioButton choose_random = new JRadioButton("Random");
		choose_random.setSelected(true);
		choose_random.setHorizontalAlignment(SwingConstants.LEFT);
		choose_random.setFont(new Font("Arial Black", Font.BOLD, 25));
		choose_random.setBackground(new Color(255, 255, 240));
		choose_random.setBounds(480, 546, 199, 29);
		contentPane.add(choose_random);
		
		JRadioButton choose_smart = new JRadioButton("Smart");
		choose_smart.setHorizontalAlignment(SwingConstants.LEFT);
		choose_smart.setFont(new Font("Arial Black", Font.BOLD, 25));
		choose_smart.setBackground(new Color(255, 255, 240));
		choose_smart.setBounds(480, 604, 180, 29);
		contentPane.add(choose_smart);
		
		ButtonGroup game_type=new ButtonGroup();
		ButtonGroup opponent_type=new ButtonGroup();
		game_type.add(choose_tictac);
		game_type.add(choose_catchbunny);
		opponent_type.add(choose_smart);
		opponent_type.add(choose_random);
		
		
		JButton start_button = new JButton("start game");	
		start_button.setBackground(UIManager.getColor("Button.focus"));
		start_button.setForeground(new Color(0, 0, 128));
		start_button.setFont(new Font("Arial", Font.BOLD, 28));
		start_button.setBounds(507, 724, 214, 61);
		contentPane.add(start_button);
		/*
		 * Start button was clicked,get status of radio buttons (type of game,opponent type) and send information to server.
		 */
		start_button.addActionListener(new ActionListener() {
            
		    public void actionPerformed(ActionEvent arg0) {	
		    	//if current client already started some game
		    	if(currentGame!=RunningGame.NO_GAME) {
		    		JOptionPane.showMessageDialog(mainMenuFrame,"Sorry finish previus game to start new one!");
		    		return;
		    	}
		    	
		    	//if there is no game running.
				if(choose_tictac.isSelected()) { 
					new_game_parametrs[0]=choose_tictac.getText();
					currentGame=RunningGame.TIC_TAC_TOE;
				}
				else {
					new_game_parametrs[0]=choose_catchbunny.getText();
					currentGame=RunningGame.CATCH_THE_BUNNY;
				}
				if(choose_random.isSelected()) 
					new_game_parametrs[1]=choose_random.getText();
				else
					new_game_parametrs[1]=choose_smart.getText();
				
				fullFlag=true;
				property.firePropertyChange(new PropertyChangeEvent(this,"new game",null,new_game_parametrs));
				if(fullFlag) {//if flag stay true so server capacity is 0. 
					currentGame=RunningGame.NO_GAME;
					JOptionPane.showMessageDialog(mainMenuFrame,"Sorry server has reached maximum number of games");
				}
			}
	});
		
		mainMenuFrame.setVisible(true);
	}
	
	
	
	

	protected void createTicTacGui(){
		
		tictac_buttns=new JButton[9];
		tictacFrame=new JFrame();

		tictacFrame.setBounds(450, 10, 1300, 1000);
		JPanel contentPane =(JPanel) tictacFrame.getContentPane();
		contentPane.setBackground(new Color(34, 139, 34));
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tictacFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 0, 255));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel.setBounds(92, 228, 771, 656);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton button0 = new JButton("");
		button0.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 99));
		button0.setBackground(Color.WHITE);
		button0.setBounds(0, 0, 258, 220);
		button0.setActionCommand("0,0");
		button0.addActionListener(this);
		panel.add(button0);
		tictac_buttns[0]=button0;
		
		JButton button1 = new JButton("");
		button1.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 99));
		button1.setBackground(Color.WHITE);
		button1.setBounds(256, 0, 258, 220);
		button1.setActionCommand("0,1");
		panel.add(button1);
		button1.addActionListener(this);
		tictac_buttns[1]=button1;
		
		JButton button2 = new JButton("");
		button2.setBackground(Color.WHITE);
		button2.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 99));
		button2.setBounds(513, 0, 258, 220);
		button2.setActionCommand("0,2");
		panel.add(button2);
		button2.addActionListener(this);
		tictac_buttns[2]=button2;
		
		JButton button3 = new JButton("");
		button3.setBackground(Color.WHITE);
		button3.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 99));
		button3.setBounds(0, 218, 258, 220);
		button3.setActionCommand("1,0");
		panel.add(button3);
		button3.addActionListener(this);
		tictac_buttns[3]=button3;
		
		JButton button4 = new JButton("");
		button4.setBackground(Color.WHITE);
		button4.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 99));
		button4.setBounds(256, 218, 258, 220);
		button4.setActionCommand("1,1");
		panel.add(button4);
		button4.addActionListener(this);
		tictac_buttns[4]=button4;
		
		JButton button5 = new JButton("");
		button5.setBackground(Color.WHITE);
		button5.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 99));
		button5.setBounds(513, 218, 258, 220);
		button5.setActionCommand("1,2");
		panel.add(button5);
		button5.addActionListener(this);
		tictac_buttns[5]=button5;
		
		JButton button6 = new JButton("");
		button6.setBackground(Color.WHITE);
		button6.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 99));
		button6.setBounds(0, 436, 258, 220);
		button6.setActionCommand("2,0");
		panel.add(button6);
		button6.addActionListener(this);
		tictac_buttns[6]=button6;
		
		JButton button7 = new JButton("");
		button7.setBackground(Color.WHITE);
		button7.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 99));
		button7.setBounds(256, 436, 258, 220);
		button7.setActionCommand("2,1");
		panel.add(button7);
		button7.addActionListener(this);
		tictac_buttns[7]=button7;
		
		JButton button8 = new JButton("");
		button8.setBackground(Color.WHITE);
		button8.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 99));
		button8.setBounds(513, 436, 258, 220);
		button8.setActionCommand("2,2");		
		panel.add(button8);
		button8.addActionListener(this);
		tictac_buttns[8]=button8;
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 215, 0));
		panel_1.setBounds(0, 0, 1278, 178);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
				
		JLabel lblNewLabel = new JLabel("Tic Tac Toe");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Berlin Sans FB", Font.BOLD | Font.ITALIC, 70));
		lblNewLabel.setBounds(99, 16, 777, 120);
		panel_1.add(lblNewLabel);
		
		JButton btnStopGame = new JButton("stop game");
		btnStopGame.setBackground(new Color(0, 255, 255));
		btnStopGame.setFont(new Font("Arial Narrow", Font.PLAIN, 35));
		btnStopGame.setBounds(944, 799, 280, 71);
		contentPane.add(btnStopGame);
		
		JButton btnStartGame = new JButton("start game");
		btnStartGame.setFont(new Font("Arial Narrow", Font.PLAIN, 35));
		btnStartGame.setBackground(Color.CYAN);
		btnStartGame.setBounds(944, 680, 280, 71);
		contentPane.add(btnStartGame);
		
		btnStartGame.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(last_move==null)
				    property.firePropertyChange(new PropertyChangeEvent(this,"start game",null,null));
				else {
					JOptionPane.showMessageDialog(tictacFrame,"Game already started");			
				}
			}
		});
		
		btnStopGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				property.firePropertyChange(new PropertyChangeEvent(this,"stop game",null,null));
				tictacFrame.dispose();
				last_move=null;
				currentGame=RunningGame.NO_GAME;
			}
		});
		
		tictacFrame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				property.firePropertyChange(new PropertyChangeEvent(this,"stop game",null,null));
				tictacFrame.dispose();
				last_move=null;
				currentGame=RunningGame.NO_GAME;
			}
		});
		
		
		tictacFrame.setVisible(true);
	}
	
	protected void createCatchTheBunnyGui() {
		
		catchTheBunny_buttns=new JButton[81];
		catchTneBunnyFrame=new JFrame();

		catchTneBunnyFrame.setBounds(300,0, 1400,1030);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		catchTneBunnyFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(60, 179, 113));
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel.setBounds(94, 95, 884, 879);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton button0 = new JButton("");
		button0.setFont(new Font("Tahoma", Font.BOLD, 40));
		button0.setBackground(new Color(255, 248, 220));
		button0.setBounds(0, 0, 100, 100);
		panel.add(button0);
		button0.addActionListener(this);
		button0.setActionCommand("0,0");
		catchTheBunny_buttns[0]=button0;
		
		JButton button1 = new JButton("");
		button1.setFont(new Font("Tahoma", Font.BOLD, 40));
		button1.setBackground(new Color(255, 248, 220));
		button1.setBounds(98, 0, 100, 100);
		panel.add(button1);
		button1.addActionListener(this);
		button1.setActionCommand("0,1");
		catchTheBunny_buttns[1]=button1;
		
		JButton button2 = new JButton("");
		button2.setFont(new Font("Tahoma", Font.BOLD, 40));
		button2.setBackground(new Color(255, 248, 220));
		button2.setBounds(196, 0, 100, 100);
		panel.add(button2);
		button2.addActionListener(this);
		button2.setActionCommand("0,2");
		catchTheBunny_buttns[2]=button2;
		
		JButton button3 = new JButton("");
		button3.setFont(new Font("Tahoma", Font.BOLD, 40));
		button3.setBackground(new Color(255, 248, 220));
		button3.setBounds(294, 0, 100, 100);
		panel.add(button3);
		button3.addActionListener(this);
		button3.setActionCommand("0,3");
		catchTheBunny_buttns[3]=button3;
		
		JButton button4 = new JButton("");
		button4.setFont(new Font("Tahoma", Font.BOLD, 40));
		button4.setBackground(new Color(255, 248, 220));
		button4.setBounds(392, 0, 100, 100);
		panel.add(button4);
		button4.addActionListener(this);
		button4.setActionCommand("0,4");
		catchTheBunny_buttns[4]=button4;
		
		JButton button5 = new JButton("");
		button5.setFont(new Font("Tahoma", Font.BOLD, 40));
		button5.setBackground(new Color(255, 248, 220));
		button5.setBounds(490, 0, 100, 100);
		panel.add(button5);
		button5.addActionListener(this);
		button5.setActionCommand("0,5");
		catchTheBunny_buttns[5]=button5;
		
		JButton button6 = new JButton("");
		button6.setFont(new Font("Tahoma", Font.BOLD, 40));
		button6.setBackground(new Color(255, 248, 220));
		button6.setBounds(588, 0, 100, 100);
		panel.add(button6);
		button6.addActionListener(this);
		button6.setActionCommand("0,6");
		catchTheBunny_buttns[6]=button6;
		
		JButton button7 = new JButton("");
		button7.setFont(new Font("Tahoma", Font.BOLD, 40));
		button7.setBackground(new Color(255, 248, 220));
		button7.setBounds(686, 0, 100, 100);
		panel.add(button7);
		button7.addActionListener(this);
		button7.setActionCommand("0,7");
		catchTheBunny_buttns[7]=button7;
		
		JButton button8 = new JButton("");
		button8.setFont(new Font("Tahoma", Font.BOLD, 40));
		button8.setBackground(new Color(255, 248, 220));
		button8.setBounds(784, 0, 100, 100);
		panel.add(button8);
		button8.addActionListener(this);
		button8.setActionCommand("0,8");
		catchTheBunny_buttns[8]=button8;
		
		JButton button9 = new JButton("");
		button9.setFont(new Font("Tahoma", Font.BOLD, 40));
		button9.setBackground(new Color(255, 248, 220));
		button9.setBounds(0, 98, 100, 100);
		panel.add(button9);
		button9.addActionListener(this);
		button9.setActionCommand("1,0");
		catchTheBunny_buttns[9]=button9;
		
		JButton button10 = new JButton("");
		button10.setFont(new Font("Tahoma", Font.BOLD, 40));
		button10.setBackground(new Color(255, 248, 220));
		button10.setBounds(98, 98, 100, 100);
		panel.add(button10);
		button10.addActionListener(this);
		button10.setActionCommand("1,1");
		catchTheBunny_buttns[10]=button10;
		
		JButton button11 = new JButton("");
		button11.setFont(new Font("Tahoma", Font.BOLD, 40));
		button11.setBackground(new Color(255, 248, 220));
		button11.setBounds(196, 98, 100, 100);
		panel.add(button11);
		button11.addActionListener(this);
		button11.setActionCommand("1,2");
		catchTheBunny_buttns[11]=button11;
		
		JButton button12 = new JButton("");
		button12.setFont(new Font("Tahoma", Font.BOLD, 40));
		button12.setBackground(new Color(255, 248, 220));
		button12.setBounds(294, 98, 100, 100);
		panel.add(button12);
		button12.addActionListener(this);
		button12.setActionCommand("1,3");
		catchTheBunny_buttns[12]=button12;
		
		JButton button13 = new JButton("");
		button13.setFont(new Font("Tahoma", Font.BOLD, 40));
		button13.setBackground(new Color(255, 248, 220));
		button13.setBounds(392, 98, 100, 100);
		panel.add(button13);
		button13.addActionListener(this);
		button13.setActionCommand("1,4");
		catchTheBunny_buttns[13]=button13;
		
		JButton button14 = new JButton("");
		button14.setFont(new Font("Tahoma", Font.BOLD, 40));
		button14.setBackground(new Color(255, 248, 220));
		button14.setBounds(490, 98, 100, 100);
		panel.add(button14);
		button14.addActionListener(this);
		button14.setActionCommand("1,5");
		catchTheBunny_buttns[14]=button14;
		
		JButton button15 = new JButton("");
		button15.setFont(new Font("Tahoma", Font.BOLD, 40));
		button15.setBackground(new Color(255, 248, 220));
		button15.setBounds(588, 98, 100, 100);
		panel.add(button15);
		button15.addActionListener(this);
		button15.setActionCommand("1,6");
		catchTheBunny_buttns[15]=button15;
		
		JButton button16 = new JButton("");
		button16.setFont(new Font("Tahoma", Font.BOLD, 40));
		button16.setBackground(new Color(255, 248, 220));
		button16.setBounds(686, 98, 100, 100);
		panel.add(button16);
		button16.addActionListener(this);
		button16.setActionCommand("1,7");
		catchTheBunny_buttns[16]=button16;
		
		JButton button17 = new JButton("");
		button17.setFont(new Font("Tahoma", Font.BOLD, 40));
		button17.setBackground(new Color(255, 248, 220));
		button17.setBounds(784, 98, 100, 100);
		panel.add(button17);
		button17.addActionListener(this);
		button17.setActionCommand("1,8");
		catchTheBunny_buttns[17]=button17;
		
		JButton button18 = new JButton("");
		button18.setFont(new Font("Tahoma", Font.BOLD, 40));
		button18.setBackground(new Color(255, 248, 220));
		button18.setBounds(0, 196, 100, 100);
		panel.add(button18);
		button18.addActionListener(this);
		button18.setActionCommand("2,0");
		catchTheBunny_buttns[18]=button18;
		
		JButton button19 = new JButton("");
		button19.setFont(new Font("Tahoma", Font.BOLD, 40));
		button19.setBackground(new Color(255, 248, 220));
		button19.setBounds(98, 196, 100, 100);
		panel.add(button19);
		button19.addActionListener(this);
		button19.setActionCommand("2,1");
		catchTheBunny_buttns[19]=button19;
		
		JButton button20 = new JButton("");
		button20.setFont(new Font("Tahoma", Font.BOLD, 40));
		button20.setBackground(new Color(255, 248, 220));
		button20.setBounds(196, 196, 100, 100);
		panel.add(button20);
		button20.addActionListener(this);
		button20.setActionCommand("2,2");
		catchTheBunny_buttns[20]=button20;
		
		JButton button21 = new JButton("");
		button21.setFont(new Font("Tahoma", Font.BOLD, 40));
		button21.setBackground(new Color(255, 248, 220));
		button21.setBounds(294, 196, 100, 100);
		panel.add(button21);
		button21.addActionListener(this);
		button21.setActionCommand("2,3");
		catchTheBunny_buttns[21]=button21;
		
		JButton button22 = new JButton("");
		button22.setFont(new Font("Tahoma", Font.BOLD, 40));
		button22.setBackground(new Color(255, 248, 220));
		button22.setBounds(392, 196, 100, 100);
		panel.add(button22);
		button22.addActionListener(this);
		button22.setActionCommand("2,4");
		catchTheBunny_buttns[22]=button22;
		
		JButton button23 = new JButton("");
		button23.setFont(new Font("Tahoma", Font.BOLD, 40));
		button23.setBackground(new Color(255, 248, 220));
		button23.setBounds(490, 196, 100, 100);
		panel.add(button23);
		button23.addActionListener(this);
		button23.setActionCommand("2,5");
		catchTheBunny_buttns[23]=button23;
		
		JButton button24 = new JButton("");
		button24.setFont(new Font("Tahoma", Font.BOLD, 40));
		button24.setBackground(new Color(255, 248, 220));
		button24.setBounds(588, 196, 100, 100);
		panel.add(button24);
		button24.addActionListener(this);
		button24.setActionCommand("2,6");
		catchTheBunny_buttns[24]=button24;
		
		JButton button25 = new JButton("");
		button25.setFont(new Font("Tahoma", Font.BOLD, 40));
		button25.setBackground(new Color(255, 248, 220));
		button25.setBounds(686, 196, 100, 100);
		panel.add(button25);
		button25.addActionListener(this);
		button25.setActionCommand("2,7");
		catchTheBunny_buttns[25]=button25;
		
		JButton button26 = new JButton("");
		button26.setFont(new Font("Tahoma", Font.BOLD, 40));
		button26.setBackground(new Color(255, 248, 220));
		button26.setBounds(784, 196, 100, 100);
		panel.add(button26);
		button26.addActionListener(this);
		button26.setActionCommand("2,8");
		catchTheBunny_buttns[26]=button26;
		
		JButton button27 = new JButton("");
		button27.setFont(new Font("Tahoma", Font.BOLD, 40));
		button27.setBackground(new Color(255, 248, 220));
		button27.setBounds(0, 293, 100, 100);
		panel.add(button27);
		button27.addActionListener(this);
		button27.setActionCommand("3,0");
		catchTheBunny_buttns[27]=button27;
		
		JButton button28 = new JButton("");
		button28.setFont(new Font("Tahoma", Font.BOLD, 40));
		button28.setBackground(new Color(255, 248, 220));
		button28.setBounds(98, 293, 100, 100);
		panel.add(button28);
		button28.addActionListener(this);
		button28.setActionCommand("3,1");
		catchTheBunny_buttns[28]=button28;
		
		JButton button29 = new JButton("");
		button29.setFont(new Font("Tahoma", Font.BOLD, 40));
		button29.setBackground(new Color(255, 248, 220));
		button29.setBounds(196, 293, 100, 100);
		panel.add(button29);
		button29.addActionListener(this);
		button29.setActionCommand("3,2");
		catchTheBunny_buttns[29]=button29;
		
		JButton button30 = new JButton("");
		button30.setFont(new Font("Tahoma", Font.BOLD, 40));
		button30.setBackground(new Color(255, 248, 220));
		button30.setBounds(294, 293, 100, 100);
		panel.add(button30);
		button30.addActionListener(this);
		button30.setActionCommand("3,3");
		catchTheBunny_buttns[30]=button30;
		
		JButton button31 = new JButton("");
		button31.setFont(new Font("Tahoma", Font.BOLD, 40));
		button31.setBackground(new Color(255, 248, 220));
		button31.setBounds(392, 293, 100, 100);
		panel.add(button31);
		button31.addActionListener(this);
		button31.setActionCommand("3,4");
		catchTheBunny_buttns[31]=button31;
		
		JButton button32 = new JButton("");
		button32.setFont(new Font("Tahoma", Font.BOLD, 40));
		button32.setBackground(new Color(255, 248, 220));
		button32.setBounds(490, 293, 100, 100);
		panel.add(button32);
		button32.addActionListener(this);
		button32.setActionCommand("3,5");
		catchTheBunny_buttns[32]=button32;
		
		JButton button33 = new JButton("");
		button33.setFont(new Font("Tahoma", Font.BOLD, 40));
		button33.setBackground(new Color(255, 248, 220));
		button33.setBounds(588, 293, 100, 100);
		panel.add(button33);
		button33.addActionListener(this);
		button33.setActionCommand("3,6");
		catchTheBunny_buttns[33]=button33;
		
		JButton button34 = new JButton("");
		button34.setFont(new Font("Tahoma", Font.BOLD, 40));
		button34.setBackground(new Color(255, 248, 220));
		button34.setBounds(686, 293, 100, 100);
		panel.add(button34);
		button34.addActionListener(this);
		button34.setActionCommand("3,7");
		catchTheBunny_buttns[34]=button34;
		
		JButton button35 = new JButton("");
		button35.setFont(new Font("Tahoma", Font.BOLD, 40));
		button35.setBackground(new Color(255, 248, 220));
		button35.setBounds(784, 293, 100, 100);
		panel.add(button35);
		button35.addActionListener(this);
		button35.setActionCommand("3,8");
		catchTheBunny_buttns[35]=button35;
		
		JButton button36 = new JButton("");
		button36.setFont(new Font("Tahoma", Font.BOLD, 40));
		button36.setBackground(new Color(255, 248, 220));
		button36.setBounds(0, 390, 100, 100);
		panel.add(button36);
		button36.addActionListener(this);
		button36.setActionCommand("4,0");
		catchTheBunny_buttns[36]=button36;
		
		JButton button37 = new JButton("");
		button37.setFont(new Font("Tahoma", Font.BOLD, 40));
		button37.setBackground(new Color(255, 248, 220));
		button37.setBounds(98, 390, 100, 100);
		panel.add(button37);
		button37.addActionListener(this);
		button37.setActionCommand("4,1");
		catchTheBunny_buttns[37]=button37;
		
		JButton button38 = new JButton("");
		button38.setFont(new Font("Tahoma", Font.BOLD, 40));
		button38.setBackground(new Color(255, 248, 220));
		button38.setBounds(196, 390, 100, 100);
		panel.add(button38);
		button38.addActionListener(this);
		button38.setActionCommand("4,2");
		catchTheBunny_buttns[38]=button38;
		
		JButton button39 = new JButton("");
		button39.setFont(new Font("Tahoma", Font.BOLD, 40));
		button39.setBackground(new Color(255, 248, 220));
		button39.setBounds(294, 390, 100, 100);
		panel.add(button39);
		button39.addActionListener(this);
		button39.setActionCommand("4,3");
		catchTheBunny_buttns[39]=button39;
		
		JButton button40 = new JButton("");
		button40.setFont(new Font("Tahoma", Font.BOLD, 40));
		button40.setBackground(new Color(255, 248, 220));
		button40.setBounds(392, 390, 100, 100);
		panel.add(button40);
		button40.addActionListener(this);
		button40.setActionCommand("4,4");
		catchTheBunny_buttns[40]=button40;
		
		JButton button41 = new JButton("");
		button41.setFont(new Font("Tahoma", Font.BOLD, 40));
		button41.setBackground(new Color(255, 248, 220));
		button41.setBounds(490, 390, 100, 100);
		panel.add(button41);
		button41.addActionListener(this);
		button41.setActionCommand("4,5");
		catchTheBunny_buttns[41]=button41;
		
		JButton button42 = new JButton("");
		button42.setFont(new Font("Tahoma", Font.BOLD, 40));
		button42.setBackground(new Color(255, 248, 220));
		button42.setBounds(588, 390, 100, 100);
		panel.add(button42);
		button42.addActionListener(this);
		button42.setActionCommand("4,6");
		catchTheBunny_buttns[42]=button42;
		
		JButton button43 = new JButton("");
		button43.setFont(new Font("Tahoma", Font.BOLD, 40));
		button43.setBackground(new Color(255, 248, 220));
		button43.setBounds(686, 390, 100, 100);
		panel.add(button43);
		button43.addActionListener(this);
		button43.setActionCommand("4,7");
		catchTheBunny_buttns[43]=button43;
		
		JButton button44 = new JButton("");
		button44.setFont(new Font("Tahoma", Font.BOLD, 40));
		button44.setBackground(new Color(255, 248, 220));
		button44.setBounds(784, 390, 100, 100);
		panel.add(button44);
		button44.addActionListener(this);
		button44.setActionCommand("4,8");
		catchTheBunny_buttns[44]=button44;
		
		JButton button45 = new JButton("");
		button45.setFont(new Font("Tahoma", Font.BOLD, 40));
		button45.setBackground(new Color(255, 248, 220));
		button45.setBounds(0, 488, 100, 100);
		panel.add(button45);
		button45.addActionListener(this);
		button45.setActionCommand("5,0");
		catchTheBunny_buttns[45]=button45;
		
		JButton button46 = new JButton("");
		button46.setFont(new Font("Tahoma", Font.BOLD, 40));
		button46.setBackground(new Color(255, 248, 220));
		button46.setBounds(98, 488, 100, 100);
		panel.add(button46);
		button46.addActionListener(this);
		button46.setActionCommand("5,1");
		catchTheBunny_buttns[46]=button46;
		
		JButton button47 = new JButton("");
		button47.setFont(new Font("Tahoma", Font.BOLD, 40));
		button47.setBackground(new Color(255, 248, 220));
		button47.setBounds(196, 488, 100, 100);
		panel.add(button47);
		button47.addActionListener(this);
		button47.setActionCommand("5,2");
		catchTheBunny_buttns[47]=button47;
		
		JButton button48 = new JButton("");
		button48.setFont(new Font("Tahoma", Font.BOLD, 40));
		button48.setBackground(new Color(255, 248, 220));
		button48.setBounds(294, 488, 100, 100);
		panel.add(button48);
		button48.addActionListener(this);
		button48.setActionCommand("5,3");
		catchTheBunny_buttns[48]=button48;
		
		JButton button49 = new JButton("");
		button49.setFont(new Font("Tahoma", Font.BOLD, 40));
		button49.setBackground(new Color(255, 248, 220));
		button49.setBounds(392, 488, 100, 100);
		panel.add(button49);
		button49.addActionListener(this);
		button49.setActionCommand("5,4");
		catchTheBunny_buttns[49]=button49;
		
		JButton button50 = new JButton("");
		button50.setFont(new Font("Tahoma", Font.BOLD, 40));
		button50.setBackground(new Color(255, 248, 220));
		button50.setBounds(490, 488, 100, 100);
		panel.add(button50);
		button50.addActionListener(this);
		button50.setActionCommand("5,5");
		catchTheBunny_buttns[50]=button50;
		
		JButton button51 = new JButton("");
		button51.setFont(new Font("Tahoma", Font.BOLD, 40));
		button51.setBackground(new Color(255, 248, 220));
		button51.setBounds(588, 488, 100, 100);
		panel.add(button51);
		button51.addActionListener(this);
		button51.setActionCommand("5,6");
		catchTheBunny_buttns[51]=button51;
		
		JButton button52 = new JButton("");
		button52.setFont(new Font("Tahoma", Font.BOLD, 40));
		button52.setBackground(new Color(255, 248, 220));
		button52.setBounds(686, 488, 100, 100);
		panel.add(button52);
		button52.addActionListener(this);
		button52.setActionCommand("5,7");
		catchTheBunny_buttns[52]=button52;
		
		JButton button53 = new JButton("");
		button53.setFont(new Font("Tahoma", Font.BOLD, 40));
		button53.setBackground(new Color(255, 248, 220));
		button53.setBounds(784, 488, 100, 100);
		panel.add(button53);
		button53.addActionListener(this);
		button53.setActionCommand("5,8");
		catchTheBunny_buttns[53]=button53;
		
		JButton button54 = new JButton("");
		button54.setFont(new Font("Tahoma", Font.BOLD, 40));
		button54.setBackground(new Color(255, 248, 220));
		button54.setBounds(0, 586, 100, 100);
		panel.add(button54);
		button54.addActionListener(this);
		button54.setActionCommand("6,0");
		catchTheBunny_buttns[54]=button54;
		
		JButton button55 = new JButton("");
		button55.setFont(new Font("Tahoma", Font.BOLD, 40));
		button55.setBackground(new Color(255, 248, 220));
		button55.setBounds(98, 586, 100, 100);
		panel.add(button55);
		button55.addActionListener(this);
		button55.setActionCommand("6,1");
		catchTheBunny_buttns[55]=button55;
		
		JButton button56 = new JButton("");
		button56.setFont(new Font("Tahoma", Font.BOLD, 40));
		button56.setBackground(new Color(255, 248, 220));
		button56.setBounds(196, 586, 100, 100);
		panel.add(button56);
		button56.addActionListener(this);
		button56.setActionCommand("6,2");
		catchTheBunny_buttns[56]=button56;
		
		JButton button57 = new JButton("");
		button57.setFont(new Font("Tahoma", Font.BOLD, 40));
		button57.setBackground(new Color(255, 248, 220));
		button57.setBounds(294, 586, 100, 100);
		panel.add(button57);
		button57.addActionListener(this);
		button57.setActionCommand("6,3");
		catchTheBunny_buttns[57]=button57;
		
		JButton button58 = new JButton("");
		button58.setFont(new Font("Tahoma", Font.BOLD, 40));
		button58.setBackground(new Color(255, 248, 220));
		button58.setBounds(392, 586, 100, 100);
		panel.add(button58);
		button58.addActionListener(this);
		button58.setActionCommand("6,4");
		catchTheBunny_buttns[58]=button58;
		
		JButton button59 = new JButton("");
		button59.setFont(new Font("Tahoma", Font.BOLD, 40));
		button59.setBackground(new Color(255, 248, 220));
		button59.setBounds(490, 586, 100, 100);
		panel.add(button59);
		button59.addActionListener(this);
		button59.setActionCommand("6,5");
		catchTheBunny_buttns[59]=button59;
		
		JButton button60 = new JButton("");
		button60.setFont(new Font("Tahoma", Font.BOLD, 40));
		button60.setBackground(new Color(255, 248, 220));
		button60.setBounds(588, 586, 100, 100);
		panel.add(button60);
		button60.addActionListener(this);
		button60.setActionCommand("6,6");
		catchTheBunny_buttns[60]=button60;
		
		JButton button61 = new JButton("");
		button61.setFont(new Font("Tahoma", Font.BOLD, 40));
		button61.setBackground(new Color(255, 248, 220));
		button61.setBounds(686, 586, 100, 100);
		panel.add(button61);
		button61.addActionListener(this);
		button61.setActionCommand("6,7");
		catchTheBunny_buttns[61]=button61;
		
		JButton button62 = new JButton("");
		button62.setFont(new Font("Tahoma", Font.BOLD, 40));
		button62.setBackground(new Color(255, 248, 220));
		button62.setBounds(784, 586, 100, 100);
		panel.add(button62);
		button62.addActionListener(this);
		button62.setActionCommand("6,8");
		catchTheBunny_buttns[62]=button62;
		
		JButton button63 = new JButton("");
		button63.setFont(new Font("Tahoma", Font.BOLD, 40));
		button63.setBackground(new Color(255, 248, 220));
		button63.setBounds(0, 680, 100, 100);
		panel.add(button63);
		button63.addActionListener(this);
		button63.setActionCommand("7,0");
		catchTheBunny_buttns[63]=button63;
		
		JButton button64 = new JButton("");
		button64.setFont(new Font("Tahoma", Font.BOLD, 40));
		button64.setBackground(new Color(255, 248, 220));
		button64.setBounds(98, 680, 100, 100);
		panel.add(button64);
		button64.addActionListener(this);
		button64.setActionCommand("7,1");
		catchTheBunny_buttns[64]=button64;
		
		JButton button65 = new JButton("");
		button65.setFont(new Font("Tahoma", Font.BOLD, 40));
		button65.setBackground(new Color(255, 248, 220));
		button65.setBounds(196, 680, 100, 100);
		panel.add(button65);
		button65.addActionListener(this);
		button65.setActionCommand("7,2");
		catchTheBunny_buttns[65]=button65;
		
		JButton button66 = new JButton("");
		button66.setFont(new Font("Tahoma", Font.BOLD, 40));
		button66.setBackground(new Color(255, 248, 220));
		button66.setBounds(294, 680, 100, 100);
		panel.add(button66);
		button66.addActionListener(this);
		button66.setActionCommand("7,3");
		catchTheBunny_buttns[66]=button66;
		
		JButton button67 = new JButton("");
		button67.setFont(new Font("Tahoma", Font.BOLD, 40));
		button67.setBackground(new Color(255, 248, 220));
		button67.setBounds(392, 680, 100, 100);
		panel.add(button67);
		button67.addActionListener(this);
		button67.setActionCommand("7,4");
		catchTheBunny_buttns[67]=button67;
		
		JButton button68 = new JButton("");
		button68.setFont(new Font("Tahoma", Font.BOLD, 40));
		button68.setBackground(new Color(255, 248, 220));
		button68.setBounds(490, 680, 100, 100);
		panel.add(button68);
		button68.addActionListener(this);
		button68.setActionCommand("7,5");
		catchTheBunny_buttns[68]=button68;
		
		JButton button69 = new JButton("");
		button69.setFont(new Font("Tahoma", Font.BOLD, 40));
		button69.setBackground(new Color(255, 248, 220));
		button69.setBounds(588, 680, 100, 100);
		panel.add(button69);
		button69.addActionListener(this);
		button69.setActionCommand("7,6");
		catchTheBunny_buttns[69]=button69;
		
		JButton button70 = new JButton("");
		button70.setFont(new Font("Tahoma", Font.BOLD, 40));
		button70.setBackground(new Color(255, 248, 220));
		button70.setBounds(686, 680, 100, 100);
		panel.add(button70);
		button70.addActionListener(this);
		button70.setActionCommand("7,7");
		catchTheBunny_buttns[70]=button70;
		
		JButton button71 = new JButton("");
		button71.setFont(new Font("Tahoma", Font.BOLD, 40));
		button71.setBackground(new Color(255, 248, 220));
		button71.setBounds(784, 680, 100, 100);
		panel.add(button71);
		button71.addActionListener(this);
		button71.setActionCommand("7,8");
		catchTheBunny_buttns[71]=button71;
		
		JButton button72 = new JButton("");
		button72.setFont(new Font("Tahoma", Font.BOLD, 40));
		button72.setBackground(new Color(255, 248, 220));
		button72.setBounds(0, 778, 100, 100);
		panel.add(button72);
		button72.addActionListener(this);
		button72.setActionCommand("8,0");
		catchTheBunny_buttns[72]=button72;
		
		JButton button73 = new JButton("");
		button73.setFont(new Font("Tahoma", Font.BOLD, 40));
		button73.setBackground(new Color(255, 248, 220));
		button73.setBounds(98, 778, 100, 100);
		panel.add(button73);
		button73.addActionListener(this);
		button73.setActionCommand("8,1");
		catchTheBunny_buttns[73]=button73;
		
		JButton button74 = new JButton("");
		button74.setFont(new Font("Tahoma", Font.BOLD, 40));
		button74.setBackground(new Color(255, 248, 220));
		button74.setBounds(196, 778, 100, 100);
		panel.add(button74);
		button74.addActionListener(this);
		button74.setActionCommand("8,2");
		catchTheBunny_buttns[74]=button74;
		
		JButton button75 = new JButton("");
		button75.setFont(new Font("Tahoma", Font.BOLD, 40));
		button75.setBackground(new Color(255, 248, 220));
		button75.setBounds(294, 778, 100, 100);
		panel.add(button75);
		button75.addActionListener(this);
		button75.setActionCommand("8,3");
		catchTheBunny_buttns[75]=button75;
		
		JButton button76 = new JButton("");
		button76.setFont(new Font("Tahoma", Font.BOLD, 40));
		button76.setBackground(new Color(255, 248, 220));
		button76.setBounds(392, 778, 100, 100);
		panel.add(button76);
		button76.addActionListener(this);
		button76.setActionCommand("8,4");
		catchTheBunny_buttns[76]=button76;
		
		JButton button77 = new JButton("");
		button77.setFont(new Font("Tahoma", Font.BOLD, 40));
		button77.setBackground(new Color(255, 248, 220));
		button77.setBounds(490, 778, 100, 100);
		panel.add(button77);
		button77.addActionListener(this);
		button77.setActionCommand("8,5");
		catchTheBunny_buttns[77]=button77;
		
		JButton button78 = new JButton("");
		button78.setFont(new Font("Tahoma", Font.BOLD, 40));
		button78.setBackground(new Color(255, 248, 220));
		button78.setBounds(588, 778, 100, 100);
		panel.add(button78);
		button78.addActionListener(this);
		button78.setActionCommand("8,6");
		catchTheBunny_buttns[78]=button78;
		
		JButton button79 = new JButton("");
		button79.setFont(new Font("Tahoma", Font.BOLD, 40));
		button79.setBackground(new Color(255, 248, 220));
		button79.setBounds(686, 778, 100, 100);
		panel.add(button79);
		button79.addActionListener(this);
		button79.setActionCommand("8,7");
		catchTheBunny_buttns[79]=button79;
		
		JButton button80 = new JButton("");
		button80.setFont(new Font("Tahoma", Font.BOLD, 40));
		button80.setBackground(new Color(255, 248, 220));
		button80.setBounds(784, 778, 100, 100);
		panel.add(button80);
		button80.addActionListener(this);
		button80.setActionCommand("8,8");
		catchTheBunny_buttns[80]=button80;
		
		JLabel lblNewLabel = new JLabel("Catch the Bunny");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Yu Gothic", Font.BOLD, 50));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(94, 0, 884, 92);
		contentPane.add(lblNewLabel);
		
		JButton btnStopGame= new JButton("stop game");
		btnStopGame.setFont(new Font("Arial Narrow", Font.PLAIN, 35));
		btnStopGame.setBounds(1026, 838, 306, 59);
		contentPane.add(btnStopGame);
		
		JButton btnStartGame = new JButton("start game");
		btnStartGame.setFont(new Font("Arial Narrow", Font.PLAIN, 35));
		btnStartGame.setBounds(1026, 722, 306, 59);
		contentPane.add(btnStartGame);
		
		btnStartGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(last_move==null)
				    property.firePropertyChange(new PropertyChangeEvent(this,"start game",null,null));
				else {
					JOptionPane.showMessageDialog(catchTneBunnyFrame,"Sorry game already start");			
				}
					
			}
		});
		
		btnStopGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				property.firePropertyChange(new PropertyChangeEvent(this,"stop game",null,null));
				catchTneBunnyFrame.dispose();
				last_move=null;
				currentGame=RunningGame.NO_GAME;
			}
		});
		
		catchTneBunnyFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				property.firePropertyChange(new PropertyChangeEvent(this,"stop game",null,null));
				catchTneBunnyFrame.dispose();
				last_move=null;
				currentGame=RunningGame.NO_GAME;
			}
		});
			
		catchTneBunnyFrame.setVisible(true);
		
	}
    
	/*
	 * Enum shows which game client play now. 
	 */
	private enum RunningGame {
       TIC_TAC_TOE,
       CATCH_THE_BUNNY,
       NO_GAME;   
	}
}