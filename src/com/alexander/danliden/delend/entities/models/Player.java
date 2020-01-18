package com.alexander.danliden.delend.entities.models;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import com.alexander.danliden.delend.animation.Animator;
import com.alexander.danliden.delend.bouncytext.BouncyText;
import com.alexander.danliden.delend.collision.CheckMultiplayerCollision;
import com.alexander.danliden.delend.collision.CheckPlayerCollision;
import com.alexander.danliden.delend.entities.Entity;
import com.alexander.danliden.delend.gui.PlayersOnlineGUI;
import com.alexander.danliden.delend.gui.TransportGUI;
import com.alexander.danliden.delend.input.InputHandler;
import com.alexander.danliden.delend.mainpackage.Game;
import com.alexander.danliden.delend.mainpackage.Resources;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;
import com.alexander.danliden.delend.managers.HUDmanagerSELF;
import com.alexander.danliden.delend.projectiles.Projectile;
import com.alexander.danliden.delend.utils.DeathNotice;
import com.alexander.danliden.delend.utils.Screentext;
import com.alexander.danliden.delend.world.Block;
import com.alexander.danliden.delend.world.TileManager;
import com.alexander.danliden.delend.world.World;


public class Player extends Entity {

	private boolean up = false;
	private boolean right = false;
	private boolean down = false;
	private boolean left = false;
	private float speedUp = 5.0f, speedRight = 5.0f, speedDown = 5.0f, speedLeft = 5.0f;
	
	private float MAX_HEALTH = 100f;
	private float MAX_MANA = 100f;
	private int playerwidth = 64, playerheight = 64;
	private String coord = "";
	//private String playerInformation = "";
	private CopyOnWriteArrayList<BouncyText> bouncytext = new CopyOnWriteArrayList<BouncyText>();
	public static CopyOnWriteArrayList<DeathNotice> deathlist = new CopyOnWriteArrayList<DeathNotice>();
	private int MAX_TELEPORT = 3;
	private int teleportQuantity = MAX_TELEPORT;
	private PlayerMP me;
	public static CopyOnWriteArrayList<Projectile> projectiles = new CopyOnWriteArrayList<Projectile>();
	private Client client;
	private World world;
	private int id;
	private CheckMultiplayerCollision playerCollisions;
	private float health = 100;
	private long animationSpeed = 50;
	protected int animationState = 3;
	public int radius = 400;
	double degree;
	int timer = 0;
	private int startTrackerDistanceX = 500, startTrackerDistanceY = 300;
	private int bleepSize = 12;
	private boolean txtUp = false, txtDown = false, txtLeft = false, txtRight = false;
	public Rectangle screenbounds;
	private float teleportPastX = 0, teleportPastY = 0;
	private float teleportCurrX = 0, teleportCurrY = 0;
	boolean teleporting = false;
	private boolean attackRight = false, attackLeft = false, attackUp = false, attackDown = false;
	
	private boolean playerCollisionActive = false;
	private HUDmanagerSELF hud;
	
	private Animator teleportAnimationPAST, teleportAnimationCURR;
	private Animator firePhaseAnimation, lightPhaseAnim;
	private int fireRevealAtFrame = 15;
	private boolean hidden_fire = false;
	private boolean firePhased = false;
	private boolean lightPhased = false;
	private int lightPerkTimer = 0; 
	private long attackSpeed = 80;
	private boolean glowing = false;
	private float ping = 0;
	private float xa, ya;
	private int pingTimer = 0;
	private boolean animationPacketInit = false;
	
	private boolean canCastFire = true;
	private boolean canCastThunder = true;
	private float thunderTimer = 0;
	private float fireTimer = 0;
	private float fireCooldown = 1f;
	private float thunderCooldown = 1f; 
	public Rectangle bounds;
	public TransportGUI transportgui;
	private ArrayList<transportNPC> transporters = new ArrayList<transportNPC>();	
	private transportNPC t = null;
	private Screentext information;
	private boolean showInteractText = false;
	private String latestAttacker;
	private boolean hasDied = false;
	private boolean multiplayerTransporting = false;
	private int lightHealthRegeneration = 2;
	private PlayersOnlineGUI playerlist;
	
	public Player(float x, float y, World world) {
		super(x, y);
		this.world = world;
		playerlist = new PlayersOnlineGUI(this,client);
		hud = new  HUDmanagerSELF(this, null, playerlist);
		initAnimation();
		if(World.gamecamera != null)
		bounds = new Rectangle((int)(x - World.gamecamera.getxOffset() + 8),(int)(y - World.gamecamera.getyOffset()- 5), 25,30);
		information = new Screentext("Press <E> to interact",16,Color.orange);
		transportgui = new TransportGUI(this,client);
		}
		
	public Player(float x, float y, Client client, World world, int id) {
		super(x, y);
		this.id = id;
		this.client = client;
		this.world = world;
		playerCollisions = new CheckMultiplayerCollision(client, id);
		
		
		initAnimation();
		playerlist = new PlayersOnlineGUI(this,client);
		hud = new  HUDmanagerSELF(this, client, playerlist);
		transportgui = new TransportGUI(this,client);
		bounds = new Rectangle((int)(x - World.gamecamera.getxOffset() + 8),(int)(y - World.gamecamera.getyOffset()- 5), 25,30);
		information = new Screentext("Press <E> to interact",16,Color.orange);
		}
		
	private void initAnimation(){
		// walking
		Resources.anim_up.setSpeed(animationSpeed);
		Resources.anim_up.play();
		
		Resources.anim_down.setSpeed(animationSpeed);
		Resources.anim_down.play();
		
		Resources.anim_left.setSpeed(animationSpeed);
		Resources.anim_left.play();
		
		Resources.anim_right.setSpeed(animationSpeed);
		Resources.anim_right.play();
		
		// attacks
		Resources.attackRightAnimation.setSpeed(attackSpeed);
		Resources.attackRightAnimation.play();
		
		Resources.attackLeftAnimation.setSpeed(attackSpeed);
		Resources.attackLeftAnimation.play();
		
		Resources.attackDownAnimation.setSpeed(attackSpeed);
		Resources.attackDownAnimation.play();
		
		Resources.attackUpAnimation.setSpeed(attackSpeed);
		Resources.attackUpAnimation.play();
		
		teleportAnimationPAST = new Animator(Resources.teleportList);
		teleportAnimationPAST.setSpeed(30);
		teleportAnimationPAST.play();
		
		teleportAnimationCURR = new Animator(Resources.teleportList);
		teleportAnimationCURR.setSpeed(30);
		teleportAnimationCURR.play();
		
		firePhaseAnimation = new Animator(Resources.phaseExplosionList);
		firePhaseAnimation.setSpeed(30);
		firePhaseAnimation.play();
		
		lightPhaseAnim = new Animator(Resources.phaseLightningList);
		lightPhaseAnim.setSpeed(animationSpeed);
		lightPhaseAnim.play();
		
	}
	
