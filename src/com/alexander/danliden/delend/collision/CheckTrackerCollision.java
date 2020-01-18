package com.alexander.danliden.delend.collision;

import java.awt.Rectangle;

import com.alexander.danliden.delend.entities.models.PlayerMP;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;

public class CheckTrackerCollision {

	private Client client;
	private int id;
	
	public CheckTrackerCollision(Client client, int id){
		this.client = client;
		this.id = id;
	}
	
	public boolean collision(Rectangle Screenbounds){
		if(client != null){
			if(client.onlinePlayers != null){
				if(!client.onlinePlayers.isEmpty()){
					
					for(PlayerMP players: client.onlinePlayers){
						if(players.getId() != id){
							if(players.bounds.contains(Screenbounds)){
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
