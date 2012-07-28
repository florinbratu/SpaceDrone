package com.killerappzz.spider.objects;

import com.killerappzz.spider.objects.ObjectManager.SceneState;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Abstraction of background image.
 * essentially a sprite, but with 
 * the underlying bitmap scaled to screen size
 * 
 * NB: the (x,y) coordinates are the coordinates
 * of the upper left corner of the piece from the big img
 * to be displayed on screen! Good to know when camera effect
 * is made by moving the background selection window.
 * 
 * @author florin
 *
 */
public class Background extends Sprite{
	
	// the source rectangle i.e. the selection window 
	// to be displayed from the image background
	private final Rect sourceRect;
	private final Rect destRect;
	// screen coordinates
	private int screenWidth;
	private int screenHeight; 
	
	public Background(Context context, Options bitmapOptions, int resourceId,
			ObjectManager manager) {
		super(context, bitmapOptions, resourceId, manager);
		this.setPosition(0, 0);
		setVelocity(0, 0);
		this.screenWidth = (int)manager.getViewport().getScreenWidth();
		this.screenHeight = (int)manager.getViewport().getScreenHeight();
		this.sourceRect = new Rect(0, 0, screenWidth, screenHeight);
		this.destRect = new Rect(0, 0, screenWidth, screenHeight);
	}
	
	@Override
	public void boundsCheck(int worldWidth, int worldHeight) {
		// the Background is actually larger than the Screen
		// so the tests will be "upside down"
		// the (x,y) coordinates are coordinates for 
		// upper left corner. when the background moves, 
		// he does it inverse to objs(negative velocity)
		// thus the rules for the bounds checking
        
        if ((this.x < 0.0f && this.getVelocityX() < 0.0f) 
                || (this.x > this.width - screenWidth 
                        && this.getVelocityX() > 0.0f)) {
            this.x = Math.max(0.0f, 
                    Math.min(this.x, this.width - screenWidth));
            this.setVelocity(0, 0);
        }
        
        if ((this.y < 0.0f && this.getVelocityY() < 0.0f) 
                || (this.y > this.height  - screenHeight 
                        && this.getVelocityY() > 0.0f)) {
            this.y = Math.max(0.0f, 
                    Math.min(this.y, this.height - screenHeight));
            this.setVelocity(0, 0);
        }
	}
	
	@Override
	public void draw(Canvas canvas) {
		// hack to get rid of flickering
		canvas.drawColor(Color.BLACK);
    	// display the selected window to be displayed
    	canvas.drawBitmap(this.mBitmap, sourceRect, destRect, null);
	}
	
	@Override
	public void updatePosition(float timeDeltaSeconds) {
		super.updatePosition(timeDeltaSeconds);
		this.sourceRect.set((int)this.x, (int)this.y, 
				(int)this.x + this.screenWidth, (int)this.y + this.screenHeight);
	}
	
	@Override
	public void setVelocity(float velocityX, float velocityY) {
		super.setVelocity(velocityX, velocityY);
		if(velocityX == 0 && velocityY == 0 
				&& SceneState.SCENE_MOVE.equals(this.theManager.state)) {
			this.theManager.doneScreenScrollBackground();
		}
	}

	@Override
	public void updateScreen(int width, int height) {
		super.updateScreen(width, height);
		this.screenWidth = width;
		this.screenHeight = height;
		this.destRect.set(0, 0, width, height);
		this.sourceRect.set((int)this.x, (int)this.y, 
				(int)this.x + width, (int)this.y + height);
	}
	
}