	private void multiplayerMoveXWithPlayerCollision(){
		// Right
		if(right){
			animationState = 2;
			if(!CheckPlayerCollision.Collision(
					new Point((int) (x - World.gamecamera.getxOffset() + 15  + speedRight - 2),
							  (int)( y-  World.gamecamera.getyOffset())),
					
					new Point((int)( x-  World.gamecamera.getxOffset() + 15+ speedRight - 2),
							  (int)( y-  World.gamecamera.getyOffset() + 25)))
					
					 &&
					 !playerCollisions.collision(	
					new Point((int) (x - World.gamecamera.getxOffset() + 25  + speedRight + 4),
							  (int)( y-  World.gamecamera.getyOffset())),
					
					new Point((int)( x-  World.gamecamera.getxOffset() + 25+ speedRight  + 4),
							  (int)( y-  World.gamecamera.getyOffset() + 25)))
					
				
			
			){
				x += speedRight;
				
			}else{
				x += 0;
			}
			
		}
		
		
	// Left
		if(left){
			animationState = 4;
			if(!CheckPlayerCollision.Collision(
					new Point((int) (x - World.gamecamera.getxOffset()  - speedLeft - 8),
							  (int)( y-  World.gamecamera.getyOffset())),
					
					new Point((int)( x-  World.gamecamera.getxOffset() - speedLeft  - 8),
							  (int)( y-  World.gamecamera.getyOffset() + 25)))
					&& 
					
					!playerCollisions.collision(
							new Point((int) (x - World.gamecamera.getxOffset()  - speedLeft - 4),
									  (int)( y-  World.gamecamera.getyOffset())),
							
							new Point((int)( x-  World.gamecamera.getxOffset() - speedLeft  - 4),
									  (int)( y-  World.gamecamera.getyOffset() + 25)))
							
			
			){
				x -= speedLeft;
				
			}else{
				x -= 0;
			}
		}else if(!left && !right){
			
			x -= 0;
		}
	}
	
	private void multiplayerMoveYWithPlayerCollision(){
		// up
		if(up){
			animationState = 1;
			if(!CheckPlayerCollision.Collision(
					new Point((int) (x - World.gamecamera.getxOffset()  - speedUp + 2),
							  (int)( y-  World.gamecamera.getyOffset())  - 3),
					
					new Point((int)( x-  World.gamecamera.getxOffset() + 15 - speedUp - 2),
							  (int)( y-  World.gamecamera.getyOffset()) - 3))
					&&
					!playerCollisions.collision(
					new Point((int) (x - World.gamecamera.getxOffset()  - speedUp + 4),
							  (int)( y-  World.gamecamera.getyOffset()) - 8),
					
					new Point((int)( x-  World.gamecamera.getxOffset() + 15 - speedUp  + 2),
							  (int)( y-  World.gamecamera.getyOffset())- 8))
				
			){
				y -= speedUp;
			
			}else{
				y -= 0;
			}
		}
		
	// down
		if(down){
			animationState = 3;
			if(!CheckPlayerCollision.Collision(
					new Point((int) (x - World.gamecamera.getxOffset() - speedDown  + 4),
							  (int)( y-  World.gamecamera.getyOffset() + 25) + 3),
					
					new Point((int)( x-  World.gamecamera.getxOffset() + 15 - speedDown - 1),
							  (int)( y-  World.gamecamera.getyOffset() + 25) + 3))
					&&
					!playerCollisions.collision(	
					new Point((int) (x - World.gamecamera.getxOffset() - speedDown + 4 ),
							  (int)( y-  World.gamecamera.getyOffset() + 25) + 8),
					
					new Point((int)( x-  World.gamecamera.getxOffset() + 25 - speedDown + 2),
							  (int)( y-  World.gamecamera.getyOffset() + 25) + 8))
			
			){
				y += speedDown;
				
			}else{
				y += 0;
			}
		}else if(!up && !down){
			
			y -= 0;
		}
	}
	
	private void multiplayerMoveX(double delta){
		// Right
				if(right){
					animationState = 2;
					if(!CheckPlayerCollision.Collision(
							new Point((int) (x - World.gamecamera.getxOffset() + playerwidth  - 10 ),
									  (int)( y-  World.gamecamera.getyOffset())),
							
							new Point((int)( x-  World.gamecamera.getxOffset()+ playerwidth  - 10 ),
									  (int)( y-  World.gamecamera.getyOffset() + 20)))
									){
						x += (speedRight + delta);
					}else{
						x += 0;
					}
					
				}
				
				
			// Left
				if(left){
					animationState = 4;
					if(!CheckPlayerCollision.Collision(
							new Point((int) (x - World.gamecamera.getxOffset() + 8),
									  (int)( y-  World.gamecamera.getyOffset())),
							
							new Point((int)( x-  World.gamecamera.getxOffset()   + 8),
									  (int)( y-  World.gamecamera.getyOffset() + 20)))
							){
						x -= (speedLeft + delta);
						
						
					}else{
						x -= 0;
					}
				}else if(!left && !right){
					
					x -= 0;
				}
	}
	
