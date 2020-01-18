package com.alexander.danliden.delend.mainpackage.startup;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alexander.danliden.delend.mainpackage.Resources;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Serverlist;

public class Launcher extends JFrame {
private static final long serialVersionUID = 1L;

 // TODO: Screen resolution options
	private JPanel contentPane;
	private Launcher launcher;
	private Serverlist sl;
	private int app_w = 600;
	private int app_h = 350;
	private boolean Sound = false;
	private Color btn_color = new Color(40,40,40);
	private Font font = new Font("Comic Sans MS", Font.ITALIC, 12);
	public static Assets assets;
	/*
	 * TWO MAIN FUNCTIONS IN THE SAME PROJECT, WHHHHHAAAAAT?!
	 * I hope my programming teacher won't be seeing this ;)
	 * Why you might ask yourself? Because the launcher is a application by itself, still made in the same project because im lazy
	 * 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Main.main(null, true, "lel", "localhost", 8192); // Debug
				
					// Load assets before starting launcher
					assets = new Assets();
					assets.load();
					
					
					// Start shit up
					Launcher frame = new Launcher();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}


	public Launcher() {
		launcher = this; // ...Obviously
		setResizable(false);
		setTitle(CORE_V.WINDOW_TITLE);
		setSize(app_w,app_h);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		//contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Components();
		if(Sound)BackgroundSound();
		Background();
		
		
	}
	
	
	private void BackgroundSound(){
		assets.backgroundsound.setVolume(0.5f);
		assets.backgroundsound.start();
	}
	
	private void Background(){
		JLabel backgroundLbl = new JLabel(assets.Application_backgroundIcon);
		//backgroundLbl.setIcon(Assets.Application_backgroundIcon);
		backgroundLbl.setBounds(0,0,app_w - 15,app_h - 25);
		backgroundLbl.setVisible(true);
		contentPane.add(backgroundLbl);
		
	}
	
	private void Components(){
		JButton btnSingleplayer = new JButton("Play Singleplayer");
		btnSingleplayer.setForeground(Color.WHITE);
		btnSingleplayer.setFont(font);
		btnSingleplayer.setBounds(10, 117, 146, 30);
		btnSingleplayer.setBackground(btn_color);
		btnSingleplayer.setFocusPainted(false);
		contentPane.add(btnSingleplayer);
		btnSingleplayer.setEnabled(false);
		
		
		btnSingleplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//dispose();
				//Main.main(null,false, null,null,-1);
				
			}
		});
		
		JButton btnMultiplayer = new JButton("Multiplayer");
		btnMultiplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc(); // Clean up unused shit
				if(sl == null)
				sl = new Serverlist(launcher); // Well, open up the server application list and send in the launcher if you might want to go back
				else
					sl.setVisible(true);
			}
		});
		btnMultiplayer.setForeground(Color.WHITE);
		btnMultiplayer.setBackground(btn_color);
		btnMultiplayer.setFont(font);
		btnMultiplayer.setBounds(10, 167, 118, 30);
		btnMultiplayer.setFocusPainted(false);
		contentPane.add(btnMultiplayer);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		btnQuit.setForeground(Color.WHITE);
		btnQuit.setBackground(btn_color);
		btnQuit.setFocusPainted(false);
		btnQuit.setFont(font);
		btnQuit.setBounds(10, 280, 88, 30);
		contentPane.add(btnQuit);
	}
	
	
	
	
	
}
