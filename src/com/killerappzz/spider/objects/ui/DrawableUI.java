package com.killerappzz.spider.objects.ui;

import com.killerappzz.spider.objects.Sprite;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Drawable Object part of the User Interface.
 * 
 * @author florin
 *
 */
public class DrawableUI extends Sprite{
	
	public DrawableUI(Context context, BitmapFactory.Options bitmapOptions, int resourceId)
	{
		super(context, bitmapOptions, resourceId);
	}

	@Override
	public void updateScreen(int width, int height) {
		// don't need it. yet
	}
	
	 @Override
	public void boundsCheck(int screenWidth, int screenHeight) {
		 // nothing -> don;t need it
	}

}
