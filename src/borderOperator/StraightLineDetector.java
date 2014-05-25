package borderOperator;

import image.ImageTransform;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class StraightLineDetector {

	BufferedImage image;
	final int black = Color.BLACK.getRGB();
	final int white = Color.WHITE.getRGB();
	
	int[][] bitMask;
	int maskWidth, maskHeight;

	private int grayscaledPixel(int pixelValue){
		int rgb = pixelValue;
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int b = (rgb & 0xFF);
		return (r + g + b) / 3;
	}

	public StraightLineDetector(BufferedImage targetImage){
		image = cloneImage(targetImage);
		image = setMatrix();
		save();
	}

	public BufferedImage setMatrix(){
		BufferedImage buffer = image;

		maskWidth = buffer.getWidth();
		maskHeight = buffer.getHeight();

		bitMask = new int[maskWidth][maskHeight];
		
		for(int i = 0; i < maskWidth; i++){
			for(int j = 0; j < maskHeight; j++){
				int rgb = grayscaledPixel(buffer.getRGB(i, j));
				if(rgb > 180){
					bitMask[i][j] = 0;
					buffer.setRGB(i, j, white);
				}else{
					bitMask[i][j] = 1;
					buffer.setRGB(i, j, black);
				}
			}	
		}
		
		return buffer;
	}

	public void save(){
		save(image);
	}
	
	public void save(BufferedImage newImage){
		ImageTransform it = new ImageTransform(newImage);
		it.saveWithoutPersist(it.getImage(), "PNG");
	}

	public BufferedImage cloneImage(BufferedImage image) {
		return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
	}	

	public int[] compare(BufferedImage compared){

		int width = compared.getWidth();
		int height = compared.getHeight();
		int[][] pixels = new int[width][height];

		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int rgb = grayscaledPixel(compared.getRGB(i, j));
				if(rgb > 128){
					pixels[i][j] = 0;
				}else{
					pixels[i][j] = 1;
				}
			}
		}
		
		width = width-maskWidth;
		height = height-maskHeight;
		
		int[] results = {-1,-1,-1};
		String highers = "";
		int higher = -1;

		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int precision = 0;
				
				for(int x=0;x<maskWidth;x++){
					for(int y=0;y<maskHeight;y++){
						if(pixels[i+x][j+y]==bitMask[x][y]){
							if(bitMask[x][y] == 1){
								precision += 10;
							}else{
								precision += 1;
							}
						}else{
							precision -= 2;
						}
					}
				}
				if(precision>higher){
					higher = precision;
					results[0] = i;
					results[1] = j;
					results[2] = precision;
				}
			}
		}
		System.out.println(highers);
		
		return results;
	}

	public void detectBasedIn(int[] markings) {
		// TODO Auto-generated method stub
		
	}

}
