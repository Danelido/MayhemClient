package com.alexander.danliden.delend.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.alexander.danliden.delend.input.InputHandler;
import com.alexander.danliden.delend.mainpackage.Resources;

public class Block extends Rectangle {
	private static final long serialVersionUID = 1L;
	private boolean debug = false;
	private float x,y;
	public static int BlockSize = 64;
	private BufferedImage block;
	private BlockType blocktype;
	private boolean isSolid;
	private boolean isAlive;
	
	public enum BlockType{
		GRASS,
		WALLTOP,
		WALL,
		NEUTRAL_GROUND,
		BATTLE_GROUND,
		CRATE;
	}
	
	
	public Block(float x, float y, BlockType blocktype){
		this.x = x;
		this.y = y;
		isAlive = true;
		this.blocktype = blocktype;
		init();
	}
	
	public void init(){
		switch(blocktype){
		case GRASS:
			block = Resources.stoneTile;
			break;
		case WALLTOP:
			block = Resources.wall_top1;
			break;
		case NEUTRAL_GROUND:
			block = Resources.neutral_ground;
			break;
		case BATTLE_GROUND:
			block = Resources.battle_ground;
			break;
		case CRATE:
			block = Resources.crate;
			break;
		case WALL:
			block = Resources.wall;
			break;
		}
	}
	
	public Block isSolid(boolean isSolid){
		this.isSolid = isSolid;
		return this;
	}
	
	
	public void update(double delta){
		if(World.gamecamera != null)
		setBounds((int)(x - World.gamecamera.getxOffset()), (int)(y - World.gamecamera.getyOffset()), BlockSize, BlockSize);
	}
	
	public void render(Graphics2D g){
		if(isAlive){
			if(World.gamecamera != null)
			g.drawImage(block, (int)(x - World.gamecamera.getxOffset()), (int)(y - World.gamecamera.getyOffset()), BlockSize, BlockSize, null);
			
		}
		if(debug){
			if(isSolid()){
				g.setColor(Color.blue);
				if(World.gamecamera != null)
				g.fillRect((int)(x - World.gamecamera.getxOffset()), (int)(y - World.gamecamera.getyOffset()), BlockSize, BlockSize);
			}
		
		if(World.gamecamera != null){
		if(this.contains(InputHandler.mouse)){
			if(isSolid())
				g.setColor(new Color(255,0,0,50));
			else
				g.setColor(new Color(0,255,0,50));
			g.fillRect((int)(x - World.gamecamera.getxOffset()), (int)(y - World.gamecamera.getyOffset()), BlockSize, BlockSize);
		}
		}
		}
		
	}
	
	public boolean isSolid(){
		return isSolid;
	}
	
	public boolean isAlive(){
		return isAlive();
	}
	
	public float getBlockCenterX(){
		return (int)(x + BlockSize / 2);
	}
	
	public float getBlockCenterY(){
		return (int)(y + BlockSize / 2);
	}
	
	public void setAlive(boolean isAlive){
		this.isAlive = isAlive;
	}
	
	
}
