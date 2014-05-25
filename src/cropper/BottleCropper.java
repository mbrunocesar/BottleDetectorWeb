package cropper;

import image.ImageTransform;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class BottleCropper {

	BufferedImage conjunction;
	final int red = Color.RED.getRGB();
	final int white = Color.WHITE.getRGB();

	int[][] pixelsCanny,pixelsSobel;

	private int grayscaledPixel(int pixelValue){
		int rgb = pixelValue;
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int b = (rgb & 0xFF);
		return (r + g + b) / 3;
	}
	
	public BottleCropper(BufferedImage image, BufferedImage canny, BufferedImage sobel){ 
	
		conjunction = cloneImage(image);
		
		int thisWidth = conjunction.getWidth();
		int thisHeight = conjunction.getHeight();

		pixelsCanny = new int[thisWidth][thisHeight];
		pixelsSobel = new int[thisWidth][thisHeight];
		
		int thisSobel,thisCanny;
		
		for(int i=0;i<thisWidth;i++){
			for(int j=0;j<thisHeight;j++){
				thisCanny = grayscaledPixel(canny.getRGB(i, j));
				thisSobel = grayscaledPixel(sobel.getRGB(i, j));

				if(thisCanny>128){
					thisCanny = 0;
				}else{
					thisCanny = 255;
				}
				if(thisSobel>128){
					thisSobel = 0;
				}else{
					thisSobel = 255;
				}
				
				pixelsCanny[i][j] = thisCanny;
				pixelsSobel[i][j] = thisSobel;
			}
		}
		

		for(int i=2;i<thisWidth-2;i++){
			for(int j=2;j<thisHeight-2;j++){
				if(pixelsCanny[i][j]==0 && pixelsSobel[i][j]==0){
					
					if(getConjunctions(i, j)>=3){
						setConjunctions(i, j);
					}
					
				}
			}
		}
	}
	
	public int getConjunctions(int i, int j){
		int result = 0;
		// BUFFERIZE
		if(pixelsCanny[i][j]==pixelsSobel[i][j]){result++;}
		if(pixelsCanny[i][j]==pixelsSobel[i][j]){result++;}
		if(pixelsCanny[i][j]==pixelsSobel[i][j]){result++;}

		if(pixelsCanny[i][j]==pixelsSobel[i][j]){result++;}
		if(pixelsCanny[i][j]==pixelsSobel[i][j]){result++;}
		if(pixelsCanny[i][j]==pixelsSobel[i][j]){result++;}

		if(pixelsCanny[i][j]==pixelsSobel[i][j]){result++;}
		if(pixelsCanny[i][j]==pixelsSobel[i][j]){result++;}
		if(pixelsCanny[i][j]==pixelsSobel[i][j]){result++;}
		
		return result;
	}

	public void setConjunctions(int i, int j){
		// BUFFERIZE
		conjunction.setRGB(i-1, j-1, red);
		conjunction.setRGB(i-1, j, red);
		conjunction.setRGB(i-1, j+1, red);
		
		conjunction.setRGB(i, j-1, red);
		conjunction.setRGB(i, j, red);
		conjunction.setRGB(i, j+1, red);

		conjunction.setRGB(i+1, j-1, red);
		conjunction.setRGB(i+1, j, red);
		conjunction.setRGB(i+1, j+1, red);
	}

	public void suavizate(int i, int j){
		// BUFFERIZE
		conjunction.setRGB(i-1, j-1, conjunction.getRGB(i-2, j-2));
		conjunction.setRGB(i-1, j, conjunction.getRGB(i-2, j));
		conjunction.setRGB(i-1, j+1, conjunction.getRGB(i-2, j+2));
		
		conjunction.setRGB(i, j-1, conjunction.getRGB(i, j-2));
		conjunction.setRGB(i, j, conjunction.getRGB(i+2, j));
		conjunction.setRGB(i, j+1, conjunction.getRGB(i, j+2));

		conjunction.setRGB(i+1, j-1, conjunction.getRGB(i+2, j-2));
		conjunction.setRGB(i+1, j, conjunction.getRGB(i+2, j));
		conjunction.setRGB(i+1, j+1, conjunction.getRGB(i+2, j+2));
	}
	
	public BufferedImage cloneImage(BufferedImage image) {
        return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
    }	
	
	public void save(){
		ImageTransform it = new ImageTransform(conjunction);
		it.saveWithoutPersist(it.getImage(), "PNG");
	}
	
	
}
