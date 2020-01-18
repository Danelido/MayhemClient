package com.alexander.danliden.delend.chatprogram;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private int chatF_w = 300;
	private int chatF_h = 200;
	private JPanel contentPane;
	private JFrame gameFrame;
	private boolean selfDebug = false;
	
	private Color baseColor = new Color(0,0,0,50);
	
	//Components
	private JTextField userMessageBox;
	private JTextArea chatBox;
	
	//Client
	private Client client;
	private keyCommands keycommands;
	private String username;
	
	public Frame(JFrame gameFrame, Client client, String username){
		if(!selfDebug)this.gameFrame = gameFrame;
		if(!selfDebug)this.client = client;
		if(!selfDebug)this.username = username;
		if(selfDebug)initializeAndStart();
	}
	
	
	public void initializeAndStart(){
		setSize(chatF_w,chatF_h);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		//if(!selfDebug)setFocusable(false);
		if(!selfDebug)setLocationRelativeTo(null);
		setUndecorated(true);
		setBackground(baseColor);
		keycommands = new keyCommands(this,gameFrame);
		
		if(!selfDebug)setLocation(gameFrame.getX(), (gameFrame.getY() + gameFrame.getHeight()) - chatF_h - 10);
		contentPane = new JPanel();
		contentPane.setSize(chatF_w, chatF_h);
		contentPane.setLayout(null);
		contentPane.setBackground(baseColor);
		contentPane.addKeyListener(keycommands);
		contentPane.setVisible(true);
		add(contentPane);
		components();
		TextFieldHandler();
		if(selfDebug)setVisible(true);
		else setVisible(false);
	}
	
	private void components(){
		chatBox = new JTextArea();
		chatBox.setBounds(20, 10, chatF_w - 40, chatF_h - 60);
		chatBox.setEditable(false);
		chatBox.setBackground(baseColor);
		chatBox.setForeground(new Color(255,255,255,255));
		chatBox.setVisible(true);
		JScrollPane scroll = new JScrollPane(chatBox);
		scroll.setBounds(20, 10, chatF_w - 40, chatF_h - 60);
		scroll.setBackground(new Color(1,0,0,0));
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getViewport().setBorder(null);
		scroll.setViewportBorder(null);
		scroll.setBorder(null);
		scroll.setVisible(true);
		
		contentPane.add(scroll);
		
		userMessageBox = new JTextField();
		userMessageBox.setBounds(20,chatF_h - 45, chatF_w - 40, 30 );
		userMessageBox.setBackground(baseColor);
		userMessageBox.setForeground(new Color(255,255,255,255));
		userMessageBox.setBorder(new LineBorder(new Color(64,64,64,255),1));
		userMessageBox.setVisible(true);
		contentPane.add(userMessageBox);
		
	}
	
	
	private void TextFieldHandler(){
		
		userMessageBox.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					
					String temp = ("ch" + username +"," + userMessageBox.getText());
					if(client != null){
						client.send(temp.getBytes());
					}
					userMessageBox.setText("");
					
				}
			}
			public void keyReleased(KeyEvent e){
				
			}
			public void keyTyped(KeyEvent e){
				repaint();
}
		});
		
	}
	
	public void appendChat(String username, String information){
		chatBox.append(username + ": " + information+ "\n");
	}
	
	public boolean ifFocused(){
		return isFocused();
	}
	
	public JPanel getPanel(){
		return contentPane;
	}
	
	


}





