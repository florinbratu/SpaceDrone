package com.killerappzz.spider.objects;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

import com.killerappzz.spider.geometry.Path2D;
import com.killerappzz.spider.geometry.PathIterator;
import com.killerappzz.spider.geometry.Point2D;
import com.killerappzz.spider.geometry.Shape;

/**
 * An Android Path, but augmented with geometrical information,
 * supplied via geometry package.
 *  
 * @author florin
 *
 */
public class GeometricPath extends Path {
	
	// so we can do advanced ops on it
	private final Path2D geometry;
	// the geometrical center of the Path
	private final Point2D center;
	// the path number of points
	private int vertexCount;
	// the points list
	private final List<Point2D> vertices;
	// the Scale factors. we need them when Offset'ing
	private float scaleFactorX = 1;
	private float scaleFactorY = 1;
	
	public GeometricPath() {
		this.geometry = new Path2D.Float();
		this.center = new Point2D.Float();
		this.vertexCount = 0;
		this.vertices = new LinkedList<Point2D>();
	}
	
	public GeometricPath(GeometricPath trailingPath) {
		super(trailingPath);
		// not really used...
		this.geometry = trailingPath.geometry;
		this.center = trailingPath.center;
		this.vertices = trailingPath.vertices;
	}
	
	public List<Point2D> getVertices() {
		return this.vertices;
	}

	@Override
	public void moveTo(float x, float y) {
		this.geometry.moveTo(x, y);
		this.center.setLocation(x, y);
		this.vertexCount++;
		this.vertices.add(new Point2D.Float(x,y));
		super.moveTo(x, y);
	}
	
	public void moveTo(double x, double y) {
		moveTo((float)x, (float)y);
	}
	
	@Override
	public void lineTo(float x, float y) {
		this.geometry.lineTo(x, y);
		this.center.setLocation((vertexCount * center.getX() + x) / (vertexCount + 1), 
				(vertexCount * center.getY() + y) / (vertexCount + 1));
		this.vertexCount++;
		this.vertices.add(new Point2D.Float(x,y));
		super.lineTo(x, y);
	}
	
	public void lineTo(double x, double y) {
		this.lineTo((float)x, (float)y);
	}
	
	@Override
	public void close() {
		this.geometry.closePath();
		super.close();
	}
	
	@Override
	public void rewind() {
		this.geometry.reset();
		// reset center
		this.center.setLocation(0, 0);
		this.vertexCount = 0;
		this.vertices.clear();
		super.rewind();
	}
	
	@Override
	public void reset() {
		this.geometry.reset();
		// reset center
		this.center.setLocation(0, 0);
		this.vertexCount = 0;
		this.vertices.clear();
		super.reset();
	}
	
	@Override
	public void offset(float dx, float dy) {
		super.offset(dx * this.scaleFactorX, dy * this.scaleFactorY);
		// handle local offsets, well... locally!
		this.center.offset(dx,dy);
		for(Point2D vertex: this.vertices) {
			vertex.offset(dx,dy);
		}
		// offset the geometry
		this.geometry.offset(dx,dy);
	}
	
	/**
	 * Adauga path-ul la configuratia curenta.
	 * Fara a tine cont insa de moveTo-uri!
	 * Practic, doar adauga vertices din src 
	 * la path-ul nostru
	 * 
	 * @param src
	 */
	public void addGeometricPath(GeometricPath src) {
		float[] coords = new float[6];
		PathIterator it = src.getGeometry().getPathIterator(null);
		while(!it.isDone()){
			it.currentSegment(coords);
			lineTo(coords[0], coords[1]);
			it.next();
		}
		super.addPath(src);
	}

	/**
	 * Tests if the given point is inside out path.
	 * It relies mostly on the contains method of our Geometry.
	 * HOWEVER! we need to test ourselves for path vertices
	 * as unfortunately! our geometry reports these as NOT contained.
	 * HOWEVER! points on the border of our Geometry are correctly 
	 * reported as contained! Also see the {@link Shape} javadoc
	 */
	public boolean contains(float x, float y) {
		// test for polygon vertices.
		// this is not tested by Path2D
		PathIterator it = this.geometry.getPathIterator(null);
		float[] coords = new float[6];
		while(!it.isDone()) {
			it.currentSegment(coords);
			if(coords[0] == x && coords[1] == y)
				return true;
			it.next();
		}
		return this.geometry.contains(x, y);
	}
	
	public boolean contains(Point2D point) {
		return contains(point.getX(), point.getY());
	}
	
	public boolean contains(double x, double y) {
		return contains((float)x, (float)y);
	}
	
	public Path2D getGeometry() {
		return geometry;
	}
	
	public Point2D getCenter() {
		return this.center;
	}
	
	protected boolean boundsTest(RectF bounds, float x, float y) {
		return bounds.bottom == y || bounds.top == y 
			|| bounds.left == x || bounds.right == x;
	}
	
	protected boolean boundsTest(RectF bounds, Point2D point) {
		return boundsTest(bounds, (float)point.getX(), (float)point.getY());
	}
	
	@Override
	public String toString() {
		PathIterator it = this.geometry.getPathIterator(null);
		float[] coords = new float[6];
		StringBuilder sb = new StringBuilder();
		while(!it.isDone()) {
			int type = it.currentSegment(coords);
			sb.append("Segment type:" + type);
			sb.append(";Coords:(" + coords[0] + "," + coords[1] + ");");
			it.next();
		}
		sb.append("Geometrical center: " + "(" + center.getX() + "," + center.getY() + ");");
		return sb.toString();
	}

	public void update(GeometricPath trailingPath) {
		set(trailingPath);
	}
	
	public String verticesToString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for(int i=0;i<vertexCount;i++) {
			sb.append( i + ":" + vertices.get(i) + ";");
		}
		sb.append("}");
		return sb.toString();
	}

	public void scale(float scaleFactorX, float scaleFactorY) {
		// compose scaling with previous scale ops
		this.scaleFactorX *= scaleFactorX;
		this.scaleFactorY *= scaleFactorY;
		// scale this path, for drawing
		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(scaleFactorX, scaleFactorY);
		super.transform(scaleMatrix);
	}
	
}
