package com.alexander.danliden.delend.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.alexander.danliden.delend.chatprogram.Frame;
import com.alexander.danliden.delend.entities.Entity;
import com.alexander.danliden.delend.entities.models.Player;
import com.alexander.danliden.delend.gamecamera.Gamecamera;
import com.alexander.danliden.delend.mainpackage.Game;
import com.alexander.danliden.delend.mainpackage.Main;
import com.alexander.danliden.delend.mainpackage.Resources;
import com.alexander.danliden.delend.mainpackage.corevalues.CORE_V;
import com.alexander.danliden.delend.mainpackage.startup.mulitplayer.Client;
import com.alexander.danliden.delend.projectiles.Projectile;
import com.alexander.danliden.delend.world.Block.BlockType;



public class World {

	private TileManager tiles = new TileManager(this);
	private BufferedImage map;
	private int mapwidth;
	private int mapheight;
	
	public static Player player;
	public Rectangle render;
	public static Gamecamera gamecamera;
	private int BlockSize = 48;
	public static Rectangle playerBounds;
	public boolean chatOpen = false;
	public static CopyOnWriteArrayList<Entity>entities = new CopyOnWriteArrayList<Entity>();    
	
	private Client client;
	
	public static Rectangle townRectangle, battlegroundRectangle;
	private int townX = 0, townY = 0, battlegroundX = (12*Block.BlockSize), battlegroundY = (59*Block.BlockSize);
	private int townWidth =(14*Block.BlockSize), townHeight = (13*Block.BlockSize), battlegroundWidth = (103*Block.BlockSize), battlegroundHeight = (55 * Block.BlockSize);
	
	private Comparator<Entity> renderOrder = new Comparator<Entity>(){
		@Override
		public int compare(Entity a, Entity b) {
			if(a.getY() < b.getY())
				return -1;
			return 1;
		}	
	};
	
	
	
	public World(BufferedImage map, int mapwidth, int mapheight){
		this.map = map;
		this.mapwidth = mapwidth;
		this.mapheight = mapheight;
		// Login packet
		if(Game.multiplayer){
			client = new Client(Game.username,Game.address, Game.port, this);
			client.start();
			String connectPacket = "00" + Game.username;
			client.send(connectPacket.getBytes());
			
		}
		initialize();
		
	}
	
	private void initialize(){
	
		if(!Game.multiplayer){
			gamecamera = new Gamecamera(0,0);
			gamecamera.move(0, 0);
			player = new Player(100,100,this);
			entities.add(player);
			render = new Rectangle((int)(player.getX()  - CORE_V.WINDOW_WIDTH / 2 - gamecamera.getxOffset() - BlockSize),
					(int)(player.getY() - CORE_V.WINDOW_HEIGHT / 2  - gamecamera.getyOffset() - BlockSize),
					CORE_V.WINDOW_WIDTH + BlockSize, CORE_V.WINDOW_HEIGHT + BlockSize);
			playerBounds = new Rectangle((int)(player.getX() - gamecamera.getxOffset() - player.getWidth() / 2),
					(int)(player.getY() - gamecamera.getyOffset() - player.getHeight() / 2),
					25,25);
			}
		
		
		townRectangle = new Rectangle(townX,townY,townWidth,townHeight);
		battlegroundRectangle = new Rectangle(battlegroundX, battlegroundY, battlegroundWidth, battlegroundHeight);
		if(CORE_V.SOUND_ENABLED){
		
		}
	}
	
