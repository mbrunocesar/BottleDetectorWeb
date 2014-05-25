package borderOperator;
import java.awt.image.*;

public class SobelEdgeDetector {
 
    private int[][] sobelx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    private int[][] sobely = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
 
    public int rgb_to_luminance(int rgb) {
        int r = (rgb & 0xff0000) >> 16;
        int g = (rgb & 0xff00) >> 8;
        int b = (rgb & 0xff);
        return (3*r + b + 4*g) >> 3;
    }
 
    public int level_to_greyscale(int level) {
        return (level << 16) | (level << 8) | level;
    }
 
    public BufferedImage cloneImage(BufferedImage image) {
        return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
    }
 
    public BufferedImage sobelEdgeDetection(BufferedImage image) {
        BufferedImage ret = cloneImage(image);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int level = 255;
                if ((x > 0) && (x < (width - 1)) && (y > 0) && (y < (height - 1))) {
                    int sumX = 0;
                    int sumY = 0;
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                        	int tempLuminance = rgb_to_luminance(image.getRGB(x+i, y+j)); 
                            sumX += tempLuminance * sobelx[i+1][j+1];
                            sumY += tempLuminance * sobely[i+1][j+1];
                        }
                    }
                    level = Math.abs(sumX) + Math.abs(sumY);
                    if (level < 0) {
                        level = 0;
                    } else if (level > 255) {
                        level = 255;
                    }
                    level = 255 - level;
                }
                ret.setRGB(x, y, level_to_greyscale(level));
            }
        }
        return ret;
    }
 
}