package com.alexander.danliden.delend.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.world.World;

public class Screentext {

	private float x, y;
	private String text;
	private Color color;
	private int size;
	
	private int tw,th;
	private Font font;
	private boolean center = false;
	
	public Screentext(float x, float y, String text, int size, Color color){
		this.x=x;
		this.y = y;
		this.text = text;
		this.color = color;
		this.size = size;
		
		font = new Font("Arial", Font.BOLD, size);
		
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at,true,true);
		tw = (int)font.getStringBounds(text, frc).getWidth();
		th = (int)font.getStringBounds(text, frc).getHeight();
		
	}
	
	public Screentext(String text, int size,Color color){
		center = true;
		this.text = text;
		this.color = color;
		this.size = size;
		
		font = new Font("Arial", Font.BOLD, size);
		
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at,true,true);
		tw = (int)font.getStringBounds(text, frc).getWidth();
		th = (int)font.getStringBounds(text, frc).getHeight();
		
	}
	
	public void update(){
		
	}
	
	public void render(Graphics2D g){
		g.setFont(font);
		g.setColor(color);
		if(center && World.gamecamera != null){
		g.drawString(text,(int)(CORE_V.WINDOW_WIDTH/2 - tw / 2 ),
				(int)(CORE_V.WINDOW_HEIGHT / 2 + th + 100));
		
		}
		else if(!center && World.gamecamera != null){
			g.drawString(text,(int)(x) , (int)(y));
		}
	}
	
	
}
