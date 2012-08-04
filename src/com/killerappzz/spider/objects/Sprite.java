package com.killerappzz.spider.objects;


import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * The Canvas version of a sprite.  This class keeps a pointer to a bitmap
 * and draws it at the Sprite's current location.
 * 
 * OBS coordinates x,y are the lower left corner coordinates,
 * 	expressed in world coordinates. Should you need anything
 *  else => subclass and implement yer own stuff
 */
public abstract class Sprite extends DrawableObject {
    protected Bitmap mBitmap;
    protected ObjectManager theManager;
	// the intrinsic dimensions of the object.
	// these are the original values, before scaling
    public final float intrinsicWidth;
	public final float intrinsicHeight;
    
    public Sprite(Bitmap bitmap, ObjectManager manager) {
        mBitmap = bitmap;
        this.theManager = manager;
        this.intrinsicHeight = mBitmap.getHeight();
        this.intrinsicWidth = mBitmap.getWidth();
    }
    
    /**
     * Creates Sprite directly from given resource.
     * Automatically assigns size from image options.
     * 
     * @param context the Context providing the underlying resource
     * @param resourceId the image resource
     */
    public Sprite(Context context, BitmapFactory.Options bitmapOptions, int resourceId, ObjectManager manager) {
    	this.theManager = manager;
    	if (context != null) {
            
            InputStream is = context.getResources().openRawResource(resourceId);
            try {
                Bitmap decodedBitmap = BitmapFactory.decodeStream(is, null, bitmapOptions);
                this.width = bitmapOptions.outWidth;
            	this.height = bitmapOptions.outHeight;
            	this.intrinsicWidth = bitmapOptions.outWidth;
            	this.intrinsicHeight = bitmapOptions.outHeight;
                // scale the bitch, according to the viewport
                int dstWidth = (int)(this.width * this.theManager.getViewport().getScaleFactorX());
                int dstHeight = (int)(this.height * this.theManager.getViewport().getScaleFactorY());
                mBitmap = Bitmap.createScaledBitmap(decodedBitmap, dstWidth, dstHeight, true);
                this.width = dstWidth;
                this.height = dstHeight;
                decodedBitmap.recycle();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    // Ignore.
                }
            }
        } else
        	this.intrinsicHeight = this.intrinsicWidth = 0;
    	
    }
    
    @Override
    public void draw(Canvas canvas) {
        // draw by using screen coordinates!
        canvas.drawBitmap(mBitmap, theManager.getViewport().worldToScreenX(x),
        		canvas.getHeight() - this.height - theManager.getViewport().worldToScreenY(y), null);
    }
    
    @Override
    public void updateScreen(int width, int height) {
    	// scale the bitch, according to the viewport
        int dstWidth = (int)(this.width * this.theManager.getViewport().getScaleFactorX());
        int dstHeight = (int)(this.height * this.theManager.getViewport().getScaleFactorY());
        Bitmap scaledShit = Bitmap.createScaledBitmap(mBitmap, dstWidth, dstHeight, true);
        mBitmap.recycle();
        mBitmap = scaledShit;
        this.width = dstWidth;
        this.height = dstHeight;
    }
    

    /**
     * Release resources. Especially the most important one: the underlying bitmap
     */
    @Override
    public void cleanup() {
    	mBitmap.recycle();
    	mBitmap = null;
    }
    
}