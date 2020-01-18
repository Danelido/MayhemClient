package com.alexander.danliden.delend.mainpackage;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.alexander.danliden.delend.animation.Animator;
import com.alexander.danliden.delend.utils.Soundfile;

public class Resources {

	private static String FILEPATH = "/com/alexander/danliden/delend/resources/";
	private static String SOUNDPATH = "/com/alexander/danliden/delend/resources/soundfiles/";
	
	
	
	
	public static Font font, damageFont, informationFont;
	public static BufferedImage arenaMap;
	
	//Tiles
	private static BufferedImage neutral_ground_sheet, battle_ground_sheet;
	public static BufferedImage stoneTile;
	public static BufferedImage wall_top1;
	public static BufferedImage wall;
	public static BufferedImage neutral_ground, battle_ground, crate;
	
	
	//player
	private static BufferedImage playersheet;
	public static Animator anim_up, anim_down , anim_left, anim_right;
	private static ArrayList<BufferedImage> anim_list_up, anim_list_down, anim_list_left, anim_list_right;
	public static BufferedImage playerLookUp, playerLookDown, playerLookLeft, playerLookRight;
	private static int w = 24, h = 32;
	
	// player attack
	public static Animator attackUpAnimation, attackDownAnimation, attackLeftAnimation, attackRightAnimation;
	private static ArrayList<BufferedImage> attackUpList,attackDownList,attackLeftList,attackRightList;
	
	// stuff
	public static BufferedImage mouseIcon;
	
	
	//fireball spell
	public static BufferedImage fireball;
	private static BufferedImage fireballsheet;
	public static Animator fireballAnimation;
	public static ArrayList<BufferedImage> fireballAnimationList;
	private static int fbW = 64, fbH = 64;
	private static BufferedImage explosionsheet;
	public static ArrayList<BufferedImage> explosionList;
	public static ArrayList<BufferedImage> phaseExplosionList;
	private static BufferedImage phaseExplosionsheet;
	
	//lightningbolt
	public static BufferedImage lighteningball;
	private static ArrayList<BufferedImage> lightningboltList;
	public static Animator lightningboltAnimation;
	private int lbW = 64, lbH = 64;
	public static ArrayList<BufferedImage> lighteningboltImpact ;	
	public static ArrayList<BufferedImage> phaseLightningList;
	// Teleport
	public static BufferedImage teleportIcon;
	public static ArrayList<BufferedImage> teleportList;
	private int tpW = 64, tpH = 64;
	
	//Npc
	public static BufferedImage transporterguy;
	
	//transport
	public static ArrayList<BufferedImage> smokelist;
	private int smokeW = 50;
	private int smokeH = 47;
	public static Soundfile backgroundsound;
	
	
	public static void loadAllResources(){
		
		Fonts();
		Maps();
		Tiles();
		PlayerResources();
		Fireball();
		LightningBolt();
		Teleport();
		resources();
		npcs();
		smoke();
		loadSounds();
	}
	
	
	
	private static void loadSounds(){
		backgroundsound = new Soundfile(SOUNDPATH + "backgroundsound.wav");
	}
	
	private static void smoke(){
		smokelist = new ArrayList<BufferedImage>();
		
		for(int i = 0; i < 19; i++){
			smokelist.add(loadImage(FILEPATH + "smoke_black_1_19_"+ (i + 1) +".png"));	
			
		}
		
	}
	private static void npcs(){
		transporterguy = crop(loadImage(FILEPATH + "npcsheet.png"), 2,5,32,32);
	}
	
	private static void Teleport(){
		teleportList = new ArrayList<BufferedImage>();
		for(int i = 0; i < 9; i++){
			teleportList.add(loadImage(FILEPATH + "light_glow_0" + (i+1) + ".png"));
			
		}
		teleportList.add(loadImage(FILEPATH + "light_glow_10" + ".png"));
		teleportIcon = loadImage(FILEPATH + "light_glow_05.png");
	}
	
	private static void LightningBolt(){
		lightningboltList = new ArrayList<BufferedImage>();
		phaseLightningList = new ArrayList<BufferedImage>();
		
		for(int i = 0; i < 20; i++){
			lightningboltList.add(loadImage(FILEPATH + "lighteningball_1_20_" + (i+1) + ".png"));
			
		}
		lighteningball = loadImage(FILEPATH + "lighteningball_1_20_13.png");
		lightningboltAnimation = new Animator(lightningboltList);
		
		lighteningboltImpact = new ArrayList<BufferedImage>();
		for(int j = 0; j < 20; j++){
			lighteningboltImpact.add(loadImage(FILEPATH + "spell_bluetop_1_" + (j+1) + ".png"));
		}
		
		for(int i = 0; i < 32; i++){
			phaseLightningList.add(loadImage(FILEPATH + "aura_test_1_32_" +(i+1) +".png"));
			
		}
		
		
	}
	
