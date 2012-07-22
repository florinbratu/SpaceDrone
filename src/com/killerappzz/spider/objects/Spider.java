/**
 * 
 */
package com.killerappzz.spider.objects;

import android.content.Context;
import android.graphics.BitmapFactory.Options;

import com.killerappzz.spider.Constants;
import com.killerappzz.spider.engine.ICollidable;
import com.killerappzz.spider.geometry.Edge2D;

/**
 * The main object of the game
 * 
 * @author florin
 *
 */
public class Spider extends Sprite implements ICollidable{
	
	private final ObjectManager om;
	// movement vector
	private final Edge2D movementVector;
    // screen dimensions
    protected int screenWidth;
    protected int screenHeight;
    private float maxSpeed;
    private float defaultSpeed;

	public Spider(Context context, Options bitmapOptions, int resourceId, ObjectManager manager,
			int scrWidth, int scrHeight) {
		super(context, bitmapOptions, resourceId);
		this.om = manager;
		this.movementVector = new Edge2D.Float();
		this.screenWidth = scrWidth;
		this.screenHeight = scrHeight;
		this.speed = this.defaultSpeed = 0.5f * (this.screenWidth + this.screenHeight) / Constants.DEFAULT_SPIDER_SPEED_FACTOR;
        this.maxSpeed = 0.5f * (this.screenWidth + this.screenHeight) / Constants.MAX_SPIDER_SPEED_FACTOR;
	}
	
	// coordinate conversion methods
	public final float toScreenX(float worldX) {
		return worldX + width/2;
	}

	public final float toScreenY(float worldY) {
		return this.screenHeight - (worldY + height/2);
	}
	
	public Edge2D getMovementVector() {
		return movementVector;
	}
	
	@Override
	public void setVelocity(float velocityX, float velocityY) {
		super.setVelocity(velocityX, velocityY);
		if( !(velocityX == 0.0f && velocityY == 0.0f) 
				&& this.om.needStateSwitch()) {
			this.om.doneScreenScrollBackground();
		}
	}

	@Override
	public void updateScreen(int width, int height) {
		// TODO maybe useful in za future
	}

	@Override
	public void collisionHandler() {
		// TODO add sexy collision animation(explosion?) with fence
		// stop movement
		this.setVelocity(0, 0);
	}

	/* lock the movement vektor according to the current position
		this is to ensure consistency with the background scrolling shit*/
	public void setMovementVector(float timeDeltaSeconds) {
		this.movementVector.setStartPoint(toScreenX(this.x), toScreenY(this.y));
		this.movementVector.setEndPoint(toScreenX(this.x + (this.getVelocityX() * this.speed * timeDeltaSeconds)), 
				toScreenY(this.y + (this.getVelocityY() * this.speed * timeDeltaSeconds))); 
	}
	
	/*
	 * update speed according to input from HUD.
	 * For the moment: update according to a linear function [-1,1] -> [-maxSpeed, maxSpeed]
	 * and speed(0)=defaultSpeed
	 */
	public void updateSpeed(float offset) {
		this.speed = (maxSpeed - defaultSpeed) * offset + defaultSpeed;
	}

}
