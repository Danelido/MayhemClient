package com.alexander.danliden.delend.collision;

import java.awt.Point;

import com.alexander.danliden.delend.entities.models.PlayerMP;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;


public class CheckMultiplayerCollision {

	private Client client;
	private int id;
	public CheckMultiplayerCollision(Client client, int id){
		this.client = client;
		this.id = id;
		
	}
	
	public boolean collision(Point p1, Point p2){
		if(client != null){
			if(client.onlinePlayers != null){
			if(!client.onlinePlayers.isEmpty()){
			for(int i = 0; i < client.onlinePlayers.size(); i++){
				PlayerMP p = null;
				p = client.onlinePlayers.get(i);
				if(p.getId() != id){
				if(p.bounds.contains(p1) || p.bounds.contains(p2)){
					return true;
				}
					
				}
				
			}
			}
			}
			}
		return false;
	}
	
}
