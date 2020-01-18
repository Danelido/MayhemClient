package com.alexander.danliden.delend.chatprogram;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class keyCommands implements KeyListener {

	private JFrame chatFrame;
	private JFrame gameFrame;
	
	public keyCommands(JFrame chatFrame, JFrame gameFrame){
		this.chatFrame = chatFrame;
		this.gameFrame = gameFrame;
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_M){
			System.out.println("SHOULD BE CLOSING");
			
				chatFrame.dispose();
				gameFrame.setFocusable(true);
				
			}
		}
		
		
	

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
