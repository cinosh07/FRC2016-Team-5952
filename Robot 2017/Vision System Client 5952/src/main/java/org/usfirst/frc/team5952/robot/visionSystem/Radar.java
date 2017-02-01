package org.usfirst.frc.team5952.robot.visionSystem;

import java.awt.AlphaComposite;
import java.awt.FlowLayout;
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

public class Radar extends JPanel {

	
	static double angle = 20.0;

    Image robot, camera, offScreenImage;
    Graphics offScreenGraphics;
    
    private String path = "";
    
	public Radar(int width, int height, String path) {

		this.path = path;
		try{
			robot = getLocalImageIcon("robot.png"); 
			camera = getLocalImageIcon("camera.png"); 
			
	        }
	        catch(Exception e){}
	       setSize(width,height);
	       setVisible(true);
	       setOpaque(false);
	}
	
	public void paint(Graphics g){
		   
		   ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.0f)); // draw transparent background
		     super.paintComponent(g);
		    ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f)); // turn on opacity
	    
		    g.drawImage(robot, 0, 0, this);   
		    g.drawImage(rotate(toBufferedImage(camera), angle), 0, 0, null);
		   
	   } 
	
	public static BufferedImage rotate(BufferedImage image, double angle) {
	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	    GraphicsConfiguration gc = getDefaultConfiguration();
	    BufferedImage result = gc.createCompatibleImage(64, 64, Transparency.TRANSLUCENT);
	    Graphics2D g = result.createGraphics();
	    g.rotate(angle, 64 / 2, 64 / 2);
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
	public void initialiseRadar(){
		   for ( double i = 0 ; i < 360 ; i++ ){
	            
			   double degrees = i;
			   double radians = Math.toRadians(degrees);
			   
			   angle =  Math.toDegrees(Math.asin(Math.sin(radians)));
	          
	           repaint();
	            
	           // then sleep for a bit for your animation
	           try { Thread.sleep(10); }   /* this will pause for X milliseconds */
	           catch (InterruptedException e) { System.err.println("sleep exception"); }
	            
	       }
	   }
	   
   public void putAngle(double angleToSet){

		   angle = angleToSet;
           repaint();
       
   }
	
	public Image getLocalImageIcon(String filename) {
		ImageIcon icon = null;
		
		File sourceimage = null;
		
	    Image image = null;
	   
	    
	    
	    
	    	sourceimage = new File(path + filename);
	    	
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
