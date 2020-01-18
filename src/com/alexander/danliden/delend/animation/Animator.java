package com.alexander.danliden.delend.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animator {

	private ArrayList<BufferedImage> frames;
	public BufferedImage sprite;
	private volatile boolean running = false;
	
	private long prevTime, speed;
	private int currentFrame;
	
	public Animator(ArrayList<BufferedImage> frames){
		this.frames = frames;
	}
	
	public void setSpeed(long speed){
	this.speed = speed;	
	}
	
	public void update(long time){
		if(running){
			
			if(time - prevTime >= speed){
				
				currentFrame++;
				try{
					if(currentFrame < frames.size()){
						sprite = frames.get(currentFrame);
					}else{
						reset();
					}
					
				}catch(IndexOutOfBoundsException e){
					currentFrame = 0;
					sprite = frames.get(currentFrame);
					e.printStackTrace();
				}
				prevTime = time;
				
			}
			
		}
	}
	
	public void play(){
		running = true;
		prevTime = 0;
		currentFrame = 0;
	}
	
	
	
	public void stop(){
		running = false;
		prevTime = 0;
		currentFrame = 0;
	}
	
	public void reset(){
		currentFrame = 0;
	}
	
	public boolean isDoneAnimating(){
		if(currentFrame >= frames.size() - 1){
			return true;
		}else
			return false;
	}
	
	public boolean doneAtFrame(int frame){
		if(currentFrame >= frame){
			return true;
		}else
			return false;
	}
	
	
	
	
	
	
	
	
	
	
	
}
