package com.alexander.danliden.delend.mainpackage.startup.mulitplayer;



import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alexander.danliden.delend.mainpackage.Main;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.mainpackage.startup.Launcher;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class Serverlist extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	public DefaultListModel listModel;
	private JTextField txtUsername;
	private Serverlist serverlist;
	private Launcher launcher;
	private JButton btnJoinServer;
	private JFrame frame;
	private JButton btnRefresh;
	private Color btn_color = new Color(40,40,40);
	private Font font = new Font("Comic Sans MS", Font.ITALIC, 12);
	
	private int app_w = 450;
	private int app_h = 240;
	
	private JoinServer js;
	
	public Serverlist(Launcher launcher) {
		setTitle(CORE_V.WINDOW_TITLE);
		serverlist = this;
		this.launcher = launcher;
		this.frame = this;
		
		close();
		components();
		searchForServer();
		
		
		
	}
	
	private void close(){
		// If someone presses the close button [X] 
				addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e){
					dispose();
					System.gc(); // Clean up shit
					launcher.setVisible(true);
					}
					
				});
	}
	
	@SuppressWarnings("rawtypes")
	private void components(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(app_w,app_h);
		setLocationRelativeTo(null);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		listModel = new DefaultListModel();
		@SuppressWarnings("unchecked")
		JList list = new JList(listModel);
		list.setForeground(Color.BLACK);
		list.setBackground(Color.LIGHT_GRAY);
		list.setFont(font);
		list.setBounds(10, 110, 415, 76);
	
		contentPane.add(list);
		
		
		
		
		
		
		
		JLabel lblAvailableServers = new JLabel("Available servers");
		lblAvailableServers.setForeground(Color.WHITE);
		lblAvailableServers.setBackground(Color.WHITE);
		lblAvailableServers.setFont(font);
		lblAvailableServers.setBounds(10, 81, 113, 18);
		contentPane.add(lblAvailableServers);
		
		btnJoinServer = new JButton("Join server");
		btnJoinServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Well, make a proper god damn name
				if(txtUsername.getText().length() < 3 || txtUsername.getText().length() > 10 ){
					JOptionPane.showMessageDialog(frame,"Username needs to be between 3 to 10 characters", "Woopsi", JOptionPane.ERROR_MESSAGE);
				}else{
					// Get the selected server(Obviously the main sever)
					if(list.getSelectedIndex() == 0){
						startgame(txtUsername.getText().trim(),CORE_V.getDefaultGatewayIP().trim(), CORE_V.getDefaultPort());
					}
				}
				
			}
		});
		btnJoinServer.setFont(font);
		btnJoinServer.setForeground(Color.WHITE);
		btnJoinServer.setFocusPainted(false);
		btnJoinServer.setBackground(btn_color);
		btnJoinServer.setBounds(122, 76, 102, 23);
		contentPane.add(btnJoinServer);
		
		btnRefresh = new JButton("Search");
		btnRefresh.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				
				
				searchForServer();
				
				
				
				
			}
		});
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setFont(font);
		btnRefresh.setBackground(btn_color);
		btnRefresh.setBounds(223, 76, 89, 23);
		btnRefresh.setFocusPainted(false);
		contentPane.add(btnRefresh);
		
		JButton btnRawSearch = new JButton("Raw search"); // Raw search, mostly for debug purpose but still useful. 
		btnRawSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc(); // Clean up unused shit
				if(js == null){
				js = new JoinServer(launcher, serverlist);
				}
				else {
					js.setVisible(true);
				}
			}
		});
		btnRawSearch.setFont(font);
		btnRawSearch.setForeground(Color.WHITE);
		btnRawSearch.setBackground(btn_color);
		btnRawSearch.setBounds(307, 76, 102, 23);
		btnRawSearch.setFocusPainted(false);
		contentPane.add(btnRawSearch);
		
		JLabel lblGametag = new JLabel("Gametag");
		lblGametag.setForeground(Color.WHITE);
		lblGametag.setFont(font);
		lblGametag.setBackground(Color.WHITE);
		lblGametag.setBounds(10, 21, 73, 18);
		contentPane.add(lblGametag);
		
		txtUsername = new JTextField();
		txtUsername.setFont(font);
		txtUsername.setBounds(10, 50, 89, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		
		if(listModel.size() == 0){
			btnJoinServer.setEnabled(false);
		}
		
		
		JLabel backgroundLbl = new JLabel(launcher.assets.Application_backgroundIcon);
		//backgroundLbl.setIcon(Assets.Application_backgroundIcon);
		backgroundLbl.setBounds(0,0,app_w,app_h);
		backgroundLbl.setVisible(true);
		contentPane.add(backgroundLbl);
		
	}
	
	@SuppressWarnings("unchecked")
	public void addMainServerToList(int connectedplayers){
		// Remove
		if(listModel.size() != 0){
			for(int i = 0; i < listModel.size(); i++){
				listModel.remove(i);
			}
			}
		
		// Add the server to our list.
		//NOTE: This will be called from within the checker class
		listModel.addElement("Main server: " + "Players: " + connectedplayers);
		
		if(listModel.size() > 0 ){
			btnJoinServer.setEnabled(true);
		}else
			btnJoinServer.setEnabled(false);
		
	}
	
	public void startgame(String username, String address, int port){
		dispose(); // Dispose the ugly menu and start the game
		System.gc(); // Clean up unused shit
		Main.main(null, true, username,address,port); 
		// Note: IF we got a response, the game will be started from within the checker class!
		
		
	}
	
	private void searchForServer(){
		
		if(listModel.size() != 0){
			for(int i = 0; i < listModel.size(); i++){
				listModel.remove(i);
			}
			}
	
		listModel.addElement("Hold on... Searching");
		
		ServerChecker checker = new ServerChecker(CORE_V.getDefaultGatewayIP().trim(), CORE_V.getDefaultPort(), serverlist); // Server checker, checks if there is an available server online
		checker.start(); // Start the checker up
		String checkServer = "-2"; // Our message to the main server
		checker.send(checkServer.getBytes());	// Send this message (package) and hope for a response
		// NOTE: Ping - Pong idea, if we get a response the server will be added to our list
	}
	
	public void refreshButtonEnabled(boolean b){
		btnRefresh.setEnabled(b);
	}
	
}
