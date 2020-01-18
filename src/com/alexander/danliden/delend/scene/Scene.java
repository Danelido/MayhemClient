package com.alexander.danliden.delend.scene;

import java.awt.Graphics2D;

import com.alexander.danliden.delend.animation.Animator;
import com.alexander.danliden.delend.entities.models.Player;
import com.alexander.danliden.delend.mainpackage.Resources;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;
import com.alexander.danliden.delend.world.World;

public class Scene {

	private boolean start, rewind, done,firstPhaseDone,secondPhaseDone;
	private float rotation, x ,y;
	private float rotationSpeed;
	private float growSpeedX, growSpeedY;
	
	private float width, height;
	private Animator smoke;
	private Player player;
	private Client client;
	
	public Scene(Player player, Client client){
		this.player = player;
		this.client = client;
		start = false;
		rewind = false;
		done = false;
		firstPhaseDone = false;
		secondPhaseDone = false;
		
		rotation = 0;
		rotationSpeed = 8 * 15;
		growSpeedX = 45;
		growSpeedY = growSpeedX / 16 * 9;
		width = 2;
		height = 2;
		
		//x = ((Main.WINDOW_WIDTH / 2) - (width / 2));
		//y = ((Main.WINDOW_HEIGHT / 2) - (height / 2));
		
		x = this.player.getX();
		y = this.player.getY();
		
		smoke = new Animator(Resources.smokelist);
		smoke.setSpeed(60);
		smoke.play();
		
		
	}
	
	
	public void update(double delta){
		// First Phase
		if(!firstPhaseDone && !done){
		if(start){
			
			/*
			rotation += rotationSpeed;
			width += growSpeedX;
			height += growSpeedY;
			
			if(width < Main.WINDOW_WIDTH + 50)
			x -= growSpeedX/2;
			if(height < Main.WINDOW_HEIGHT + 50)
			y -= growSpeedY/2;
			*/
		}
		/*
		if(width >= Main.WINDOW_WIDTH + 50){
			width = Main.WINDOW_WIDTH + 50;
		}
		if(height >= Main.WINDOW_HEIGHT + 50){
			height = Main.WINDOW_HEIGHT + 50;
		}
		if(width >= Main.WINDOW_WIDTH + 50 && height >= Main.WINDOW_HEIGHT + 50 ){
			firstPhaseDone = true;
		}	
		 */
		
		if(smoke.isDoneAnimating()){
			firstPhaseDone = true;
			smoke.reset();
		}
		
		}
		
		//SecondPhase
		if(firstPhaseDone){
		
			/*
				rotation -= rotationSpeed;
				width -= growSpeedX;
				height -= growSpeedY;
				
				//if(width > Main.WINDOW_WIDTH / 2 - 50)
				x += growSpeedX/2;
				//if(height > Main.WINDOW_HEIGHT /2 - 50)
				y += growSpeedY/2;
			
			if(width <= 1){
				width = 1;
			}
			if(height <= 1){
				height = 1;
			}
			if(width <= 1 && height <= 1){
				secondPhaseDone = true;
			}
			
		*/
			if(smoke.isDoneAnimating()){
				secondPhaseDone = true;
			}
		}
			
		if(secondPhaseDone){
			done = true;
		}
		
		x = this.player.getX();
		y = this.player.getY();
		
		
		if(done){
			smoke.reset();
			reset();
		}
	}
	
	public void render(Graphics2D g){
	
		/*
		// Rotation
				g.rotate(Math.toRadians(rotation), 
						(int)x + width/ 2 , 
						(int) y  + height/2 );
				
				//##########################################################
				// Everything between here rotates
				if(start && !done){
					g.setColor(Color.black);
					g.fillRect((int)x, (int)y, (int)width, (int)height);
				}
			
				
				//############################################################
				// Reset, because i dont want everything in the entire world to rotate!
				// Important line!
				g.rotate(-Math.toRadians(rotation), 
						(int)x  + width / 2 , 
						(int) y  + height/2 );
		
		
		*/
		if(start && !done){
		g.drawImage(smoke.sprite,(int)(x - World.gamecamera.getxOffset() - 15), (int)(y - World.gamecamera.getyOffset() - 70),80,110, null);
		smoke.update(System.currentTimeMillis());
		}
		
		
		
	}


	public boolean isDone() {
		return done;
	}


	public void setStart(boolean start) {
		this.start = start;
	}
	
	public void reset(){
		start = false;
		rewind = false;
		done = false;
		firstPhaseDone = false;
		secondPhaseDone = false;
		
		rotation = 0;
		rotationSpeed =8 * 15;
		growSpeedX = 45;
		growSpeedY = growSpeedX / 16 * 9;
		width = 2;
		height = 2;
		
		String message = "12" + false + "," + player.getId();
		if(client != null)
			client.send(message.getBytes());
		
	}


	public boolean isFirstPhaseDone() {
		return firstPhaseDone;
	}


	public boolean isStart() {
		return start;
	}
}