	private void multiplayerMoveY(double delta){
		// up
		if(up){
			animationState = 1;
			if(!CheckPlayerCollision.Collision(
					new Point((int) (x - World.gamecamera.getxOffset() + playerwidth - 15),
							  (int)( y-  World.gamecamera.getyOffset())  - 3),
					
					new Point((int)( x-  World.gamecamera.getxOffset() + 15),
							  (int)( y-  World.gamecamera.getyOffset()) - 3))
				
			){
				y -= (speedUp + delta);
			
			}else{
				y -= 0;
			}
		}
		
	// down
		if(down){
			animationState = 3;
			if(!CheckPlayerCollision.Collision(
					new Point((int) (x - World.gamecamera.getxOffset() + playerwidth  - 15),
							  (int)( y-  World.gamecamera.getyOffset() + 25)),
					
					new Point((int)( x-  World.gamecamera.getxOffset() + 15),
							  (int)( y-  World.gamecamera.getyOffset() + 25)))
			
			){
				y += (speedDown + delta);
				
			}else{
				y += 0;
			}
		}else if(!up && !down){
			
			y -= 0;
		}
	}
	
	private void moveX(){
	// Right
		if(right){
			if(!CheckPlayerCollision.Collision(
					new Point((int) (x - World.gamecamera.getxOffset() + playerwidth /2  + speedRight + 1),
							  (int)( y-  World.gamecamera.getyOffset()- playerheight / 2)),
					
					new Point((int)( x-  World.gamecamera.getxOffset() + playerwidth /2 + speedRight + 1),
							  (int)( y-  World.gamecamera.getyOffset() + playerheight / 2)))
			
			){
				x += speedRight;
				
			}else{
				x += 0;
			}
			
		}
		
		
	// Left
		if(left){
			if(!CheckPlayerCollision.Collision(
					new Point((int) (x - World.gamecamera.getxOffset() - playerwidth /2  - speedLeft - 1),
							  (int)( y-  World.gamecamera.getyOffset()- playerheight / 2)),
					
					new Point((int)( x-  World.gamecamera.getxOffset() - playerwidth /2 - speedLeft - 1),
							  (int)( y-  World.gamecamera.getyOffset() + playerheight / 2)))
			
			){
				x -= speedLeft;
				
			}else{
				x -= 0;
			}
		}else if(!left && !right){
			
			x -= 0;
		}
		
	
	}
	
	private void moveY(){
		// up
				if(up){
					if(!CheckPlayerCollision.Collision(
							new Point((int) (x - World.gamecamera.getxOffset() - playerwidth /2  - speedUp + 2),
									  (int)( y-  World.gamecamera.getyOffset()- playerheight / 2) - 1),
							
							new Point((int)( x-  World.gamecamera.getxOffset() + playerwidth /2 - speedUp - 2),
									  (int)( y-  World.gamecamera.getyOffset() - playerheight / 2) - 1))
					
					){
						y -= speedUp ;
						
					}else{
						y -= 0;
					}
				}
				
			// down
				if(down){
					if(!CheckPlayerCollision.Collision(
							new Point((int) (x - World.gamecamera.getxOffset() - playerwidth /2  - speedDown + 1),
									  (int)( y-  World.gamecamera.getyOffset() + playerheight / 2) + 3),
							
							new Point((int)( x-  World.gamecamera.getxOffset() + playerwidth /2 - speedDown - 1),
									  (int)( y-  World.gamecamera.getyOffset() + playerheight / 2) + 3))
					
					){
						y += speedDown;
						
					}else{
						y += 0;
					}
				}
				
	}
	
	private boolean oneKey = true;
	
