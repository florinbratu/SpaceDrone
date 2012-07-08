package com.killerappzz.spider;

/**
 * The constants
 * @author florin
 *
 */
public interface Constants {

	public static final int TOUCH_ERROR_TOLERANCE_PERCENTILE = 10;
	
	/* vitezele le masor in felul urmator: cate secunde ii ia
	 * sa traverseze tot ecranul!
	 */
	public static final int DEFAULT_SPIDER_SPEED_FACTOR = 4;
	
	/* procentul de ecran pe care il mai afisez inainte 
	 * sa incep sa scrollez ecranul
	 * */
	public static final int EXPANSION_PERCENTILE = 15;
}
