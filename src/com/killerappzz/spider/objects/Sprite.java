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
 */
public abstract class Sprite extends DrawableObject {
    protected Bitmap mBitmap;
    
    public Sprite(Bitmap bitmap) {
        mBitmap = bitmap;
    }
    
    /**
     * Creates Sprite directly from given resource.
     * Automatically assigns size from image options.
     * 
     * @param context the Context providing the underlying resource
     * @param resourceId the image resource
     */
    public Sprite(Context context, BitmapFactory.Options bitmapOptions, int resourceId) {
    	if (context != null) {
            
            InputStream is = context.getResources().openRawResource(resourceId);
            try {
                mBitmap = BitmapFactory.decodeStream(is, null, bitmapOptions);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    // Ignore.
                }
            }
        }
    	
    	this.width = bitmapOptions.outWidth;
    	this.height = bitmapOptions.outHeight;
    }
    
    @Override
    public void draw(Canvas canvas) {
        // The Canvas system uses a screen-space coordinate system, that is,
        // 0,0 is the top-left point of the canvas.  But in order to align
        // with OpenGL's coordinate space (which places 0,0 and the lower-left),
        // for this test I flip the y coordinate.
        canvas.drawBitmap(mBitmap, x, canvas.getHeight() - (y + height), null);
    }
    

    /**
     * Release resources. Especially the most important one: the underlying bitmap
     */
    @Override
    public void cleanup() {
    	mBitmap.recycle();
    	mBitmap = null;
    }

	@Override
	public void boundsCheck(int screenWidth, int screenHeight) {
    	if ((this.x < 0.0f && this.getVelocityX() < 0.0f) 
                || (this.x > screenWidth- this.width 
                        && this.getVelocityX() > 0.0f)) {
    		// TODO different behaviour for different objects
    		// spider will "stick" to margins. the styx will bounce.
    		// for the moment we have only spider behaviour
            this.x = Math.max(0.0f, 
                    Math.min(this.x, screenWidth - this.width));
            this.setVelocity(0, 0);
        }
        
        if ((this.y < 0.0f && this.getVelocityY() < 0.0f) 
                || (this.y > screenHeight - this.height 
                        && this.getVelocityY() > 0.0f)) {
            this.y = Math.max(0.0f, 
                    Math.min(this.y, screenHeight - this.height));
            this.setVelocity(0, 0);
        }
	}
    
}