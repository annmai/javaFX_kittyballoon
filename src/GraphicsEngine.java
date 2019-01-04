package kittyballoon;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @author Ann Mai
 */

class GraphicsEngine {
	
	private Pane root;
	private AnimatedImageFactory graphicsFactory = new AnimatedImageFactory();
	private ArrayList<ImageView> clouds = new ArrayList<ImageView>();
	private ArrayList<AnimatedImage> presents = new ArrayList<>();
	private ArrayList<AnimatedImage> reapers = new ArrayList<>();
	private ArrayList<AnimatedImage> balloons = new ArrayList<>();
	private ArrayList<AnimatedImage> popped_balloons = new ArrayList<>();
	private Kitty cat;
	
	GraphicsEngine(Pane root, Kitty cat) {
		this.root = root;
		this.cat = cat;
	}
	
	void initializeGraphics() {
	
		generateClouds();
		generateKitty(cat);
		generateGrimReapers();
		generatePresents();
		generateBalloons();
	}
	
	void scrollGraphics() {
		scrollClouds();
		scrollBalloons();
		scrollPresents();
		scrollReapers();
	}
	
	
	// generates the Kitty cat graphic on the root pane
    void generateKitty(Kitty cat) {
    	
		ImageView catImg = cat.getGraphics();
        catImg.setTranslateX(100);
        catImg.setTranslateY(150);
        root.getChildren().add(catImg);
        
    }
    
    // detects and displays appropriate images upon collision
    void monitorCollisons() {
    	
    	for(int i = 0; i < balloons.size(); ++i) {
    		
    		Balloon balloonObj = (Balloon) balloons.get(i);
    		ImageView balloon = balloonObj.getGraphics();
    		
    		if(cat.getGraphics().getBoundsInParent().intersects(balloon.getBoundsInParent())) {
    			popped_balloons.add(balloonObj);
   
    		}	
    	}
    }
    
    ArrayList<AnimatedImage> getPoppedBalloonsList() {
    	return popped_balloons;
    }
    
    // generates clouds randomly on the root pane
    private void generateClouds() {
    	
		Cloud cloudObj = new Cloud();
    	
        for (int i = 0; i < cloudObj.getNumClouds(); ++i) {
        	
            ImageView cloud = cloudObj.getCloudImageView(i);
            clouds.add(cloud);
            cloud.setX(getRandomX_ON_SCREEN());
            cloud.setY(Math.random() * Main.SCENE_HEIGHT * 0.5);
            root.getChildren().add(cloud);
        }
    }

    
    private void generatePresents() {
    	
    	presents = graphicsFactory.generateAnimatedImages(Assest.PRESENT, 1, 3);
    	addToPane(presents, Axis.Y, Visibility.OFFSCREEN);
   
    }   
    
 
    private void generateBalloons() {

    	balloons = graphicsFactory.generateAnimatedImages(Assest.BALLOON, 4, 7);
    	addToPane(balloons, Axis.Y, Visibility.OFFSCREEN);
   
    }   
    
 
    private void generateGrimReapers() {
    	
        reapers = graphicsFactory.generateAnimatedImages(Assest.GRIM_REAPER, 3,4);
      	addToPane(reapers, Axis.X, Visibility.ONSCREEN);
      	
    }
   
    
    /* --- Adds the list of AnimatedImages to the root pane --- */
    private void addToPane(ArrayList<AnimatedImage> listOfImages, Axis axis, Visibility visibility) {
    	
       	double x_value = Math.random();
    	double y_value = Math.random();
    	
        for (int i = 0; i < listOfImages.size(); ++i) {

        	AnimatedImage animatedImage = listOfImages.get(i);
        	ImageView graphics = animatedImage.getGraphics();
        	
        	if(visibility == Visibility.ONSCREEN) {
        		x_value = getRandomX_ON_SCREEN();
        		y_value = getRandomY_ON_SCREEN(graphics.getImage());
        	}
        	else {
        		if(axis == Axis.X) {
        			x_value = getRandomX_OFF_SCREEN(graphics.getImage());
        			y_value = getRandomY_ON_SCREEN(graphics.getImage());
        		}
        		else {
        			x_value = getRandomX_ON_SCREEN();
        			y_value = Main.SCENE_HEIGHT + 10;
        		}
        			
        	}
        	
            graphics.setX(x_value);
            graphics.setY(y_value);
            root.getChildren().add(graphics);
        }
        
        // assign new position if AnimatedImages overlap
        repositionIfOverlap(listOfImages, axis); 
    	
    }
    
