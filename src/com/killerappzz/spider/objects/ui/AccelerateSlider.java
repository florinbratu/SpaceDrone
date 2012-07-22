package com.killerappzz.spider.objects.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.killerappzz.spider.Constants;
import com.killerappzz.spider.objects.DrawableObject;

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
	// boolean marking if slider button pressed or not
	private boolean pressed;

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public AccelerateSlider(Context context, BitmapFactory.Options bitmapOptions, 
			int sliderBaseResID, int sliderButtonResID, int sliderButtonPressedResID)
	{
		// load drawables
		this.sliderBase = new DrawableUI(context, bitmapOptions, sliderBaseResID);
		this.sliderButton = new DrawableUI(context, bitmapOptions, sliderButtonResID);
		this.sliderButtonPressed = new DrawableUI(context, bitmapOptions, sliderButtonPressedResID);
		// initial positions. they are relative to the slider position
		this.sliderBase.x = this.x + Constants.MOVEMENT_SLIDER_BASE_X;
		this.sliderBase.y = this.y + Constants.MOVEMENT_SLIDER_BASE_Y;
		this.sliderButton.x = this.x + Constants.MOVEMENT_SLIDER_BUTTON_X;
		this.sliderButton.y = this.y + Constants.MOVEMENT_SLIDER_BUTTON_Y;
		this.sliderButtonPressed.x = this.x + Constants.MOVEMENT_SLIDER_BUTTON_X;
		this.sliderButtonPressed.y = this.y + Constants.MOVEMENT_SLIDER_BUTTON_Y;
		// inital state == depressed
		this.pressed = false;
	}

	@Override
	public void boundsCheck(int screenWidth, int screenHeight) {
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
		// TODO Auto-generated method stub
	}

}
