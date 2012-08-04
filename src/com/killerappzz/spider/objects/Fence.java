package com.killerappzz.spider.objects;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.killerappzz.spider.Customization;
import com.killerappzz.spider.engine.ICollidable;
import com.killerappzz.spider.geometry.Edge2D;

/**
 * The Fence, defining the bounds within which the
 * object should move
 * 
 * Coordinates of the geometric path are expressed in
 * a weird coordinates system, which is like the world
 * coordinates system, but with Y axis flipped.
 * TODO rewrite for a path in world coordinates
 * 
 * @author florin
 *
 */
public class Fence extends DrawableObject implements ICollidable{
	
	private final GeometricPath perimeter;
	private final Paint perimeterPaint;
	private final ObjectManager manager;
	
	public Fence(Context context, Options bitmapOptions, ObjectManager theManager) {
		this.x = this.y = 0;
		this.manager = theManager;
		setVelocity(0, 0);
		this.perimeter = new GeometricPath();
		this.perimeterPaint = Customization.getPerimeterPaint(context, bitmapOptions);
	}
	
	/* create some perimeter around the given Object.
	 * Just for testing purposes...
	 * */
	public void inlineCreate(DrawableObject object) {
		int size = 100;
		float scrWidth = this.manager.getViewport().getWorldWidth();
		float scrHeight = this.manager.getViewport().getWorldHeight();
		// out of bounds polygon
		/*this.perimeter.moveTo( object.x - size, object.y - size );
		this.perimeter.lineTo( object.x + size + scrWidth, object.y - size );
		this.perimeter.lineTo( object.x + size + scrWidth, object.y + size + scrHeight );
		this.perimeter.lineTo( object.x + scrWidth, object.y + size + scrHeight );
		this.perimeter.lineTo( object.x + scrWidth, object.y + size );
		this.perimeter.lineTo( object.x - size, object.y + size );*/
		/* rectangle*/
		/*this.perimeter.moveTo(100, 100);
		this.perimeter.lineTo( scrWidth - 200, 100);
		this.perimeter.lineTo( scrWidth - 200, scrHeight - 100);
		this.perimeter.lineTo(100, scrHeight - 100);*/
		// hexagone
		perimeter.moveTo(300, 0);
		perimeter.lineTo(400,200);
		perimeter.lineTo(400, 400);
		perimeter.lineTo(300, 600);
		perimeter.lineTo(200, 400);
		perimeter.lineTo(200, 200);
		this.perimeter.close();
		// scale it according to viewport
		this.perimeter.scale(this.manager.getViewport().getScaleFactorX(),
				this.manager.getViewport().getScaleFactorY());
	}
	
	// Load perimeter geometry from file
	public void loadFromFile() {
		// TODO TODO TODO!
	}

	/* (non-Javadoc)
	 * @see com.killerappzz.spider.objects.DrawableObject#boundsCheck(int, int)
	 */
	@Override
	public void boundsCheck(int worldWidth, int worldHeight) {
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
		this.perimeter.scale(this.manager.getViewport().getScaleFactorX(),
				this.manager.getViewport().getScaleFactorY());
		// TODO for sure we will do more stuff here, when we will do culling
	}

	public void collisionHandler() {
		// TODO add sexy collision animation
	}
	
	private Edge2D collidedEdge = null;
	private boolean bOldContains = true;

	/*
	 * Collision test algorithm. 
	 * Current complexity: O(N = no of edges)
	 * TODO improve complexity
	 * TODO fill in collidedEdge, need this for FX
	 * 
	 * Cum functioneaza pt moment: ne bazam pe primitiva contains() a poligonului
	 * care zice daca un pct e in interiorul poligonului sau nu.
	 * Dar tre sa tinem cont ca si poligonul se misca!!
	 * Atunci, algoritmul este: am collision daca:
	 * - inainte de miscarea tuturor obiectelor, punctul era inauntru(de unde oldContains)
	 * - dupa miscarea tuturor obiectelor, pct nu mai e inauntru
	 */
	public boolean collisionTest(Edge2D movementVector) {
		return bOldContains && !this.perimeter.contains(movementVector.getEndPoint());
		/*
		this.collidedEdge = null;
		Point2D currentVertex;
		Point2D nextVertex;
		Edge2D currentEdge = new Edge2D.Float();
		List<Point2D> vertices = this.perimeter.getVertices();
		for( int i = 0 ; i < vertices.size() - 1; i++) {
			currentVertex = vertices.get(i);
			nextVertex = vertices.get(i+1);
			currentEdge.set(currentVertex, nextVertex); 
			if(movementVector.touches(currentEdge)) {
				this.collidedEdge = currentEdge;
				Log.d(Constants.DEBUG_TAG, "Hit edge: (" + i + " , " + (i+1) + ");");
				return true;
			}
		}
		// test final edge: (Vn-1, V0)
		currentEdge.set(vertices.get(vertices.size() - 1), vertices.get(0));
		if(movementVector.touches(currentEdge)) {
			this.collidedEdge = currentEdge;
			Log.d(Constants.DEBUG_TAG, "Hit edge: (" + (vertices.size() - 1) + " , 0);");
			return true;
		}
		return false;*/
	}

	public void collisionPrefetch(Edge2D movementVector) {
		bOldContains = this.perimeter.contains(movementVector.getStartPoint());
	}

}
