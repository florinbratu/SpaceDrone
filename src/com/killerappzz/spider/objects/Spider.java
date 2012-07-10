/**
 * 
 */
package com.killerappzz.spider.objects;

import android.content.Context;
import android.graphics.BitmapFactory.Options;

/**
 * The main object of the game
 * 
 * @author florin
 *
 */
public class Spider extends Sprite {
	
	private final ObjectManager om;

	public Spider(Context context, Options bitmapOptions, int resourceId, ObjectManager manager) {
		super(context, bitmapOptions, resourceId);
		this.om = manager;
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

}
