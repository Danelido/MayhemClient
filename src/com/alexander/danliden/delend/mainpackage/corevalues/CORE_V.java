package com.alexander.danliden.delend.mainpackage.corevalues;

public class CORE_V {

	// Storing some core values, might get larger with time but who knows.
	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
	public static final String WINDOW_TITLE = "Mayhem";
	public static double framecap = 1.0 / 60.0;
	public static boolean SOUND_ENABLED = false;
	
	// private just in case
	private static final String DEFAULTGATEWAYIP = "Put your router IP here"; // Server IP
	private static final int DEFAULTPORT = 39678;					// Server Port ( the server port )
	
	public static String getDefaultGatewayIP(){
		return DEFAULTGATEWAYIP;
	}
	
	public static int getDefaultPort(){
		return DEFAULTPORT;
	}
	
}
