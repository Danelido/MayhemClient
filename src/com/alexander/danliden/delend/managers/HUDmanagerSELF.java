package com.alexander.danliden.delend.managers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

import com.alexander.danliden.delend.entities.models.Player;
import com.alexander.danliden.delend.gui.PlayersOnlineGUI;
import com.alexander.danliden.delend.mainpackage.Resources;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;
import com.alexander.danliden.delend.managers.effects.ActionbarEffect;
import com.alexander.danliden.delend.utils.Button;

public class HUDmanagerSELF {

	private final boolean debugPoly = false;
	private boolean isFire = true, isLightning = false;
	private int abW = 64;
	private int abH = abW;
	private int gap = 5;
	private int teleportQuantity = 3;
	
	
	private ActionbarEffect fireEff1, fireEff2,fireEff3,fireEff4;
	private ActionbarEffect lightEff1,lightEff2,lightEff3,lightEff4;
	private Player player;
	private Client client;
	private int alpha = 90;
	private int alphaSpeed;
	private Button playerlist;
	private Button chat;
	private PlayersOnlineGUI onlinePlayers;
	private boolean buttonHeldOver = false;
	
	public HUDmanagerSELF(Player player, Client client, PlayersOnlineGUI onlinePlayers){
		initActionbarEffects();
		this.player = player;
		this.client = client;
		this.onlinePlayers = onlinePlayers;
	
		alphaSpeed = 1;
		
		playerlist = new Button(CORE_V.WINDOW_WIDTH - 90, CORE_V.WINDOW_HEIGHT - 75,
				70,25,"Online (P)", 12,"playerlist", new Color(0,0,0,150), new Color(0,255,0,150), true);
		chat = new Button(CORE_V.WINDOW_WIDTH - 180, CORE_V.WINDOW_HEIGHT - 75,
				70,25,"Chat (M)", 12,"playerlist", new Color(0,0,0,150), new Color(0,255,0,150), true);
		
	}
	
	private void initActionbarEffects(){
		 int size = 5;
		 float speed = 1f;
	
		 // FIRE
		 fireEff1 = new ActionbarEffect(
				CORE_V.WINDOW_WIDTH / 2 - abW - gap,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30,
				CORE_V.WINDOW_WIDTH / 2 - abW - gap + abW - 5,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30 + abH -5,
				1,
				speed,
				size,
				new Color(255,0,0,150));
		fireEff2 = new ActionbarEffect(
				CORE_V.WINDOW_WIDTH / 2 - abW - gap,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30,
				CORE_V.WINDOW_WIDTH / 2 - abW - gap + abW - 5,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30 + abH -5,
				2,
				speed,
				size,
				new Color(255,0,0,150));
		fireEff3 = new ActionbarEffect(
				CORE_V.WINDOW_WIDTH / 2 - abW - gap,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30,
				CORE_V.WINDOW_WIDTH / 2 - abW - gap + abW - 5,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30 + abH -5,
				3,
				speed,
				size,
				new Color(255,0,0,150));
		fireEff4 = new ActionbarEffect(
				CORE_V.WINDOW_WIDTH / 2 - abW - gap,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30,
				CORE_V.WINDOW_WIDTH / 2 - abW - gap + abW - 5,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30 + abH -5,
				4,
				speed,
				size,
				new Color(255,0,0,150));
		
		
		// LIGHTNING
		lightEff1 = new ActionbarEffect(	
				CORE_V.WINDOW_WIDTH / 2 + gap,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30,
				CORE_V.WINDOW_WIDTH / 2 + gap + abW - size,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30 + abH -5,
				1,
				speed,
				size,
				new Color(0,0,255,150));
		lightEff2 = new ActionbarEffect(	
				CORE_V.WINDOW_WIDTH / 2 + gap,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30,
				CORE_V.WINDOW_WIDTH / 2 + gap + abW - size,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30 + abH -5,
				2,
				speed,
				size,
				new Color(0,0,255,150));
		lightEff3 = new ActionbarEffect(	
				CORE_V.WINDOW_WIDTH / 2 + gap,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30,
				CORE_V.WINDOW_WIDTH / 2 + gap + abW - size,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30 + abH -5,
				3,
				speed,
				size,
				new Color(0,0,255,150));
		lightEff4 = new ActionbarEffect(	
				CORE_V.WINDOW_WIDTH / 2 + gap,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30,
				CORE_V.WINDOW_WIDTH / 2 + gap + abW - size,
				CORE_V.WINDOW_HEIGHT - abH - gap - 30 + abH -5,
				4,
				speed,
				size,
				new Color(0,0,255,150));
	}
	
