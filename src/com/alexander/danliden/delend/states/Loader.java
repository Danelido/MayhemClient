package com.alexander.danliden.delend.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.alexander.danliden.delend.mainpackage.Resources;

public class Loader extends State{

	private boolean doneLoading = false;

	
	public void initialize() {
		if(!doneLoading){
			// load resources
			Resources.loadAllResources();
			doneLoading = true;
		}
	}

	
	public void update(double delta) {
		if(doneLoading){
			setStateTo(new Playstate());
		}
	}

	
	public void render(Graphics2D g) {
		
	}

	
	public void mousePressed(MouseEvent e) {
		
		
	}

	
	public void mouseMoved(MouseEvent e) {
		
		
	}

	
	public void mouseReleased(MouseEvent e) {
		
		
	}


	public void keyPressed(KeyEvent e) {
		
		
	}

	
	public void keyReleased(KeyEvent e) {
		
		
	}
	
	
	
	
}
