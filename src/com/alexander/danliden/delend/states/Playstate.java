package com.alexander.danliden.delend.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.alexander.danliden.delend.world.Map;
import com.alexander.danliden.delend.world.World;

public class Playstate extends State {

	private Map map;
	
	public Playstate(){
		map = new Map();
		map.init();
		
		
	}
	
	public void update(double delta){
		map.update(delta);
	}
	
	public void render(Graphics2D g){
		map.render(g);
	}

	
	public void initialize() {
		
		
	}

	
	public void mousePressed(MouseEvent e) {
		World.player.mousePressed(e);
		
	}

	
	public void mouseMoved(MouseEvent e) {
		
		
	}

	
	public void mouseReleased(MouseEvent e) {
		
		
	}

	
	public void keyPressed(KeyEvent e) {
		World.player.keyPressed(e);
		
	}

	
	public void keyReleased(KeyEvent e) {
		World.player.keyReleased(e);
		
	}
	
}
