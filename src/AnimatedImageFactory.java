package kittyballoon;

import java.util.ArrayList;

/**
 * @author Ann Mai
 */

class AnimatedImageFactory {
	
	AnimatedImageFactory() {}
	
	ArrayList<AnimatedImage> generateAnimatedImages(Assest type, int min, int max) {
		
    	int numImages = (int) ((Math.random()*((max - min)+ 1))+ min);
    	ArrayList<AnimatedImage> list = new ArrayList<>();
    	
        for (int i = 0; i < numImages; i++) {
        	
        	if(type == Assest.GRIM_REAPER){
        		GrimReaper reaper = new GrimReaper();
        		list.add(reaper);     
        	}
        	else if(type == Assest.BALLOON) {
        		Balloon balloon = new Balloon();
                list.add(balloon);
        	}
        	else if(type == Assest.PRESENT) {
        		Present present = new Present();
                list.add(present);
        	}
        	else {
        		throw new RuntimeException("Assest Enum value required. Valid values are Assest.GRIM_REAPER, Assest.BALLOON, or Assest.PRESENT");
        	}	
        }
        
        return list;
	}

}
