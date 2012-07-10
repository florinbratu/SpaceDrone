package com.killerappzz.spider.objects;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.killerappzz.spider.Customization;

/**
 * The Fence, defining the bounds within which the
 * object should move
 * 
 * @author florin
 *
 */
public class Fence extends DrawableObject {
	
	private final GeometricPath perimeter;
	private final Paint perimeterPaint;
	
	public Fence(Context context, Options bitmapOptions) {
		this.x = this.y = 0;
		setVelocity(0, 0);
		this.perimeter = new GeometricPath();
		this.perimeterPaint = Customization.getPerimeterPaint(context, bitmapOptions);
	}
	
	/* create some perimeter around the given Object.
	 * Just for testing purposes...
	 * */
	public void inlineCreate(DrawableObject object) {
		int size = 100;
		this.perimeter.moveTo( object.x - size, object.y - size );
		this.perimeter.lineTo( object.x + size, object.y - size );
		this.perimeter.lineTo( object.x + size, object.y + size );
		this.perimeter.lineTo( object.x - size, object.y + size );
		this.perimeter.close();
	}
	
	// Load perimeter geometry from file
	public void loadFromFile() {
		// TODO TODO TODO!
	}

	/* (non-Javadoc)
	 * @see com.killerappzz.spider.objects.DrawableObject#boundsCheck(int, int)
	 */
	@Override
	public void boundsCheck(int screenWidth, int screenHeight) {
		// TODO for culling might be useful

	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawPath(this.perimeter, this.perimeterPaint);
	}
	
	@Override
	public void updatePosition(float timeDeltaSeconds) {
		float oldX = this.x;
		float oldY = this.y;
		super.updatePosition(timeDeltaSeconds);
		if(oldX != this.x || oldY != this.y) {
			this.perimeter.offset(this.x - oldX, this.y - oldY);
		}
	}
	
	// OBS this relies on the fact that setVelocity is called only from ObjectManager!!!
	@Override
	public void setVelocity(float velocityX, float velocityY) {
		super.setVelocity(-velocityX, -velocityY);
	}

	/* (non-Javadoc)
	 * @see com.killerappzz.spider.objects.DrawableObject#cleanup()
	 */
	@Override
	public void cleanup() {
		// TODO unload textures, if use them for Perimeter

	}

	/* (non-Javadoc)
	 * @see com.killerappzz.spider.objects.DrawableObject#updateScreen(int, int)
	 */
	@Override
	public void updateScreen(int width, int height) {
		// TODO for sure we will do stuff here, when we will do culling

	}

}
