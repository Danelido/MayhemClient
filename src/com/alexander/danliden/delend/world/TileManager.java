package com.alexander.danliden.delend.world;


import java.awt.Graphics2D;
import java.util.ArrayList;



public class TileManager {

	public static ArrayList<Block> blocks = new ArrayList<Block>();
	private World world;
	
	public TileManager(World world){
	this.world = world;	
	}
	
	
	public void update(double delta){
		if(!blocks.isEmpty()){
		for(Block block : blocks){
			block.update(delta);
			if(world.render != null){
				if(world.render.intersects(block)){
					block.setAlive(true);
				}else{
					block.setAlive(false);
				}
			}
		
		}
		}
	}
	
	
	public void render(Graphics2D g){
		for(Block block : blocks){
			block.render(g);
		}
		
	}
	
	
	
	
	
}
