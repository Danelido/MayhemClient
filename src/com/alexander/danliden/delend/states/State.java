package com.alexander.danliden.delend.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.alexander.danliden.delend.mainpackage.Main;

public abstract class State {

	public abstract void initialize();
	public abstract void update(double delta);
	public abstract void render(Graphics2D g);
	
	public abstract void mousePressed(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
	public abstract void mouseReleased(MouseEvent e);
	
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
	
	public void setStateTo(State newState)
	{
		Main.game.setState(newState);
	}
}
