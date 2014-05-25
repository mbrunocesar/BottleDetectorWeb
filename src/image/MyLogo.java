package image;

import java.awt.image.BufferedImage;

public class MyLogo extends MyImage{

	public MyLogo(String urlImage) {
		super(urlImage);
	}

	public MyLogo(String pathImage, int checkin) {
		super(pathImage, checkin);
	}

	@Override
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
					acumulator +=  (33.0 * 255.0 - Math.abs(thisPixel - myPixels[i][j]))/255.0;
						
				}else{
					if(thisPixel!=0){
						//numTimesMiss++;
						acumulator -= 0 * (33.0 * myPixels[i][j])/255.0;
						
					}else{
						//numTimesCommom++;
						acumulator += 0 * 33.0;
					}
				}
			}
		}
		//System.out.println(lowerPixel+" - "+higherPixel);
		
		return (acumulator/1000000);
	}

	

}
