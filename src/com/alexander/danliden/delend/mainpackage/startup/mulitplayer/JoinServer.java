package com.alexander.danliden.delend.mainpackage.startup.mulitplayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.alexander.danliden.delend.mainpackage.Main;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.mainpackage.startup.Launcher;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JoinServer extends JFrame {
	private static final long serialVersionUID = 1L;
	/*********************************************************
	 * 
	 * RAW SEARCH CLASS, WORKS THE SAME AS OUR SEVERLIST CLASS
	 * 
	 *********************************************************/
	
	JLabel lblSearching;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtIP;
	private JTextField txtPort;
	private Launcher launcher;
	public JLabel lblErrorMsg;
	private String username, address, port;
	private JFrame frame;
	public boolean hey = false;
	private JoinServer joinserver;
	int portNumber;
	private Serverlist serverlist;
	
	private int app_w = 350;
	private int app_h = 400;
	
	
	public JoinServer(Launcher launcher, Serverlist serverlist) {
		this.launcher = launcher;
		this.serverlist = serverlist;
		joinserver = this;
		setResizable(false);
		setTitle(CORE_V.WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(app_w,app_h);
		setLocationRelativeTo(null);
		setVisible(true);
		createWindow();
		frame = this;
	}
	
	private void createWindow(){
		// If someone presses the close button [X] 
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
			dispose();
			System.gc(); // Clean up shit
			serverlist.setVisible(true);
			}
			
		});
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGametag = new JLabel("Gametag: ");
		lblGametag.setForeground(Color.WHITE);
		lblGametag.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
		lblGametag.setBounds(10, 48, 69, 19);
		contentPane.add(lblGametag);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
		txtUsername.setBounds(10, 78, 86, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblServerIp = new JLabel("Server IP");
		lblServerIp.setForeground(Color.WHITE);
		lblServerIp.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
		lblServerIp.setBounds(10, 140, 69, 19);
		contentPane.add(lblServerIp);
		
		txtIP = new JTextField();
		txtIP.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
		txtIP.setBounds(10, 170, 156, 20);
		contentPane.add(txtIP);
		txtIP.setColumns(10);
		
		JLabel label = new JLabel(":");
		label.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
		label.setBounds(176, 173, 19, 14);
		contentPane.add(label);
		
		txtPort = new JTextField();
		txtPort.setBounds(186, 170, 69, 20);
		contentPane.add(txtPort);
		txtPort.setColumns(10);
		
		JLabel lblServerPort = new JLabel("Server Port");
		lblServerPort.setForeground(Color.WHITE);
		lblServerPort.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
		lblServerPort.setBounds(186, 140, 86, 19);
		contentPane.add(lblServerPort);
		
		JButton btnJoin = new JButton("Search & Join");
		btnJoin.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean clearToSearch = false;
				if(lblErrorMsg.isVisible()){
				lblErrorMsg.setVisible(false);
				}
				
				if(txtUsername.getText().length() != 0){
				 username = txtUsername.getText();
				}
				if(txtIP.getText().length() != 0){
					address = txtIP.getText();
				}
				if(txtPort.getText().length() != 0){
					port = txtPort.getText();
				}
				
				if(txtUsername.getText().length() < 3 || txtUsername.getText().length() > 10 ){
					JOptionPane.showMessageDialog(frame,"Username needs to be between 3 to 10 characters", "Retard alert", JOptionPane.ERROR_MESSAGE);
				}else{
					if(txtIP.getText().length() == 0){
						JOptionPane.showMessageDialog(frame,"Enter server IP", "Retard alert", JOptionPane.ERROR_MESSAGE);
					}else{
						if((txtPort.getText().length() == 0)){
							JOptionPane.showMessageDialog(frame,"Enter server port", "Retard alert", JOptionPane.ERROR_MESSAGE);
						}
	
				}
				
				try{
					portNumber = Integer.parseInt(port);
					clearToSearch = true;
				}catch(NumberFormatException nfe){
					clearToSearch = false;
					JOptionPane.showMessageDialog(frame,"Port can't contain letters", "Retard alert", JOptionPane.ERROR_MESSAGE);
				}
				}
				if(clearToSearch){
				lblSearching.setVisible(true);	
				ServerChecker checker = new ServerChecker(username, address, portNumber, joinserver);
				checker.start();
				String checkServer = "doyouexist";
				checker.send(checkServer.getBytes());
				}
			
			}
			});
		btnJoin.setForeground(Color.WHITE);
		btnJoin.setBackground(Color.DARK_GRAY);
		btnJoin.setBounds(10, 227, 125, 30);
		contentPane.add(btnJoin);
		
		JLabel lblLanNetwork = new JLabel("RAW Search");
		lblLanNetwork.setForeground(Color.WHITE);
		lblLanNetwork.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
		lblLanNetwork.setBounds(127, 11, 100, 19);
		contentPane.add(lblLanNetwork);
		
		lblErrorMsg = new JLabel("That server do not exist. :(");
		lblErrorMsg.setForeground(Color.RED);
		lblErrorMsg.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
		lblErrorMsg.setBounds(81, 302, 182, 14);
		lblErrorMsg.setVisible(false);
		contentPane.add(lblErrorMsg);
		
		lblSearching = new JLabel("Searching...");
		lblSearching.setForeground(Color.WHITE);
		lblSearching.setVisible(false);
		lblSearching.setFont(new Font("Comic Sans MS", Font.ITALIC, 13));
		lblSearching.setBounds(129, 272, 86, 19);
		contentPane.add(lblSearching);
		
		
		JLabel backgroundLbl = new JLabel(launcher.assets.Application_backgroundIcon_RAW);
		//backgroundLbl.setIcon(Assets.Application_backgroundIcon);
		backgroundLbl.setBounds(0,0,app_w,app_h);
		backgroundLbl.setVisible(true);
		contentPane.add(backgroundLbl);
		
		
		
		
	}
	
	public void startgame(){
		lblSearching.setVisible(false);	
		dispose();
		Main.main(null, true, username,address,Integer.parseInt(port));
		
	}
	
	public void failedSearch(boolean status){
		lblSearching.setVisible(false);	
		lblErrorMsg.setVisible(status);
	}
}