	public void update(double delta){
		if(gamecamera != null){
			gamecamera.centerOnPlayer(player);
		}
		tiles.update(delta);
		
		
		if(client != null){
			if(client.frame != null){
				
				if(client.frame.ifFocused() && !chatOpen){
					chatOpen = true;
				}

			if(!client.frame.ifFocused() && chatOpen){
				client.frame.dispose();
				chatOpen = false;
				System.out.println("Closing");
				
			}
			
			//Update chat position
			if(chatOpen){
				client.frame.setLocation(
						Main.WINDOW_FRAME.getLocationOnScreen().x + 5, 
						(Main.WINDOW_FRAME.getLocationOnScreen().y + CORE_V.WINDOW_HEIGHT) - client.frame.getHeight() - 10 );
				
					client.frame.repaint();
					
				//}
			}
			client.frame.toFront();
			
			
			
			}
		}
		
		if(townRectangle != null && gamecamera != null)
		townRectangle.setBounds((int)(townX - gamecamera.getxOffset()),(int)(townY - gamecamera.getyOffset()),townWidth,townHeight);
		
		if(battlegroundRectangle != null && gamecamera != null)
		battlegroundRectangle.setBounds((int)(battlegroundX - gamecamera.getxOffset()), 
				(int)(battlegroundY - gamecamera.getyOffset()), battlegroundWidth, battlegroundHeight);
		
		if(player != null){
			if(player.transportgui != null)
				player.transportgui.update(delta);
		}
		
		
		if(!entities.isEmpty()){
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).update(delta);
		}
		}
		
		
		
		
		if(render != null){
			if(gamecamera != null){
			render.setBounds((int)(player.getX()  - CORE_V.WINDOW_WIDTH / 2 - gamecamera.getxOffset() - BlockSize),
					(int)(player.getY() - CORE_V.WINDOW_HEIGHT / 2  - gamecamera.getyOffset() - BlockSize),
					CORE_V.WINDOW_WIDTH + BlockSize * 2, CORE_V.WINDOW_HEIGHT + BlockSize);
			}
			
		}
		if(playerBounds != null){
			if(!Game.multiplayer){
				playerBounds.setBounds((int)(player.getX() - gamecamera.getxOffset() - player.getWidth() / 2),
						(int)(player.getY() - gamecamera.getyOffset() - player.getHeight() / 2),
						25,25);
			}else if(Game.multiplayer){
				if(gamecamera != null){
				playerBounds.setBounds((int)(player.getX() - gamecamera.getxOffset()),
						(int)(player.getY() - gamecamera.getyOffset()),
						25,25);
				}
			}
		}
		
		if(!entities.isEmpty() && entities != null)
			entities.sort(renderOrder);
		
		if(!Player.projectiles.isEmpty()){
			for(Projectile p: Player.projectiles){
				p.update(delta);
			}
		}
		
	}
	
	public void render(Graphics2D g){
		tiles.render(g);
		if(!entities.isEmpty()){
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).render(g);
		}
		}
		g.setColor(Color.red);
		if(!Game.multiplayer){
		g.drawRect((int)(player.getX() - gamecamera.getxOffset() - player.getWidth() / 2),
				(int)(player.getY() - gamecamera.getyOffset() - player.getHeight() / 2),
				25,25);
		}else if(Game.multiplayer){
			if(gamecamera != null){
			
			
			}
			
			if(player != null){
				player.selfRender(g);
			}
			
		}
		
		if(player != null){
			if(player.transportgui != null)
				player.transportgui.render(g);
		}
		
		
		
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public static void modAlpha(BufferedImage modMe, double modAmount) {
        //
    for (int x = 0; x < modMe.getWidth(); x++) {          
        for (int y = 0; y < modMe.getHeight(); y++) {
                //
            int argb = modMe.getRGB(x, y); //always returns TYPE_INT_ARGB
            int alpha = (argb >> 24) & 0xff;  //isolate alpha

            alpha *= modAmount; //similar distortion to tape saturation (has scrunching effect, eliminates clipping)
            alpha &= 0xff;      //keeps alpha in 0-255 range

            argb &= 0x00ffffff; //remove old alpha info
            argb |= (alpha << 24);  //add new alpha info
            modMe.setRGB(x, y, argb);            
        }
    }
}
	
	public void generateMap(){
		for(int x = 0; x < mapwidth; x++){
			for(int y = 0; y < mapheight; y++){
				int col = map.getRGB(x, y);
				switch(col & 0xFFFFFF){
				case 0x33119c:
					TileManager.blocks.add(new Block(x * Block.BlockSize, y * Block.BlockSize, BlockType.NEUTRAL_GROUND));
					break;
				case 0x130639:
					TileManager.blocks.add(new Block(x * Block.BlockSize, y * Block.BlockSize, BlockType.BATTLE_GROUND));
					break;
				case 0xe54100:
					TileManager.blocks.add(new Block(x * Block.BlockSize, y * Block.BlockSize, BlockType.CRATE).isSolid(true));
					break;
				case 0x6c6c6c:
					TileManager.blocks.add(new Block(x * Block.BlockSize, y * Block.BlockSize, BlockType.WALLTOP).isSolid(true));
					break;
				case 0x8b8b8b:
					TileManager.blocks.add(new Block(x * Block.BlockSize, y * Block.BlockSize, BlockType.WALL).isSolid(true));
					break;
				}
			}
		}
	}
	
}
