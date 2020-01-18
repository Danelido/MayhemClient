package com.alexander.danliden.delend.gamecamera;

import com.alexander.danliden.delend.entities.models.Player;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;


public class Gamecamera {

	private float xOffset, yOffset;
	
	public Gamecamera(float xOffset, float yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		
	}
	
	public void move(float xAmt, float yAmt){
		xOffset += xAmt;
		yOffset += yAmt;
	}
	
	
	public void centerOnPlayer(Player p){
		if(p != null){
		xOffset = p.getX()  - (CORE_V.WINDOW_WIDTH / 2) + (p.getWidth() / 2);
		yOffset = p.getY() - (CORE_V.WINDOW_HEIGHT / 2)  +  (p.getHeight() / 2);
		}
		}
	
	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}


	
}
