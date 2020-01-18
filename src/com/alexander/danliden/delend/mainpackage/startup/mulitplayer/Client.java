package com.alexander.danliden.delend.mainpackage.startup.mulitplayer;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.alexander.danliden.delend.chatprogram.Frame;
import com.alexander.danliden.delend.entities.models.Player;
import com.alexander.danliden.delend.entities.models.PlayerMP;
import com.alexander.danliden.delend.entities.models.transportNPC;
import com.alexander.danliden.delend.gamecamera.Gamecamera;
import com.alexander.danliden.delend.mainpackage.Main;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.projectiles.Projectile;
import com.alexander.danliden.delend.utils.DeathNotice;
import com.alexander.danliden.delend.world.Block;
import com.alexander.danliden.delend.world.World;

public class Client extends Thread {

	
	
	/*    	NETWORK PACKETS
	 * 		packetdelay - ping
	 * 		00 - Login
	 * 		01 - Disconnect
	 * 		02 - ping	
	 * 		03 - new player
	 * 		04 - player moving				ID at [2]
	 * 		05 - (health)     ID at [2]
	 * 		06 - projectile creation			ID at [5]
	 * 		07 - attackState					ID at [1]
	 * 		08 - teleport animation		ID at [1]
	 * 		09 - phases ( animation)		ID at [2]
	 * 		10 - Show Damage text					    ID at [2]			
	 * 		11 - someone died (PVP)
	 * 		12 - transport animation(online) 
	 * 		13 - (Mana) 
	 * 		ch - chat data
	 * 		666 - display ping > NOT FUNCTIONAL
	 *		99 - checker 
	*/
	
	private DatagramSocket socket;
	private int port;
	private InetAddress ip;
	private String username,address;
	
	private int ID = -1;
	private World world;
	
	public List<PlayerMP> onlinePlayers = new ArrayList<PlayerMP>();
	private float x, y;
	private boolean getExistingPlayersCoord = false;
	private boolean SendPing = true;
	
	public Frame frame;
	
	public Client(String username, String address, int port, World world){
		this.username = username;
		this.address = address;
		this.port = port;
		this.world = world;
		
		
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (SocketException e) {
			
			e.printStackTrace();
		} catch (UnknownHostException e) {
	
			e.printStackTrace();
		}
		
		
	}
	
	
	public void run(){
		while(true){
			receive();
		}
	}
	