	public void update(double delta) {
		
		playerlist.update(delta);
		
		if (World.gamecamera != null && bounds == null)
			bounds = new Rectangle((int) (x - World.gamecamera.getxOffset() + 8),
					(int) (y - World.gamecamera.getyOffset() - 5), 25, 30);

		if (bounds != null)
			bounds.setBounds((int) (x - World.gamecamera.getxOffset() + 8),
					(int) (y - World.gamecamera.getyOffset() - 5), 25, 30);

		if (!Game.multiplayer) {
			moveX();
			moveY();
		}

		
		
		if(up){
			if(right && !oneKey || left && !oneKey ){
				speedRight = (float)Math.sqrt(12.5D);
				speedLeft =  (float)Math.sqrt(12.5D);
			}
		}
		if(down){
			if(right && !oneKey || left && !oneKey) {
				speedRight = (float)Math.sqrt(12.5D);
				speedLeft =  (float)Math.sqrt(12.5D);
			}
		}
		if(right){
			if(up && !oneKey|| down && !oneKey){
				speedUp = (float)Math.sqrt(12.5D);
				speedDown =  (float)Math.sqrt(12.5D);
			}
		}
		if(left){
			if(up && !oneKey|| down && !oneKey){
				speedUp = (float)Math.sqrt(12.5D);
				speedDown =  (float)Math.sqrt(12.5D);
			}
		}
		
		if(oneKey){
			speedDown = 5.0f;
			speedRight = 5.0f;
			speedLeft = 5.0f;
			speedUp = 5.0f;
		}
		
		xa = x;
		ya = y;

		if (xa > x) {
			if (movingdir != 4) {
				movingdir = 4;
			}
			if (!isMoving)
				isMoving = true;

			animationState = movingdir;

		}
		if (xa < x) {
			if (movingdir != 2) {
				movingdir = 2;
			}
			if (!isMoving)
				isMoving = true;
			animationState = movingdir;

		}
		if (ya > y) {
			if (movingdir != 1) {
				movingdir = 1;
			}
			if (!isMoving)
				isMoving = true;
			animationState = movingdir;

		}
		if (ya < y) {
			if (movingdir != 3) {
				movingdir = 3;
			}
			if (!isMoving)
				isMoving = true;
			animationState = movingdir;

		}

		if (World.gamecamera != null && screenbounds != null) {
			screenbounds.setBounds((int) (x - World.gamecamera.getxOffset()) - CORE_V.WINDOW_WIDTH / 2 + 50,
					(int) (y - World.gamecamera.getyOffset()) - CORE_V.WINDOW_HEIGHT / 2 + 50, CORE_V.WINDOW_WIDTH - 50,
					CORE_V.WINDOW_HEIGHT - 75);
		}

		animationState = movingdir;
		// hud.update();
		if (attackRight) {
			if (Resources.attackRightAnimation.isDoneAnimating()) {
				attackRight = false;
				Resources.attackRightAnimation.reset();
			}
		}

		if (attackLeft) {
			if (Resources.attackLeftAnimation.isDoneAnimating()) {
				attackLeft = false;
				Resources.attackLeftAnimation.reset();
			}
		}

		if (attackDown) {
			if (Resources.attackDownAnimation.isDoneAnimating()) {
				attackDown = false;
				Resources.attackDownAnimation.reset();
			}
		}

		if (attackUp) {
			if (Resources.attackUpAnimation.isDoneAnimating()) {
				attackUp = false;
				Resources.attackUpAnimation.reset();
			}
		}
		if (hud.getTeleportQuantity() < MAX_TELEPORT) {
			timer++;
			if (timer % ((2) * 60) == 0) {
				hud.setTeleportQuantity(hud.getTeleportQuantity() + 1);
				timer = 0;
			}
		}

		if (teleporting) {
			if (teleportAnimationPAST.isDoneAnimating() && teleportAnimationCURR.isDoneAnimating()) {
				teleportAnimationPAST.reset();
				teleportAnimationCURR.reset();
				teleporting = false;
			}
		}

		if (firePhased) {
			if (firePhaseAnimation.doneAtFrame(fireRevealAtFrame)) {
				hidden_fire = false;
			}

			if (firePhaseAnimation.isDoneAnimating()) {
				firePhased = false;
				firePhaseAnimation.reset();
			}
		}
		if (lightPhased) {
			if (lightPhaseAnim.isDoneAnimating()) {
				lightPhased = false;
				lightPhaseAnim.reset();
			}
		}

		hud.update(delta);
		if (bouncytext != null) {
			for (BouncyText bouncy : bouncytext) {
				bouncy.update(delta);
			}
		}

		if (transportgui != null) {
			if(!transporters.isEmpty()){
				for(int i = 0; i < transporters.size(); i++){
					if(bounds.intersects(transporters.get(i).bounds)){
						if(!transportgui.isShow())
						showInteractText = true;
						else
						showInteractText = false;
						
						t = transporters.get(i);
						break;
					}
					}
				
			}
		}
		if(t != null){
			if(!bounds.intersects(t.bounds)){
				transportgui.hideGUI();
				showInteractText = false;
			}
		}
		
		
		
		if(deathlist != null && !deathlist.isEmpty()){
			for(int i = 0; i < deathlist.size() ; i++){
				deathlist.get(i).update(delta);
				
				if(deathlist.size() > 3){
					deleteDeathNotice(deathlist.get(2));
					if(deathlist.size() > 0)
						deathlist.get(0).updateSize(deathlist.size() - 1);
						deathlist.get(1).updateSize(deathlist.size() - 2);
						deathlist.get(2).updateSize(deathlist.size() - 3);
					
				}
				if(deathlist.size() > 0)
					deathlist.get(0).updateSize(deathlist.size() - 1);
				if(deathlist.size() > 1)
					deathlist.get(1).updateSize(deathlist.size() - 2);
				if(deathlist.size() > 2)
					deathlist.get(2).updateSize(deathlist.size() - 3);
				
			}
			
		
			
		}

		deathFunction();
		cooldownFunction();
		if (Game.multiplayer) {
			network(delta);
		}
	}
		
	private void deathFunction(){
		if(me != null){
			if(me.getHealth() <= 0){
				me.setHealth(MAX_HEALTH);
				if(client != null && me.getUsername() != null){
					String message = "11" + latestAttacker + "," + me.getUsername() + "," + deathlist.size();
					client.send(message.getBytes());
				}
				hasDied = true;
			}
		}
		
		
		if(hasDied) {
			setX(250f);
			setY(250f);
			if(client != null){
				client.send(("05" + health + "," + id + "," + id).getBytes());
				String coords = "04"+ x + "," + y + "," + client.getID() + "," + movingdir + "," + isMoving;
				client.send(coords.getBytes());
			}
			hasDied = false;
	
			
		}
		
	
		
		
	}
	
	private void cooldownFunction(){
		
		if(!canCastFire){
			fireTimer += 1;
			if(fireTimer >= (fireCooldown*50)){
				fireTimer = 0;
				canCastFire = true;
			}
		}
		
		if(!canCastThunder){
			thunderTimer += 1;
			if(thunderTimer >= (thunderCooldown * 10)){
				thunderTimer = 0;
				canCastThunder = true;
			}
		}
		
		
	}
	
