package com.killerappzz.spider.ui.touch;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.MotionEvent;

public class MultiTouchFilter extends SingleTouchFilter {
	private boolean mSupportsMultitouch = false;
	
	public MultiTouchFilter(Context context, InputTouchScreen inputSystem, int screenHeight) {
		super(inputSystem,screenHeight);
		PackageManager packageManager = context.getPackageManager();
		mSupportsMultitouch = packageManager.hasSystemFeature("android.hardware.touchscreen.multitouch");
	}
	
    @Override
    public void updateTouch(MotionEvent event) {
    	final int pointerCount = event.getPointerCount();
    	for (int x = 0; x < pointerCount; x++) {
    		final int action = event.getAction();
    		final int actualEvent = action & MotionEvent.ACTION_MASK;
    		final int id = event.getPointerId(x);
    		if (actualEvent == MotionEvent.ACTION_POINTER_UP || 
    				actualEvent == MotionEvent.ACTION_UP || 
    				actualEvent == MotionEvent.ACTION_CANCEL) {
        		inputSystem.release(id);
        	} else {
        		inputSystem.press(id, 0, event.getX(x), screenHeight - event.getY(x)); 
        	}
    	}
    }
    
    @Override
    public boolean supportsMultitouch() {
    	return mSupportsMultitouch;
    }
}
