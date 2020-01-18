package com.alexander.danliden.delend.mainpackage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;

public class Main {
	/*
	 * “Action Music”. Music by Marcelo Fernandez (http://www.marcelofernandezmusic.com). 
	 */
	
	public static JFrame WINDOW_FRAME = new JFrame();	//Creating JFrame
	public static Game game;	// Public instance of game class
	
	private static void initialize(boolean multiplayer, String username, String address, int port){
		WINDOW_FRAME.setTitle(CORE_V.WINDOW_TITLE);	// Title
		WINDOW_FRAME.setSize(CORE_V.WINDOW_WIDTH, CORE_V.WINDOW_HEIGHT);	// Window width & window height
		WINDOW_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Exit the application on close
		WINDOW_FRAME.setResizable(false);	// Disable resize option
		WINDOW_FRAME.setLocationRelativeTo(null);	// Center the JFrame
		
		game = new Game(multiplayer,username, address, port);	// Creating the game class and giving it to "game"
		
		WINDOW_FRAME.add(game);	// Add the game to the JFrame
		WINDOW_FRAME.setVisible(true);	// display the window
		
		game.start();	// Start the gameloop
		
	}
	
	public static void main(String[] args, boolean multiplayer, String username, String address, int port) 
	{
		
		initialize(multiplayer, username, address, port);	// Start this shit up
	}

	public static void fatalError(String errormessage){
		JOptionPane.showMessageDialog(WINDOW_FRAME, errormessage, "Retard alert", JOptionPane.ERROR_MESSAGE); // Something is wrong, display it
		System.exit(0);	// exit the whole thing
	}
	
}
