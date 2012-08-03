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
	
	public DrawableUI(Context context, BitmapFactory.Options bitmapOptions, int resourceId, ObjectManager manager)
	{
		super(context, bitmapOptions, resourceId, manager);
	}

	@Override
	public void boundsCheck(int worldWidth, int worldHeight) {
		 // nothing -> don;t need it
	}

}
