package com.alexander.danliden.delend.projectiles;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import com.alexander.danliden.delend.animation.Animator;
import com.alexander.danliden.delend.collision.CheckPlayerCollision;
import com.alexander.danliden.delend.entities.models.Player;
import com.alexander.danliden.delend.entities.models.PlayerMP;
import com.alexander.danliden.delend.mainpackage.Resources;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;
import com.alexander.danliden.delend.world.World;

public class Projectile extends Rectangle{
	private static final long serialVersionUID = 1L;
	
	
	private double x,y,angleX,angleY,rotation;
	private double speed = 13;
	private int w = 5; 
	private int h = 32;
	private Client client;
	private int id;
	private long animationSpeed = 30;
	
	public static int projectileWidth = 64, projectileHeight = 64;
	
	private boolean explode = false;
	private Animator explosion;
	private Animator lightningImpact;
	
	private boolean fire = false, lightening = false;
	private boolean playerhit = false;
	
	public Projectile(float x, float y, double angleX, double angleY, double rotation, String type){
		this.x = x;
		this.y =y;
		this.angleX = angleX;
		this.angleY = angleY;
		this.rotation = rotation;
		
		setBounds((int)(x - World.gamecamera.getxOffset()), (int)(y - World.gamecamera.getyOffset()), w,h);
		
		
		
		
		Resources.fireballAnimation.setSpeed(animationSpeed);
		Resources.fireballAnimation.play();
		Resources.lightningboltAnimation.setSpeed(animationSpeed);
		Resources.lightningboltAnimation.play();
		
		lightningImpact = new Animator(Resources.lighteningboltImpact);
		lightningImpact.setSpeed(animationSpeed);
		lightningImpact.play(); 
		explosion = new Animator(Resources.explosionList);
		explosion.setSpeed(animationSpeed);
		explosion.play();
		if(type.equalsIgnoreCase("fire")){
			fire = true;
			lightening = false;
		}
		if(type.equalsIgnoreCase("lightening")){
			lightening = true;
			fire = false;
		}
	}
	private boolean canRegisterDamage = false;
	public Projectile(float x, float y, double angleX, double angleY, double rotation, Client client, int id, String type, boolean canRegisterDamage){
		this.x = x;
		this.y =y;
		this.angleX = angleX;
		this.angleY = angleY;
		this.rotation = rotation;
		this.client = client;
		this.id = id;
		this.canRegisterDamage = canRegisterDamage;
		
		setBounds((int)(x - World.gamecamera.getxOffset()), (int)(y - World.gamecamera.getyOffset()+ h / 2), projectileWidth,h);
		
		
		
		Resources.fireballAnimation.setSpeed(60);
		Resources.fireballAnimation.play();
		
		Resources.lightningboltAnimation.setSpeed(animationSpeed);
		Resources.lightningboltAnimation.play();
		lightningImpact = new Animator(Resources.lighteningboltImpact);
		lightningImpact.setSpeed(animationSpeed);
		lightningImpact.play(); 
		explosion = new Animator(Resources.explosionList);
		explosion.setSpeed(animationSpeed);
		explosion.play();
		
		if(type.equalsIgnoreCase("fire")){
			fire = true;
			lightening = false;
		}
		if(type.equalsIgnoreCase("lightening")){
			lightening = true;
			fire = false;
		}
		
		
	}
	
	public void update(double delta){
		setBounds((int)(x - World.gamecamera.getxOffset()), (int)(y - World.gamecamera.getyOffset()+ h / 2), projectileWidth,h);
		if(!explode){
		
		x += angleX * speed;
		y += angleY * speed;
		
		outOfMap();
		checkCollision();
		collisionWithPlayers();
		}
		
		
		if(explode){
			if(fire){
			if(explosion.isDoneAnimating()){
				Player.removeProjectile(this);
			}
			}
			if(lightningImpact.isDoneAnimating()){
				Player.removeProjectile(this);
			}
		}
		
	}
	private boolean hit = false;
	private float impactX, impactY;
	private void collisionWithPlayers(){
		
		
		if(client != null){
		if(client.onlinePlayers != null){
		if(!client.onlinePlayers.isEmpty()){
			for(PlayerMP players : client.onlinePlayers){
				if(players.getId() != id){
				if(players.hitbox.intersects(this) && !players.isMultiplayerTransporting() && !players.bounds.intersects(World.townRectangle)){
					if(!explode ){
						impactX = players.getX();
						impactY = players.getY();
						playerhit = true;
						explode = true;
						
						if(canRegisterDamage){
						if(fire){
							
							float fireDamage = (new Random().nextFloat() + 6) *  (new Random().nextInt(3) + 1);
							String message = ("05" + (players.getHealth() - fireDamage) + "," + players.getId() + "," + id);
							String message2 = ("10" + fireDamage + "," + players.getId() + "," + id );
							client.send(message.getBytes());
							client.send(message2.getBytes());
							
						}
						if(lightening){
							float LighteningDamage = new Random().nextFloat() + new Random().nextFloat() * 2;
							String message = ("05" + (players.getHealth() - LighteningDamage) + "," + players.getId() + "," + id);
							String message2 = ("10" + LighteningDamage + "," + players.getId() + "," + id );
							client.send(message.getBytes());
							client.send(message2.getBytes());
						
						}
						}
						
					}
					}
					
				}
			}
			}
	
		}
		}
		
	}
	
