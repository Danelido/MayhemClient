package com.alexander.danliden.delend.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.alexander.danliden.delend.input.InputHandler;


public class Button extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	private float x, y;
	private int width, height;
	private String buttonMessage;
	
	private boolean isClicked;
	private boolean isHeldOver;
	private int tw, ptw;
	private int th , pth;
	private BufferedImage button_default;
	private BufferedImage button_hoverOver;
	private int fontsize;
	private String identifier;
	private Font font;
	private boolean frame;
	private Color neutralColor, activeColor;
	
	public Button(float x, float y, int width, int height, String buttonMessage, int fontsize, String identifier){
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonMessage = buttonMessage;
		this.fontsize = fontsize;
		this.identifier = identifier;
		isClicked = false;
		isHeldOver = false;
		
		
		
		setBounds((int)x, (int)y, width, height);
		font = new Font("SansSerif", Font.ITALIC, this.fontsize);
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at,true,true);
		tw = (int)font.getStringBounds(buttonMessage, frc).getWidth();
		th = (int)font.getStringBounds(buttonMessage, frc).getHeight();
		
	
		
	}
	
		public Button(float x, float y, int width, int height, String buttonMessage, int fontsize, String identifier, Color neutralColor, Color activeColor, boolean frame){
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonMessage = buttonMessage;
		this.fontsize = fontsize;
		this.identifier = identifier;
		this.neutralColor = neutralColor;
		this.activeColor =activeColor;
		this.frame = frame;
		isClicked = false;
		isHeldOver = false;
		
		
		
		setBounds((int)x, (int)y, width, height);
		font = new Font("SansSerif", Font.ITALIC, this.fontsize);
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at,true,true);
		tw = (int)font.getStringBounds(buttonMessage, frc).getWidth();
		th = (int)font.getStringBounds(buttonMessage, frc).getHeight();
		
	
		
	}
	
	
	public void update(double delta)
	{
		setBounds((int)x, (int)y, width, height);
		
		if(getBounds().contains(InputHandler.mouse)){
			isHeldOver = true;
		}else{
			isHeldOver = false;
		}
		
	}
	
	
	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}


	public void setHeldOver(boolean isHeldOver) {
		this.isHeldOver = isHeldOver;
	}


	public void render(Graphics2D g){
		
		
		if(neutralColor != null)
			g.setColor(neutralColor);
		
		if(neutralColor == null)
			g.setColor(new Color(0,0,0,0));
			
		g.fillRect((int)x, (int)y, width, height);
		
		if(isHeldOver){
			
			if(activeColor == null)
				g.setColor(new Color(0,255,0,150));
			else
				g.setColor(activeColor);
			
			g.fillRect((int)x, (int)y, width, height);
		}
		
		//if(frame){
			g.setColor(Color.white);
			g.drawRect((int)x, (int)y, width, height);
		//}
		
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(buttonMessage, (int)x + width/2 - tw / 2, (int)y + height / 2 + th / 2 - 2 );
		
		

	}
	
	
	public boolean isClicked(){
		return isClicked;
	}
	
	public boolean isHeldOver(){
		return isHeldOver;
	}


	public String getIdentifier() {
		return identifier;
	}
	
}
