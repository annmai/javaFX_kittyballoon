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
    private Kitty cat;
    private ScoreLabel scoreLabel = new ScoreLabel(Main.SCENE_WIDTH,0);
    static Timeline gameLoop;
    static boolean isGameOver = false;
	
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			root = new Pane();
			initializeGame();
			
			Scene scene = new Scene(root, SCENE_WIDTH , SCENE_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Kitty Balloon");
			primaryStage.setScene(scene);
			primaryStage.show();
			
	        scene.onKeyPressedProperty().bind(root.onKeyPressedProperty());
	        
	        
	        root.setOnKeyPressed(keyEvent -> {
	        	
	        	KeyCode keycode = keyEvent.getCode();
	        	
	            if (keycode.equals(KeyCode.RIGHT) && !isGameOver)
	            	cat.moveForward();
	            
	            else if(keycode.equals(KeyCode.LEFT) && !isGameOver)
	            	cat.moveBackward();
	            
	            else if((keycode.equals(KeyCode.SPACE) || keycode.equals(KeyCode.UP)) && !isGameOver)
	            	cat.moveUp();
	            
	            else if(keycode.equals(KeyCode.DOWN) && !isGameOver)
	            	cat.moveDown();
	            
	            else if(isGameOver && keycode.equals(KeyCode.ENTER))
	            	initializeGame();
	            
	            else {
	            	// do nothing
	            }
	                      
	        });
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	private void initializeGame() {
		
		root.getChildren().clear();
		isGameOver = false;
		
		// add score label to root pane
	    scoreLabel.setOpacity(.8);
	    scoreLabel.setText("Score: 0");
	    root.getChildren().add(scoreLabel);
	    
		cat = new Kitty();
		graphicsEngine = new GraphicsEngine(root, cat);
		graphicsEngine.initializeGraphics();

		// construct new time line that will serve as the game loop
        gameLoop = new Timeline(new KeyFrame(Duration.millis(1000 / FPS), new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
             
            	graphicsEngine.scrollGraphics();
            	graphicsEngine.refreshFrames(graphicsEngine.getGrimReapersList(), Cycle.INDEFINITE);
            	graphicsEngine.monitorCollisons();
            	graphicsEngine.refreshFrames(graphicsEngine.getPoppedBalloonsList(), Cycle.ONCE);
            	scoreLabel.setText("Score: " + graphicsEngine.getScore());	
            }
            	
        }));
        
        gameLoop.setCycleCount(Animation.INDEFINITE);;
        gameLoop.play();
        
	}
    
    
	public static void main(String[] args) {
		launch(args);
	}
}
