package com.alexander.danliden.delend.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import com.alexander.danliden.delend.entities.models.Player;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;

public class DeathNotice {

	private String deathNotice;
	private float x,y;
	private Font font;
	private int tw,th;
	private int size;
	private int destroyTimer = 0;
	private int s;
	
	public  DeathNotice(String deathNotice, int size){
		this.deathNotice = deathNotice;
		this.size = size;
		this.s = size + 1;
		
		font = new Font("Arial", Font.BOLD, 13);
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at,true,true);
		tw = (int)font.getStringBounds(deathNotice, frc).getWidth();
		th = (int)font.getStringBounds(deathNotice, frc).getHeight();
	
		this.x = CORE_V.WINDOW_WIDTH- tw - 35;
		this.y = CORE_V.WINDOW_HEIGHT / 2  + ( - th - 10) * size ;
		
	}
	
	public void update(double delta){
		destroyTimer++;
		this.y = CORE_V.WINDOW_HEIGHT / 2  + ( th ) * size ;
		if(destroyTimer >= 60 * (10)){
			Player.deleteDeathNotice(this);
		}
	
	}
	
	public void updateSize(int size){
		this.size = size;
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.orange);
		g.setFont(font);
		g.drawString(deathNotice , x, y);
		
	}
	
	
}
