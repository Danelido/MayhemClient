package com.alexander.danliden.delend.world;

import java.awt.Graphics2D;

import com.alexander.danliden.delend.mainpackage.Resources;

public class Map {

	public World Arena;
	private int mapwidth = 150, mapheight = 150;
	
	public Map() {
		
	}
	
	public void init(){
		Arena = new World(Resources.arenaMap, mapwidth, mapheight);
		Arena.generateMap();
	}
	
	public void update(double delta){
		Arena.update(delta);
	}
	
	public void render(Graphics2D g){
		Arena.render(g);
	}
}