   /* --- checks if images in the list overlap and relocates them if they do --- */
   private void repositionIfOverlap(ArrayList<AnimatedImage> listOfImgs, Axis axis) {
       
	   for(int i = 0; i < listOfImgs.size(); ++i) {
       	for(int j = 0; j < listOfImgs.size(); ++j) {
       		
       		if(i == j)
       			continue;
       		
       		ImageView image1 = listOfImgs.get(i).getGraphics();
       		ImageView image2 = listOfImgs.get(j).getGraphics();
       		
       		while(image1.intersects(image2.getBoundsInParent())) {
       			
       			double x_value;
       			double y_value;
       			
       			if(axis == Axis.X) {
       				x_value = getRandomX_OFF_SCREEN(image1.getImage());
        			y_value = getRandomY_ON_SCREEN(image1.getImage());
       			}
       			else {
        			x_value = getRandomX_ON_SCREEN();
        			y_value = getRandomY_OFF_SCREEN(image1.getImage());
       			}
       		   
       			image1.setX(x_value);
       			image1.setY(y_value);
       		}
       	}
	   }
   }
   
   // for each cloud on the screen move it 1 pixel toward the left, if the cloud goes off
	// screen relocate it to a new position so it appears to scroll back on screen
    private void scrollClouds() {	
    	
        for (int i = 0; i < clouds.size(); ++i) {
        	
            if (clouds.get(i).getX() < -clouds.get(i).getImage().getWidth()) {
                clouds.get(i).setX(Main.SCENE_WIDTH + Math.random() + 5);
                clouds.get(i).setY(Math.random() * Main.SCENE_HEIGHT * 0.5);
            }
            
            clouds.get(i).setX(clouds.get(i).getX() - 1);
        }  	
    }
    
    // displays presents in such a way as if they are flying upwards from off screen to on screen
    private void scrollPresents() {
    	scrollAnimatedImages(Axis.Y, presents, 10);
    } 
    
    private void scrollBalloons() {
    	scrollAnimatedImages(Axis.Y, balloons, 15); 
    } 
    
    // create illusion of movement for GrimReapers
    private void scrollReapers() {
    	scrollAnimatedImages(Axis.X, reapers, 3);

    }
    
    private void scrollAnimatedImages(Axis axis, ArrayList<AnimatedImage> listOfImages, int speed) {
    	
        for (int i = 0; i < listOfImages.size(); ++i) {
        	
        	AnimatedImage animatedImage = listOfImages.get(i);
        	ImageView imageView = animatedImage.getGraphics();
        	
        	if(axis == Axis.X) {
        		
                if (imageView.getX() < -imageView.getImage().getWidth()) {
               	 
               	 double x = getRandomX_OFF_SCREEN(imageView.getImage());
               	 double y = getRandomY_ON_SCREEN(imageView.getImage());
               	 imageView.setX(x);
               	 imageView.setY(y);
               	 
                }
                else
                	imageView.setX(imageView.getX() - speed);
        		
        	}
        	else {
        		double imageHeight = imageView.getImage().getHeight();
        		
                if(imageView.getY() < -imageHeight) {
                	imageView.setY(getRandomY_OFF_SCREEN(imageView.getImage()));
                	imageView.setX(getRandomX_ON_SCREEN());
                }
                else
                	imageView.setY(imageView.getY() - Math.random() * speed);
        	}

        }  
    	
    }
    
 
    
    /* ---- Helper Functions to Generate Random positions for X, Y ---- */
    private double getRandomX_ON_SCREEN(){
    	return Math.random() * Main.SCENE_WIDTH + Math.random();
    }
    
    private double getRandomX_OFF_SCREEN(Image image) {
    	return Main.SCENE_WIDTH + image.getWidth() + Math.random();
    }
    
    private double getRandomY_ON_SCREEN(Image image) {
    	return Math.abs(Math.random() * Main.SCENE_HEIGHT - image.getHeight());
    }
    
    private double getRandomY_OFF_SCREEN(Image image) {
    	return Main.SCENE_HEIGHT + image.getHeight() + Math.random();
    }
    
    
    /*-----------------------------------------------------------------*/

	// refreshes Animated Image frames
	void refreshFrames(AnimatedImage image) {
	    image.refresh();
	}
	
	// refreshes all Animated Image frames in list
	void refreshFrames(ArrayList<AnimatedImage> listOfImg, Cycle cycle) {
		
		for(int i = 0; i < listOfImg.size(); ++i) {
			
			if(cycle == Cycle.INDEFINITE) 
				refreshFrames(listOfImg.get(i));
			
			else { // cycles through the AnimatedImage's frames only once
				
				AnimatedImage image = listOfImg.get(i);
					
				if(image.frameCounter == image.NUM_FRAMES ) {
						image.frameCounter = 0;
						ImageView imgView = image.getGraphics();
						imgView.setImage(image.frames[0]);
						imgView.setY(getRandomY_OFF_SCREEN(imgView.getImage()));
						listOfImg.remove(image);
				}
				else
					image.nextFrame();	
			}

		}
	}

}

