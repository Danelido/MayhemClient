package com.alexander.danliden.delend.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alexander.danliden.delend.entities.models.Player;
import com.alexander.danliden.delend.entities.models.PlayerMP;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;
import com.alexander.danliden.delend.world.World;

public class PlayersOnlineGUI {

	private Player player;
	private Client client;
	private int width, height;
	
	private int x, y,tw,th;
	
	private boolean active = false;
	private Font font;
	private PlayerMP playermp;
	private int gap;
	
	private int staticPositionX;
	
	private CopyOnWriteArrayList<PlayerMP> playerlist = new CopyOnWriteArrayList<PlayerMP>();
	private AffineTransform at;
	private FontRenderContext frc;
	public PlayersOnlineGUI(Player player, Client client){
		this.player = player;
		this.client = client;
		
		font = new Font("Arial", Font.BOLD, 12);
		width = 200;
		height = 300;
		playermp = null;
		x = CORE_V.WINDOW_WIDTH - width - 50;
		y = 50;
		gap = 15;
		 at = new AffineTransform();
		frc = new FontRenderContext(at,true,true);
		staticPositionX = x + gap + 104 + 10;
		
	}
	
	private int updatelistTimer = 0;
	public void update(double delta){
		if(active){
			if(client != null){
				if(client.onlinePlayers.size() <= 10){
			for(int i = 0; i < client.onlinePlayers.size(); i++){
				playermp = client.onlinePlayers.get(i);
				if(!playerlist.contains(playermp))
				playerlist.add(playermp);
			}
			}
			
				for(int i = 0; i < playerlist.size(); i++){
					playermp = playerlist.get(i);
					if(!client.onlinePlayers.contains(playermp)){
						playerlist.remove(playermp);
					}
				}
			
			}
		}
		
	
		
	}
	
	
	public void render(Graphics2D g){
		if(active){
		g.setColor(new Color(0,0,0,150));
		g.fillRect(x, y, width, height);
		g.setColor(new Color(255,255,255,255));
		g.drawRect(x, y, width, height);
		g.setColor(Color.orange);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString("Players online", x + width / 4 - 2,  y+ 20);
		g.drawLine(x, y + 35,x + width, y+35);
		
		g.setFont(font);
		g.setColor(Color.orange);
		g.drawString("Name", x + gap, y + 32);
		g.drawString("Location", x + width - 70, y + 32);
		
		
		if(client != null){
		for(int i = 0; i < playerlist.size(); i++){
			playermp = playerlist.get(i);
			if(playermp.getId() == player.getId()){
				g.setColor(Color.white);
				g.drawString(playermp.getUsername() + " [You]",x + gap , y + ((gap + 5) * i) + 50);
				g.setColor(Color.green);
				g.fillRect((x + width - 10), y + ((gap + 5) * i) + 43, 5 ,5);
				g.setColor(Color.yellow);
				g.drawLine(x, y + ((gap + 5) * i) + 55,x + width,  y + ((gap + 5) * i) + 55);
				
			}else{
				if(frc != null)
				tw = (int)font.getStringBounds(playermp.getUsername(), frc).getWidth();
				
				g.setColor(Color.white);
				if(playermp.bounds.intersects(World.townRectangle)){
					g.drawString(playermp.getUsername(),x + gap , y + ((gap + 5) * i) + 50);
					g.setColor(Color.orange);
					g.drawString("Town",staticPositionX , y + ((gap + 5) * i) + 50);
				}else if(playermp.bounds.intersects(World.battlegroundRectangle)){
					g.setFont(font);
					g.drawString(playermp.getUsername(),x + gap , y + ((gap + 5) * i) + 50);
					g.setColor(Color.red);
					//g.setFont(new Font("Arial", Font.BOLD, 12));
					g.drawString("Arena",staticPositionX, y + ((gap + 5) * i) + 50);
					
				}else{
					g.drawString(playermp.getUsername(),x + gap , y + ((gap + 5) * i) + 50);
					g.setColor(Color.lightGray);
					g.drawString("Unknown",staticPositionX , y + ((gap + 5) * i) + 50);
					
				}
				g.setColor(Color.green);
				g.fillRect((x + width - 10), y + ((gap + 5) * i) + 43, 5 ,5);
				g.setColor(Color.yellow);
				g.drawLine(x, y + ((gap + 5) * i) + 55,x + width,  y + ((gap + 5) * i) + 55);
			}
		}
	}
		}
		
	}
	
	
	
	
	
	
	public void activateORdisable()
	{
		active = !active;
	}
	
	
}
