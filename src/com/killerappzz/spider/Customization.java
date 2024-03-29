package com.killerappzz.spider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.BitmapFactory.Options;

/**
 * Customization constants and behaviour 
 * 
 * @author florin
 *
 */
public final class Customization {
	
	/*
	 * look and feel-ul perimetrului
	 */
	public static final int CLAIMED_COLOR = Color.BLACK;
	public static final float CLAIMED_STROKE_WIDTH = 2;
	public static Paint getPerimeterPaint(Context context, Options bitmapOptions) {
		Paint perimeterPaint = new Paint();
		// initialize the paint
        perimeterPaint.setAntiAlias(true);
        perimeterPaint.setDither(true);
        perimeterPaint.setColor(CLAIMED_COLOR);
        perimeterPaint.setStyle(Paint.Style.STROKE);
        perimeterPaint.setStrokeWidth(CLAIMED_STROKE_WIDTH);
        // set the spider texture
        /* TODO activate this while drawing borders artistically
         * claimedPathPaint.setShader(
        		new BitmapShader(
        				loadFromRes(context, bitmapOptions, R.drawable.spiderweb), 
        				TileMode.REPEAT, TileMode.REPEAT));
        claimedPathPaint.setFilterBitmap(true);*/
        return perimeterPaint;
	}
	
	public static final int STATS_TEXT_SIZE = 18;
	public static Paint getStatsPaint() {
		Paint textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setDither(true);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setTextSize(STATS_TEXT_SIZE);
		return textPaint;
	}
	
	// vibration period for haptic feedback
	public static final long VIBRATION_PERIOD = 300;
	
	// this is the world coordinates width/height.
	// choice motivation: looks good on my phone! :P
	public static final int WORLD_WIDTH = 569;
	public static final int WORLD_HEIGHT = 320;
}
