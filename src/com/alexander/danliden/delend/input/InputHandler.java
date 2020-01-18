package com.alexander.danliden.delend.input;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.alexander.danliden.delend.states.State;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

	
	private State currentState;
	public static Point mouse = new Point();;
	
	
	public void setState(State state){
		this.currentState = state;
		
	}
	
	public void mouseDragged(MouseEvent arg0) {
		
		
	}

	
	public void mouseMoved(MouseEvent e) {
		currentState.mouseMoved(e);
		mouse.setLocation(e.getX(), e.getY());
	}

	
	public void mouseClicked(MouseEvent arg0) {
		
		
	}

	
	public void mouseEntered(MouseEvent arg0) {
		
		
	}

	
	public void mouseExited(MouseEvent arg0) {
		
		
	}

	
	public void mousePressed(MouseEvent e) {
		currentState.mousePressed(e);
		
	}

	
	public void mouseReleased(MouseEvent e) {
		
		currentState.mouseReleased(e);
	}

	
	public void keyPressed(KeyEvent e) {
		currentState.keyPressed(e);
		
	}

	
	public void keyReleased(KeyEvent e) {
		currentState.keyReleased(e);
		
	}

	
	public void keyTyped(KeyEvent e) {
		
		
	}

}
