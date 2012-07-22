package com.killerappzz.spider.ui;

import android.content.Context;
import android.os.Build;

import com.killerappzz.spider.objects.ui.AccelerateSlider;
import com.killerappzz.spider.ui.touch.InputTouchScreen;
import com.killerappzz.spider.ui.touch.MultiTouchFilter;
import com.killerappzz.spider.ui.touch.SingleTouchFilter;
import com.killerappzz.spider.ui.touch.TouchFilter;

/**
 * This handles all user input.
 * For the time being, we only take care of touch.
 * In the future, the rest of shit will follow
 * (keyboard, dpad, etc.)
 * 
 * @author florin
 *
 */
public class UserInput {
	
	// this is for touch events for UI
    private final TouchFilter touchFilter;
    // this is to store the events received from UI
    private final InputTouchScreen touchScreen;
    // the slider UI object
    // TODO this needs to be replaced with the future more-generic HUD
    private final AccelerateSlider as;
	
	public UserInput(Context context, int screenHeight, AccelerateSlider as) {
		this.as = as;
		// the touch events receiver
		touchScreen = new InputTouchScreen();
		// the touch filter
		final int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
		if (sdkVersion < Build.VERSION_CODES.ECLAIR) {
			touchFilter = new SingleTouchFilter(touchScreen, screenHeight);
		} else {
			touchFilter = new MultiTouchFilter(context, touchScreen, screenHeight);
		}
	}
	
	public void process(float timeDeltaSeconds) {
		
		// acceleration slider
		final InputXY sliderTouch = touchScreen.findPointerInRegion(
				as.getSliderRegionX(),
				as.getSliderRegionY(),
				as.getSliderRegionWidth(),
				as.getSliderRegionHeight());
		
		if (sliderTouch != null) {
			final float halfWidth = as.getSliderBarWidth() / 2.0f;
			final float center = as.getSliderBarY() + halfWidth;
			final float offset = sliderTouch.getY() - center;
			float magnitudeRamp = Math.abs(offset) > halfWidth ? 1.0f : (Math.abs(offset) / halfWidth);
			// offset the button
			this.as.setMovementSliderOffset(magnitudeRamp * Math.signum(offset));
			// set pressed state
			this.as.setPressed(true);
		} else {
			this.as.setPressed(false);
		}
	}

	public TouchFilter getTouchFilter() {
		return this.touchFilter;
	}

}
