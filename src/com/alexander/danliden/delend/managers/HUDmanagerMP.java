package com.alexander.danliden.delend.managers;

import java.awt.Color;

import java.awt.Graphics2D;

import com.alexander.danliden.delend.world.World;

public class HUDmanagerMP {

	private float health,mana,  x,  y;
	private float length = 50;
	
	public HUDmanagerMP(float health,float mana, float x, float y){
		this.health = health;
		this.mana = mana;
		this.x = x;
		this.y = y;
	}
	public void setHealth(float health){
		this.health = health;
	}
	public void setMana(float mana){
		this.mana = mana;
	}
	
	public void updatePosition(float x, float y){
		this.x = x; 
		this.y = y;
		
		if(health <= 0){
			setHealth(0F);
		}
		if(mana <= 0){
			setMana(0F);
		}
		
	}
	
	
	public void render(Graphics2D g){
		
		/*********
		 * HEALTH*
		 *********/
		
		g.setColor(Color.red);
		g.fillRect((int)(x - World.gamecamera.getxOffset() - length / 2 + 31),
				(int)(y - World.gamecamera.getyOffset() - 46),
				50, 
				5);
		g.setColor(Color.green);
		g.fillRect((int)(x - World.gamecamera.getxOffset() - length / 2 + 31),
				(int)(y - World.gamecamera.getyOffset() - 46), 
				(int)(0.5 * health), 
				5);
		
		/*******
		 * MANA*
		 *******/
		
		g.setColor(new Color(64,64,64));
		g.fillRect((int)(x - World.gamecamera.getxOffset() - length / 2 + 31),
				(int)(y - World.gamecamera.getyOffset() - 40),
				50, 
				5);
		g.setColor(Color.blue);
		g.fillRect((int)(x - World.gamecamera.getxOffset() - length / 2 + 31),
				(int)(y - World.gamecamera.getyOffset() - 40), 
				(int)(0.5 * mana), 
				5);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
