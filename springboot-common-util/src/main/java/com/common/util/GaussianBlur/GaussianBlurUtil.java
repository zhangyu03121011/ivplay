package com.common.util.GaussianBlur;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.common.util.GaussianBlur.filter.GaussianFilter;
public class GaussianBlurUtil {
    public GaussianBlurUtil(){}

    public static void setGaussianBlurImg(String imgPath, int filterNum) {
    	try{
            File file = new File(imgPath);
            BufferedImage b1 = ImageIO.read(file);
            GaussianFilter filter = new GaussianFilter(filterNum);
            BufferedImage blurredImage = filter.filter(b1, new BufferedImage(b1.getWidth(), b1.getHeight(), 2));
            ImageIO.write(blurredImage, "png", file);
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN))
                Desktop.getDesktop().open(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("static-access")
	public static void main(String[] args) {
        String imgPath = "target\\IMG_4214.PNG";
        int filterNum = 20;
        setGaussianBlurImg(imgPath, filterNum);
	}
}
