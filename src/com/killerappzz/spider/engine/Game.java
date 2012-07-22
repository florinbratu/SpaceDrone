package com.killerappzz.spider.engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.GestureDetector;

import com.killerappzz.spider.Constants;
import com.killerappzz.spider.R;
import com.killerappzz.spider.objects.Background;
import com.killerappzz.spider.objects.Fence;
import com.killerappzz.spider.objects.ObjectManager;
import com.killerappzz.spider.objects.Spider;
import com.killerappzz.spider.rendering.GameRenderer;

/**
 * This will encapsulate the logic of the game
 * 
 * @author florin
 *
 */
public class Game {
	
	private final int screenWidth;
	private final int screenHeight;
	
    private final ObjectManager manager;
    private final GameRenderer renderer;
    private final BitmapFactory.Options bitmapOptions;
    private GestureDetector touchHandler;
    private final GameData data;
	
	public Game(Activity parentActivity) {
		// We need to know the width and height of the display pretty soon,
        // so grab the information now.
        DisplayMetrics dm = new DisplayMetrics();
        parentActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        
        data = new GameData();
        manager = new ObjectManager(this, data);
		renderer = new GameRenderer(manager);
		bitmapOptions = new BitmapFactory.Options();
		touchHandler = new GestureDetector(parentActivity, manager);
		
        // Clear out any old profile results.
		renderer.getProfiler().resetAll();
	}
	
	public void load(Context context) {
		manager.init(context);
		// Sets our preferred image format to 16-bit, 565 format.
		bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		// Make the background.
        // Note that the background image is larger than the screen, 
        // so some clipping will occur when it is drawn.
		Background background = new Background(context, bitmapOptions, 
				R.drawable.background, screenWidth, screenHeight, manager);
        manager.addBackgroundObject(background);
        
        // Make the Fence
        Fence fence = new Fence(context, bitmapOptions, screenWidth, screenHeight);
        // TODO load from File
        manager.addFence(fence);
        
        // Make the spider
        Spider spider = new Spider(context, bitmapOptions, R.drawable.spider, manager, screenWidth, screenHeight);
        // Spider location.
        int centerX = (this.screenWidth - (int)spider.width) / 2;
        int centerY = (this.screenHeight - (int)spider.height) / 2;
        spider.x = centerX;
        spider.y = centerY;
        spider.speed = 0.5f * (this.screenWidth + this.screenHeight) / Constants.DEFAULT_SPIDER_SPEED_FACTOR;
        manager.addSpider(spider);
        
        fence.inlineCreate(spider);
        // set speeds of Background objects
        manager.setBackgroundSpeeds(spider);
        
        // Now's a good time to run the GC.  Since we won't do any explicit
        // allocation during the test, the GC should stay dormant and not
        // influence our results.
        Runtime r = Runtime.getRuntime();
        r.gc();
	}
	
	public void cleanup() {
		manager.cleanup();
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}
	
	public GameRenderer getRenderer() {
		return renderer;
	}
	
	public GestureDetector getTouchHandler() {
		return this.touchHandler;
	}
	
	public ObjectManager getObjectManager() {
		return this.manager;
	}
	
}
