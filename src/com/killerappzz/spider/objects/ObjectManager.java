package com.killerappzz.spider.objects;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

import com.killerappzz.spider.Constants;
import com.killerappzz.spider.Customization;
import com.killerappzz.spider.ProfileRecorder;
import com.killerappzz.spider.engine.Game;
import com.killerappzz.spider.geometry.Edge2D;

/**
 * Handles objects which are displayed on the screen
 * throughout their lifetime
 * 
 * @author florin
 *
 */
public class ObjectManager extends SimpleOnGestureListener{
	
	private final List<DrawableObject> objects; 
	// spider is a special role
	private Spider spider;
	// fence is another special role
	private Fence fence;
	// list of objects that appear in the background
	private final List<DrawableObject> backgroundObjects;
	// lit of objects that are in the scene
	private final List<DrawableObject> sceneObjects;
	private final Game game;
	// the scene movement state
	public SceneState state;
	private final Paint statsPaint;
	
	public ObjectManager(Game theGame) {
		this.objects = new LinkedList<DrawableObject>();
		this.backgroundObjects = new LinkedList<DrawableObject>();
		this.sceneObjects = new LinkedList<DrawableObject>();
		this.state = SceneState.OBJECT_MOVE;
		this.game = theGame;
		statsPaint = Customization.getStatsPaint();
	}
	
	public void addObject(DrawableObject object) {
    	this.objects.add(object);
    }
	
	public void addBackgroundObject(DrawableObject object) {
		addObject(object);
		this.backgroundObjects.add(object);
	}
	
	public void addSceneObject(DrawableObject object) {
		addObject(object);
		this.sceneObjects.add(object);
	}
	
	public void addSpider(Spider spider){
		this.objects.add(spider);
		this.sceneObjects.add(spider);
		this.spider = spider;
	}
	
	public void addFence(Fence fence) {
		this.objects.add(fence);
		this.backgroundObjects.add(fence);
		this.fence = fence;
	}
	
	public void draw(Canvas canvas) {
		for(DrawableObject object : objects) {
    		object.draw(canvas);
    	}
		// draw statistics
		final ProfileRecorder profiler = game.getRenderer().getProfiler();
		final long frameTime = 
                profiler.getAverageTime(ProfileRecorder.PROFILE_FRAME);
		final int fps = frameTime > 0 ? 1000 / (int)frameTime : 0;
		canvas.drawText("fps:" + fps, Constants.STATS_POSITION_X, 
				Constants.STATS_POSITION_Y, statsPaint);
	}
	
	public void cleanup() {
		for(DrawableObject obj:objects) {
			obj.cleanup();
		}
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		float touchX = e1.getX();
		// need to flip Y position
		float touchY = (float)game.getScreenHeight() - e1.getY();
		if(spiderTouched(touchX, touchY)) {
			Log.d("SPIDER", "Spider touched!");
			spider.setVelocity(e2.getX() - e1.getX(), - e2.getY() + e1.getY() );
			Log.d("SPIDER", "Velocity:" + spider.getVelocityX() + ";" + spider.getVelocityY());
		}
		return true;
	}

	private boolean spiderTouched(float x, float y) {
		float range = (1.0f * (this.game.getScreenHeight() + this.game.getScreenWidth()) 
			* Constants.TOUCH_ERROR_TOLERANCE_PERCENTILE) / 200.0f;
		// calculate the distance from touch point to spider
		double distance = Math.sqrt( (x - spider.x - spider.width * 0.5f) * (x - spider.x - spider.width * 0.5f) 
				+ (y - spider.y) * (y - spider.y) );
		return (float)distance < range;
	}

