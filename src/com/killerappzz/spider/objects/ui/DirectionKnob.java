package com.killerappzz.spider.objects.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.killerappzz.spider.Constants;
import com.killerappzz.spider.objects.DrawableObject;
import com.killerappzz.spider.objects.ObjectManager;

/**
 * The knob controlling the movement direction
 * 
 * @author florin
 *
 */
public class DirectionKnob extends DrawableObject {
	
	private final DrawableUI knob;
	private final DrawableUI touchSpot;
	// boolean marking if slider button pressed or not
	private boolean pressed;
	// some useful coordinates, precomputed
	private float centerX;
	private float centerY;
	private float knobRadius;
	private float touchSpotRadius;

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
		if(!pressed) {
			// reset knob position
			setTouchSpot(this.centerX, this.centerY);
		}
	}
	
	public DirectionKnob(Context context, BitmapFactory.Options bitmapOptions, 
			int knobResID, int touchSpotResID, ObjectManager manager)
	{
		// load drawables
		this.knob = new DrawableUI(context, bitmapOptions, knobResID, manager);
		this.touchSpot = new DrawableUI(context, bitmapOptions, touchSpotResID, manager);
		// inital state == depressed
		this.pressed = false;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		// component positions are relative to the slider position
		this.knob.setPosition(this.x + Constants.DIRECTION_KNOB_X * this.knob.width
				- this.knob.width, // this is hackish. we know the component is on the right
								// that s why we shift it to fit in the visibe area 
				this.y + Constants.DIRECTION_KNOB_Y * this.knob.height);
		this.touchSpot.setPosition(this.knob.getPositionX() + (this.knob.width - this.touchSpot.width) / 2, 
				this.knob.getPositionY() + (this.knob.height - this.touchSpot.height) / 2);
		// coordinates of the knob center
		this.centerX = this.knob.getPositionX() + this.knob.width / 2;
		this.centerY = this.knob.getPositionY() + this.knob.height / 2;
		// radiuses for the two circles
		this.knobRadius = this.knob.width / 2;
		this.touchSpotRadius = this.touchSpot.width / 2;
	}
		
	@Override
	public void boundsCheck(int worldWidth, int worldHeight) {
		// useless
	}

	@Override
	public void draw(Canvas canvas) {
		this.knob.draw(canvas);
		//if(pressed)
			this.touchSpot.draw(canvas);
	}

	@Override
	public void cleanup() {
		this.knob.cleanup();
		this.touchSpot.cleanup();
	}

	@Override
	public void updateScreen(int width, int height) {
		this.knob.updateScreen(width, height);
		this.touchSpot.updateScreen(width, height);
		// need to reset positions, this will recompute placement of sub elements
		setPosition(this.x, this.y);
	}

	public float getTouchRegionX() {
		return this.knob.getPositionX();
	}

	public float getTouchRegionY() {
		return this.knob.getPositionY();
	}

	public float getTouchRegionWidth() {
		return this.knob.width;
	}

	public float getTouchRegionHeight() {
		return this.knob.height;
	}

	/*
	 * tests whether the given point can be used as the center
	 * for the touch spot so that it does not go out of bounds
	 */
	public boolean touchWithinRegion(float x, float y) {
		float dist = distance(x,y,centerX,centerY);
		return knobRadius - touchSpotRadius >= dist;
	}

	private float distance(float x, float y, float centerX, float centerY) {
		// quick n dirty distance function
		float dx = x - centerX;
		float dy = y - centerY;
		return (float)Math.sqrt( dx * dx + dy * dy );
	}

	public void setTouchSpot(float x, float y) {
		this.touchSpot.setPosition(x - this.touchSpot.width / 2, 
				y - this.touchSpot.height / 2);
	}

	/*
	 * the touch coordinates are out of bounds.
	 * We will compute the most extreme point so that the direction is the same
	 * as the touch point but the touchSpot does not fall out of bounds.
	 * 
	 *  This is calculated by finding the intersection between the segment
	 *  (Center, Point) where Center is the center and POint is the touch point
	 *  with the Circle centered in Center and with radius R = knobRadius - touchSpotRadius
	 *  
	 *  Here's the underlying math which explains the formulae below.
	 *  We use the parametric equations for the:
	 *  1) Segment (Center, Point) :
	 *  xT = xP * t + xC * (1 - t)
	 *  yT = yP * t + yC * (1 - t)
	 *  2) Circle (Center, R) :
	 *  (xT - xC) ^ 2 + (yT - yC) ^ 2 = R ^ 2
	 *  
	 *  Substituting 1) in 2) and simplifying the equation yields the value for t:
	 *  	t = R / dist
	 *  where dist is the distance between Center and Point.
	 *  Then, by replacing the value of t in 1), we get the coordinates we want! 
	 */
	public void setBorderTouchSpot(float px, float py) {
		float radius = knobRadius - touchSpotRadius;
		float dist = distance(px,py,centerX,centerY);
		float t = radius / dist;
		float touchSpotx = px * t + centerX * (1 - t);
		float touchSpoty = py * t + centerY * (1 - t);
		touchSpotx -= this.touchSpot.width / 2;
		touchSpoty -= this.touchSpot.height / 2;
		this.touchSpot.setPosition(touchSpotx, touchSpoty);
	}

	public float getNewVelocityX(float touchX) {
		return touchX - this.centerX;
	}

	public float getNewVelocityY(float touchY) {
		return touchY - this.centerY;
	}

}
