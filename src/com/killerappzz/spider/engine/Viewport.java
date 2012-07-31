/**
 * 
 */
package com.killerappzz.spider.engine;

/**
 * This will handle the so-called viewport transformation in graphics world. 
 * 	(I'm thinking especially OpenGL optique)
 * 
 * The basic idea: we have two sets of coordinates: 
 * 	1) World coordinates: this coordinate system is used to define object positions,
 * 		object size etc. When designing the game one thinks in this coordinate system.
 * 		The screen size in world coordinates is up to us to decide ;)
 * 		BTW: origin is lower left corner
 *  2) Screen coordinates: these are the coordinates passed to the Canvas for rendering.
 *  	The size of the screen is dictated by the hardware. The origin is the upper left corner(!)
 *  	All touch coordinates are received in screen coordinates.
 *  
 *  This class will handle conversions between the two types of coordinates.
 * @author florin
 *
 */
public class Viewport {
	
	// world size
	private final float worldWidth;
	private final float worldHeight;
	// screen size
	private float screenWidth;
	private float screenHeight;
	// keep old screen width/height. for screen resized events
	private float oldScreenWidth;
	private float oldScreenHeight;
	
	public Viewport(float worldWidth, float worldHeight, float screenWidth,
			float screenHeight) {
		super();
		this.worldWidth = worldWidth;
		this.worldHeight = worldHeight;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		// initially: we consider we do rescale between world and screen
		this.oldScreenWidth = this.worldWidth;
		this.oldScreenHeight = this.worldHeight;
	}
	
	public float worldToScreenX(float x) {
		return x * this.screenWidth / this.worldWidth;
	}
	
	public float screenToWorldX(float x) {
		return x * this.worldWidth / this.screenWidth;
	}
	
	public float worldToScreenY(float y) {
		return y * this.screenHeight / this.worldHeight;
	}
	
	public float screenToWorldY(float y) {
		return y * this.worldHeight / this.screenHeight;
	}
	
	// will be used by touch pointers so OK even tho not the reverse formulae
	public float touchScreenToWorldY(float y) {
		return (this.screenHeight - y) * this.worldHeight / this.screenHeight;
	}

	public float getScaleFactorX() {
		return this.screenWidth / this.oldScreenWidth;
	}

	public float getScaleFactorY() {
		return this.screenHeight / this.oldScreenHeight;
	}
	
	public void updateScreen(float width, float height) {
		this.oldScreenHeight = this.screenHeight;
		this.oldScreenWidth = this.screenWidth;
		this.screenHeight = height;
		this.screenWidth = width;
	}

	// get screen order of magnitude
	public float getScreenOOM() {
		return this.screenHeight + this.screenWidth;
	}

	public float getWorldWidth() {
		return this.worldWidth;
	}

	public float getWorldHeight() {
		return this.worldHeight;
	}

	public float getScreenWidth() {
		return this.screenWidth;
	}
	
	public float getScreenHeight() {
		return this.screenHeight;
	}

}
