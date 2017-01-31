package org.usfirst.frc.team5952.robot.visionSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5952.robot.commands.ClientVisionCommunication;
import org.usfirst.frc.team5952.robot.commands.VisionCommunication;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class StreamManager {

	//TODO Pour Tester sur l'ordi mettre a true sinon a false
	public boolean debug = false;

	private String camera1IP = "";
	private String camera2IP = "";
	
	public String title = "";
	
	// ***********************************************************************
	
	private static StreamManager instance = null;
	private int teamnumber = 5952;
	private int inputstreamport = 1185;
	private Boolean multicam = false;
	public Boolean fullscreen = false;
	private NetworkTable table = null;
	private JFrame playerWindow = null;
	private JLabel videoPlayer = null;
	private ImageIcon imageVideo = null;
	private ImageIcon defaultImageVideo = null;
	private ImageIcon backgroundClean = null;

	private Target targetPanel;
	private JLabel messageBox;
	private JLabel sight;
	
	private String message = "";
	
	private boolean cleanVideo = true;
	public OSD osd = null;
	private int buttonBar2ButtonWidth = 100;
	private int buttonBar2ButtonHeight = 25;
	
	public ClientVisionCommunication visionCommunication; 

	public static String localPath = "";
	
	private int videoContainerMaxHeight = 423;
	
	
	protected StreamManager(String path) {
		
		localPath = path;
	      // Exists only to defeat instantiation.
	   }

	public static StreamManager getInstance() {
		if (instance == null) {
			instance = new StreamManager(localPath);
		}
		return instance;
	}
	public static StreamManager getInstance(String path) {
		if (instance == null) {
			instance = new StreamManager(path);
		}
		return instance;
	}
	public void visibleOSD(ActionEvent e) {
		
	}
	
	
	public void init() {
		
		//Definir les images et les icones
    	backgroundClean = getLocalImageIcon("back_clean.jpg");
    	defaultImageVideo = getLocalImageIcon("back.jpg");
	    //Construction du GUI
	    GridBagConstraints c = new GridBagConstraints();
	    playerWindow = new JFrame("");
	    playerWindow.setSize(800, 480);
	  	playerWindow.getContentPane().setLayout(new GridBagLayout()); 	
	  	playerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	if (fullscreen) {
	  		playerWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
	  	}
	  	playerWindow.setUndecorated(true);
	  	playerWindow.getContentPane().setBackground(Color.black);
	  	playerWindow.setTitle(title);

	  	//*********************************************************************************************
  		//
  		//          Barre de Bouton bas
  		//
  		//*********************************************************************************************
	     
	    //*********************************************************
	    
	    JPanel buttonBar = new JPanel(new FlowLayout());
	    JButton OSDButton = new JButton("OSD");
	    OSDButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    
	    OSDButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    	   if (osd.isVisible()) {
	    		   osd.setVisible(false);
	    	   } else {
	    		   osd.setVisible(true);
	    	   }
	    	   if (sight.isVisible()) {
	    		   
	    		   sight.setVisible(false);
	    	   } else {
	    		   
	    		   sight.setVisible(true);
	    		   
	    	   }
	    	   if (targetPanel.isVisible()) {
	    		   
	    		   targetPanel.setVisible(false);
	    	   } else {
	    		   
	    		   targetPanel.setVisible(true);
	    		   
	    	   }
	    	   
	    	  }
	    	});
	    buttonBar.add(OSDButton);    
	    
	    JButton sightButton = new JButton("Sight");
	    sightButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    sightButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    	   if (sight.isVisible()) {
	    		   
	    		   sight.setVisible(false);
	    	   } else {
	    		   
	    		   sight.setVisible(true);
	    		   
	    	   }
	    	  }
	    	});
	    buttonBar.add(sightButton);
	    
	    JButton targetButton = new JButton("Target");
	    targetButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    targetButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    	   if (targetPanel.isVisible()) {
	    		   
	    		   targetPanel.setVisible(false);
	    	   } else {
	    		   
	    		   targetPanel.setVisible(true);
	    		   
	    	   }
	    	  }
	    	});
	    buttonBar.add(targetButton);
	    
	    JButton robotCompassButton = new JButton("Robot Compass");
	    robotCompassButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    robotCompassButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    	   if (osd.robotCompassLabel.isVisible()) {
	    		   
	    		   osd.robotCompassLabel.setVisible(false);
	    	   } else {
	    		   
	    		   osd.robotCompassLabel.setVisible(true);
	    		   
	    	   }
	    	  }
	    	});
	    buttonBar.add(robotCompassButton);
	    
	    JButton radarCompassButton = new JButton("Radar Compass");
	    radarCompassButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    radarCompassButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    	   if (osd.radarCompassLabel.isVisible()) {
	    		   
	    		   osd.radarCompassLabel.setVisible(false);
	    	   } else {
	    		   
	    		   osd.radarCompassLabel.setVisible(true);
	    		   
	    	   }
	    	  }
	    	});
	    buttonBar.add(radarCompassButton);	    
	    
	    JButton cleanFeedtButton = new JButton("Clean Feed");
	    cleanFeedtButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    cleanFeedtButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
	    	   // TODO envoyer commande sur la network table cleanfeed or not
	    	  }
	    	});
	    buttonBar.add(cleanFeedtButton);
	    
	    JButton mapButton = new JButton("Map");
	    mapButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    mapButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
