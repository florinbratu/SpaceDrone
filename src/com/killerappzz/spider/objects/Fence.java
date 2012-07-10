package com.killerappzz.spider.objects;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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
		this.perimeterPaint = getPerimeterPaint(context, bitmapOptions);
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
	
	/*
	 * TODO move this to Customization class
	 */
	/*
	 * look and feel-ul perimetrului
	 */
	public static final int CLAIMED_COLOR = Color.BLACK;
	public static final float CLAIMED_STROKE_WIDTH = 2;
	private Paint getPerimeterPaint(Context context, Options bitmapOptions) {
		Paint perimeterPaint = new Paint();
		// initialize the paint
        perimeterPaint.setAntiAlias(true);
        perimeterPaint.setDither(true);
        perimeterPaint.setColor(CLAIMED_COLOR);
        perimeterPaint.setStyle(Paint.Style.STROKE);
        perimeterPaint.setStrokeWidth(CLAIMED_STROKE_WIDTH);
        // set the spider texture
        /* TODO activate this while drawing borders artistically
         * claimedPathPaint.setShader(
        		new BitmapShader(
        				loadFromRes(context, bitmapOptions, R.drawable.spiderweb), 
        				TileMode.REPEAT, TileMode.REPEAT));
        claimedPathPaint.setFilterBitmap(true);*/
        return perimeterPaint;
	}

	/* (non-Javadoc)
	 * @see com.killerappzz.spider.objects.DrawableObject#boundsCheck(int, int)
	 */
	@Override
	public void boundsCheck(int screenWidth, int screenHeight) {
		// TODO for culling might be useful

	}

	/* (non-Javadoc)
	 * @see com.killerappzz.spider.objects.DrawableObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		canvas.drawPath(this.perimeter, this.perimeterPaint);
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
