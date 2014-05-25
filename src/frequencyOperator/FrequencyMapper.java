package frequencyOperator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FrequencyMapper {


	BufferedImage image;
	int[][] pixels;

	int baseX = 6;
	int baseY = 7;

	int squaredX = 36;
	int squaredY = 49;
	private boolean debug;

	public FrequencyMapper(BufferedImage newImage){
		image = newImage;	
	}

	public FrequencyMapper(String pathImage){
		try {
			File fileImg = new File(pathImage);  
			image = ImageIO.read(fileImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadByFile(String pathImage){
		try {
			File fileImg = new File(pathImage);  
			image = ImageIO.read(fileImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean save(BufferedImage newImage){
		boolean result = true;
		String lastStamped = new Long(System.currentTimeMillis()).toString();
		try {
			ImageIO.write(newImage, "PNG", new File("images/freq"+lastStamped+".png"));
		} catch (IOException e) {
			result = false;
		}
		return result;
	}


	private int grayscaledPixel(int pixelValue){
		int rgb = pixelValue;
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int b = (rgb & 0xFF);
		return (r + g + b) / 3;
	}

	public int level_to_greyscale(int level) {
		return (level << 16) | (level << 8) | level;
	}

	public int process(){
		return process(false);
	}
	
	public int process(boolean search){
		debug = search;
		
		int width = image.getWidth();
		int height = image.getHeight();
		pixels = new int[width][height];

		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int gray = grayscaledPixel(image.getRGB(i, j));

				if(gray<120){
					pixels[i][j] = 1;
					//image.setRGB(i, j, level_to_greyscale(0));
				}else{
					pixels[i][j] = 0;
					//image.setRGB(i, j, level_to_greyscale(255));
				}
			}
		}

		height = image.getHeight()-2;

		double product = 0.0;

		int limX = width-squaredY;
		int limY = height-squaredX;

		if(!debug){
			for(int i = 0; i < limX; i++){
				for(int j = 0; j < limY; j++){

					product = frequenciaAcumulada(i,j);
					if(product>0){
						//System.out.println("found in "+i+","+j+": "+product);
						//break;
					}

				}
				if(product>0){
					break;
				}
			}
		}

		return (int)product;
	}

	public double frequenciaAcumulada(int i, int j){
		double acumulated = 0.0;
		double line = 0.0;

		int limX = i+squaredY;
		int limY = j+squaredX;

		int counter = 0;

		for(int y=j; y < limY; y++){
			counter++;
			//int numBlack = 0;
			int numWhite = 0;
			for(int x=i; x < limX; x++){
				if(pixels[x][y]==0){
					//numBlack++;
					if(debug)System.out.print("_");
				}else{
					numWhite++;
					if(debug)System.out.print("X");
				}
			}
			line = (double) (numWhite);
			acumulated += line;
			if(debug)System.out.println(counter+"-"+acumulated);

			if(counter==2){
				if(acumulated<25){
					acumulated = -1;
					break;
				}
			}else if(counter==9){
				if(acumulated<70 || acumulated>110){
					acumulated = -1;
					break;
				}
			}else if(counter==18){
				if(acumulated<170 || acumulated>220){
					acumulated = -1;
					break;
				}
			}else if(counter==27){
				if(acumulated<240 || acumulated>380){
					acumulated = -1;
					break;
				}
			}
		}

		if(acumulated< 370.0 || acumulated>400.0){
			acumulated = -1;
		}else{
			acumulated = 0;
			counter = 0;
			for(int x=i; x < limX; x++){
				counter++;
				//int numBlack = 0;
				int numWhite = 0;
				for(int y=j; y < limY; y++){
					if(pixels[x][y]==0){
						//numBlack++;
						//if(debug)System.out.print("_");
					}else{
						numWhite++;
						//if(debug)System.out.print("X");
					}
				}
				line = (double) (numWhite);
				
				
				acumulated += line;
				//if(debug)System.out.println(acumulated);

				if(debug)System.out.println(counter+"-"+acumulated);

				if(counter==2){
					if(acumulated>10){
						acumulated = -1;
						break;
					}
				}else if(counter==9){
					if(acumulated<30 || acumulated>42){
						acumulated = -1;
						break;
					}
				}else if(counter==18){
					if(acumulated<55 || acumulated>80){
						acumulated = -1;
						break;
					}
				}else if(counter==27){
					if(acumulated<140 || acumulated>180){
						acumulated = -1;
						break;
					}
				}
			}
		}

		return acumulated;
	}

	public static void main(String[] args){
		FrequencyMapper fm = new FrequencyMapper("images/1381465239559.jpg");
		//FrequencyMapper fm = new FrequencyMapper("images/traditional-targets/1381113153212.jpg");
		//FrequencyMapper fm = new FrequencyMapper("images/traditional-targets/1381113146956.jpg");

		//FrequencyMapper fm = new FrequencyMapper("images/traditional-targets/1381113187165.jpg");
		fm.process(false);

		//System.out.println(fm.frequenciaAcumulada(208,236));
	}

}
