package org.usfirst.frc.team5952.robot.visionSystem;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Compass extends JPanel {

	
	static double angle = 20.0;

    Image compass, compassNeedle, offScreenImage;
    Graphics offScreenGraphics;
    
    private String debugPath = "c:\\temp\\";
    
	public Compass(int width, int height) {

		try{
			compass = getLocalImageIcon("compass.png"); 
			compassNeedle = getLocalImageIcon("compass_needle.png"); 
			
	        }
	        catch(Exception e){}
	       setSize(width,height);
	       setVisible(true);
	       setOpaque(false);
	      // moveImage();
	}
	
	public void paint(Graphics g){
		   
		   ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.0f)); // draw transparent background
		     super.paintComponent(g);
		    ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f)); // turn on opacity
	    
		    g.drawImage(compass, 0, 0, this);   
		    g.drawImage(rotate(toBufferedImage(compassNeedle), angle), 0, 0, null);
		   
	   } 
	
	public static BufferedImage rotate(BufferedImage image, double angle) {
	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	    int w = image.getWidth(), h = image.getHeight();
	    int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
	    GraphicsConfiguration gc = getDefaultConfiguration();
	    BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
	    Graphics2D g = result.createGraphics();
	    g.translate((neww - w) / 2, (newh - h) / 2);
	    g.rotate(angle, w / 2, h / 2);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}
	private static GraphicsConfiguration getDefaultConfiguration() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    return gd.getDefaultConfiguration();
	}
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	public void initialiseCompass(){
		   for ( int i = 0 ; i < 360 ; i++ ){
	           
	           //System.out.println("next set of Pixels " + xPixel);
	            
			   angle = ((double)i);
	          
	           repaint();
	            
	           // then sleep for a bit for your animation
	           try { Thread.sleep(20); }   /* this will pause for 50 milliseconds */
	           catch (InterruptedException e) { System.err.println("sleep exception"); }
	            
	       }
	   }
	   
   public void setAngle(double angleToSet){

		   angle = angleToSet;
           repaint();
       
   }
	
	public Image getLocalImageIcon(String filename) {
		ImageIcon icon = null;
		
		File sourceimage = null;
		
	    Image image = null;
	   
	    
	    
	    
	    	sourceimage = new File(debugPath + filename);
	    	
	    	try {
				image = ImageIO.read(sourceimage);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	icon = new ImageIcon(getScaledImage(image, 64, 64));
	     
		
		return image;
	}
   
   
   private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

}
