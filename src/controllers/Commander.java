package controllers;

import utilities.Logger;
import global.Path;
import image.MyImage;
import image.MyLogo;

public class Commander {

	MyImage[] baseGroup;
	MyLogo[] logoGroup;
	String[][] responses = new String[100][2];
	
	Executor[] processors = new Executor[100];
	
	public Commander(String[] urlImages){

		final String imagePath = Path.thisPath;
		
		baseGroup = new MyImage[7];
		baseGroup[0] = new MyImage(imagePath+"images/bases/hough-1.png",1);
		baseGroup[1] = new MyImage(imagePath+"images/bases/hough-2.png",1);
		baseGroup[2] = new MyImage(imagePath+"images/bases/hough-3.png",1);
		baseGroup[3] = new MyImage(imagePath+"images/bases/hough-4.png",1);
		baseGroup[4] = new MyImage(imagePath+"images/bases/hough-5.png",1);
		baseGroup[5] = new MyImage(imagePath+"images/bases/hough-6.png",1);
		baseGroup[6] = new MyImage(imagePath+"images/bases/hough-7.png",1);
		
		logoGroup = new MyLogo[1];
		logoGroup[0] = new MyLogo(imagePath+"images/bases/sec-hough-1.png",1);
		
		// Treat blocks of until 100 images by time
		if(urlImages.length<=100){
			// Low threaded efficiency...
			int counter = 0;
			for(String thisUrl : urlImages){
				if(thisUrl == null){
					break;
				}
				Logger.println("--------------");
				Logger.println("Running "+(counter+1));

				Executor executing = new Executor(thisUrl, baseGroup, logoGroup);
				
				Thread additionalThread = new Thread(executing);
				additionalThread.start();
				
				processors[counter] = executing;
				counter++;
			}
			
			boolean finished = false;
			while(!finished){
				finished = true;
				for(int i=0; i<counter; i++){
					if(!processors[i].finished){
						finished = false;
						break;
					}
				} 
				// Add a slow down timer to reduce parallel processing in this thread
			}

			for(int i=0; i<counter; i++){
				responses[i] = processors[i].getResults();
			}
			
			Logger.println("END.");
			
		}else{
			Logger.println("--------------");
			Logger.println("Buffer Overflow");
		}
	}

	public String[][] getResults() {
		return responses;
	}
	
}
