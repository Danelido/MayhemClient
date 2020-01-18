package com.alexander.danliden.delend.mainpackage;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.alexander.danliden.delend.input.InputHandler;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.states.Loader;
import com.alexander.danliden.delend.states.State;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private Thread mainThread;	// Main thread
	private boolean running = false;	// running variable
	private int fps;	// fps variable
	private boolean showInformation = true;	// Display the fps and ups?
	private State currentState;	// Game states	
	private InputHandler input;	// Input handler
	
	public static boolean multiplayer = false; // Default multiplayer is off
	
	public static String username, address; // Client username and [MAIN SERVER] IP
	public static int port;	// Port to [MAIN SERVER]
	
	public Game(boolean multiplayer, String username, String address, int port){
		this.multiplayer = multiplayer;
		this.username = username;
		this.address = address;
		this.port = port;
		setFocusable(true);
		initializeInputHandler();
		setState(new Loader());
		
	}
	
	public synchronized void start(){
		mainThread = new Thread(this,"Main Thread - gameClass");
		mainThread.start(); 	// Starting main thread
		running = true;			// Run the game loop
		
	
	}
	
	public synchronized void stop(){
		running = false;
		try {
			mainThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	private void update(double delta){
		currentState.update(delta);
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		// No blinking screen
		g.setColor(new Color(17,25,34));
		g.fillRect(0, 0, CORE_V.WINDOW_WIDTH, CORE_V.WINDOW_HEIGHT);
		
		// render current state
		currentState.render(g);
		
		//Display fps IF wanted
		if(showInformation){
			if(Resources.font != null)
				g.setFont(Resources.font);
			
			g.setColor(new Color(255,255,255));
			g.drawString("fps: " + fps, 5, 20);
		}
		
		g.dispose();
		bs.show();
		
	}
	
	public void setState(State newState){
		currentState = newState;
		currentState.initialize();
		input.setState(currentState);
	}
	
	private void initializeInputHandler(){
		input = new InputHandler();
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}
	
	public void run() 
	{
		
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		double frameTime = 0;
		int frames = 0;
		
		while(running){
			
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while(unprocessedTime >= CORE_V.framecap){
				update(CORE_V.framecap);
				unprocessedTime -= CORE_V.framecap;
				
				render = true;
				
				if(frameTime >= 1){
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
				
			}
			
			if(render){
				render = false;
				frames++;
				//System.gc();
				render();
			}else{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
		}
		
		stop();
		
	}
	
	
	
	
	
}
