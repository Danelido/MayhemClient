package com.alexander.danliden.delend.bouncytext;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.Random;

import com.alexander.danliden.delend.mainpackage.Main;
import com.alexander.danliden.delend.mainpackage.Resources;
import com.alexander.danliden.delend.world.World;

public class BouncyText {

	private String text;
	private float x, y;
	
	private boolean isAlive;
	private boolean jump;
	private boolean right;
	private boolean left;
	
	private float lifeTime = 0.5f;
	private float maxUp;
	private float currUp;
	
	private boolean healing;
	private boolean mana;
	
	public BouncyText(float x, float y, String text , boolean healing, boolean mana)
	{
		if(healing&&mana)Main.fatalError("BouncyTextClass- Healing and mana both true, change it!");
		this.x = x;
		this.y = y;
		this.text = text;
		this.mana = mana;
		isAlive = true;
		jump = true;
		Random ran = new Random();
		int rans = ran.nextInt(2);
		
		Random bounce = new Random();
		int bounceAmount = ran.nextInt(4) + 1;
		maxUp = bounceAmount;
		
		switch(bounceAmount){
		case 1:
			y -= 5;
			break;
		case 2:
			y += 5;
			break;
			
		case 3:
			x += 5;
			break;
			
		case 4:
			x -= 5;
			break;
		}
		
		switch(rans){
		case 0:
			left = false;
			right = true;
			break;
		case 1:
			right = false;
			left = true;
			break;
		}
		
		
		
		
		this.healing = healing;
		
	}
	private Color color;
	public BouncyText(float x, float y, String text , boolean healing, Color color)
	{
		this.x = x;
		this.y = y;
		this.text = text;
		this.color = color;
		isAlive = true;
		jump = true;
		Random ran = new Random();
		int rans = ran.nextInt(2);
		
		switch(rans){
		case 0:
			left = false;
			right = true;
			break;
		case 1:
			right = false;
			left = true;
			break;
		}
		
		
		this.healing = healing;
		
	}
	
	
	public void update(double delta)
	{
	
		if(isAlive){
			if(lifeTime != 0){
				lifeTime -= 0.01f;
				
			}
			if(lifeTime <= 0)
			{
				isAlive = false;
			}
			
			if(jump)
			{
				if(currUp != maxUp){
					currUp += 0.5;
					y -= currUp;
				}
				if(currUp >= maxUp){
					jump = false;
				}
				
				
				if(right)
				{
					x += currUp * new Random().nextFloat() * maxUp;
				}
				
				
				if(left)
				{
					x -= currUp * new Random().nextFloat() * maxUp;
				}
	
			}
			if(!jump)
			{
				if(currUp != 0){
					currUp -= 0.5  ;
					y -= currUp;
				}
				
				if(currUp <= 0){
					maxUp -= 0.3;
					jump = true;
				}
			}
			
			
		}else{
			
		}
		
		
	}
	
	public void render(Graphics2D g)
	{
		if(isAlive && !healing && !mana){
			g.setFont(Resources.damageFont);
			if(this.color == null){
			g.setColor(Color.orange);
			}else{
				g.setColor(color);
			}
			g.drawString(new DecimalFormat("#.##").format(Float.parseFloat(text)), (int)(x - World.gamecamera.getxOffset()),
								(int)(y - World.gamecamera.getyOffset()));
			g.setColor(Color.white);
		}
		
		if(healing && isAlive){
			g.setFont(Resources.damageFont);
			g.setColor(Color.green);
			g.drawString("+" + new DecimalFormat("#.##").format(Float.parseFloat(text)), (int)(x - World.gamecamera.getxOffset()),
					(int)(y - World.gamecamera.getyOffset()));
			g.setColor(Color.white);
		}
		if(mana && isAlive){
			g.setFont(Resources.damageFont);
			g.setColor(Color.blue);
			g.drawString("+" + new DecimalFormat("#.##").format(Float.parseFloat(text)), (int)(x - World.gamecamera.getxOffset()),
					(int)(y - World.gamecamera.getyOffset()));
			g.setColor(Color.white);
		}
		
		
	}
	
}
