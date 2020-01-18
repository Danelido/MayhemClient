package com.alexander.danliden.delend.managers.effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.alexander.danliden.delend.animation.Animator;


public class ActionbarEffect {

	
	private float xEff, yEff;
	private float startY,startX,endX, endY;
	
	private int PHASE;
	private float effectSpeed;
	private ArrayList<BufferedImage> list;
	private Animator animation;
	private int size = 	32;
	private double rotation = 0;
	private Color color;
	
	public ActionbarEffect(float startX, float startY, float endX, float endY, int PHASE, float effectSpeed, int size, Color color){
		xEff = startX;
		yEff = startY;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.PHASE = PHASE;
		this.effectSpeed = effectSpeed;
		this.size = size;
		this.color = color;
		
	}
	public ActionbarEffect(float startX, float startY, float endX, float endY, int PHASE, float effectSpeed, ArrayList<BufferedImage> list){
		xEff = startX;
		yEff = startY;
		this.startX = startX  - size / 2;
		this.startY = startY - size / 2;
		this.endX = endX - size / 2;
		this.endY = endY  - size / 2;
		this.PHASE = PHASE;
		this.effectSpeed = effectSpeed;
		this.list = list;
		initAnimation();
	}
	
	
	private void initAnimation(){
		animation = new Animator(list);
		animation.setSpeed(30);
		animation.play();
	}
	
	public void update(double delta){
		if(PHASE == 1){
			rotation = 0;
			yEff = startY;
			xEff += effectSpeed;
		if(xEff >= endX){
			xEff = endX;
			
			PHASE = 2;
		}
		}
		
		if(PHASE == 2){
			
			rotation = 90;
			xEff = endX;
			yEff += effectSpeed;
			if(yEff >= endY ){
				yEff = endY;
				
				PHASE = 3;
			}
		}
		
		if(PHASE == 3){
			rotation = 180;
			yEff = endY;
			if(xEff == startX){
				xEff = endX;
			}
			xEff -= effectSpeed;
		if(xEff <= startX  + size / 2){
			xEff = startX;
			
			PHASE = 4;
		}
		}
		
		if(PHASE == 4){
			rotation = 270;
			xEff = startX;
			if(yEff == startY){
				yEff = endY;
			}
			yEff -= effectSpeed;
			if(yEff <= startY + size / 2 ){
				yEff = startY;
				
				PHASE = 1;
			}
		}
	}
	
	
	public void render(Graphics2D g){
		
		
		if(animation != null){
		
		// Rotation
				g.rotate(Math.toRadians(rotation), 
						(int)xEff + size / 2 , 
						(int)yEff + size / 2 );
				
				//##########################################################
				// Everything between here rotates
					g.drawImage(animation.sprite,(int)xEff, (int)yEff, size, size, null);
					animation.update(System.currentTimeMillis());
				
				
				//############################################################
				// Reset, because i dont want everything in the entire world to rotate!
				// Important line!
				g.rotate(-Math.toRadians(rotation), 
						(int)xEff  + size / 2, 
						(int)yEff  + size / 2);
		
		}
		if(animation == null){
		g.setColor(color);
		g.fillRect((int)xEff, (int)yEff, size, size);
		}
	}
	
}
