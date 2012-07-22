package com.killerappzz.spider.ui.touch;

import android.view.MotionEvent;

public class SingleTouchFilter extends TouchFilter {
	
	protected final InputTouchScreen inputSystem;
	protected final int screenHeight;
	
	public SingleTouchFilter(InputTouchScreen inputSystem, int screenHeight) {
		this.inputSystem = inputSystem;
		this.screenHeight = screenHeight;
	}

	public void updateTouch(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_UP) {
    		inputSystem.release(0);
    	} else {
    		inputSystem.press(0, 0, event.getRawX(), screenHeight - event.getRawY()); 
    	}
    }

}