	private void network(double delta){
		
		if(client != null){
			if(client.isSendPing()){
				client.send("666".getBytes());
				client.setSendPing(false);
			}if(!client.isSendPing()){
					
			}
			
			
			
			
		}
		
		if(me == null){
			if(client != null){
			for(int i= 0; i < client.onlinePlayers.size(); i++){
				if(client.onlinePlayers.get(i).getId() == id){
					me = client.onlinePlayers.get(i);
					break;
				}
			}
			}
		}
		
		if(playerCollisionActive){
			multiplayerMoveXWithPlayerCollision();
			multiplayerMoveYWithPlayerCollision();
		}
		if(!playerCollisionActive){
			multiplayerMoveX(delta);
			multiplayerMoveY(delta);
			
		}
		
		
		if(animationPacketInit){
			if(xa == x && ya == y && client != null){
				if(isMoving != false){
				isMoving = false;
				coord = "04"+ x + "," + y + "," + client.getID() + "," + movingdir + "," + isMoving;
				client.send(coord.getBytes());
				}
				coord = "04"+ x + "," + y + "," + client.getID() + "," + movingdir + "," + isMoving;
				client.send(coord.getBytes());
			}
			animationPacketInit = false;
			
			}
		
		if(client != null){
		if(xa != x || ya != y){
			coord = "04"+ x + "," + y + "," + client.getID() + "," + movingdir + "," + isMoving;
			client.send(coord.getBytes());
			}
		}
		

		if(client != null){
			// När någon loggar in sätts den här till true och då uppdaterar server alla klienters position och health
			if(client.isGetExistingPlayersCoord()){
				client.setGetExistingPlayersCoord(false);
				coord = "04"+ x + "," + y + "," + client.getID() + "," + movingdir + "," + isMoving;
				client.send(coord.getBytes());
				String phaseInformation = "09" + firePhased + "," + hud.isLightning() + "," + id;
				client.send(phaseInformation.getBytes());
				
				
				if(client != null){
					if(client.onlinePlayers != null){
					if(!client.onlinePlayers.isEmpty()){
						for(PlayerMP players : client.onlinePlayers){
							if(players.getId() == id){
								String message = ("05" + (players.getHealth()) + "," + client.getID() + "," + id);
								client.send(message.getBytes());
								
							}
						}
					}
					}
				}
				
			}
		}
		
		if(client != null){
			if(health <= 0f){
				health = 0f;
			}
	
			
		}
		if(glowing){
			lightPerkTimer++;
			
					if(lightPerkTimer % 60 == 0){
						if(me != null){
						if(me.getHealth() < 100){
							me.setHealth(me.getHealth() + lightHealthRegeneration);
							String message = ("05" + me.getHealth() + "," + id + "," + -1);
							client.send(message.getBytes());
							bouncytext.add(new BouncyText(x,y,"+"+lightHealthRegeneration,true,false));
							lightPerkTimer = 0;
						}
						if(me.getHealth() >= 100f){
							me.setHealth(100f);
						}
						}
			}
		}
	
		manaRegTimer++;
		if(manaRegTimer % 30 == 0){
			if(me != null){
				if(me.getMana() < 100){
					me.setMana(me.getMana() + manaRegeneration);
					String message = ("13" + me.getMana() + "," + id);
					client.send(message.getBytes());
					bouncytext.add(new BouncyText(x,y,"+"+manaRegeneration,false,true));
					manaRegTimer = 0;
				}
				if(me.getMana() >= 100f){
					me.setMana(100f);
				}
			}
		}
	}
	private float manaRegeneration = 3f;
	private int manaRegTimer = 0;
	public static void removeProjectile(Projectile p){
		if(projectiles.contains(p))
		projectiles.remove(p);
	}

