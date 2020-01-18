package com.alexander.danliden.delend.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;

public class Menustate extends State {

	private boolean changeToPlaystate = false;
	
	public void initialize() {
	
		
	}

	
	public void update(double delta) {
	if(changeToPlaystate){
		setStateTo(new Playstate());
	}
		
	}

	
	public void render(Graphics2D g) {
	g.setColor(Color.blue);
	g.fillRect(0, 0, CORE_V.WINDOW_WIDTH, CORE_V.WINDOW_HEIGHT);
		
	}

	
	public void mousePressed(MouseEvent e) {
	
		
	}

	
	public void mouseMoved(MouseEvent e) {
	
		
	}

	
	public void mouseReleased(MouseEvent e) {
	
		
	}

	
	public void keyPressed(KeyEvent e) {
	if(e.getKeyCode() == KeyEvent.VK_P){
		changeToPlaystate = true;
	}
		
	}

	
	public void keyReleased(KeyEvent e) {
	
		
	}

}
