package com.killerappzz.spider.objects;

import android.graphics.Canvas;

/** 
 * Base class defining the core set of information necessary to render (and move
 * an object on the screen.  This is an abstract type and must be derived to
 * add methods to actually draw (see CanvasSprite and GLSprite).
 */
public abstract class DrawableObject {
    // Position.
    protected float x;
    protected float y;
    
    // Velocity. Prin velocity eu inteleg directia in care tre sa se miste
    private float velocityX;
    private float velocityY;
    
    // quick'n'dirty pause movement switch, per movement direction
    private boolean pausedX = false;
    private boolean pausedY = false;
    
    public void setVelocity(float velocityX, float velocityY) {
    	// store the velocities in normalized format
    	if(velocityX == 0 && velocityY == 0) {
    		this.velocityX = this.velocityY =0;
    	} else {
    		float velocityNorm = (float)Math.sqrt(
    				velocityX * velocityX + velocityY * velocityY);
    		this.velocityX = velocityX / velocityNorm;
    		this.velocityY = velocityY / velocityNorm;
    	}
    		
    }
    
    public float getVelocityX() {
    	return velocityX;
    }
    
    public float getVelocityY() {
    	return velocityY;
    }
    
    public float getPositionX() {
    	return this.x;
    }
    
    public float getPositionY() {
    	return this.y;
    }
    
    public void setPosition(float x, float y) {
    	this.x = x;
    	this.y = y;
    }
    
    // Movement speed
    public float speed;
    
    // Size.
    public float width;
    public float height;
    
    public void pauseX(boolean pauseFlag) {
    	this.pausedX = pauseFlag;
    }
    
    public void pauseY(boolean pauseFlag) {
    	this.pausedY = pauseFlag;
    }
    
    public void pause() {
    	this.pausedX = this.pausedY = true;
	}
    
    public void resume() {
    	this.pausedX = this.pausedY = false;
    }
    
    public void updatePosition(float timeDeltaSeconds) {
    	if(!pausedX) 
    		this.x = this.x + (this.velocityX * this.speed * timeDeltaSeconds);
    	if(!pausedY)
    		this.y = this.y + (this.velocityY * this.speed * timeDeltaSeconds);
	}
    
    /**
     * Determine how the object will react when
     * encountering the screen margins
     * 
     * @param worldWidth
     * @param worldHeight
     */
    public abstract void boundsCheck(int worldWidth, int worldHeight);
    
    public abstract void draw(Canvas canvas);
    public abstract void cleanup();
    
    public abstract void updateScreen(int width, int height);

}
