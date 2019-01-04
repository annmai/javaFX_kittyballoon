package kittyballoon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Ann Mai
 */

class Cloud {
	
	private final int NUM_CLOUDS = 7;
	private Image cloudImgs[] = new Image[NUM_CLOUDS];
	private final double OPACITY = 0.60;

    Cloud() {	
    	
    	// load images of different clouds into the cloudImgs array
        for (int i = 0; i < NUM_CLOUDS; i++)
            cloudImgs[i] = new Image(getClass().getResourceAsStream("graphics/cloud" + i + ".png"));
        
    }
    
    // returns an ImageView set with the specified cloud Image corresponding to the index parameter
    ImageView getCloudImageView(int index) {
    	ImageView imageView = new ImageView(cloudImgs[index]);
    	imageView.setOpacity(OPACITY);
    	return imageView;
    }
    
    // returns the total number of different cloud Images in the array
    int getNumClouds() {
    	return NUM_CLOUDS;
    }
}

