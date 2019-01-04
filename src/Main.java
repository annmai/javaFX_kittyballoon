package kittyballoon;

/**
 * @author Ann Mai
 */

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	// screen width and height
	static final double SCENE_WIDTH = 1000, SCENE_HEIGHT = 600; 
	private Pane root;
	private GraphicsEngine graphicsEngine;
    private final double FPS = 30;
    private Kitty cat = new Kitty();
    Timeline gameLoop;
	
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			root = new Pane();
			graphicsEngine = new GraphicsEngine(root, cat);
			initializeGame();
			
			Scene scene = new Scene(root, SCENE_WIDTH , SCENE_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Kitty Balloon");
			primaryStage.setScene(scene);
			primaryStage.show();
			
	        scene.onKeyPressedProperty().bind(root.onKeyPressedProperty());
	        
	        root.setOnKeyPressed(keyEvent -> {
	        	
	        	KeyCode keycode = keyEvent.getCode();
	        	
	            if (keycode.equals(KeyCode.RIGHT))
	            	cat.moveForward();
	            
	            else if(keycode.equals(KeyCode.LEFT))
	            	cat.moveBackward();
	            
	            else if(keycode.equals(KeyCode.SPACE) || keycode.equals(KeyCode.UP))
	            	cat.moveUp();
	            
	            else if(keycode.equals(KeyCode.DOWN))
	            	cat.moveDown();
	            else {
	            	
	            }
	                      
	        });
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	private void initializeGame() {
		
		graphicsEngine.initializeGraphics();

		// construct new time line that will serve as the game loop
        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / FPS), new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
             
            	graphicsEngine.scrollGraphics();
            	graphicsEngine.monitorCollisons();
            	graphicsEngine.refreshFrames(graphicsEngine.getPoppedBalloonsList(), Cycle.ONCE);
            		
            }
            	
        }));
        
        gameLoop.setCycleCount(Animation.INDEFINITE);;
        gameLoop.play();
	}
    
    
	public static void main(String[] args) {
		launch(args);
	}
}
