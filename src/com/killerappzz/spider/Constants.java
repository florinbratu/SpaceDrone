package com.killerappzz.spider;

/**
 * The constants
 * @author florin
 *
 */
public interface Constants {
	
	public static final String DEBUG_TAG = "SPACEDRONE";

	public static final int TOUCH_ERROR_TOLERANCE_PERCENTILE = 10;
	
	/* vitezele le masor in felul urmator: cate secunde ii ia
	 * sa traverseze tot ecranul!
	 */
	public static final int DEFAULT_SPIDER_SPEED_FACTOR = 10;
	public static final int MAX_SPIDER_SPEED_FACTOR = 4;
	
	/* procentul de ecran pe care il mai afisez inainte 
	 * sa incep sa scrollez ecranul
	 * */
	public static final int EXPANSION_PERCENTILE = 15;

	// unde afisez stats uri like fps
	public static final int STATS_POSITION_X = 0;
	public static final int STATS_POSITION_Y = 16;
	
	// unde afisez acceleration slider
	public static final float MOVEMENT_SLIDER_BASE_X = 20.0f;
	public static final float MOVEMENT_SLIDER_BASE_Y = 16.0f;
    public static final float MOVEMENT_SLIDER_BUTTON_Y = MOVEMENT_SLIDER_BASE_Y + 32.0f;
    public static final float MOVEMENT_SLIDER_BUTTON_X =  MOVEMENT_SLIDER_BASE_X - 16.0f;
    // unde afisez controlul directiei
 	public static final float DIRECTION_KNOB_X = -16.0f;
 	public static final float DIRECTION_KNOB_Y = 16.0f;
	
	// TODO customization
	public static final int MAX_LIFES = 3;
}