	private static void Fireball(){
		fireballsheet = loadImage(FILEPATH + "fireball_0.png");
		phaseExplosionsheet = loadImage(FILEPATH + "tests.png");
		fireballAnimationList = new ArrayList<BufferedImage>();
		phaseExplosionList = new ArrayList<BufferedImage>();
		
		for(int i = 0; i < 8; i++){
			fireballAnimationList.add(crop(fireballsheet,i+1,5,fbW,fbH));
			
		}
		fireball = crop(fireballsheet,2,4,fbW,fbH);
		fireballAnimation = new Animator(fireballAnimationList);
		
		
		explosionsheet = loadImage(FILEPATH + "explosion.png");
		explosionList = new ArrayList<BufferedImage>();
		
		for(int y = 0; y < 4; y++){
			for(int x = 0; x < 3; x++){
				explosionList.add(crop(explosionsheet,x + 1,y + 1,256,128));
			}
		}
		
		for(int y = 0; y < 3; y++){
			for(int x = 0; x < 10; x++){
				phaseExplosionList.add(crop(phaseExplosionsheet,(x+ 1),(y+1),100,100));
			}
		}
		
			
		
		
		
		
	}
	
	private static void resources(){
		mouseIcon = loadImage(FILEPATH + "mouseIcon.png");
	}
	
	private static void Fonts(){
		font = new Font("Arial", Font.BOLD, 12);
		damageFont = new Font("Arial", Font.BOLD, 18);
		informationFont = new Font("Arial", Font.BOLD, 11);
	}
	
	private static void Maps(){
		arenaMap = loadImage(FILEPATH + "LastManStandingMap.png");
	}
	private static void Tiles(){
		neutral_ground_sheet = loadImage(FILEPATH + "tile_1.png");
		battle_ground_sheet = loadImage(FILEPATH + "tile_s.png");
		crate = loadImage(FILEPATH + "SmallCrate.png");
		
		neutral_ground = crop(neutral_ground_sheet, 1,1,64,64);
		battle_ground = crop(battle_ground_sheet, 1,1,64,64);
		stoneTile = loadImage(FILEPATH + "Stones.png");
		wall_top1 = loadImage(FILEPATH + "wall_top1.png");
		wall = loadImage(FILEPATH + "wall2.png");
	}
	
	
	private static void PlayerResources(){
		playersheet = loadImage(FILEPATH + "Fumiko.png");
		anim_list_up = new ArrayList<BufferedImage>();
		anim_list_down = new ArrayList<BufferedImage>();
		anim_list_left = new ArrayList<BufferedImage>();
		anim_list_right = new ArrayList<BufferedImage>();
		attackUpList = new ArrayList<BufferedImage>();
		attackDownList = new ArrayList<BufferedImage>();
		attackRightList = new ArrayList<BufferedImage>();
		attackLeftList = new ArrayList<BufferedImage>();
		
		// REGULAR WALKING ANIMATIONS
		for(int i = 3; i <= 6; i++){
			if(playerLookUp == null){
				playerLookUp = crop(playersheet,5,1,w,h);
			}
			anim_list_up.add(crop(playersheet, i, 1, w,h));	
		}
		for(int i = 3; i <= 6; i++){
			if(playerLookDown == null){
				playerLookDown = crop(playersheet,2,3,w,h);
			}
			anim_list_down.add(crop(playersheet, i, 3, w,h));
		}
		for(int i = 3; i <= 6; i++){
			if(playerLookLeft == null){
				playerLookLeft = crop(playersheet,2,4,w,h);
			}
			anim_list_left.add(crop(playersheet, i, 4, w,h));
		}
		for(int i = 3; i <= 6; i++){
			if(playerLookRight == null){
				playerLookRight = crop(playersheet,2,2,w,h);
			}
			anim_list_right.add(crop(playersheet, i,2, w,h));
		}
		
		anim_up = new Animator(anim_list_up);
		anim_down = new Animator(anim_list_down);
		anim_left = new Animator(anim_list_left);
		anim_right = new Animator(anim_list_right);
		
		
		// ATTACKS
		for(int i = 1; i < 5; i++){
			attackRightList.add(crop(playersheet,i, 6,w,h));
		}
		for(int i = 1; i < 5; i++){
			attackLeftList.add(crop(playersheet,i, 8,w,h));
		}
		for(int i = 1; i < 5; i++){
			attackDownList.add(crop(playersheet,i, 7,w,h));
		}
		for(int i = 1; i < 5; i++){
			attackUpList.add(crop(playersheet,i, 5,w,h));
		}
		
		attackRightAnimation = new Animator(attackRightList);
		attackLeftAnimation = new Animator(attackLeftList);
		attackUpAnimation = new Animator(attackUpList);
		attackDownAnimation = new Animator(attackDownList);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static BufferedImage loadImage(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(Resources.class.getResourceAsStream(path));
		} catch (IOException e) {
			System.out.println("ERROR LOADING IMAGE WITH PATH" + path);
			e.printStackTrace();
		}
		return img;
	}
	
	private static BufferedImage crop(BufferedImage sheet,int col, int row, int width, int height){
		
		return sheet.getSubimage((col * width) - width, (row * height) - height, width, height);
		
	}
	
	
	
}
