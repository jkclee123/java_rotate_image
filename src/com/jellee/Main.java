package com.jellee;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Input File Name");
            return;
        }
        String fileName = args[0];
        BufferedImage inputImg = ImageIO.read(new File(fileName));
        BufferedImage outputImg = rotateImage(inputImg);
        ImageIO.write(outputImg, "jpg", new File("output.jpg"));
        return;
    }

    private static BufferedImage rotateImage(BufferedImage inputImg) {
        int width = inputImg.getWidth();
        int height = inputImg.getHeight();
        if (height >= width){
            return  inputImg;
        }
        double ratio = height * 1.0 / width;

        double angle = Math.toRadians(-90);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double x0 = 0.5 * (height - 1);     // point to rotate about
        double y0 = 0.5 * ratio * (width - 1);     // center of image

        WritableRaster inRaster = inputImg.getRaster();
        BufferedImage outputImg = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        WritableRaster outRaster = outputImg.getRaster();
        int[] pixel = new int[3];

        // rotation
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                double a = x - x0;
                double b = y - y0;
                int xx = (int) (+a * cos - b * sin + x0);
                int yy = (int) (+a * sin + b * cos + y0);

                if (xx >= 0 && xx < width && yy >= 0 && yy < height) {
                    outRaster.setPixel(x, y, inRaster.getPixel(xx, yy, pixel));
                }
            }
        }
        return outputImg;
    }
}