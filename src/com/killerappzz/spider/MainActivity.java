package com.killerappzz.spider;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.killerappzz.spider.engine.Engine;
import com.killerappzz.spider.engine.Game;
import com.killerappzz.spider.rendering.CanvasSurfaceView;

public class MainActivity extends Activity {
	
    private CanvasSurfaceView mCanvasSurfaceView;
    private Game game;
    private final int width = 400;
    private final int height = 300;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCanvasSurfaceView = new CanvasSurfaceView(this, width, height);
        game = new Game(this, width, height);
        // load the game
        game.load(this);
        // register the engine
        Engine engine = new Engine(game);
        mCanvasSurfaceView.setRenderer(game.getRenderer());
        mCanvasSurfaceView.setEvent(engine);
        setContentView(mCanvasSurfaceView);
    }
    
    
    /** Recycles all of the bitmaps loaded in onCreate(). */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCanvasSurfaceView.clearEvent();
        mCanvasSurfaceView.stopDrawing();
        game.cleanup();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	// UI processing
    	this.game.getTouchFilter().updateTouch(event);
    	// gesture detection on ObjectManager level. probably will remove this in the future
    	return game.getTouchHandler().onTouchEvent(event);
    }

}