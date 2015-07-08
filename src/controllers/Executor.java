package controllers;

import java.awt.image.BufferedImage;

import utilities.Logger;
import bitMasks.BComparer;
import image.ImageTransform;
import image.MyImage;

public class Executor implements Runnable{

	ImageTransform imageTransformer;
	MyImage[] baseGroup;
	MyImage[] logoGroup;

	String[] results;
	boolean started = false;
	public boolean finished = false;

	
	public String[] getResults(){
		return results;
	}

	public Executor(String urlImage, MyImage[] myBaseGroup, MyImage[] myLogoGroup){
		// Saves locally the image
		if(urlImage.startsWith("http")){
			imageTransformer = new ImageTransform(urlImage);
		}else{
			imageTransformer = new ImageTransform(urlImage,1);
		}
		baseGroup = myBaseGroup;
		logoGroup = myLogoGroup;
	}

	public void run() {
		if(!started){
			started = true;
			double firstAcumulater = 0.0;
			double secondAcumulater = 0.0;
			//int colorMatch = 0;

			BufferedImage original;

			//status_code, status_text, path_to_new_image, kooaba_response
			String[] returns = {"-1", "System Crash", "none", "-1"};
			if(imageTransformer.hasLoaded()){
				Logger.println("Loaded");

				// Put the image in a default size
				imageTransformer.resize(600.0);
				original = imageTransformer.cloneImage(imageTransformer.getImage());

				imageTransformer.reloadAsJPG();

				returns[2] = imageTransformer.getLastStamped();

				// Execute Sobel (or Canny) Algorithm
				//BufferedImage canny = imageTransformer.getBordersByCanny();
				//imageTransformer.save(canny,"PNG");

				BufferedImage sobel = imageTransformer.getBordersBySobel();
				//imageTransformer.save(sobel,"PNG");

				// Execute Hough algorithm (using the last saved image)
				BufferedImage newHough = null;
				newHough = imageTransformer.getHoughTransformed(sobel,120);

				for(MyImage baseImage : baseGroup){
					double thisSimilarity = baseImage.compareTo(newHough);
					firstAcumulater+=thisSimilarity;
				}
				Logger.println("Hough Similarity: "+firstAcumulater);
				//boolean detected = (acumulate > 1500.0);

				// Stylised B comparisons 
				BComparer styledB = new BComparer("bases/B.png");
				int[] markings = styledB.compare(sobel);
				int logoPattern = markings[2] / 2;
				Logger.println("Logo Pattern: "+logoPattern);

				int combined_precision = (int) ((logoPattern*firstAcumulater)/100.0);
				Logger.println("Precision: "+combined_precision);


				// Begin further analysis
				BufferedImage furtherAnalise = imageTransformer.getSegment(sobel,markings[0]-30,markings[1]-50,200,150);	
				imageTransformer.save(furtherAnalise,"PNG");

				newHough = imageTransformer.getHoughTransformed(furtherAnalise,120);
				for(MyImage baseImage : logoGroup){
					double thisSimilarity = baseImage.compareTo(newHough);
					secondAcumulater+=thisSimilarity;
					Logger.print(thisSimilarity+" + ");
				}
				Logger.println("\nHough Similarity: "+secondAcumulater);

				// Comparisons for bottle pattern
				boolean detected = (combined_precision >= 396 && secondAcumulater > 2.97)
						|| (combined_precision >= 396 && logoPattern > 2277)
						|| (combined_precision >= 363 && secondAcumulater > 2.97165)
						|| (firstAcumulater >= 18 && secondAcumulater > 2.97165);

				if(detected){
					//StraightLineDetector lineFinder = new StraightLineDetector(sobel);
					//lineFinder.detectBasedIn(markings);

					BufferedImage segment = imageTransformer.getSegment(original,markings[0]-100,0,400,600);
					imageTransformer.save(segment, "PNG");

					// Look for the label
					returns[0] = "1";
					returns[1] = "Image match";
					returns[2] = imageTransformer.getLastStamped();

				}else{
					// Case not detected a bottle send "pattern not found" response
					returns[0] = "11";
					returns[1] = "This image don't have the bottle.";
				}

			}else{
				// Case not detected the image send "pattern image not found" response
				returns[0] = "10";
				returns[1] = "Image not found.";
			}

			results = returns;
			finished = true;
		}
	}

}
