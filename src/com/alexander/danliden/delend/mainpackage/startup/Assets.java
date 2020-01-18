package com.alexander.danliden.delend.mainpackage.startup;

import javax.swing.ImageIcon;

import com.alexander.danliden.delend.utils.Soundfile;

public class Assets {

	private static String FILEPATH = "/com/alexander/danliden/delend/resources/";
	private static String SOUNDPATH = "/com/alexander/danliden/delend/resources/soundfiles/"; // If needed
	
	
	
	public ImageIcon Application_backgroundIcon;
	public ImageIcon Application_backgroundIcon_RAW;
	public Soundfile backgroundsound;
	
	
	
	public void load(){
		loadApplicationAssets();
		loadBackgroundSound();
	}
	
	
	private void loadBackgroundSound(){
		backgroundsound = new Soundfile(SOUNDPATH + "backgroundsound.wav");
	}
	
	private void loadApplicationAssets(){
		Application_backgroundIcon = new ImageIcon(getClass().getResource(FILEPATH + "ApplicationBackground.png"));
		Application_backgroundIcon_RAW = new ImageIcon(getClass().getResource(FILEPATH + "ApplicationBackground_RAW.png"));
		if(Application_backgroundIcon == null)System.out.println("APPLICATION_BACKGROUND CAN'T BE FOUND!");
		if(Application_backgroundIcon_RAW == null)System.out.println("APPLICATION_BACKGROUND_RAW CAN'T BE FOUND!");
		
	}
	
	
	
	
}
