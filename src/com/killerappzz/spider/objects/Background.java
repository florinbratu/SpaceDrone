package com.killerappzz.spider.objects;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Abstraction of background image.
 * essentially a sprite, but with 
 * the underlying bitmap scaled to screen size
 * 
 * @author florin
 *
 */
public class Background extends Sprite{
	
	// the source rectangle i.e. the selection window 
	// to be displayed from the image background
	private final Rect sourceRect;
	private final Rect destRect;
	
	private final int scrWidth;
	private final int scrHeight;
	
	public Background(Context context, Options bitmapOptions, int resourceId,
			int scrWidth, int scrHeight) {
		super(context, bitmapOptions, resourceId);
		this.scrWidth = scrWidth;
		this.scrHeight = scrHeight;
		this.x = this.y = 0;
		setVelocity(0, 0);
		this.sourceRect = new Rect(0, 0, scrWidth, scrHeight);
		this.destRect = new Rect(0, 0, scrWidth, scrHeight);
	}
	
	@Override
	public void boundsCheck(int screenWidth, int screenHeight) {
		// the Background is actually larger than the Screen
		// so the tests will be "upside down"
		// the (x,y) coordinates are coordinates for 
		// lower left corner. when background moves, he does it inverse to objs(negative velocity)
		// thus the rules for the bounds checking
		if ((this.x > 0.0f && this.getVelocityX() > 0.0f) 
                || (this.x < screenWidth - this.width
                        && this.getVelocityX() < 0.0f)) {
            this.x = Math.max(screenWidth - this.width, 
                    Math.min(this.x, 0.0f));
            this.setVelocity(0, 0);
        }
        
        if ((this.y > 0.0f && this.getVelocityY() > 0.0f) 
                || (this.y < screenHeight - this.height 
                        && this.getVelocityY() < 0.0f)) {
            this.y = Math.max(screenHeight - this.height, 
                    Math.min(this.y, 0.0f));
            this.setVelocity(0, 0);
        }
	}
	
	@Override
	public void draw(Canvas canvas) {
		// hack to get rid of flickering
		canvas.drawColor(Color.BLACK);
    	// display the selected window to be displayed
    	canvas.drawBitmap(this.mBitmap, null, destRect, null);
	}
	
	@Override
	public void updatePosition(float timeDeltaSeconds) {
		super.updatePosition(timeDeltaSeconds);
		this.sourceRect.set((int)this.x, (int)this.y + this.scrHeight, 
				(int)this.x + this.scrWidth, (int)this.y);
	}
	
}
