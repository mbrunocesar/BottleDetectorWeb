package image;

import global.Path;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class MyImage {

	final String imagePath = Path.thisPath + "images/"; 

	final int thisWidth = 600;
	final int thisHeight = 480;

	BufferedImage image = null;
	// 76.800
	int[][] myPixels = new int[thisWidth][thisHeight];

	public MyImage(String urlImage) {
		loadByURL(urlImage);
	}

	public MyImage(String pathImage, int checkin) {
		loadByFile(pathImage);
	}

	public void loadByURL(String urlImage){
		try {
			URL urlImg = new URL(urlImage);  
			image = ImageIO.read(urlImg);
			loadPixels();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadByFile(String pathImage){
		try {
			File fileImg = new File(pathImage);
			image = ImageIO.read(fileImg);
			loadPixels();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadPixels() {
		for(int i=0;i<thisWidth;i++){
			for(int j=0;j<thisHeight;j++){
				myPixels[i][j] = grayscaledPixel(image.getRGB(i, j));
			}
		}
	}

	protected int grayscaledPixel(int pixelValue){
		int rgb = pixelValue;
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int b = (rgb & 0xFF);
		return (r + g + b) / 3;
	}

	public BufferedImage getImage(){
		return image;
	}

	public void setImage(BufferedImage buffered){
		image = buffered;
	}

	public double compareTo(BufferedImage newHough) {
		double acumulator = 0;
		int thisPixel = 0;

		int lowerPixel = 9999999;
		int higherPixel = -1;
		/*
		int numTimesHit = 0;
		int numTimesCommom = 0;
		int numTimesMiss = 0;
		 */
		for(int i=0;i<thisWidth;i++){
			for(int j=0;j<thisHeight;j++){

				thisPixel = grayscaledPixel(newHough.getRGB(i,j));
				
				if(thisPixel<lowerPixel){
					lowerPixel = thisPixel;
				}
				if(thisPixel>higherPixel){
					higherPixel = thisPixel;
				}
				
				if(myPixels[i][j]!=0){
					//numTimesHit++;
					acumulator +=  (10.0 * 255.0 - Math.abs(thisPixel - myPixels[i][j]))/255.0;
						
				}else{
					if(thisPixel!=0){
						//numTimesMiss++;
						acumulator -= (10.0 * myPixels[i][j])/255.0;
						
					}else{
						//numTimesCommom++;
						acumulator += 10.0;
					}
				}
			}
		}
		//System.out.println(lowerPixel+" - "+higherPixel);
		
		return (acumulator/1000000);
	}

	

}
