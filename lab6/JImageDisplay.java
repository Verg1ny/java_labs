package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {
    /**Instance of Jcomponent class
     * Handles the image the code generates**/
    private BufferedImage image;

    public BufferedImage getImage(){
        return image;
    }

    public JImageDisplay (int width, int height){
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        /*Initializing an instance of and calling parent class method*/
        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);
    }

    /**Code block dedicated to draw the image**/
    public void paintComponent(Graphics g){
        /*Calling parent class method in order to make the image be drawn correctly*/
        super.paintComponent(g);
        g.drawImage (image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    /**Method dedicated to clear the image (sets all pixels color to black)**/
    public void clearImage(){
        int size = getWidth() * getHeight();
        int[] pixels = new int[size];
        image.setRGB(0, 0, getWidth(), getHeight(), pixels, 0, 1);
    }

    /**Sets a pixel to a custom color**/
    public void drawPixel(int x, int y, int color){
        image.setRGB(x, y, color);
    }
}
