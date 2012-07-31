package com.killerappzz.spider.objects.ui;

import com.killerappzz.spider.objects.ObjectManager;
import com.killerappzz.spider.objects.Sprite;

import android.content.Context;
import android.graphics.Bitmap;
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
	
	/*
	 * this method is to be perfomed after screen update.
	 * It is needed because for instance we may have circle
	 * that after scaling is no longer circle!
	 */
	public void shapeCorrection() {
		int dstSize = (int)((this.height > this.width) ? this.height : this.width);
        Bitmap scaledShit = Bitmap.createScaledBitmap(mBitmap, dstSize, dstSize, true);
        mBitmap.recycle();
        mBitmap = scaledShit;
        this.width = dstSize;
        this.height = dstSize;
	}

}
