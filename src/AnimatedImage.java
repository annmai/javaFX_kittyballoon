package kittyballoon;

/**
 * @author Ann Mai
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class AnimatedImage {
	
    protected int frameCounter = 0;
    protected ImageView graphics = new ImageView();
    protected Image frames[];
    protected final int IMAGE_WIDTH;
    protected final int IMAGE_HEIGHT;
    protected final int NUM_FRAMES;
    
	AnimatedImage(int numFrames, String path) {
		
    	NUM_FRAMES = numFrames;
		frames = new Image[NUM_FRAMES];
		
		// loads the different images associated with the present graphic into the frames array
    	for (int i = 0; i < NUM_FRAMES; i++)
            frames[i] = new Image(getClass().getResourceAsStream(path + i + ".png"));
    	
    	graphics.setImage(frames[0]);
    	IMAGE_WIDTH = (int) frames[0].getWidth();
    	IMAGE_HEIGHT = (int) frames[0].getHeight();
    	
	}
	
    void refresh() {
        graphics.setImage(frames[frameCounter++]);
        if (frameCounter == NUM_FRAMES) {
            frameCounter = 0;
        }
    }
    
    ImageView getGraphics() {
        return graphics;
    }
    
    int getNumFrames() {
    	return NUM_FRAMES;
    }
    
    void nextFrame() {
    	
    	if(frameCounter != NUM_FRAMES)
    		graphics.setImage(frames[frameCounter++]);
    }
	
}
