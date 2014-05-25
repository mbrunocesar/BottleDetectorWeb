package colorOperator;

import image.ImageTransform;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ColorMapper {

	BufferedImage image;

	public ColorMapper(BufferedImage newImage){
		image = newImage;	
	}

	public ColorMapper(String pathImage){
		try {
			File fileImg = new File(pathImage);  
			image = ImageIO.read(fileImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save(BufferedImage newImage){
		ImageTransform it = new ImageTransform(newImage);
		it.saveWithoutPersist(it.getImage(), "PNG");
	}

	public void loadByFile(String pathImage){
		try {
			File fileImg = new File(pathImage);  
			image = ImageIO.read(fileImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int process(){
		int result = 0;

		int width = image.getWidth();
		int height = image.getHeight();
		int[][] pixels = new int[width][height];

		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				pixels[i][j] = image.getRGB(i, j);
			}
		}

		Color color,colorCompareUp,colorCompareDown;
		int red,blue,green;

		height = image.getHeight()-2;

		for(int i = 0; i < width; i++){
			for(int j = 20; j < height; j++){
				int colorItem = pixels[i][j];
				if(colorItem!=-1){

					// Starts Color Theme 1
					color = new Color(colorItem);
					red = color.getRed();
					blue = color.getBlue();
					green = color.getGreen();
					if((red>200)&&(blue>200) && (green>200)){
						//str.append("\n\npos"+i+","+j+"-"+color+"-"+colorItem+" ");
						for(int x=0;x<60;x+=2){
							if(x>j||j+x/2>height){break;}

							colorCompareUp = new Color(pixels[i][j-x]);
							red = colorCompareUp.getRed();
							blue = colorCompareUp.getBlue();
							green = colorCompareUp.getGreen();
							if(red>220 && (green>200)&&(blue<140)){

								colorCompareDown = new Color(pixels[i][j+x/2]);
								red = colorCompareDown.getRed();
								blue = colorCompareDown.getBlue();
								green = colorCompareDown.getGreen();

								if(red<100 && (green>100)&&(blue<100)){

									//System.out.println(i+" "+j);
									result++;
								}
							}
						}
					}

					// Starts Color Theme 
				}

			}
		}
		return result;
	}

}