	public void render(Graphics2D g){
	

		// Rotation
		g.rotate(Math.toRadians(rotation), 
				(int)x - World.gamecamera.getxOffset() + projectileWidth/ 2 , 
				(int) y - World.gamecamera.getyOffset() + projectileHeight/2 );
		
		//##########################################################
		// Everything between here rotates
		if(!explode){
			if(fire){
		g.drawImage(Resources.fireballAnimation.sprite,
				(int)(x - World.gamecamera.getxOffset()),
				(int)(y - World.gamecamera.getyOffset()), projectileWidth,projectileHeight, null);
		Resources.fireballAnimation.update(System.currentTimeMillis());
			}
			else if(lightening){
				g.drawImage(Resources.lightningboltAnimation.sprite,
						(int)(x - World.gamecamera.getxOffset()),
						(int)(y - World.gamecamera.getyOffset()), projectileWidth,projectileHeight, null);
				Resources.lightningboltAnimation.update(System.currentTimeMillis());
				
				
			}
		}
		//############################################################
		// Reset, because i dont want everything in the entire world to rotate!
		// Important line!
		g.rotate(-Math.toRadians(rotation), 
				(int)x - World.gamecamera.getxOffset() + projectileWidth / 2 , 
				(int) y - World.gamecamera.getyOffset() + projectileHeight/2 );
		if(explode){
			if(playerhit){
			if(fire){
			g.drawImage(explosion.sprite,
					(int)(impactX - World.gamecamera.getxOffset() - 8),
					(int)(impactY - World.gamecamera.getyOffset() - 30), 64,64, null);
			explosion.update(System.currentTimeMillis());
			}
			if(lightening){
				g.drawImage(lightningImpact.sprite,
						(int)(impactX - World.gamecamera.getxOffset() - 38),
						(int)(impactY - World.gamecamera.getyOffset()) - 80, 128,128, null);
				lightningImpact.update(System.currentTimeMillis());
			}
			}else if(!playerhit){
				if(fire){
					g.drawImage(explosion.sprite,
							(int)(x - World.gamecamera.getxOffset() - 8),
							(int)(y - World.gamecamera.getyOffset() - 5), 64,64, null);
					explosion.update(System.currentTimeMillis());
					}
					if(lightening){
						g.drawImage(lightningImpact.sprite,
								(int)(x - World.gamecamera.getxOffset() - 38),
								(int)(y - World.gamecamera.getyOffset()) - 70, 128,128, null);
						lightningImpact.update(System.currentTimeMillis());
					}
			}
		}
		
		
			
		
	}
	
	private void outOfMap(){
		
		if((int)(x - World.gamecamera.getxOffset()) > CORE_V.WINDOW_WIDTH + 100){
		Player.removeProjectile(this);
		}
		
		if((int)(x - World.gamecamera.getxOffset()) < -150){
			Player.removeProjectile(this);
		}
		
		if((int)(y - World.gamecamera.getyOffset()) > CORE_V.WINDOW_HEIGHT + 100){
			Player.removeProjectile(this);
		}
		
		if((int)(y - World.gamecamera.getyOffset()) < - 150){
			Player.removeProjectile(this);
		}
	}
	
	private void checkCollision(){
		
		// right
		if(CheckPlayerCollision.Collision(
				new Point(
						(((int)(x - World.gamecamera.getxOffset() + projectileWidth -15 ))), 
						((int)(y - World.gamecamera.getyOffset()  + 15)))
				,
				new Point(
						((int)(x - World.gamecamera.getxOffset() + projectileWidth - 15)),
						(int)(y - World.gamecamera.getyOffset() + h + 15))))
					{
			
						explode = true;
						
					}
		
		
		
		// left side
		if(CheckPlayerCollision.Collision(
				new Point((int) (x -World.gamecamera.getxOffset() + 10),
						  (int)( y- World.gamecamera.getyOffset() + h + 15)),
				
				new Point((int)( x- World.gamecamera.getxOffset() + 10),
						  (int)( y- World.gamecamera.getyOffset() + 15)))
		){
			explode = true;
			
		}

		
		
		// Up
		if(CheckPlayerCollision.Collision(
				new Point((int) (x - World.gamecamera.getxOffset() + 10),
						  (int)( y- World.gamecamera.getyOffset() + 15)),
				
				new Point((int)( x- World.gamecamera.getxOffset() + width - 15),
						  (int)( y-World.gamecamera.getyOffset() +  15)))
		){
			explode = true;
			
		
		}
		
	
		
		// Down
		if(CheckPlayerCollision.Collision(
				new Point((int) (x -World.gamecamera.getxOffset() + 10),
						  (int)( y- World.gamecamera.getyOffset() + h   + 15)),
							
				new Point((int)( x- World.gamecamera.getxOffset() + projectileWidth - 15),
						  (int)( y- World.gamecamera.getyOffset() + h   + 15)))
		){
			
			explode = true;
		
			
		}
		
	}
	
	
}