	public void update(double delta){
		if(isFire){
			isLightning = false;
			fireEff1.update(delta);
			fireEff2.update(delta);
			fireEff3.update(delta);
			fireEff4.update(delta);
		}
		if(isLightning){
			isFire = false;
			lightEff1.update(delta);
			lightEff2.update(delta);
			lightEff3.update(delta);
			lightEff4.update(delta);
		}
		
		
	
			if(player.getHealth() < 20){
				almostDying = true;
			}else
				almostDying = false;
		
		
		if(almostDying){
			if(alpha >= 60){
				shouldIncrease = false;
				shouldDecrease = true;
			}
			if(alpha <= 20){
				shouldDecrease = false;
				shouldIncrease = true;
			}
				
			
			if(shouldIncrease){
				alpha += alphaSpeed;
			}else if(shouldDecrease){
				alpha -= alphaSpeed;
			}
			
			
		}

		
		if(playerlist != null){
			playerlist.update(delta);
			
			if(buttonHeldOver != playerlist.isHeldOver()){
				buttonHeldOver = playerlist.isHeldOver();
			}
			
			
		}
	
		if(chat != null){
			chat.update(delta);
			if(buttonHeldOver != chat.isHeldOver()){
				buttonHeldOver = chat.isHeldOver();
			}
		}
		
	}
	
	private Polygon up, left,right,down;
	
