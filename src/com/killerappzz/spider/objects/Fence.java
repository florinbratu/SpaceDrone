package com.killerappzz.spider.objects;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.killerappzz.spider.Customization;
import com.killerappzz.spider.engine.ICollidable;
import com.killerappzz.spider.geometry.Edge2D;
import com.killerappzz.spider.geometry.PathIterator;
import com.killerappzz.spider.geometry.Point2D;

/**
 * The Fence, defining the bounds within which the
 * object should move
 * 
 * @author florin
 *
 */
public class Fence extends DrawableObject implements ICollidable{
	
	private final GeometricPath perimeter;
	private final Paint perimeterPaint;
	
	private int scrWidth;
	private int scrHeight;
	
	public Fence(Context context, Options bitmapOptions, int scrWidth, int scrHeight) {
		this.x = this.y = 0;
		setVelocity(0, 0);
		this.scrWidth = scrWidth;
		this.scrHeight = scrHeight;
		this.perimeter = new GeometricPath();
		this.perimeterPaint = Customization.getPerimeterPaint(context, bitmapOptions);
	}
	
	/* create some perimeter around the given Object.
	 * Just for testing purposes...
	 * */
	public void inlineCreate(DrawableObject object) {
		int size = 100;
		this.perimeter.moveTo( object.x - size, object.y - size );
		this.perimeter.lineTo( object.x + size + this.scrWidth, object.y - size );
		this.perimeter.lineTo( object.x + size + this.scrWidth, object.y + size + this.scrHeight );
		this.perimeter.lineTo( object.x + this.scrWidth, object.y + size + this.scrHeight );
		this.perimeter.lineTo( object.x + this.scrWidth, object.y + size );
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
		this.scrHeight = height;
		this.scrWidth = width;
		// TODO for sure we will do stuff here, when we will do culling
	}

	@Override
	public void collisionHandler() {
		// TODO add sexy collision animation
		
	}
	
	private Edge2D collidedEdge = null;

	/*
	 * Collision test algorithm. 
	 * Current complexity: O(N = no of edges)
	 * TODO improve complexity
	 */
	public boolean collisionTest(Edge2D movementVector) {
		Point2D startPoint = movementVector.getStartPoint();
		// will not be considered collision if initial point not inside perimeter
		if(!this.perimeter.contains( startPoint.getX(), startPoint.getY()))
			return false;
		this.collidedEdge = null;
		
		Point2D currentVertex = new Point2D.Float();
		Point2D nextVertex = new Point2D.Float();
		Edge2D currentEdge = new Edge2D.Float();
		float[] coords = new float[6];
		PathIterator it = this.perimeter.getGeometry().getPathIterator(null);
		// get first vertex
		it.currentSegment(coords);
		currentVertex.setLocation(coords[0], coords[1]);
		it.next();
		// iterate thru edgez
		while(!it.isDone()){
			it.currentSegment(coords);
			nextVertex.setLocation(coords[0], coords[1]);
			currentEdge.set(currentVertex, nextVertex); 
			if(movementVector.touches(currentEdge)) {
				this.collidedEdge = currentEdge;
				return true;
			}
			it.next();
			currentVertex.setLocation(nextVertex);
		}
		return false;
	}

}