//	    	   if (mapPanel.isVisible()) {
//	    		   
//	    		   mapPanel.setVisible(false);
//	    	   } else {
//	    		   
//	    		   mapPanel.setVisible(true);
//	    		   
//	    	   }
	    	  }
	    	});
	    buttonBar.add(mapButton);
	    
	    JButton logButton = new JButton("Log");
	    logButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    logButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e)
	    	  {
//	    	   if (logPanel.isVisible()) {
//	    		   
//	    		   logPanel.setVisible(false);
//	    	   } else {
//	    		   
//	    		   logPanel.setVisible(true);
//	    		   
//	    	   }
	    	  }
	    	});
	    buttonBar.add(logButton);
	    
	   //***********************************************
 
	    
	    //*********************************************************************************************
  		//
  		//         Videoplayer
  		//
  		//*********************************************************************************************
	    
	    //*********************************************************
	    //TODO finaliser et synchroniser l'OSD du Video Player qui affiche les datas du robot avec la Network Tables
	    JPanel videoContainer = new JPanel(new BorderLayout());
	    
	    videoContainer.setSize(videoWidth(videoContainerMaxHeight), videoContainerMaxHeight );
	    videoContainer.setOpaque(false);
	    
	    //*********************************************************************************************
  		//
  		//          Videoplayer - OSD - Text
  		//
  		//*********************************************************************************************
	    osd = new OSD(localPath);
	    osd.setScreenSize(videoWidth(videoContainerMaxHeight), videoContainerMaxHeight);	    
