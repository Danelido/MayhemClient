package com.alexander.danliden.delend.entities.models;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import com.alexander.danliden.delend.entities.Entity;
import com.alexander.danliden.delend.mainpackage.Resources;
import com.alexander.danliden.delend.world.World;

public class transportNPC extends Entity {

	public Rectangle bounds;
	private int width,height;
	private String NPC_NAME = "Transporter";
	private Font font;
	int tw,th;
	private Player player;
	
	// NOTE FOR SELF, THESE GUYS ARE ADDED AND INITIALIZED IN CLIENT CLASS, 1 hour wasted of my life
	public transportNPC(float x, float y, Player player) {
		super(x, y);
		this.player = player;
		width = 64;
		height = 64;
		bounds = new Rectangle((int)(x - World.gamecamera.getxOffset()), (int)(y - World.gamecamera.getyOffset() + 70), width, height + 10);
		font = new Font("Arial", Font.BOLD, 14);
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at,true,true);
		tw = (int)font.getStringBounds(NPC_NAME, frc).getWidth();
		th = (int)font.getStringBounds(NPC_NAME, frc).getHeight();
	}

	@Override
	public void update(double delta) {
		bounds.setBounds((int)(x - World.gamecamera.getxOffset()), (int)(y - World.gamecamera.getyOffset() + 70), width, height + 10);
		
		
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.orange);
		g.setFont(font);
		g.drawString(NPC_NAME,(int)(x - World.gamecamera.getxOffset() + width / 2 - tw / 2), 
				(int)(y - World.gamecamera.getyOffset() - th + 5));
		g.drawImage(Resources.transporterguy,
				(int)(x - World.gamecamera.getxOffset()), 
				(int)(y - World.gamecamera.getyOffset()),
				width- 2, height -2, null);
		
		//g.drawRect((int)(x - World.gamecamera.getxOffset()), (int)(y - World.gamecamera.getyOffset() + 70), width, height);
		
	}

}
