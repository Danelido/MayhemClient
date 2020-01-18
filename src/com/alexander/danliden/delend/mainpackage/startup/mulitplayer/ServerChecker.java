package com.alexander.danliden.delend.mainpackage.startup.mulitplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;



public class ServerChecker extends Thread {
	// PACKET TYPES
	/******************
	 * "yes" - Server response
	 * "-2"  - Server information(Online players) 
	 ******************/
	
	
	private final static boolean consoleDebug = false;
	
	private DatagramSocket socket; // Socket is used to send information over the internet. Creating one of those :)
	private int port;	// Server port
	private InetAddress ip;	// We are dealing with multiplayer so obviously we would need a ipAdress, so we would know where to send our info(packages)
	private String username, address; // username and ip
	private JoinServer joinserver; 
	private Serverlist serverlist;
	// Some fancy mechanics
	private boolean started = false;
	private int attempts = 0;
	private boolean foundserver = false;
	private boolean running = false;
	

	
	
	public ServerChecker(String username, String address, int port, JoinServer joinserver){
		this.username = username;
		this.address = address;
		this.port = port;
		this.joinserver = joinserver;
		
		try {
			socket = new DatagramSocket();	// Create the socket
			ip = InetAddress.getByName(address);	// Gets our IP and stores it in our variable
			running = true;
		} catch (SocketException e) {
			System.out.println("Nope, somethings up with the socket [SERVER CHECKER]");
		} catch (UnknownHostException e) {
			joinserver.failedSearch(true);
			terminate();
			
		}
		
		
	}
	
	public ServerChecker(String address, int port, Serverlist serverlist){
		this.address = address;
		this.port = port;
		this.serverlist = serverlist;
		
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
			running = true;
		} catch (SocketException e) {
			System.out.println("Nope, somethings up with the socket [SERVER CHECKER]");
		} catch (UnknownHostException e) {
			joinserver.failedSearch(true);
			terminate();
			
		}
		
		
	}
	
	
	public void run(){
		if(!foundserver){
		timer();
		while(running){
			receive();
		}
		}
	}
	private Thread timer;
	private void receive(){
		/*
		 * Listening for packages from the server, if it receives a packet then process it
		 */
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			terminate();
			joinserver.failedSearch(true);
		}
		process(packet);
		
		
	}
	// Checking after a server, instead of doing it 3x times in 0.1s it does it once every second around 3 times then it returns failedSearch
	private void timer() {
		timer = new Thread(){
			@SuppressWarnings("unchecked")
			public void run(){
				while(running){
					if(!foundserver){
						if(consoleDebug)
							System.out.println("Searching..");
					if(attempts >= 2){
						if(joinserver != null)
						joinserver.failedSearch(true);
						
						
						// Remove searching note element
						
						if(serverlist != null && serverlist.listModel != null){
						if(serverlist.listModel.size() != 0){
							for(int i = 0; i < serverlist.listModel.size(); i++){
								serverlist.listModel.remove(i);
							}
							}
						// Tell the user that server can't be found
						serverlist.listModel.addElement("No server available");
						}
						
						if(serverlist != null)
							serverlist.refreshButtonEnabled(true);
						
						terminate();
					}else
						if(serverlist != null)
						serverlist.refreshButtonEnabled(false);
						attempts++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
			}
		};
		timer.start();
		
	}

	private void process(DatagramPacket packet) {
		String fulldata = new String(packet.getData()).trim(); // Fulldata with messagetype and the data
		String messagetype = fulldata.substring(0, 2);	// Messagetype (look at the top of this class)
		String message = fulldata.substring(2); // Data, the information.
		
		if(fulldata.equalsIgnoreCase("yes")){
			if(consoleDebug)System.out.println("Found server");
			//Found the server, start the game
			joinserver.failedSearch(false);
			joinserver.startgame();
			started = true;
			foundserver = true;
		}else if(messagetype.equalsIgnoreCase("-2")){
			String[] information = message.split(",");
			// Received a "-2" package, adding the server to our list
			serverlist.addMainServerToList(Integer.parseInt(information[1]));
			foundserver = true;
		}else{
			if(joinserver != null)
			joinserver.failedSearch(true);
		}
		if(started){
			terminate();
			try {
				this.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void send(final byte[] data){
		DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);// Send a packet (Our message, Length of our message, our IP , our port)
		
		try {
			socket.send(packet); // send it to the server
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void terminate(){
		running = false;
		socket.close();
		try {
			this.join();
			timer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("ServerClient: Terminate functíon called");
	}
	
	
	
	

	
}