//	    TODO
//	    osd.setRadarCompass_MOVABLE_ICON(radarCompass_MOVABLE_ICON);
//	    osd.setRadarCompass_MOVABLE_ICON(radarCompass_MOVABLE_ICON);
//	    osd.setRobotCompass_MOVABLE_ICON(robotCompass_MOVABLE_ICON);
	        
	    osd.setSize(videoWidth(videoContainerMaxHeight), videoContainerMaxHeight);
	    osd.setForeground(Color.white);
	    osd.setOpaque(false);
	    videoContainer.add(osd,BorderLayout.CENTER);
	    
	    //*********************************************************************************************
  		//
  		//          Videoplayer - OSD - TARGET
  		//
  		//*********************************************************************************************
	    
	    targetPanel = new Target(videoWidth(videoContainerMaxHeight), videoContainerMaxHeight, localPath);
  		targetPanel.setOpaque(false);
  		videoContainer.add(targetPanel,BorderLayout.CENTER);
	    
	    //*********************************************************************************************
  		//
  		//          Videoplayer - OSD - SIGHT
  		//
	  	//*********************************************************************************************
	    sight = new JLabel();
		sight.setIcon(StreamManager.getInstance().getLocalImageIcon("mire_blanche.png"));
		sight.setSize(videoWidth(videoContainerMaxHeight), videoContainerMaxHeight);
		videoContainer.add(sight,BorderLayout.CENTER);
	    
		
		//*********************************************************************************************
		//
		//          Videoplayer - Video Image
		//
		//*********************************************************************************************
	    videoPlayer = new JLabel(" ", imageVideo, JLabel.CENTER);
	    videoPlayer.setSize(videoWidth(videoContainerMaxHeight), videoContainerMaxHeight);
	    videoPlayer.setForeground(Color.white);
	    
	    switchVidpanelBorderColor(Color.RED);
	    
	    videoContainer.add(videoPlayer,BorderLayout.CENTER);
	    //*********************************************************************************************
  		//
  		//          Videoplayer - Background Image
  		//
  		//*********************************************************************************************
	    JLabel backgroundImage = new JLabel(" ", defaultImageVideo, JLabel.CENTER);
	   // JLabel backgroundImage = new JLabel(" ", sight_ICON, JLabel.CENTER);
	    videoContainer.add(backgroundImage,BorderLayout.CENTER);
	    
	   
	    JPanel buttonBar2 = new JPanel(new BorderLayout());
	    
	    //***********************************************
	    
	   
	    //inserer le video container dans le layout du gui
	    
	    //Ligne 1 colonne 1
	    
	    c.gridx = 0;
	    c.gridy = 0;

	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.fill = GridBagConstraints.NONE;
	    c.anchor = GridBagConstraints.LINE_START;
	    c.weighty = 1.0;   
	    c.insets = new Insets(5,5,0,0);  //padding
	    playerWindow.getContentPane().add(videoContainer,c);
	    
	    //Ligne 1 colonne 2 
	    
	    //*********************************************************************************************
  		//
  		//          Barre de Bouton droite
  		//
  		//*********************************************************************************************
	    c.gridx = 1; 
	    c.gridy = 0; 
	    c.gridwidth = GridBagConstraints.REMAINDER; 
	    c.gridheight = 1; 
	
	    c.fill = GridBagConstraints.HORIZONTAL;

	    c.anchor = GridBagConstraints.BASELINE;
	    c.insets = new Insets(5,0,0,5);  
	    
	    buttonBar2.setSize(playerWindow.getWidth() - videoPlayer.getWidth() - 20 ,videoContainerMaxHeight);
	    buttonBar2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
	    JLabel buttonBar2Label = new JLabel("");
	    buttonBar2.setOpaque(false);
	    
	    buttonBar2Label.setIcon(new ImageIcon(getScaledImage(backgroundClean.getImage(),playerWindow.getWidth() - videoPlayer.getWidth() - 20 ,videoContainerMaxHeight)));
	    buttonBar2Label.setSize(playerWindow.getWidth() - videoPlayer.getWidth() - 20 ,videoContainerMaxHeight);
	    
	    JPanel buttonBar2Panel = new JPanel();
	    buttonBar2Panel.setSize(playerWindow.getWidth() - videoPlayer.getWidth() - 20 ,videoContainerMaxHeight);
	    buttonBar2Panel.setOpaque(false);
	    buttonBar2Panel.setLayout(new BoxLayout(buttonBar2Panel, BoxLayout.Y_AXIS));  
	    buttonBar2Panel.setLayout(new GridLayout(0,1,1,5)); 
     
	  //*********************************************************************************************
  		//
  		//         Barre de Bouton droite - Message Center
  		//
  		//********************************************************************************************* 

	    messageBox = new JLabel("", JLabel.CENTER);
	    messageBox.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
	    Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(localPath + "DIGITALDREAM.TTF"));
		} catch (FontFormatException e1) {
			System.out.println("System Font Not Found !!!!!!" );
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("System Font Not Found !!!!!!" );
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		genv.registerFont(font);
		// makesure to derive the size
		font = font.deriveFont(10f);
        messageBox.setFont(font.deriveFont(Font.BOLD, 10f));
	    messageBox.setForeground(Color.GREEN);
	    messageBox.setText("<html>Initialization ...</html>");
	    messageBox.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(messageBox);	
	    
	  //*********************************************************************************************
  
	    JButton chooseTargetButton = new JButton("Choose Target");
	    chooseTargetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(chooseTargetButton);	  
	    
	    JButton lockToTargetButton = new JButton("Radar - Target Lock");
	    lockToTargetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(lockToTargetButton);
    
	    JButton cameraLockToTargetButton = new JButton("Cam - Target Lock");
	    cameraLockToTargetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(cameraLockToTargetButton);
    
	    JButton goToTargetButton = new JButton("Robot ==> Target");
	    goToTargetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(goToTargetButton);
    
	    JButton goToMaptButton = new JButton("Robot ==> Map Position");
	    goToMaptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(goToMaptButton);
    
	    JButton cameraSwitchButton = new JButton("Cam - SWITCH");
	    cameraSwitchButton.addActionListener(new ActionListener()
	    {
	    	  public void actionPerformed(ActionEvent e)
	    	  {    		  
	    		 if( visionCommunication.getCurrentCamera() == 1) {
	    			 
	    			 visionCommunication.putCurrentCamera(2);
	    			 visionCommunication.switchCamera(2);
	    			 
	    		 } else if (visionCommunication.getCurrentCamera() == 2 ) {
	    			 
	    			 visionCommunication.putCurrentCamera(1);
	    			 visionCommunication.switchCamera(1);
	    		 }    		  
	    	  }
	    	});
	    cameraSwitchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(cameraSwitchButton);
    
	    JButton cameraJoystickButton = new JButton("Cam - Joystick CTRL");
	    cameraJoystickButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(cameraJoystickButton);
	    
	    JButton pidControlsButton = new JButton("Nav - PID Controls");
	    pidControlsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(pidControlsButton);
    
	    buttonBar2Panel.setBorder(new EmptyBorder(0, 5, 0, 5));
	    buttonBar2.add(buttonBar2Panel,BorderLayout.CENTER);   
	    buttonBar2.add(buttonBar2Label,BorderLayout.CENTER);
    
	    playerWindow.getContentPane().add(buttonBar2,c);
	    
 
	    //Ligne 2 colonne 1
	    
	    c.gridy = 1; // Deuxieme ligne
	    c.gridx = 0; // Premiere colonne
	    c.gridwidth = GridBagConstraints.REMAINDER;;   //2 columns wide
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 0;       //reset to default
	    c.weighty = 1.0;   //request any extra vertical space
	    c.anchor = GridBagConstraints.CENTER; //bottom of space
	    c.insets = new Insets(5,5,5,5);  //top padding
	    buttonBar.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
	    buttonBar.setOpaque(false);
	    buttonBar.setSize(playerWindow.getWidth() - 5 ,playerWindow.getHeight() - videoPlayer.getHeight());
	    playerWindow.getContentPane().add(buttonBar,c);
	    playerWindow.setVisible(true);
  
		
	}
	
	private void sendMessage(String message) {
		if (messageBox != null) {
			messageBox.setText("<html>" + message +"</html>");
		}
		
		
	}
	public ImageIcon getLocalImageIcon(String filename, int width, int height) {
		ImageIcon icon = null;
		
		File sourceimage = null;
		
	    Image image = null;
	   
	    
	    
	    if (debug) {
	    	sourceimage = new File(localPath + filename);
	    	
	    	try {
				image = ImageIO.read(sourceimage);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	icon = new ImageIcon(getScaledImage(image, width, height));
	    } else {
	    	sourceimage = new File(localPath + filename);
	    	
	    	try {
				image = ImageIO.read(sourceimage);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	icon = new ImageIcon(getScaledImage(image, width, height));
	    }
		
		return icon;
	}
	public ImageIcon getLocalImageIcon(String filename) {
		ImageIcon icon = null;
		
		File sourceimage = null;
		
	    Image image = null;
	   
	    
	    
	    if (debug) {
	    	sourceimage = new File(localPath + filename);
	    	
	    	try {
				image = ImageIO.read(sourceimage);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	icon = new ImageIcon(getScaledImage(image, videoWidth(videoContainerMaxHeight), videoContainerMaxHeight));
	    } else {
	    	sourceimage = new File(localPath + filename);
	    	
	    	try {
				image = ImageIO.read(sourceimage);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	icon = new ImageIcon(getScaledImage(image, videoWidth(videoContainerMaxHeight), videoContainerMaxHeight));
	    }
		
		return icon;
	}
	
	public ImageIcon getTargetIcon(String filename) {
		ImageIcon icon = null;
		
		File sourceimage = null;
		
	    Image image = null;
	   
	    
	    
	    if (debug) {
	    	sourceimage = new File(localPath + filename);
	    	
	    	try {
				image = ImageIO.read(sourceimage);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	icon = new ImageIcon(getScaledImage(image, 64, 64));
	    } else {
	    	sourceimage = new File(localPath + filename);
	    	
	    	try {
				image = ImageIO.read(sourceimage);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	icon = new ImageIcon(getScaledImage(image, 64, 64));
	    }
		
		return icon;
	}
	private JLabel getEmptyLabel() {
		JLabel emptylLabel = new JLabel("     ");
	    emptylLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		return emptylLabel;
	}
	private int videoWidth(int videoHeight) {
		int videoWidth = (int) (1.333333333333333333333333*videoHeight);
		return videoWidth;
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	private void switchVidpanelBorderColor(Color color) {

		if (color == Color.RED) {
			
			
			
		
		} else if(color == Color.YELLOW) {
			
			
		}else {
		
			
		}
	    videoPlayer.setBorder(BorderFactory.createLineBorder(color, 1));
	}
	
	public void startPlayback() {
		osd.robotCompassLabel.setAngle(0.0);
		sendMessage("Init Target...");
		targetPanel.initialiseTarget();
		sendMessage("Init Compass...");
		
	    osd.robotCompassLabel.initialiseCompass();
	    osd.robotCompassLabel.setAngle(90.0);
	    sendMessage("Init Radar...");
	    //TODO Radar widget
	    
	    
		// Connect NetworkTables, and get access to the publishing table
		System.out.println("Initializing Network Table");
		sendMessage("Init Network Table...");
		NetworkTable.setClientMode();
		
		NetworkTable.setTeam(teamnumber);

		NetworkTable.initialize();
		

		
		if (!debug ) {
		 	while (true) {
		      try {
		        if (NetworkTable.connections().length > 0) {
		          break;
		        }
		        Thread.sleep(500);
		        } catch (Exception e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		    }
		
		}

		
		table = NetworkTable.getTable(VisionCommunication.TABLE_NAME);
	
		
		
		visionCommunication = new ClientVisionCommunication(table);
		
	
		// This is the network port you want to stream the raw received image to
		// By rules, this has to be between 1180 and 1190, so 1185 is a good
		// choice
		int streamPort = inputstreamport;

		// This stores our reference to our mjpeg server for streaming the input
		// image
		MjpegServer inputStream = new MjpegServer("MJPEG Server", streamPort);
		
		 // HTTP Camera
	    
	    // This is our camera name from the robot. this can be set in your robot code with the following command
	    // CameraServer.getInstance().startAutomaticCapture("YourCameraNameHere");
	    // "USB Camera 0" is the default if no string is specified

	    HttpCamera camera = null;

		
	    	
	    switchVidpanelBorderColor(Color.YELLOW);
	    sendMessage("Connecting to video stream ... " + getCameraURL(camera1IP));      
	    camera = new HttpCamera("CoprocessorCamera", getCameraURL(camera1IP));
		inputStream.setSource(camera);
	    	
	    	
	    

	    
		// This creates a CvSink for us to use. This grabs images from our
		// selected camera,
		// and will allow us to use those images in opencv
		CvSink imageSink = new CvSink("CV Image Grabber");
		imageSink.setSource(camera);

		// This creates a CvSource to use. This will take in a Mat image that
		// has had OpenCV operations
		// operations
		CvSource imageSource = new CvSource("CV Image Source", VideoMode.PixelFormat.kMJPEG, 640, 480, 30);
		MjpegServer cvStream = new MjpegServer("CV Image Stream", 1186);
		cvStream.setSource(imageSource);

		cvStream.free();

		// All Mats and Lists should be stored outside the loop to avoid
		// allocations
		// as they are expensive to create
		Mat inputImage = new Mat();
		Mat hsv = new Mat();

		System.out.println("Camera Streaming Starting at " + camera1IP + ":" + inputstreamport);
		sendMessage("Waiting for connection .....");
		// Infinitely process image
		while (true) {
			// Grab a frame. If it has a frame time of 0, there was an error.
			// Just skip and continue
			
			long frameTime = imageSink.grabFrame(inputImage);
			if (frameTime == 0)
				continue;

			// Below is where you would do your OpenCV operations on the
			// provided image
			// The sample below just changes color source to HSV
			Imgproc.cvtColor(inputImage, hsv, Imgproc.COLOR_BGR2HSV);

			// Here is where you would write a processed image that you want to
			// restreams
			// This will most likely be a marked up image of what the camera
			// sees
			// For now, we are just going to stream the HSV image
			
			if (cleanVideo) {
				
				imageVideo = new ImageIcon(createAwtImage(inputImage));
				
			} else {
				
				imageVideo = new ImageIcon(createAwtImage(hsv));
				
			}
			switchVidpanelBorderColor(Color.GREEN);
			videoPlayer.setIcon(imageVideo);
			videoPlayer.repaint();	
			
			if (message.length() < 1) {
				
				sendMessage("Connected");
				if (visionCommunication.getCurrentCamera() == 1) {
					
					osd.cameraNameLabel.setText(VisionCommunication.DEFAULT_CAMERA_1_NAME);
				} else {
					osd.cameraNameLabel.setText(VisionCommunication.DEFAULT_CAMERA_2_NAME);
				}
				
				
			} else {
				
				sendMessage(message);
			}
			
			
		}
	
	}

	private BufferedImage createAwtImage(Mat mat) {

	    int type = 0;
	    if (mat.channels() == 1) {
	        type = BufferedImage.TYPE_BYTE_GRAY;
	    } else if (mat.channels() == 3) {
	        type = BufferedImage.TYPE_3BYTE_BGR;
	    } else {
	        return null;
	    }

	    BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
	    WritableRaster raster = image.getRaster();
	    DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	    byte[] data = dataBuffer.getData();
	    mat.get(0, 0, data);

	    return image;
	}

	
	 private static HttpCamera setHttpCamera(String cameraName, MjpegServer server) {
		    // Start by grabbing the camera from NetworkTables
		    NetworkTable publishingTable = NetworkTable.getTable("CameraPublisher");
		    // Wait for robot to connect. Allow this to be attempted indefinitely
		    while (true) {
		      try {
		        if (publishingTable.getSubTables().size() > 0) {
		          break;
		        }
		        Thread.sleep(500);
		        } catch (Exception e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		    }


		    HttpCamera camera = null;
		    if (!publishingTable.containsSubTable(cameraName)) {
		      return null;
		    }
		    ITable cameraTable = publishingTable.getSubTable(cameraName);
		    String[] urls = cameraTable.getStringArray("streams", null);
		    if (urls == null) {
		      return null;
		    }
		    ArrayList<String> fixedUrls = new ArrayList<String>();
		    for (String url : urls) {
		      if (url.startsWith("mjpg")) {
		        fixedUrls.add(url.split(":", 2)[1]);
		      }
		    }
		    camera = new HttpCamera("CoprocessorCamera", fixedUrls.toArray(new String[0]));
		    server.setSource(camera);
		    return camera;
		  }
	 
	 
	 private static HttpCamera setVisionSystemCamera(String ip, MjpegServer server) {
	

		    HttpCamera camera = null;

		    camera = new HttpCamera("CoprocessorCamera", getCameraURL(ip));
		    if (camera != null) {
		    	server.setSource(camera);
			    }
  
		    return camera;
		  }


	public String getCamera1Ip() {
		return camera1IP;
	}

	public void setCamera1Ip(String ip) {
		this.camera1IP = ip;
	}
	
	public String getCamera2Ip() {
		return camera2IP;
	}

	public void setCamera2Ip(String ip) {
		this.camera2IP = ip;
	}
	
	public int getInputstreamport() {
		return inputstreamport;
	}

	public void setInputstreamport(int inputstreamport) {
		this.inputstreamport = inputstreamport;
	}
	public int getTeamnumber() {
		return teamnumber;
	}

	public void setTeamnumber(int teamnumber) {
		this.teamnumber = teamnumber;
	}

	public NetworkTable getTable() {
		return table;
	}

	public void setTable(NetworkTable table) {
		this.table = table;
	}

	public JFrame getJframe() {
		return playerWindow;
	}

	public void setJframe(JFrame playerWindow) {
		this.playerWindow = playerWindow;
	}

	public JLabel getVidpanel() {
		return videoPlayer;
	}

	public void setVidpanel(JLabel videoPlayer) {
		this.videoPlayer = videoPlayer;
	}

	public boolean isCleanVideo() {
		return cleanVideo;
	}

	public void setCleanVideo(boolean cleanVideo) {
		this.cleanVideo = cleanVideo;
	}

	private static String getCameraURL(String ip) {

		String url = "http://";
		url = url + ip;
		url = url + ":1185/stream.mjpg";
		
		return url;
	}
	

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public Target getTargetPanel() {
		return targetPanel;
	}

	public void setTargetPanel(Target targetPanel) {
		this.targetPanel = targetPanel;
	}

	public Boolean getMulticam() {
		return multicam;
	}

	public void setMulticam(Boolean multicam) {
		this.multicam = multicam;
	}

	

}