	public void updatePositions(float timeDeltaSeconds) {
		// check for potential scene state switch
		boolean xExpansion = needXExpansion(game.getScreenWidth());
		boolean yExpansion = needYExpansion(game.getScreenHeight());
		boolean needExpand = xExpansion || yExpansion;
		if(state.equals(SceneState.OBJECT_MOVE)) {
			if(needExpand) {
				for(DrawableObject backgroundObject : this.backgroundObjects){
					backgroundObject.setVelocity( xExpansion ? spider.getVelocityX() : 0, 
							yExpansion ? -spider.getVelocityY() : 0);
					backgroundObject.resume();
				}
				for(DrawableObject sceneObject: this.sceneObjects) {
					sceneObject.pauseX(xExpansion);
					sceneObject.pauseY(yExpansion);
				}
				this.state = SceneState.SCENE_MOVE;
			}
		} else if(state.equals(SceneState.SCENE_MOVE)) {
			if(!needExpand) {
				for(DrawableObject backgroundObject : this.backgroundObjects){
					backgroundObject.pause();
				}
				for(DrawableObject sceneObject: this.sceneObjects) {
					sceneObject.resume();
				}
				this.state = SceneState.OBJECT_MOVE;
			}
		}
		
		// preload some data for collision detection mechanism
		if(spider.speed!=0 && !(spider.getVelocityX() == 0 && spider.getVelocityY() == 0)) {
			spider.setMovementVector(timeDeltaSeconds);
			fence.collisionPrefetch(spider.getMovementVector());
		}
		
		// update positions
		for(DrawableObject object : objects) {
			if(object.speed!=0 && !(object.getVelocityX() == 0 && object.getVelocityY() == 0)) {
				object.updatePosition(timeDeltaSeconds);
				object.boundsCheck(game.getScreenWidth(), game.getScreenHeight());
			}
		}
		
		// do the collision check
		if(spider.speed!=0 && !(spider.getVelocityX() == 0 && spider.getVelocityY() == 0)) {
			collisionsCheck();
		}
		
	}
	
	// test for collision Drone -> Fence
	private void collisionsCheck() {
		if(this.fence.collisionTest(
				this.spider.getMovementVector())) 
		{
			// we actually hit something!
			// call collision handlers
			this.spider.collisionHandler();
			this.fence.collisionHandler();
		}
	}

	public boolean needXExpansion(int screenWidth) {
		return ((spider.x < screenWidth * Constants.EXPANSION_PERCENTILE / 100 && spider.getVelocityX() < 0.0f) 
                || (spider.x > screenWidth - screenWidth * Constants.EXPANSION_PERCENTILE / 100
                        && spider.getVelocityX() > 0.0f));
	}
	
	public boolean needYExpansion(int screenHeight) {
		return ((spider.y < screenHeight * Constants.EXPANSION_PERCENTILE / 100 && spider.getVelocityY() < 0.0f) 
                || (spider.y > screenHeight - screenHeight * Constants.EXPANSION_PERCENTILE / 100 
                        && spider.getVelocityY() > 0.0f));
	}

	public enum SceneState {
		OBJECT_MOVE,
		SCENE_MOVE
	}

	public void doneScreenScrollBackground() {
		this.state = SceneState.OBJECT_MOVE;
		for(DrawableObject backgroundObject : this.backgroundObjects){
			backgroundObject.pause();
		}
		for(DrawableObject sceneObject: this.sceneObjects) {
			sceneObject.resume();
		}
	}

	public boolean needStateSwitch() {
		if(state.equals(SceneState.SCENE_MOVE)) {
			// switch if spider and background move in opposite direction(s)
			DrawableObject backgroundObject = backgroundObjects.get(0);
			return backgroundObject.getVelocityX() * spider.getVelocityX() <= 0 
					&& backgroundObject.getVelocityY() * spider.getVelocityY() <= 0;
		}
		else
			return false;
	}

	public void updateScreen(int width, int height) {
		for(DrawableObject obj:this.objects) {
			obj.updateScreen(width, height);
		}
	}

	public void setBackgroundSpeeds(Spider spider) {
		for(DrawableObject backgroundObject : this.backgroundObjects)
			backgroundObject.speed = spider.speed;
	}
	
}
