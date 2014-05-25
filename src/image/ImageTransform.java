package image;

import global.Path;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import borderOperator.CannyEdgeDetector;
import borderOperator.HoughTransformed;
import borderOperator.SobelEdgeDetector;
import borderOperator.HoughTransformed.ArrayData;

public class ImageTransform {

	final String imagePath = Path.thisPath + "images/"; 
	
	BufferedImage image = null;
	String lastStamped = null;

	public ImageTransform(BufferedImage inputImage) {
		image = inputImage;
	}
	
	public ImageTransform(String urlImage) {
		try {
			URL fileImg = new URL(urlImage);  
			image = ImageIO.read(fileImg);
		} catch (IOException e) {
			System.out.println("Couldnt load\n--------------");
		}
	}
	
	public ImageTransform(String urlImage, int checkin) {
		try {
			File fileImg = new File(imagePath+urlImage);  
			image = ImageIO.read(fileImg);
		} catch (IOException e) {
			System.out.println("Couldnt load\n--------------");
		}		
	}

	public boolean hasLoaded(){
		return image != null;
	}
	
	
	public void resize(double maximum){
		int width = image.getWidth();
		int height = image.getHeight();
		
		double xfactor;
		
		if(width>height){
			xfactor = maximum/width;
		}else{
			xfactor = maximum/height;
		}
		
		BufferedImage imageBuffer = new BufferedImage((int)(width*xfactor), (int)(height*xfactor), BufferedImage.TYPE_INT_RGB);  
		Graphics2D gg = (Graphics2D) imageBuffer.getGraphics();
		try {  
			gg.drawImage(image, 0, 0, imageBuffer.getWidth(), imageBuffer.getHeight(), null);  
		} finally {  
			gg.dispose();  
		}
		image = imageBuffer;
		
	}

	public void load(String format){
		image = loadToBuffer(format);
	}
	
	public BufferedImage loadToBuffer(String format){
		BufferedImage buffered = null;
		try{
			String extension = ".png";
			if(format.equals("GIF")){
				extension = ".gif";
			}else if(format.equals("JPG")){
				extension = ".jpg";
			}
			
			File fileImg = new File(imagePath+lastStamped+extension);  
			buffered = ImageIO.read(fileImg);
		} catch (IOException e) {}
		return buffered;
	}
	
	public boolean save(String format){
		return save(image,format);
	}

	public boolean save(BufferedImage newImage, String format){
		boolean result = true;
		lastStamped = new Long(System.currentTimeMillis()).toString();
		try {
			String extension = ".png";
			if(format.equals("GIF")){
				extension = ".gif";
			}else if(format.equals("JPG")){
				extension = ".jpg";
			}else{
				format = "PNG";
			}

			ImageIO.write(newImage, format, new File(imagePath+lastStamped+extension));
		} catch (IOException e) {
			result = false;
		}
		return result;
	}

	public boolean saveWithoutPersist(BufferedImage newImage, String format){
		System.out.println("CALLING THIS!");
		boolean result = true;
		String thisStamped = new Long(System.currentTimeMillis()).toString();
		try {
			String extension = ".png";
			if(format.equals("GIF")){
				extension = ".gif";
			}else if(format.equals("JPG")){
				extension = ".jpg";
			}else{
				format = "PNG";
			}
			
			ImageIO.write(newImage, format, new File(imagePath+thisStamped+"np"+extension));
		} catch (IOException e) {
			result = false;
		}
		return result;
	}
	

	public void reloadAsJPG() {
		save("JPG");
		load("JPG");
	}

	public BufferedImage cloneImage(BufferedImage image) {
		return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
	}	
	
	public BufferedImage getBordersByCanny(){		
		CannyEdgeDetector detector = new CannyEdgeDetector();

		//adjust its parameters as desired
		detector.setLowThreshold(0.8f);
		detector.setHighThreshold(1f);

		//apply it to an image
		detector.setSourceImage(image);
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		
		return edges;
	}
	
	
	public BufferedImage getBordersBySobel(){		
		SobelEdgeDetector detector = new SobelEdgeDetector();
		return detector.sobelEdgeDetection(image);
	}

	public BufferedImage getHoughTransformed(BufferedImage targetImage,int minContrast){
		String newname = new Long(System.currentTimeMillis()).toString() + "hough";
		return getHoughTransformed(targetImage,"",minContrast,newname);
	}

	public BufferedImage getHoughTransformed(BufferedImage targetImage,String targetPath,int minContrast,String newName) {
		BufferedImage output = null;
		try{
			ArrayData inputData = HoughTransformed.getArrayDataFromBuffer(targetImage);
			ArrayData outputData = HoughTransformed.houghTransform(inputData, 600, 480, minContrast);
			
			output = HoughTransformed.writeOutputImage(targetPath+newName+".png", outputData,false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return output;
	}

	public BufferedImage getSegment(BufferedImage baseImage, int minX, int minY, int width, int height) {
		if(minX<0){
			minX=0;
		}
		if(minY<0){
			minY=0;
		}

		if(minX + width>baseImage.getWidth()){
			width = baseImage.getWidth() - minX;
		}
		if(minY + height>baseImage.getHeight()){
			height = baseImage.getHeight() - minY;
		}
		
		return baseImage.getSubimage(minX, minY, width, height);
	}

	public BufferedImage getImage(){
		return image;
	}

	public String getLastStamped(){
		return lastStamped;
	}


	@SuppressWarnings("unused")
	public static void main(String[] args){
		String target[] = new String[5];
		target[0] = "1386476232571";
		target[1] = "1386476238393";
		target[2] = "1386476242097";
		target[3] = "1386476246145";
		target[4] = "1386476250698";
		
		for(String tg:target){
			ImageTransform it = new ImageTransform("WebContent/images/middlers/"+tg+".png", 1);
			
			BufferedImage newHough = null;
			newHough = it.getHoughTransformed(it.getImage(),70);
		}
		
	}

}
