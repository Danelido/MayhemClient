package com.alexander.danliden.delend.entities;

import java.awt.Graphics2D;

public abstract class Entity {

	protected float x, y;
	protected int movingdir = 3;
	protected boolean isMoving = false;
	
	public Entity(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public abstract void update(double delta);
	public abstract void render(Graphics2D g);

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getMovingdir() {
		return movingdir;
	}

	public void setMovingdir(int movingdir) {
		this.movingdir = movingdir;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	
}
