package com.alexander.danliden.delend.entities.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import com.alexander.danliden.delend.mainpackage.Resources;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;
import com.alexander.danliden.delend.managers.HUDmanagerMP;
import com.alexander.danliden.delend.world.World;

public class PlayerMP extends Player {

	private World world;
	private int id;
	private String username;
	public Rectangle bounds, hitbox;
	private float health = 100f;
	private float mana = 100f;
	private Client client;
	
	
	private HUDmanagerMP hud;
	
	AffineTransform aff = new AffineTransform();
	FontRenderContext frc = new FontRenderContext(aff,true,true);
	
	int nameWidth, nameHeight;
	public PlayerMP(String username, float x, float y, int id, World world) {
		super(x, y, world);
		this.id = id;
		this.username = username;
		this.world = world;
		bounds = new Rectangle((int)x,(int)y, 25,25);
		hitbox = new Rectangle((int)x, (int)y, 25,25);
		hud = new HUDmanagerMP(health,mana, x ,y);
		nameWidth = (int)(Resources.font.getStringBounds((username).trim(), frc).getWidth());
		nameHeight = (int)(Resources.font.getStringBounds((username).trim(), frc).getHeight());
		
	}
	
	public PlayerMP(String username, float x, float y, Client client, int id, World world){
		super(x,y,client, world, id);
		this.id = id;
		this.client = client;
		this.username = username;
		this.world = world;
		bounds = new Rectangle((int)x,(int)y, 25,25);
		hitbox = new Rectangle((int)x, (int)y, 25,25);
		hud = new HUDmanagerMP(health,mana, x ,y);
		nameWidth = (int)(Resources.font.getStringBounds((username).trim(), frc).getWidth());
		nameHeight = (int)(Resources.font.getStringBounds((username).trim(), frc).getHeight());
	}
	
	public void update(double delta){
		bounds.setBounds((int)(x - World.gamecamera.getxOffset() + 8),(int)(y - World.gamecamera.getyOffset()- 5), 25,30);
		hitbox.setBounds((int)(x - World.gamecamera.getxOffset() + 8),(int)(y - World.gamecamera.getyOffset()- 24), 30,48);
		super.update(delta);
		hud.updatePosition(x, y);
		
		if(health <= 0){
			health = 0;
		}
		if(mana <= 0){
			mana = 0;
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void render(Graphics2D g){
		super.render(g);
		
		g.setFont(Resources.font);
		g.setColor(Color.orange);
		g.drawString(username,
				(int)(x - World.gamecamera.getxOffset()  - CORE_V.WINDOW_WIDTH / 2 + 32  + CORE_V.WINDOW_WIDTH/2 - nameWidth / 2),
				(int)(y- World.gamecamera.getyOffset() - nameHeight - 45 ));
		hud.render(g);
		
		
		
	}

	public String getUsername() {
		return username;
	}
	
	public void setMana(float mana){
		this.mana = mana;
		hud.setMana(mana);
	}
	
	public void setHealth(float health){
		this.health = health;
		hud.setHealth(health);
	}

	public float getHealth() {
		return health;
	}
	public float getMana(){
		return mana;
	}


}
