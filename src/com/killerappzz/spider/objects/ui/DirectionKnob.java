package com.killerappzz.spider.objects.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.killerappzz.spider.Constants;
import com.killerappzz.spider.objects.DrawableObject;

/**
 * The knob controlling the movement direction
 * 
 * @author florin
 *
 */
public class DirectionKnob extends DrawableObject {
	
	private final DrawableUI knob;
	private final DrawableUI touchSpot;
	private int screenWidth;
	// boolean marking if slider button pressed or not
	private boolean pressed;

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public DirectionKnob(Context context, BitmapFactory.Options bitmapOptions, 
			int knobResID, int touchSpotResID, int screenWidth)
	{
		this.screenWidth = screenWidth;
		// load drawables
		this.knob = new DrawableUI(context, bitmapOptions, knobResID);
		this.touchSpot = new DrawableUI(context, bitmapOptions, touchSpotResID);
		// inital state == depressed
		this.pressed = false;
	}
	
	// TODO flexible setPosition in DrawableUI, taking into account the corner placement and sub components size
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		// component positions are relative to the slider position
		this.knob.x = this.x + Constants.DIRECTION_KNOB_X
				- this.knob.width; // this is hackish. we know the component is on the right
		// that s why we shift it to fit in the visibe area
		this.knob.y = this.y + Constants.DIRECTION_KNOB_Y;
		this.touchSpot.x = this.knob.x + (this.knob.width - this.touchSpot.width) / 2;
		this.touchSpot.y = this.knob.y + (this.knob.height - this.touchSpot.height) / 2;
	}
		
	@Override
	public void boundsCheck(int screenWidth, int screenHeight) {
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
		this.screenWidth = width;
	}

}
