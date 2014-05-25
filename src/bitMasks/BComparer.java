package bitMasks;

import global.Path;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BComparer {

	final String imagePath = Path.thisPath + "images/"; 
	
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

	public BComparer(String pathImage){
		try {
			File fileImg = new File(imagePath+pathImage);  
			image = ImageIO.read(fileImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setMatrix();
	}

	public BufferedImage setMatrix(){
		BufferedImage buffer = image;

		maskWidth = buffer.getWidth();
		maskHeight = buffer.getHeight();

		bitMask = new int[maskWidth][maskHeight];
		
		for(int i = 0; i < maskWidth; i++){
			for(int j = 0; j < maskHeight; j++){
				int rgb = grayscaledPixel(buffer.getRGB(i, j));
				if(rgb > 128){
					bitMask[i][j] = 0;
					//buffer.setRGB(i, j, white);
				}else{
					bitMask[i][j] = 1;
					//buffer.setRGB(i, j, black);
				}
			}
		}
		
		return buffer;
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
		int higher = -1;

		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int precision = 0;
				
				for(int x=0;x<maskWidth;x++){
					for(int y=0;y<maskHeight;y++){
						if(pixels[i+x][j+y]==bitMask[x][y]){
							if(bitMask[x][y] == 1){
								precision += 20;
							}else{
								precision += 2;
							}
						}else{
							precision -= 5;
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
		
		return results;
	}

}
