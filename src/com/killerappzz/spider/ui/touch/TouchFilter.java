package com.killerappzz.spider.ui.touch;

import android.content.Context;
import android.view.MotionEvent;

public abstract class TouchFilter{

	public abstract void updateTouch(MotionEvent event);
	
	public boolean supportsMultitouch() {
		return false;
	}

}
