package com.killerappzz.spider.objects.ui;

import com.killerappzz.spider.objects.ObjectManager;
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
	
	// the intrinsic dimensions of the object.
	// these are the original values, before scaling
	// we use them for alignment calculations
	public final float intrinsicWidth;
	public final float intrinsicHeight;
	
	public DrawableUI(Context context, BitmapFactory.Options bitmapOptions, int resourceId, ObjectManager manager)
	{
		super(context, bitmapOptions, resourceId, manager);
		this.intrinsicWidth = bitmapOptions.outWidth;
    	this.intrinsicHeight = bitmapOptions.outHeight;
	}

	@Override
	public void boundsCheck(int worldWidth, int worldHeight) {
		 // nothing -> don;t need it
	}

}