	public void render(Graphics2D g) {
	if(!Game.multiplayer){
	g.setColor(Color.blue);
	g.fillRect((int)(x - World.gamecamera.getxOffset() - playerwidth / 2), 
			(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight);
	}
	
	if(!projectiles.isEmpty()){
		for(Projectile p: projectiles){
			p.render(g);
		}
	}
	

	
	
	// FOR YOUR SELF (i think)
	// animation shit
	if(animationState == 1){
		if(transportgui != null){
		if(!attackRight && !attackLeft && !attackDown && !attackUp && !teleporting && !hidden_fire && !transportgui.isTransporting() && !multiplayerTransporting){
		if(isMoving){
		
		g.drawImage(Resources.anim_up.sprite,(int)(x - World.gamecamera.getxOffset()), 
				(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		Resources.anim_up.update(System.currentTimeMillis());
		
	}else{
		g.drawImage(Resources.playerLookUp,(int)(x - World.gamecamera.getxOffset()), 
				(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
	}
		}
		}
}
	if(animationState == 2){
		if(transportgui != null)
		if(!attackRight && !attackLeft && !attackDown && !attackUp && !teleporting && !hidden_fire && !transportgui.isTransporting() && !multiplayerTransporting){
		if(isMoving){
		g.drawImage(Resources.anim_right.sprite,(int)(x - World.gamecamera.getxOffset() ), 
				(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		Resources.anim_right.update(System.currentTimeMillis());
		}
		else{
			g.drawImage(Resources.playerLookRight,(int)(x - World.gamecamera.getxOffset() ), 
					(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		}
		}
	}
	
	
	if(animationState == 3){
		if(transportgui != null)
		if(!attackRight && !attackLeft && !attackDown && !attackUp && !teleporting && !hidden_fire && !transportgui.isTransporting() && !multiplayerTransporting){
		if(isMoving){
			g.drawImage(Resources.anim_down.sprite,(int)(x - World.gamecamera.getxOffset() ), 
					(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
			Resources.anim_down.update(System.currentTimeMillis());
		}else{
			g.drawImage(Resources.playerLookDown,(int)(x - World.gamecamera.getxOffset()), 
					(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		}
		}
	}
	
	if(animationState == 4){
		if(transportgui != null)
		if(!attackRight && !attackLeft && !attackDown && !attackUp && !teleporting && !hidden_fire && !transportgui.isTransporting() && !multiplayerTransporting){
		if(isMoving){
			g.drawImage(Resources.anim_left.sprite,(int)(x - World.gamecamera.getxOffset()), 
					(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
			Resources.anim_left.update(System.currentTimeMillis());
			
		}else{
			g.drawImage(Resources.playerLookLeft,(int)(x - World.gamecamera.getxOffset() ), 
					(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		}
		}
	}
	
	if(transportgui != null)
	if(attackRight && !teleporting && !hidden_fire && !transportgui.isTransporting()){
		
		g.drawImage(Resources.attackRightAnimation.sprite,(int)(x - World.gamecamera.getxOffset()), 
				(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		Resources.attackRightAnimation.update(System.currentTimeMillis());
		
		
	}
	
	if(transportgui != null)
	if(attackLeft && !teleporting && !hidden_fire && !transportgui.isTransporting()){
		g.drawImage(Resources.attackLeftAnimation.sprite,(int)(x - World.gamecamera.getxOffset()), 
				(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		Resources.attackLeftAnimation.update(System.currentTimeMillis());
	}
	
	if(transportgui != null)
	if(attackDown && !teleporting && !hidden_fire && !transportgui.isTransporting()){
		g.drawImage(Resources.attackDownAnimation.sprite,(int)(x - World.gamecamera.getxOffset()), 
				(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		Resources.attackDownAnimation.update(System.currentTimeMillis());
	}
	
	if(transportgui != null)
	if(attackUp && !teleporting && !hidden_fire &&!transportgui.isTransporting() &&  !multiplayerTransporting ){
		g.drawImage(Resources.attackUpAnimation.sprite,(int)(x - World.gamecamera.getxOffset()), 
				(int)(y- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		Resources.attackUpAnimation.update(System.currentTimeMillis());
	}
	
	
	if(teleporting){
		// PAST
		g.drawImage(teleportAnimationPAST.sprite,(int)(teleportPastX - World.gamecamera.getxOffset()), 
				(int)(teleportPastY- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		teleportAnimationPAST.update(System.currentTimeMillis());
		// CURR
		g.drawImage(teleportAnimationCURR.sprite,(int)(teleportCurrX - World.gamecamera.getxOffset()), 
				(int)(teleportCurrY- World.gamecamera.getyOffset() - playerheight / 2), playerwidth, playerheight , null);
		teleportAnimationCURR.update(System.currentTimeMillis());
	}

	
	if(firePhased){
		g.drawImage(firePhaseAnimation.sprite,(int)(x - World.gamecamera.getxOffset() - 100), 
				(int)(y- World.gamecamera.getyOffset() - 140), 256, 256 , null);
				firePhaseAnimation.update(System.currentTimeMillis());
	}
	
	
	if((lightPhased || hud.isLightning() && !teleporting) || (glowing && !teleporting)){
		g.drawImage(lightPhaseAnim.sprite,(int)(x - World.gamecamera.getxOffset() - 8), 
				(int)(y- World.gamecamera.getyOffset() - playerheight / 2 - 8), playerwidth + 16, playerheight + 16 , null);
			lightPhaseAnim.update(System.currentTimeMillis());
	}
	
	
	
	}
	
	public void selfRender(Graphics2D g){
		g.setColor(Color.white);
		//g.drawString("ping: " + (ping / 60), 5, 60);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		for(PlayerMP players: client.onlinePlayers){
			if(players.getId() == id){
				
			}
		}
		if(me != null){
			// HEALTH
		//g.drawString(new DecimalFormat("#.##").format(me.getHealth()) + "%", 350, CORE_V.WINDOW_HEIGHT - 80);
		g.setColor(new Color(255,0,0,150));
		g.fillRect(500,  CORE_V.WINDOW_HEIGHT - (int)(MAX_HEALTH) - 40, 25, (int)(MAX_HEALTH ));
		g.setColor(new Color(0,255,0,150));
		g.fillRect(500,  CORE_V.WINDOW_HEIGHT - (int)(MAX_HEALTH) - 40, 25, (int)(me.getHealth()));
		g.setColor(new Color(255,255,255,255));
		g.drawRect(500, CORE_V.WINDOW_HEIGHT - (int)(MAX_HEALTH) - 40, 25, (int)(MAX_HEALTH));
		
		// MANA
		g.setColor(new Color(64,64,64,150));
		g.fillRect(535,  CORE_V.WINDOW_HEIGHT - (int)(MAX_MANA) - 40 , 25, (int)(MAX_HEALTH ));
		g.setColor(new Color(0,0,255,150));
		g.fillRect(535,  CORE_V.WINDOW_HEIGHT - (int)(MAX_MANA) - 40+ (int)(MAX_MANA - me.getMana()), 25, (int)(me.getMana()) );
		g.setColor(new Color(255,255,255,255));
		g.drawRect(535, CORE_V.WINDOW_HEIGHT - (int)(MAX_MANA) - 40, 25, (int)(MAX_HEALTH));
		//me.setMana(100f);
		
		}
		tracker(g);
		
		hud.render(g);
		if(bouncytext != null){
			for(BouncyText bouncy: bouncytext){
				bouncy.render(g);
			}
			playerlist.render(g);
		}
		
		if(!canCastFire){
			g.setColor(new Color(255,0,0,100));
			g.fillRect(CORE_V.WINDOW_WIDTH / 2  -  64 - 5 + 1, CORE_V.WINDOW_HEIGHT - 64 - 5 - 29, 63, 63);
		}
		if(!canCastThunder){
			g.setColor(new Color(255,0,0,100));
			g.fillRect(CORE_V.WINDOW_WIDTH / 2 +  5 + 1, CORE_V.WINDOW_HEIGHT - 64 - 5 - 29, 63, 63);
		}
		
		if(showInteractText){
			if(information != null)
			information.render(g);
		}
		if(deathlist != null && !deathlist.isEmpty()){
			for(int i = 0; i < deathlist.size() ; i++){
				deathlist.get(i).render(g);
			}
		}
		
		
	}

	private void tracker(Graphics2D g)
	{
		
		
		
		
		g.setColor(Color.red);
		for(PlayerMP players: client.onlinePlayers){
			if(players.getId() != id){
				
				
				
				
				double X = ((int)( (getX() - World.gamecamera.getxOffset()) -   (players.getX() - World.gamecamera.getxOffset()) ));
				double Y = ((int)( (getY() - World.gamecamera.getyOffset()) -   (players.getY() - World.gamecamera.getyOffset()) ));
				double distance = Math.sqrt((X * X) + ( Y* Y));
				
				
				double xx = (int)(players.getX() - World.gamecamera.getxOffset() - bleepSize / 2);
				double yy = (int)(players.getY() - World.gamecamera.getyOffset() - bleepSize / 2);
				
				if(xx >= CORE_V.WINDOW_WIDTH - 20){
					xx = CORE_V.WINDOW_WIDTH - 20;
					txtUp = false;
					txtDown = true;
				}
				
				if(xx <= 5){
					xx = 5;
					txtDown = false;
					txtUp = true;
				}
				if(yy >= CORE_V.WINDOW_HEIGHT - 45){
					yy = CORE_V.WINDOW_HEIGHT - 45;
					txtRight = true;
					txtLeft = false;
				}
				if(yy <= 5){
					yy = 5;
					txtLeft = true;
					txtRight = false;
				}
				
				if(X > startTrackerDistanceX || Y > startTrackerDistanceY || X < -startTrackerDistanceX ||  Y < -startTrackerDistanceY ){
					
					double textX = 0;
					double textY = 0;
					
					if(txtRight){
						
						textX = CORE_V.WINDOW_WIDTH - 20;
						textY = yy;
					}
					
					
					
					if(txtLeft){
						textX = 15;
						textY = yy;
					}
					
					
					
					if(txtDown){
						textY = yy + 10;
						textX = xx - 55;
					}
					
					if(txtUp){
						textY = yy + 10;
						textX = xx + 15;
					}
						
					
				g.setFont(Resources.informationFont);
				g.drawString("(" + new DecimalFormat("#.##").format(distance) +" ft)", (int)textX , (int)textY);	
				g.fillOval((int)xx, (int)yy, bleepSize, bleepSize);
				
				}
			}
		}
		
		
		
		
		
		
	}
	
	public void HitSomething(String damage, float x, float y){
		bouncytext.add(new BouncyText(x,y,damage,false,Color.orange));
	}
	
	public void mouseMoved(MouseEvent e){
		
	}
	public int MANA_COST_FIREBALL = 20;
	public int MANA_COST_LIGHTENING = 5;
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && !hud.isButtonHeldOver()){
		
			if(transportgui.isShow()){
				transportgui.mousePressed(e);	
			}
			
			
			
			else if(client != null && !transportgui.isShow() && !transportgui.isTransporting()&& !world.chatOpen ){
			
			if((hud.isFire() && canCastFire) || (hud.isLightning() && canCastThunder)){
			int mx = e.getX();
			int my = e.getY();
			// direction angle
			double angle = Math.atan2((my -(( y + playerheight / 2) - World.gamecamera.getyOffset() - Projectile.projectileHeight / 2)), 
					(mx - ((x + playerwidth / 2) - World.gamecamera.getxOffset() )));
			double angleX = Math.cos(angle);
			double angleY = Math.sin(angle);
			// Rotation angle
			double X = (x + playerwidth / 2 - World.gamecamera.getxOffset() - mx) ;
			double Y = (y  + playerheight / 2 - World.gamecamera.getyOffset() - Projectile.projectileHeight / 4 - my) ;
			degree =Math.toDegrees( Math.atan2( (-Y), -X));
			
			
			
			if(hud.isFire() && me.getMana() >= MANA_COST_FIREBALL){
				if(canCastFire){
				canCastFire = false;
				String projectilecreation = 
						"06"+ (float)(x ) + "," + (float)(y - playerheight /2 ) + ","
								+ angleX + "," + angleY + ","  + degree + "," + id + "," + "fire";
				client.send(projectilecreation.getBytes());
				
				me.setMana(me.getMana() - MANA_COST_FIREBALL );
				
				}
				
			}
			
			else if(hud.isLightning() && me.getMana() >= MANA_COST_LIGHTENING){
				if(canCastThunder){
					canCastThunder = false;
				String projectilecreation = 
						"06"+ (float)(x) + "," + (float)(y - playerheight / 2 ) + ","
								+ angleX + "," + angleY + ","  + degree + "," + id + "," + "lightening";
				client.send(projectilecreation.getBytes());
				me.setMana(me.getMana() - MANA_COST_LIGHTENING );
				}
			
			}
			
			
			if(!canCastThunder || !canCastFire){
			if(hud.getRight().contains(InputHandler.mouse)){
				attackRight = true;
				String message = "07" + attackRight + "," + id + "," + 2;
				client.send(message.getBytes());
			}
			

			else if(hud.getLeft().contains(InputHandler.mouse)){
				attackLeft = true;
				String message = "07" + attackLeft + "," + id+ "," + 4;
				client.send(message.getBytes());
			}
			

			else if(hud.getUp().contains(InputHandler.mouse)){
				attackUp = true;
				String message = "07" + attackUp + "," + id+ "," + 1;
				client.send(message.getBytes());
			}
			

			else if(hud.getDown().contains(InputHandler.mouse)){
				attackDown = true;
				String message = "07" + attackDown + "," + id+ "," + 3;
				client.send(message.getBytes());
			}
			
			
			}
			
		}
			}
		
	
		}
		
		else if(e.getButton() == MouseEvent.BUTTON3 && !hud.isButtonHeldOver()){
			
			if(transportgui.isShow()){
				transportgui.hideGUI();
			}
			
			else if(!teleporting && !transportgui.isShow() && !transportgui.isTransporting()){
			if(hud.getTeleportQuantity() > 0){
			
			for(Block blocks: TileManager.blocks){
			if(blocks.contains(InputHandler.mouse)){
				if(!blocks.isSolid()){
					teleportPastX = getX();
					teleportPastY = getY();
					teleportCurrX = blocks.getBlockCenterX()- playerwidth/2;
					teleportCurrY = blocks.getBlockCenterY()- playerheight/2;
					setX(blocks.getBlockCenterX()- playerwidth/2);
					setY(blocks.getBlockCenterY()- playerheight/2);
					
					String message = 
					"08" +
					teleporting +
					","+
					id + 
					"," +
					teleportPastX +
					"," +
					teleportPastY + 
					"," + 
					teleportCurrX +
					"," + 
					teleportCurrY;
					
					
					for(int i = 0; i < 10; i++){
						client.send(message.getBytes());
					}
					
					
					String coords = "04"+ x + "," + y + "," + client.getID() + "," + movingdir + "," + isMoving;
					client.send(coords.getBytes());
					
					teleporting = true;
					hud.setTeleportQuantity(hud.getTeleportQuantity() - 1);
				}
				
				
				
				
			
			}
		}
			}
	}
}
		
		if( hud != null){
			hud.mousePressed(e);
		}
		
	
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_W && !transportgui.isTransporting())
		{
			down = false;
			up = true;
			movingdir = 1;
			isMoving = true;
			animationPacketInit = true;
			
			if(right || left || down){
				oneKey = false;
			}
			
			}
		
		if(e.getKeyCode() == KeyEvent.VK_A && !transportgui.isTransporting())
		{
			right = false;
			left = true; 
			movingdir = 4;
			isMoving = true;
			animationPacketInit = true;
			
			if(right || up || down){
				oneKey = false;
			}else
				oneKey = true;
			
			
			}
		
		if(e.getKeyCode() == KeyEvent.VK_S && !transportgui.isTransporting())
		{
			up = false;
			down = true; 
			movingdir = 3;
			isMoving = true;
			animationPacketInit = true;
				
			if(right || left || up){
				oneKey = false;
			}
		
			
			}
		
	
		if(e.getKeyCode() == KeyEvent.VK_D && !transportgui.isTransporting())
		{
			left = false;
			right = true;
			movingdir = 2;
			isMoving = true;
			animationPacketInit = true;
			
			if(up || left || down){
				oneKey = false;
			}
			
			
			}
		
		
		
		
		
		boolean reverse = false;
		if(e.getKeyCode() == KeyEvent.VK_H){
			if(world != null)
				if(!reverse){
					reverse = true;
					World.modAlpha(Resources.playerLookUp, 15);
				}else if(reverse){
					World.modAlpha(Resources.playerLookUp, 255);
				}
				
			
		}
		
		
		
		
		
		if(e.getKeyCode() == KeyEvent.VK_1 ){
			if(!lightPhased && !hud.isFire() && !teleporting && !transportgui.isTransporting()){
			hud.setFire(true);
			firePhased = true;
			hidden_fire = true;
			String message = "09" + firePhased + "," + lightPhased + "," + id;
			client.send(message.getBytes());
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_2){
			if(!firePhased && !hud.isLightning() && !teleporting && !transportgui.isTransporting()){
			hud.setLightning(true);
			lightPhased = true;
			String message = "09" + firePhased + "," + lightPhased + "," + id;
			client.send(message.getBytes());
			}
			
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_E){
			if(!transporters.isEmpty() && !transportgui.isTransporting()){
				for(int i = 0; i < transporters.size(); i++){
					if(transporters.get(i).bounds.intersects(bounds)){
						if(transportgui != null){
						if(!transportgui.isShow()){
							transportgui.showGUI();
							
						}
						
					}
					}
				}
			}
			
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P){
			if(playerlist != null){
				playerlist.activateORdisable();
			}
			
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_M){
			if(client != null){
				if(client.frame != null){
					if(!client.frame.ifFocused()){
					client.frame.setVisible(true);		
					client.frame.getPanel().setFocusable(true);
					}
					
				}
			}
		}
		
		
		
		
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_W)
		{
			
			up = false;
			if(down){
				movingdir = 3;
			}else if(right){
				movingdir = 2;
			}else if(left){
				movingdir = 4;
			}
			animationPacketInit = true;
			 if(!right && !left && !up && !down){
					isMoving = false;
				}
		
			 if(!right || !left || !down){
					oneKey = true;
				}
			 System.out.println("Released W");
			 
			}
		
		if(e.getKeyCode() == KeyEvent.VK_A)
		{
			left = false; 
			animationPacketInit = true;
			if(down){
				movingdir = 3;
			}else if(right){
				movingdir = 2;
			}else if(up){
				movingdir = 1;
			}
			
			
			 if(!right && !left && !up && !down){
					isMoving = false;
				}
			 if(!right || !up || !down){
					oneKey = true;
				}
			 System.out.println("Released A");
			}
		
		if(e.getKeyCode() == KeyEvent.VK_S)
		{
			animationPacketInit = true;
			down = false; 
			if(up){
				movingdir = 1;
			}else if(right){
				movingdir = 2;
			}else if(left){
				movingdir = 4;
			}
			
			
			 if(!right && !left && !up && !down){
					isMoving = false;
				}
			 
			 if(!right || !left || !up){
					oneKey = true;
				}
			 System.out.println("Released S");
			}
		
		if(e.getKeyCode() == KeyEvent.VK_D)
		{
			animationPacketInit = true;
			right = false;
			if(down){
				movingdir = 3;
			}else if(up){
				movingdir = 1;
			}else if(left){
				movingdir = 4;
			}
			
			
			 if(!right && !left && !up && !down){
					isMoving = false;
				}
			 
			 if(!up || !left || !down){
					oneKey = true;
				}
			 System.out.println("Released D");
			}
		
		
		
	}
	
	public void setAttackStateRight(boolean attack){
		this.attackRight = attack;
	}
	
	public void setAttackStateLeft(boolean attack){
		this.attackLeft = attack;
	}
	
	public void setAttackStateUp(boolean attack){
		this.attackUp = attack;
	}
	
	public void setAttackStateDown(boolean attack){
		this.attackDown = attack;
	}
	
	public int getWidth(){
		return playerwidth;
	}
	
	public int getHeight(){
		return playerheight;
	}

	public boolean isTeleporting() {
		return teleporting;
	}

	public void setTeleporting(boolean teleporting) {
		this.teleporting = teleporting;
	}

	public float getTeleportPastX() {
		return teleportPastX;
	}

	public void setTeleportPastX(float teleportPastX) {
		this.teleportPastX = teleportPastX;
	}

	public float getTeleportPastY() {
		return teleportPastY;
	}

	public void setTeleportPastY(float teleportPastY) {
		this.teleportPastY = teleportPastY;
	}

	public float getTeleportCurrX() {
		return teleportCurrX;
	}

	public void setTeleportCurrX(float teleportCurrX) {
		this.teleportCurrX = teleportCurrX;
	}

	public float getTeleportCurrY() {
		return teleportCurrY;
	}

	public void setTeleportCurrY(float teleportCurrY) {
		this.teleportCurrY = teleportCurrY;
	}

	public boolean isFirePhased() {
		return firePhased;
	}

	public void setFirePhased(boolean firePhased) {
		this.firePhased = firePhased;
	}

	public boolean isLightPhased() {
		return lightPhased;
	}

	public void setLightPhased(boolean lightPhased) {
		this.lightPhased = lightPhased;
	}

	public boolean isGlowing() {
		return glowing;
	}

	public void setGlowing(boolean glowing) {
		this.glowing = glowing;
	}

	public void setPing(float ping) {
		this.ping = ping;
	}
	
	public void setLatestAttacker(String username){
		this.latestAttacker = username;
	}
	
	public void addTransporters(transportNPC t){
		if(!transporters.contains(t)){
			transporters.add(t);
		}
	}
	
	public static void deleteDeathNotice(DeathNotice dn){
		if(deathlist.contains(dn)){
			deathlist.remove(dn);
		}
	}

	public int getId() {
		return id;
	}

	public void setMultiplayerTransporting(boolean multiplayerTransporting) {
		this.multiplayerTransporting = multiplayerTransporting;
	}

	public boolean isMultiplayerTransporting() {
		return multiplayerTransporting;
	}
	
	public float getHealth(){
		if(me != null){
			return me.getHealth();
		}
		return health;
	}
	
}
