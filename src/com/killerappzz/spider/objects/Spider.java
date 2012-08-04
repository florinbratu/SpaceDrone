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
    private float maxSpeed;
    private float defaultSpeed;
    // the pusher
    private RotationObject pusher;

	public Spider(Context context, Options bitmapOptions, int resourceId, ObjectManager manager) {
		super(context, bitmapOptions, resourceId, manager);
		this.om = manager;
		this.movementVector = new Edge2D.Float(); 
	}
	
	public void setSpeed(Background background) {
		this.speed = this.defaultSpeed = 0.5f * (background.intrinsicWidth + background.intrinsicHeight) / Constants.DEFAULT_SPIDER_SPEED_FACTOR;
        this.maxSpeed = 0.5f * (background.intrinsicWidth + background.intrinsicHeight) / Constants.MAX_SPIDER_SPEED_FACTOR;
	}
	
	public void setPusher(RotationObject pusher) {
		this.pusher = pusher;
		// the pusher is solidaire with the movement
		pusher.x = this.x ;
        pusher.y = this.y ;
        pusher.speed = this.speed;
	}
	
	public Edge2D getMovementVector() {
		return movementVector;
	}
	
	@Override
	public void setVelocity(float velocityX, float velocityY) {
		super.setVelocity(velocityX, velocityY);
		if( !(velocityX == 0.0f && velocityY == 0.0f)) {
			if(this.om.needStateSwitch())
				this.om.doneScreenScrollBackground();
			this.pusher.setRotationAngle(movementAngleDegrees());
			// pusher is solidaire with our movement
			this.pusher.setVelocity(velocityX, velocityY);
		}
	}

	/*
	 * calculate the angle of the spider movement, in degrees
	 */
	private double movementAngleDegrees() {
		// the value in radians
		double angleRadians = 0;
		double dx = this.getVelocityX();
		double dy = this.getVelocityY();
		if(dy==0)
			if(dx<0)
				angleRadians = Math.PI;
			else
				return 0;
		else {
			// we use the handy atan2
			angleRadians = Math.atan2(dx, dy);
		}
		
		// convert radians to degrees
		return angleRadians * 180 / Math.PI;
	}
	
	@Override
	public void boundsCheck(int worldWidth, int worldHeight) {
    	if ((this.x < 0.0f && this.getVelocityX() < 0.0f) 
                || (this.x > worldWidth - this.intrinsicWidth 
                        && this.getVelocityX() > 0.0f)) {
            this.x = Math.max(0.0f, 
                    Math.min(this.x, worldWidth - this.intrinsicWidth));
            this.setVelocity(0, 0);
        }
        
        if ((this.y < 0.0f && this.getVelocityY() < 0.0f) 
                || (this.y > worldHeight - this.intrinsicHeight 
                        && this.getVelocityY() > 0.0f)) {
            this.y = Math.max(0.0f, 
                    Math.min(this.y, worldHeight - this.intrinsicHeight));
            this.setVelocity(0, 0);
        }
	}

	public void collisionHandler() {
		// TODO add sexy collision animation(explosion?) with fence
		// stop movement
		this.setVelocity(0, 0);
	}

	/* lock the movement vektor according to the current position
		this is to ensure consistency with the background scrolling shit*/
	public void setMovementVector(float timeDeltaSeconds) {
		this.movementVector.setStartPoint(this.x, this.om.getViewport().getWorldHeight() - this.y);
		this.movementVector.setEndPoint(this.x + (this.getVelocityX() * this.speed * timeDeltaSeconds),
				this.om.getViewport().getWorldHeight() - (this.y + (this.getVelocityY() * this.speed * timeDeltaSeconds)));
	}
	
	/*
	 * update speed according to input from HUD.
	 * For the moment: update according to a linear function [-1,1] -> [-maxSpeed, maxSpeed]
	 * and speed(0)=defaultSpeed
	 */
	public void updateSpeed(float offset) {
		this.speed = (maxSpeed - defaultSpeed) * offset + defaultSpeed;
		// we also need to update background objects speeds!!
		om.setBackgroundSpeeds(this);
		// the pusher follows closely
		pusher.speed = this.speed;
	}

}
