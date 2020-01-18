package com.alexander.danliden.delend.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.alexander.danliden.delend.entities.models.Player;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;
import com.alexander.danliden.delend.scene.Scene;
import com.alexander.danliden.delend.utils.Button;
import com.alexander.danliden.delend.world.Block;
import com.alexander.danliden.delend.world.World;

public class TransportGUI {

	private int width, height;
	private int buttonHeight = 25;
	private float x, y;
	public ArrayList<Button> buttons = new ArrayList<Button>();
	private boolean show;
	private Font privatefont;
	private String TITLE = "Transporter";
	private int ptw,pth;
	private Player player;
	private Client client;
	private boolean atTown = true, atBattleground = false;
	private Scene scene;
	private boolean travel = false;
	private boolean toBattleground = false, toTown = false;
	private boolean transporting = false;
	
	
	public TransportGUI(Player player, Client client){
		this.player = player;
		this.client = client;
		width = 150;
		height = 130;
		x = (CORE_V.WINDOW_WIDTH / 2 ) - (width / 2);
		y = (CORE_V.WINDOW_HEIGHT / 2) - (height / 2) + 70;
		show = false;
		addButton(new Button(x + 1,y + 30,width - 2,buttonHeight,"Battleground", 14, "battleground"));
		addButton(new Button(x + 1,y + 30 + buttonHeight,width - 2,buttonHeight,"Town", 14,"town"));
		privatefont = new Font("Arial", Font.BOLD, 16);
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at,true,true);
		ptw = (int)privatefont.getStringBounds(TITLE, frc).getWidth();
		pth = (int)privatefont.getStringBounds(TITLE, frc).getHeight();
		scene = new Scene(player, client);
	}
	
	public void update(double delta){
		
		if(scene != null){
			if(scene.isStart())
			scene.update(delta);
			
			transporting = scene.isStart();
		}
		
		if(show){

		if(!buttons.isEmpty()){
			for(int i = 0; i < buttons.size(); i++){
				buttons.get(i).update(delta);
				
				
			}
		}
		if(player != null && World.gamecamera != null && World.townRectangle != null && World.battlegroundRectangle != null){
			if(player.bounds.intersects(World.townRectangle)){
				atTown = true;
			}else if(!player.bounds.intersects(World.townRectangle)){
				atTown = false;
			}
			
			if(player.bounds.intersects(World.battlegroundRectangle)){
				atBattleground = true;
			}else if(!player.bounds.intersects(World.battlegroundRectangle)){
				atBattleground = false;
			}
			
		}
		
		
		}
		if(scene.isFirstPhaseDone()){
			travel = true;
				
			}
		
		if(travel){
			travel = false;
			if(toBattleground){
				toBattleground = false;
				player.setX(108 * Block.BlockSize );
				player.setY(82 * Block.BlockSize );
				String coords = "04"+ player.getX() + "," + player.getY() + "," + client.getID() + "," + 3 + "," + false;
				client.send(coords.getBytes());
				
			}else if(toTown){
				toTown = false;
				player.setX(7 * Block.BlockSize);
				player.setY(6 * Block.BlockSize);
				String coords = "04"+ player.getX() + "," + player.getY() + "," + client.getID() + "," + 3 + "," + false;
				client.send(coords.getBytes());
				
			}
		}
		
		
	}
	
	public void render(Graphics2D g){
		if(show){
		
			
		// Draw frame
		g.setColor(new Color(0,0,0,200));
		g.fillRect((int)x, (int)y, width, height);
		g.setColor(new Color(255,255,255,255));
		g.drawRect((int)x, (int)y, width, height);
		
		if(!buttons.isEmpty()){
			for(int i = 0; i < buttons.size(); i++){
				buttons.get(i).render(g);
			}
		}
		

		// Draw title
		g.setFont(privatefont);
		g.setColor(Color.orange);
		g.drawString(TITLE, (int)x + width / 2 - ptw / 2, (int)y + pth);
		
		
		}
		
		if(scene != null){
			scene.render(g);
		}
	}
	
	public void addButton(Button b){
		if(!buttons.contains(b))
			buttons.add(b);
	}
	public void removeButton(Button b){
		if(!buttons.isEmpty()){
			if(buttons.contains(b)){
				buttons.remove(b);
			}
		}
	}
	
	
	public void mousePressed(MouseEvent e){
		for(int i = 0; i < buttons.size(); i++){
		if(buttons.get(i).isHeldOver()){
			if(buttons.get(i).getIdentifier().equalsIgnoreCase("battleground")){
				if(!atBattleground){
				if(player != null && client != null){
					hideGUI();
					scene.setStart(true);
					toBattleground = true;
					String message = "12" + scene.isStart() + "," + player.getId();
					if(client != null)
						client.send(message.getBytes());
				}
				}
			
			}else if(buttons.get(i).getIdentifier().equalsIgnoreCase("town")){
				if(!atTown){
					if(player != null && client != null){
						hideGUI();
						scene.setStart(true);
						toTown = true;
						String message = "12" + scene.isStart() + "," + player.getId();
						if(client != null)
							client.send(message.getBytes());
						}
				}
				
				
			}
			
		}
			
			
			
		}
	}
	
	
	public void showGUI(){
		show = true;
	}
	
	public void hideGUI(){
		show = false;
	}

	public boolean isShow() {
		return show;
	}

	public boolean isTransporting() {
		return transporting;
	}
	
}
