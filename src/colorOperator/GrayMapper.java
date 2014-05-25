package colorOperator;

import image.ImageTransform;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrayMapper {

	BufferedImage image;

	private int grayscaledPixel(int pixelValue){
		int rgb = pixelValue;
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int b = (rgb & 0xFF);
		return (r + g + b) / 3;
	}

	public BufferedImage setGrayscale(BufferedImage targetImage){
		BufferedImage buffer = cloneImage(targetImage);

		int width = buffer.getWidth();
		int height = buffer.getHeight();

		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int rgb = grayscaledPixel(buffer.getRGB(i, j));
				int r = (rgb << 16);
				int g = (rgb << 8);
				int b = (rgb);
				rgb = (r + g + b);

				buffer.setRGB(i, j, rgb);
			}	
		}
		return buffer;
	}

	public GrayMapper(BufferedImage newImage){
		image = cloneImage(newImage);
		image = setGrayscale(image);
	}

	public GrayMapper(String pathImage){
		try {
			File fileImg = new File(pathImage);  
			image = ImageIO.read(fileImg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = setGrayscale(image);
	}

	public BufferedImage cloneImage(BufferedImage image) {
		return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
	}	

	public void save(){
		save(image);
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

	public int[] process(){
		save(image);
		int[] results = {-1, -1, 0};

		int width = image.getWidth();
		int height = image.getHeight();
		int[][] pixels = new int[width][height];

		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int grayscale = (image.getRGB(i, j) >> 16) & 0xFF;
				if(grayscale>128){
					pixels[i][j] = 1;
				}else{
					pixels[i][j] = 0;
				}
			}
		}

		int red;
		int precision_level = 0;

		int i = 30;
		int j = 20;

		for(i = 30; i < width-30; i++){
			for(j = 20; j < height-20; j++){
				red = pixels[i][j];
				// Starts Color Theme 1
				int sum = 0;
				int geral_sum = 0;
				if(red>200){
					//str.append("\n\npos"+i+","+j+"-"+color+"-"+colorItem+" ");
					for(int y=0;y<20;y+=1){
						sum = 0;
						for(int x=0;x<30;x+=1){
							if(x+i>=width||y+j>=height){break;}

							red = pixels[x+i][y+j];

							sum += red;
						}
						
						geral_sum += sum;

						if(y<10){
							if(sum < 6000 || sum > 8000){ break; }
							if(y==3){
								if(geral_sum < 21000 || geral_sum > 33000){ break; }
							}
							if(y==9){
								if(geral_sum < 60000 || geral_sum > 72000){ break; }
							}
						}
						if(y==12){
							if(geral_sum < 78000 || geral_sum > 88000){ break; }
						}
						if(y>15){
							if(sum > 5500){ break; }
						}

						//System.out.println("general sum "+y+" - "+sum+" - "+geral_sum);
					}
					int processed_sum = geral_sum/20;
					//System.out.println("final sum "+processed_sum/20);

					if(processed_sum > 5300 && processed_sum < 5700){
						if(precision_level > 0){
							if(processed_sum > 5420 && processed_sum < 5580){
								if(precision_level > 1){
									if(processed_sum > 5480 && processed_sum < 5520){
										if(precision_level > 2){
											if(processed_sum > 5492 && processed_sum < 5508){
												int[] positions = {i, j, processed_sum};
												results = positions;
												precision_level = 4;
												//System.out.println(results[0]+" "+results[1]+" - "+results[2]+ " - p"+precision_level);
											}
										}else{
											int[] positions = {i, j, processed_sum};
											results = positions;
											precision_level = 3;
											//System.out.println(results[0]+" "+results[1]+" - "+results[2]+ " - p"+precision_level);
										}
									}
								}else{
									int[] positions = {i, j, processed_sum};
									results = positions;
									precision_level = 2;
								}
							}
						}else{
							int[] positions = {i, j, processed_sum};
							results = positions;
							precision_level = 1;
						}

					}

					// Starts Color Theme 
				}
			}
		}

		System.out.println(results[0]+" "+results[1]+" - "+results[2]+ " - p"+precision_level);

		return results;
	}

}
