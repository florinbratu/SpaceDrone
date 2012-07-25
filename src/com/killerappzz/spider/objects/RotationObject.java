package com.killerappzz.spider.objects;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;

/**
 * Like a Sprite it will display some image.
 * But can also be rotated around a second object,
 * called the pivot object
 * 
 * @author florin
 *
 */
public class RotationObject extends Sprite{
	
	private float rotationAngle;
	// the object we are following
	private DrawableObject pivot;
	private float screenHeight;

	public RotationObject(Context context, Options bitmapOptions, int resourceId, 
			DrawableObject thePivot, float screenHeight) {
		super(context, bitmapOptions, resourceId);
		this.pivot = thePivot;
		this.screenHeight = screenHeight;
	}
	
	public void setRotationAngle(double angle) {
		this.rotationAngle = (float)angle;
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		canvas.rotate(rotationAngle, pivot.x, canvas.getHeight() - pivot.y);
		super.draw(canvas);
		canvas.restore();
	}
	
	@Override
	public void updatePosition(float timeDeltaSeconds) {
		// we follow the pivot object
		// TODO maybe some width/height adjustments to avoid collisions
		this.x = pivot.x;
		this.y = pivot.y;
	}

	@Override
	public void updateScreen(int width, int height) {
		this.screenHeight = height;
	}

}
