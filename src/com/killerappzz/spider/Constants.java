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
	 * Acuma este dependent de dimensiunea Spider-ului
	 * si nu de dimensiunea ecranului! 
	 * TODO nu mai vine odata HUD-ul ala care sa-mi dicteze
	 * zona de expansion...
	 * */
	public static final int EXPANSION_FACTOR = 2;

	// unde afisez stats uri like fps
	public static final int STATS_POSITION_X = 0;
	public static final int STATS_POSITION_Y = 16;
	
	// offset-urile subcomponentelor din acceleration slider
	// exprimate procentual in fct de dimensiunea componentei(obs pot avea si negativ)
	public static final float MOVEMENT_SLIDER_BASE_X = 0.50f;
	public static final float MOVEMENT_SLIDER_BASE_Y = 0.125f;
    // offset-urile subcomponentelor din acceleration slider
 	// exprimate procentual in fct de dimensiunea componentei
 	public static final float DIRECTION_KNOB_X = -0.125f;
 	public static final float DIRECTION_KNOB_Y = 0.125f;
	
	// TODO customization
	public static final int MAX_LIFES = 3;
}
