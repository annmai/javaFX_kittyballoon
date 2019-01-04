package kittyballoon;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

/**
 * @author Ann Mai
 */

class Kitty extends AnimatedImage {
	
    private TranslateTransition forward;
    private TranslateTransition back;
    private TranslateTransition up;
    private TranslateTransition down;
    private RotateTransition rotate;
    private TranslateTransition gravity;
    private final double MOVEMENT_OFFSET = 120;
    private final double MOVEMENT_ANGLE = 20;

    Kitty() {
        
    	super(3, "graphics/pusheen");
    	
        forward = new TranslateTransition(Duration.millis(500), graphics);
        forward.setInterpolator(Interpolator.LINEAR);
        forward.setByX(MOVEMENT_OFFSET);
        
        back = new TranslateTransition(Duration.millis(500), graphics);
        back.setInterpolator(Interpolator.LINEAR);
        back.setByX(-MOVEMENT_OFFSET);
        
        up = new TranslateTransition(Duration.millis(500), graphics);
        up.setInterpolator(Interpolator.LINEAR);
        up.setByY(-MOVEMENT_OFFSET);
        
        down = new TranslateTransition(Duration.millis(500), graphics);
        down.setInterpolator(Interpolator.LINEAR);
        down.setByY(MOVEMENT_OFFSET);
        
        gravity = new TranslateTransition(Duration.millis(10 * Main.SCENE_HEIGHT), graphics);
        gravity.setInterpolator(Interpolator.LINEAR);
        gravity.setByY(Main.SCENE_HEIGHT + 20);
        gravity.play();
        
        rotate = new RotateTransition(Duration.millis(500), graphics);
        

    }
    
    void moveForward() {
    	rotate.setDuration(Duration.millis(100));
    	rotate.setToAngle(MOVEMENT_ANGLE);
    	rotate.stop();
    	rotate.play();
    	forward.play();

        rotate.setOnFinished((finishedEvent) -> {
        	rotate.setDuration(Duration.millis(500));
            rotate.setToAngle(0);
            rotate.stop();
            rotate.play();
        });
    }
    
    void moveBackward() {
    	
    	rotate.setDuration(Duration.millis(100));
    	rotate.setToAngle(-MOVEMENT_ANGLE);
    	rotate.stop();
    	rotate.play();
    	back.play();

        rotate.setOnFinished((finishedEvent) -> {
        	rotate.setDuration(Duration.millis(500));
            rotate.setToAngle(0);
            rotate.stop();
            rotate.play();
        });
    }
    
    void moveUp() {
    	gravity.stop();
    	up.play();
    	
    	up.setOnFinished((finishedEvent) -> {
            gravity.play();
        });
    }
    
    void moveDown() {
    	down.play();
    }

}
