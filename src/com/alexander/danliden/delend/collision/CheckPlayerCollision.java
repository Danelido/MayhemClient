package com.alexander.danliden.delend.collision;

import java.awt.Point;

import com.alexander.danliden.delend.world.Block;
import com.alexander.danliden.delend.world.TileManager;


public class CheckPlayerCollision {

	public static boolean Collision(Point p1, Point p2){
		for(Block block: TileManager.blocks){
			if(block.isSolid()){
				if(block.contains(p1) || block.contains(p2)){
					return true;
				}
			}
		}
		return false;
		
	}
	
}
