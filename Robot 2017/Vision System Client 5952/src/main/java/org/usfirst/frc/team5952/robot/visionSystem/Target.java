package org.usfirst.frc.team5952.robot.visionSystem;

import java.io.File;
import java.io.IOException;
import java.awt.*;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Target extends JPanel {
	
	public static final  String UNLOCK_STATE = "UNLOCK_STATE";
	public static final  String CAM_LOCK_STATE = "CAM_LOCK_STATE";
	public static final  String ROBOT_LOCK_STATE = "ROBOT_LOCK_STATE";
	
	private static String state = "UNLOCK_STATE";
	
	static int xPixel = 20;
    static int yPixel = 20; 
    Image targetRed, targetYellow, targetGreen, offScreenImage;
    Graphics offScreenGraphics;
	
	private String debugPath = "c:\\temp\\";
	
	
	public Target(int width, int height) {
		
		try{
			targetRed = getLocalImageIcon("target_rouge.png"); 
			targetYellow = getLocalImageIcon("target_jaune.png"); 
			targetGreen = getLocalImageIcon("target_verte.png"); 
	        }
	        catch(Exception e){}
	       setSize(width,height);
	       setVisible(true);
	       setOpaque(false);
	      // moveImage();
	}
	
	public Target(String targetText, boolean useBgImage, boolean allowAnimate, ImageIcon icon) {
		
		try{
	          //myImage = getLocalImageIcon("target_rouge.png"); 
	        }
	        catch(Exception e){}
	       setSize(200,200);
	       setVisible(true);
	       setOpaque(false);
	       //moveImage();
	}


   public void paint(Graphics g){
	   
	   ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.0f)); // draw transparent background
	     super.paintComponent(g);
	    ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f)); // turn on opacity
	    
	    
	    
	    
	    switch (state) {
        case UNLOCK_STATE:  g.drawImage(targetRed, xPixel, yPixel, this);
                 break;
        case CAM_LOCK_STATE:  g.drawImage(targetYellow, xPixel, yPixel, this);
                 break;
        case ROBOT_LOCK_STATE:  g.drawImage(targetGreen, xPixel, yPixel, this);
                 break;
        default: g.drawImage(targetRed, xPixel, yPixel, this);
                 break;
	    }

   
	   
   } 
   

   
   public void initialiseTarget(){
	   for ( int i = 0 ; i < 300 ; i++ ){
           
           //System.out.println("next set of Pixels " + xPixel);
            
		   if (i > 50 && i < 100) {
			   setState(CAM_LOCK_STATE);
		   }
		   if (i > 100 && i < 150) {
			   setState(ROBOT_LOCK_STATE);
		   }
		   if (i > 150 && i < 175) {
			   setState(UNLOCK_STATE);
		   }
           
          
           if (yPixel < 175) {
        	   
        	   
        	   yPixel +=1;
           } else {
        	   
        	   yPixel= 175;
           }
           if (xPixel < 250) {
        	   
        	   
        	   xPixel +=1;
           } else {
        	   
        	   xPixel= 250;
           }
           
          
           repaint();
            
           // then sleep for a bit for your animation
           try { Thread.sleep(20); }   /* this will pause for 50 milliseconds */
           catch (InterruptedException e) { System.err.println("sleep exception"); }
            
       }
   }
   
   public void moveTarget(int x, int y){
	   
           
           //System.out.println("next set of Pixels " + xPixel);
                        
		   xPixel =x;
           yPixel =y;
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



	public static String getState() {
		return state;
	}

	public static void setState(String state) {
		Target.state = state;
	}

	
}
