package com.killerappzz.spider.rendering;

import android.graphics.Canvas;

import com.killerappzz.spider.ProfileRecorder;
import com.killerappzz.spider.objects.ObjectManager;
import com.killerappzz.spider.rendering.CanvasSurfaceView.Renderer;

/**
 * An extremely simple renderer based on the CanvasSurfaceView drawing
 * framework.  Simply draws a list of sprites to a canvas every frame.
 * Do we need more ???
 */
public class GameRenderer implements Renderer {

	private final ObjectManager om;
	private final ProfileRecorder profiler;
	
    public GameRenderer(ObjectManager m) {
    	this.om = m;
    	this.profiler = new ProfileRecorder();
	}
    
    public void drawFrame(Canvas canvas) {
    	this.om.draw(canvas);
    }

    public void sizeChanged(int width, int height) {
        this.om.updateScreen(width,height);
    }

	public ProfileRecorder getProfiler() {
		return this.profiler;
	}

}