	public void render(Graphics2D g){
		if(isFire){
			g.setColor(Color.green);
		}
		else
		g.setColor(Color.gray);
		
		g.drawRect(CORE_V.WINDOW_WIDTH / 2 - abW - gap, CORE_V.WINDOW_HEIGHT - abH - gap - 30, abW, abH);
		g.setColor(new Color(0,0,0,200));
		g.fillRect(CORE_V.WINDOW_WIDTH / 2  -  abW - gap + 1, CORE_V.WINDOW_HEIGHT - abH - gap - 29, 63, 63);
		g.drawImage(Resources.fireball,CORE_V.WINDOW_WIDTH / 2 - abW - gap, CORE_V.WINDOW_HEIGHT - abH - gap - 30, 64,64,  null);
		
		if(isLightning)
			g.setColor(Color.green);
		else
		g.setColor(Color.gray);
		
		
		g.drawRect(CORE_V.WINDOW_WIDTH / 2 +  gap, CORE_V.WINDOW_HEIGHT - abH - gap - 30, abW, abH);
		g.setColor(new Color(0,0,0,200));
		g.fillRect(CORE_V.WINDOW_WIDTH / 2 +  gap + 1, CORE_V.WINDOW_HEIGHT - abH - gap - 29, 63, 63);
		g.drawImage(Resources.lighteningball,CORE_V.WINDOW_WIDTH / 2 +  gap, CORE_V.WINDOW_HEIGHT - abH - gap - 30, 64,64,  null);
		
		g.setColor(Color.white);
		g.drawString("1",CORE_V.WINDOW_WIDTH / 2 - abW - gap + 5, CORE_V.WINDOW_HEIGHT - 35 - gap);
		g.drawString("2",CORE_V.WINDOW_WIDTH / 2 + 15, CORE_V.WINDOW_HEIGHT - 35 - gap);
		
		
		
		
		
		
		g.setColor(Color.gray);
		g.drawRect(CORE_V.WINDOW_WIDTH / 2 +  gap*2 + 5 + 64 + 1, CORE_V.WINDOW_HEIGHT - abH - gap -5 , 38, 38);
		if(teleportQuantity == 0){
			g.setColor(new Color(255,0,0,200));
		}else{
			g.setColor(new Color(0,0,0,200));
			
		}
		g.fillRect(CORE_V.WINDOW_WIDTH / 2 +  gap*2 + 5 + 64 + 2, CORE_V.WINDOW_HEIGHT - abH - gap - 4, 37, 37);
		
		g.drawImage(Resources.teleportIcon, CORE_V.WINDOW_WIDTH / 2 +  gap*2 + 5 + 64 + 2, CORE_V.WINDOW_HEIGHT - abH - gap - 4, 37, 37, null);
		g.drawImage(Resources.mouseIcon, CORE_V.WINDOW_WIDTH / 2 +  gap*2 + 5 + 64 + 3, CORE_V.WINDOW_HEIGHT - abH - gap + 16, 16, 16, null);
		g.setColor(Color.white);
		g.setFont(Resources.informationFont);
		g.drawString("Charges: " + teleportQuantity,CORE_V.WINDOW_WIDTH / 2 +  gap*2 + 5 + 64 + 3 + abW - 20, CORE_V.WINDOW_HEIGHT - abH - gap + 30);
		
		
		if(isFire){
			fireEff1.render(g);
			fireEff2.render(g);
			fireEff3.render(g);
			fireEff4.render(g);
		}
		if(isLightning){
			lightEff1.render(g);
			lightEff2.render(g);
			lightEff3.render(g);
			lightEff4.render(g);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	// Up
		g.setColor(Color.white);
		int ux[] = {CORE_V.WINDOW_WIDTH,CORE_V.WINDOW_WIDTH /2, CORE_V.WINDOW_WIDTH /2,0};
		int uy[] = {0,CORE_V.WINDOW_HEIGHT / 2 - 48 / 2, CORE_V.WINDOW_HEIGHT / 2 - 48 / 2,0};
		up = new Polygon(ux,uy,ux.length);
		
		// Down
	
		int dx[] = {CORE_V.WINDOW_WIDTH, CORE_V.WINDOW_WIDTH / 2,CORE_V.WINDOW_WIDTH / 2, 0};
		int dy[] = {CORE_V.WINDOW_HEIGHT, CORE_V.WINDOW_HEIGHT / 2  - 48 / 2, CORE_V.WINDOW_HEIGHT / 2  - 48 / 2, CORE_V.WINDOW_HEIGHT};
		down = new Polygon(dx, dy, dx.length);
		
		// Left
	
		int lx[] = {0, CORE_V.WINDOW_WIDTH / 2, CORE_V.WINDOW_WIDTH / 2 , 0};
		int ly[] = {CORE_V.WINDOW_HEIGHT, CORE_V.WINDOW_HEIGHT / 2 - 48 / 2, CORE_V.WINDOW_HEIGHT / 2 - 48 / 2, 0};
		left = new Polygon(lx, ly, lx.length);
		
		// Right
		
		int rx[] = {CORE_V.WINDOW_WIDTH, CORE_V.WINDOW_WIDTH / 2, CORE_V.WINDOW_WIDTH / 2, CORE_V.WINDOW_WIDTH};
		int ry[] = {CORE_V.WINDOW_HEIGHT, CORE_V.WINDOW_HEIGHT / 2 - 48 / 2, CORE_V.WINDOW_HEIGHT / 2 - 48 / 2, 0};
		right = new Polygon(rx, ry, rx.length);
		
		if(debugPoly){
		g.drawPolygon(up);
		g.drawPolygon(down);
		g.drawPolygon(left);
		g.drawPolygon(right);
		}
		
		
		if(almostDying){
			g.setColor(new Color(255,0,0,alpha));
			g.fillRect(0, 0, CORE_V.WINDOW_WIDTH, CORE_V.WINDOW_HEIGHT);
		}
		
		if(playerlist != null){
			playerlist.render(g);
		}
		if(chat != null){
			chat.render(g);
		}
		
		
	}
	private boolean almostDying = false;
	private boolean shouldDecrease = false;
	private boolean shouldIncrease = false;
	
	
	
	public void mousePressed(MouseEvent e){
		if(playerlist.isHeldOver()){
			onlinePlayers.activateORdisable();
		}
		if(chat.isHeldOver()){
			if(client != null){
				if(client.frame != null){
					if(!client.frame.isVisible())
					client.frame.setVisible(true);
				}
			}
		}
		
		
	}
	
	public boolean isFire() {
		return isFire;
	}


	public void setFire(boolean isFire) {
		this.isFire = isFire;
		this.isLightning = false;
	}


	public boolean isLightning() {
		return isLightning;
	}


	public void setLightning(boolean isLightning) {
		this.isLightning = isLightning;
		this.isFire = false;
	}


	public Polygon getUp() {
		return up;
	}


	public void setUp(Polygon up) {
		this.up = up;
	}


	public Polygon getLeft() {
		return left;
	}


	public void setLeft(Polygon left) {
		this.left = left;
	}


	public Polygon getRight() {
		return right;
	}


	public void setRight(Polygon right) {
		this.right = right;
	}


	public Polygon getDown() {
		return down;
	}


	public void setDown(Polygon down) {
		this.down = down;
	}

	public int getTeleportQuantity() {
		return teleportQuantity;
	}

	public void setTeleportQuantity(int teleportQuantity) {
		this.teleportQuantity = teleportQuantity;
	}

	public boolean isButtonHeldOver() {
		return buttonHeldOver;
	}
	
	
	
	
	
}