	private void receive(){
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data,data.length);
		
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		process(packet);
		
	}
	

	
	private void process(DatagramPacket packet){
		String fulldata = new String(packet.getData()).trim();
		String messagetype = fulldata.substring(0, 2);
		String message = fulldata.substring(2);
		
		
		if(messagetype.equals("-1")){
			Main.fatalError("You have been kicked from the server. Game is closing down");
		}
		
		if(messagetype.equalsIgnoreCase("00")){
			String[] ValuableInformation = message.split(",");
			setID(Integer.parseInt(ValuableInformation[0]));
			setUsername(ValuableInformation[1]);
			System.out.println("You are connected to the server. " + getUsername() + "(" + getID() + ")");
			World.gamecamera = new Gamecamera(0,0);
			World.gamecamera.move(0, 0);
			PlayerMP player = null;
			player = new PlayerMP( getUsername(),150,150, this, getID(), world);
			onlinePlayers.add(player);
			world.addEntity(player);
			World.player = player;
			// Add NPC
			transportNPC transportTOWN = new transportNPC(7 * Block.BlockSize,-5, World.player);
			transportNPC transportBATTLEGROUND = new transportNPC(108 * Block.BlockSize,79 * Block.BlockSize, World.player);
			world.addEntity(transportTOWN);
			World.player.addTransporters(transportTOWN);
			world.addEntity(transportBATTLEGROUND);
			World.player.addTransporters(transportBATTLEGROUND);
			
			//___________
			world.render = new Rectangle((int)World.player.getX(), (int)World.player.getY(), CORE_V.WINDOW_WIDTH - 64, CORE_V.WINDOW_HEIGHT - 64);
			World.playerBounds = new Rectangle((int)(player.getX() + player.getWidth() / 2),
					(int)(player.getY() + player.getHeight() / 2),
					25,25);
			World.player.screenbounds = new Rectangle((int)World.player.getX(), (int)World.player.getY(),  CORE_V.WINDOW_WIDTH, CORE_V.WINDOW_HEIGHT);
			World.gamecamera.centerOnPlayer(World.player);
			
			
			//Start up the chatwindow
			frame = new Frame(Main.WINDOW_FRAME, this, username);
			frame.initializeAndStart();
			
		} else if(messagetype.equals("01")){
			String[] ValuableInformation = message.split(",");
			for(int i = 0; i < onlinePlayers.size(); i++){
				PlayerMP p = onlinePlayers.get(i);
				if(p.getId() == Integer.parseInt(ValuableInformation[1])){
					onlinePlayers.remove(p);
					World.entities.remove(p);
					if(ValuableInformation[0].equalsIgnoreCase("true")){
						System.out.println(p.getUsername() + "(" + ValuableInformation[1] + ") has disconnected from the server.");
						
						
					}else if(ValuableInformation[0].equalsIgnoreCase("false")){
						System.out.println(p.getUsername() + "(" + ValuableInformation[1] + ") timed out from the server.");
					}
					break;
				}
			}
			
		} else if(messagetype.equalsIgnoreCase("02")){
			String response = "02";
			send((response + getID()).getBytes());
			
		} else if(messagetype.equalsIgnoreCase("03")){
			String[] ValuableInformation = message.split(",");
			int id = Integer.parseInt(ValuableInformation[0]);
			PlayerMP player = null;
			player = new PlayerMP(ValuableInformation[1], 150,150, id,null);
			onlinePlayers.add(player);
			world.addEntity(player);
			setGetExistingPlayersCoord(true);
			
		} else if(messagetype.equalsIgnoreCase("04")){
			String[] coords = message.split(",");
			movePlayers(
					Float.parseFloat(coords[0]),
					Float.parseFloat(coords[1]),
					Integer.parseInt(coords[2]),
					Integer.parseInt(coords[3]),
					Boolean.parseBoolean(coords[4])		
					);
			
			
		}else if(messagetype.equalsIgnoreCase("05")){
			String[] information = message.split(",");
			setHealth(Float.parseFloat(information[0]), Integer.parseInt(information[1]) , Integer.parseInt(information[2]));
		}else if(messagetype.equalsIgnoreCase("06")){
			String[] information = message.split(",");
		
			
			createProjectile(Float.parseFloat(information[0]),
							 Float.parseFloat(information[1]),
							 Double.parseDouble(information[2]),
							 Double.parseDouble(information[3]),
							 Double.parseDouble(information[4]),
							 Integer.parseInt(information[5]),
							 information[6].trim());
			
		}else if(messagetype.equals("07")){
			String[] information = message.split(",");
			setPlayerAttackState(Boolean.parseBoolean(information[0]),
					Integer.parseInt(information[1]),
					Integer.parseInt(information[2]));
			
			
		}else if(messagetype.equals("08")){
		String[] information = message.split(",");
			playerTeleporting(
					Boolean.parseBoolean(information[0]),
					Integer.parseInt(information[1]),
					Float.parseFloat(information[2]),
					Float.parseFloat(information[3]),
					Float.parseFloat(information[4]),
					Float.parseFloat(information[5]));
			
			
			
		}else if(messagetype.equals("09")){
			String[] information = message.split(",");
			playerPhase(Boolean.parseBoolean(information[0]), Boolean.parseBoolean(information[1]), Integer.parseInt(information[2]));
		}else if(messagetype.equals("10")){
			String[] information = message.split(",");
			showDamage(information[0].trim(), Integer.parseInt(information[1]), Integer.parseInt(information[2]));
		}else if(messagetype.equals("11")){
			String[] information = message.split(",");
			addDeathnotice(information[0].trim(), information[1].trim(), Integer.parseInt(information[2]));
		}else if(messagetype.equals("12")){
			String[] information = message.split(",");
			PlayerMP p = null;
			for(int i = 0; i < onlinePlayers.size(); i++){
				p = onlinePlayers.get(i);
				if(p.getId() == Integer.parseInt(information[1])){
					p.setMultiplayerTransporting(Boolean.parseBoolean(information[0]));
				}
			}
		}else if(messagetype.equals("13")){
			String[] information = message.split(",");
			setMana(Float.parseFloat(information[0]), Integer.parseInt(information[1]));
		}else if(messagetype.equals("ch")){
			String information[] = message.split(",");
			
			if(information.length <= 1) return;
			
			updateChat(information[0].trim(), information[1].trim());
			
		}
		
		else if(fulldata.equals("666")){
			setSendPing(true);
		}
		
		
		
		
		else if(messagetype.equalsIgnoreCase("99")){
			System.out.println("SERVER: " + "Checking");
			System.out.println("OnlinePlayer: " + onlinePlayers.size() + "\nEntities: " + World.entities.size());
		}else{
			System.out.println("UNKNOWN TYPE: " + message);
		}
		
	}
	
	


	public void send(final byte[] data){
		DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	private void updateChat(String username, String information) {
		frame.appendChat(username, information);
		
	}
	
	
	private void addDeathnotice(String attacker, String deadguy, int arraysize){
		Player.deathlist.add(new DeathNotice(attacker + " killed " +deadguy, arraysize));
	}
	
	private void playerPhase(boolean fire, boolean light, int id){
		PlayerMP p = null;
		for(int i = 0; i < onlinePlayers.size(); i++){
			p = onlinePlayers.get(i);
			if(p.getId() == id){
				p.setFirePhased(fire);
				p.setLightPhased(light);
				p.setGlowing(light);
			}
			
			
		}
		
	}
	
	private void playerTeleporting(boolean teleport, int id, float pastX, float pastY, float currX, float currY){
		PlayerMP p = null;
		for(int i = 0; i < onlinePlayers.size(); i++){
			p = onlinePlayers.get(i);
			if(p.getId() == id){
				p.setTeleporting(true);
				p.setTeleportPastX(pastX);
				p.setTeleportPastY(pastY);
				p.setTeleportCurrX(currX);
				p.setTeleportCurrY(currY);
				
			}
			
			
			
		}
	}
	
	private void setPlayerAttackState(boolean b, int id, int dir){
		PlayerMP p = null;
		for(int i = 0; i < onlinePlayers.size(); i++){
			p = onlinePlayers.get(i);
			if(p.getId() == id){
			
			if(dir == 1)
				p.setAttackStateUp(b);	
			
			if(dir == 2)
				p.setAttackStateRight(b);	
			
			if(dir == 3)
				p.setAttackStateDown(b);	
			
			if(dir == 4)
				p.setAttackStateLeft(b);	
			
			}
			
			
		}
	}
	
	private void createProjectile(float x, float y, double angleX, double angleY, double rotation, int id, String type){
		
		if(getID() != id){
			Player.projectiles.add(new Projectile(x,y,angleX,angleY,rotation, this,id,type,false));

		}else{
			if(World.player.bounds.intersects(World.battlegroundRectangle)){
			Player.projectiles.add(new Projectile(x,y,angleX,angleY,rotation, this,id,type,true));
			}
			else{
				Player.projectiles.add(new Projectile(x,y,angleX,angleY,rotation, this,id,type,false));
			}
		}
		
		
	}
	
	private void movePlayers(float x, float y, int id, int movingDir, boolean isMoving){
		PlayerMP p = null;
		for(int i = 0; i < onlinePlayers.size(); i++){
			p = onlinePlayers.get(i);
			if(p.getId() == id && p.getId() != ID){
			p.setX(x);
			p.setY(y);
			p.setMovingdir(movingDir);
			p.setMoving(isMoving);
			
			}
		}
	}
	
	
	
	private void setMana(float mana, int id){
		PlayerMP p = null;
		for(int i = 0; i < onlinePlayers.size(); i++){
			p = onlinePlayers.get(i);
			if(p.getId() == id){
				p.setMana(mana);		
				
			}
		}
	}
	
	private void setHealth(float health, int id, int attackerID){
		PlayerMP p = null;
		String attackername = "";
		for(int j = 0; j < onlinePlayers.size(); j++){
			p = onlinePlayers.get(j);
				if(p.getId() == attackerID){
					attackername = p.getUsername();
				

			}	
}

		for(int i = 0; i < onlinePlayers.size(); i++){
			p = onlinePlayers.get(i);
			if(p.getId() == id){
				p.setHealth(health);		
				p.setLatestAttacker(attackername);
			}
		}
		

	
	}
		
		
	
	
	private void showDamage(String damage, int playerHitID, int shooterID){
		PlayerMP p = null;
		float enemyX = 0, enemyY = 0;
		boolean findEnemy = false;
		
		for(int i = 0; i < onlinePlayers.size(); i++){
			p = onlinePlayers.get(i);
			if(p.getId() == playerHitID){
				enemyX =  p.getX();
				enemyY = p.getY();
				findEnemy = true;
				
			}
		
		}
		if(findEnemy){
		for(int i = 0; i < onlinePlayers.size(); i++){
			p = onlinePlayers.get(i);
			if(p.getId() == shooterID){
				p.HitSomething(damage, enemyX, enemyY);
				
			}
		
		}
		}
		
		
		
		
	}
	

	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public InetAddress getIp() {
		return ip;
	}


	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public boolean isGetExistingPlayersCoord() {
		return getExistingPlayersCoord;
	}


	public void setGetExistingPlayersCoord(boolean getExistingPlayersCoord) {
		this.getExistingPlayersCoord = getExistingPlayersCoord;
	}


	public boolean isSendPing() {
		return SendPing;
	}


	public void setSendPing(boolean sendPing) {
		SendPing = sendPing;
	}
	
}
