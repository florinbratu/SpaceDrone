package com.killerappzz.spider.objects.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.killerappzz.spider.Constants;
import com.killerappzz.spider.objects.DrawableObject;
import com.killerappzz.spider.objects.ObjectManager;

/**
 * The slider controlling the Acceleration value
 * 
 * @author florin
 *
 */
public class AccelerateSlider extends DrawableObject{
	
	private final DrawableUI sliderBase;
	private final DrawableUI sliderButton;
	private final DrawableUI sliderButtonPressed;
	private float originalSliderButtonY;
	// boolean marking if slider button pressed or not
	private boolean pressed;

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public AccelerateSlider(Context context, BitmapFactory.Options bitmapOptions, 
			int sliderBaseResID, int sliderButtonResID, int sliderButtonPressedResID,
			ObjectManager om)
	{
		// load drawables
		this.sliderBase = new DrawableUI(context, bitmapOptions, sliderBaseResID, om);
		this.sliderButton = new DrawableUI(context, bitmapOptions, sliderButtonResID, om);
		this.sliderButtonPressed = new DrawableUI(context, bitmapOptions, sliderButtonPressedResID, om);
		// inital state == depressed
		this.pressed = false;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		// component positions are relative to the slider position
		this.sliderBase.setPosition(this.x + Constants.MOVEMENT_SLIDER_BASE_X * this.sliderBase.width , 
				this.y + Constants.MOVEMENT_SLIDER_BASE_Y * this.sliderBase.height);
		this.sliderButton.setPosition(this.sliderBase.getPositionX() + Constants.MOVEMENT_SLIDER_BUTTON_X * this.sliderButton.width,
				this.sliderBase.getPositionY() + Constants.MOVEMENT_SLIDER_BUTTON_Y * this.sliderButton.height);
		this.sliderButtonPressed.setPosition(this.sliderButton.getPositionX(), this.sliderButton.getPositionY());
		this.originalSliderButtonY = this.sliderButton.getPositionY();
	}

	@Override
	public void boundsCheck(int worldWidth, int worldHeight) {
		// don't need
	}

	@Override
	public void draw(Canvas canvas) {
		this.sliderBase.draw(canvas);
		if(pressed)
			this.sliderButtonPressed.draw(canvas);
		else
			this.sliderButton.draw(canvas);
	}

	@Override
	public void cleanup() {
		sliderBase.cleanup();
		sliderButton.cleanup();
		sliderButtonPressed.cleanup();
	}

	@Override
	public void updateScreen(int width, int height) {
		this.sliderBase.updateScreen(width, height);
		this.sliderButton.updateScreen(width, height);
		this.sliderButtonPressed.updateScreen(width, height);
		// need to reset positions, this will recompute placement of sub elements
		setPosition(this.x, this.y);
	}
	
	public void setMovementSliderOffset(float offset) {
		float yOffset = offset * (this.sliderBase.height / 2.0f);
		this.sliderButton.setPosition(this.sliderButton.getPositionX(), 
				this.originalSliderButtonY + yOffset);
		this.sliderButtonPressed.setPosition(this.sliderButton.getPositionX(), 
				this.originalSliderButtonY + yOffset);
    }

	/* the methods below establish the screen region
	 *  where screen touches will be handled by the slider
	 */
	public float getSliderRegionX() {
		return this.x;
	}
	
	public float getSliderRegionY() {
		return this.y;
	}

	public float getSliderRegionHeight() {
		return (1 + Constants.MOVEMENT_SLIDER_BASE_Y) * this.sliderBase.height;
	}

	public float getSliderRegionWidth() {
		return (1 + Constants.MOVEMENT_SLIDER_BASE_X) * sliderBase.width;
	}

	public float getSliderBarWidth() {
		// because it's rotated
		return sliderBase.height;
	}

	public float getSliderBarY() {
		return sliderBase.getPositionY();
	}

}
