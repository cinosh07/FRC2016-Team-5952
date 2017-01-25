package org.usfirst.frc.team5952.robot.visionSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.SpringLayout;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class CameraManager {

	//TODO Pour Tester sur l'ordi mettre a true sinon a false
	private boolean debug = true;
	private static String camera1URL = "http://192.168.1.54:1185/stream.mjpg";
	private static String camera2URL = "http://192.168.1.54:1185/stream.mjpg";
	// ***********************************************************************
	
	private String title = "Robuck Team 5962 - Vision System Client";
	private static CameraManager instance = null;
	private int teamnumber = 5952;
	private int inputstreamport = 1185;
	private String cameraName = "Camera1";
	private String robotIP = "10.59.52.2";
	private String ip = "192.168.1.54";
	private NetworkTable table = null;
	private JFrame playerWindow = null;
	private JLabel videoPlayer = null;
	private ImageIcon imageVideo = null;
	private ImageIcon defaultImageVideo = null;
	private ImageIcon backgroundClean = null;
	private boolean cleanVideo = true;
	private OSD osd = null;
	private int buttonBar2ButtonWidth = 125;
	private int buttonBar2ButtonHeight = 25;
	private int buttonBarButtonWidth = 125;
	private int buttonBarButtonHeight = 25;
	
	private int videoContainerMaxHeight = 375;
	
	
	protected CameraManager() {
	      // Exists only to defeat instantiation.
	   }

	public static CameraManager getInstance() {
		if (instance == null) {
			instance = new CameraManager();
		}
		return instance;
	}
	
	public void visibleOSD(ActionEvent e) {
		
	}
	public void init() {
		
		//Definir l'image d'arriere plan de la fenetre video
		File sourceimage = null;
		File sourcebackgroundClean = null;
	    Image image = null;
	    Image imagebackgroundClean = null;
	    if (debug) {
	    	sourceimage = new File("c:\\temp\\back.jpg");
	    	sourcebackgroundClean = new File("c:\\temp\\back_clean.jpg");
	    	try {
				image = ImageIO.read(sourceimage);
				imagebackgroundClean = ImageIO.read(sourcebackgroundClean);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	backgroundClean = new ImageIcon(getScaledImage(imagebackgroundClean, videoWidth(videoContainerMaxHeight), videoContainerMaxHeight));
	    	defaultImageVideo = new ImageIcon(getScaledImage(image, videoWidth(videoContainerMaxHeight), videoContainerMaxHeight));
	    } else {
	    	sourceimage = new File("/home/pi/Robot2017/back.png");
	    	sourcebackgroundClean = new File("/home/pi/Robot2017/back_clean.jpg");
	    	try {
				image = ImageIO.read(sourceimage);
				imagebackgroundClean = ImageIO.read(sourcebackgroundClean);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	backgroundClean = new ImageIcon(getScaledImage(imagebackgroundClean, videoWidth(videoContainerMaxHeight), videoContainerMaxHeight));
	    	defaultImageVideo = new ImageIcon(getScaledImage(image, videoWidth(videoContainerMaxHeight), videoContainerMaxHeight));
	    }
	    
	    
	    //Construction du GUI
	    GridBagConstraints c = new GridBagConstraints();
	    playerWindow = new JFrame("");
	    playerWindow.setSize(800, 480);
	  	playerWindow.getContentPane().setLayout(new GridBagLayout()); 	
	  	playerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	if (!debug) {
	  		playerWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
	  	}
	  	playerWindow.setUndecorated(true);
	  	playerWindow.getContentPane().setBackground(Color.black);
	  	playerWindow.setTitle(title);

	     
	    //*********************************************************
	    //TODO ajouter les boutons et controles du Video Player vers le robot
	    
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
	    	  }
	    	});
	    buttonBar.add(OSDButton);
	    
	    
	    
	    
	    JButton sightButton = new JButton("Sight");
	    sightButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    buttonBar.add(sightButton);
	    
	    JButton targetButton = new JButton("Target");
	    targetButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    buttonBar.add(targetButton);
	    
	    JButton radarCompassButton = new JButton("Radar Compass");
	    radarCompassButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    buttonBar.add(radarCompassButton);
	    
	    JButton fullscreenButton = new JButton("Fullscreen");
	    fullscreenButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    buttonBar.add(fullscreenButton);
	    
	    JButton mapButton = new JButton("Map");
	    mapButton.setSize(buttonBar2ButtonWidth, buttonBar2ButtonHeight);
	    buttonBar.add(mapButton);
	    
	   //***********************************************
 
	    //*********************************************************
	    //TODO finaliser et synchroniser l'OSD du Video Player qui affiche les datas du robot avec la Network Tables
	    JPanel videoContainer = new JPanel(new BorderLayout());
	    
	    videoContainer.setSize(videoWidth(videoContainerMaxHeight), videoContainerMaxHeight );
	    videoContainer.setOpaque(false);
	    osd = new OSD();
	    
	    osd.setSize(videoWidth(videoContainerMaxHeight), videoContainerMaxHeight);
	    osd.setForeground(Color.white);
	    osd.setOpaque(false);
	    videoContainer.add(osd,BorderLayout.CENTER);
	    
	    videoPlayer = new JLabel(" ", imageVideo, JLabel.CENTER);
	    videoPlayer.setSize(videoWidth(videoContainerMaxHeight), videoContainerMaxHeight);
	    videoPlayer.setForeground(Color.white);
	    
	    switchVidpanelBorderColor(Color.RED);
	    
	    videoContainer.add(videoPlayer,BorderLayout.CENTER);
	    
	    JLabel backgroundImage = new JLabel(" ", defaultImageVideo, JLabel.CENTER);
	    videoContainer.add(backgroundImage,BorderLayout.CENTER);
	    
	   
	    JPanel buttonBar2 = new JPanel(new BorderLayout());
	    
	    //***********************************************
	    
	    
	    
	    
	  //Ligne 1 colonne 1
	    
	    c.gridx = 0;
	    c.gridy = 0;
	     /* une seule cellule sera disponible pour ce composant. */
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.fill = GridBagConstraints.NONE;
	    c.anchor = GridBagConstraints.LINE_START;
	    c.weighty = 1.0;   //request any extra vertical space
	    c.insets = new Insets(5,5,0,0);  //padding
	    playerWindow.getContentPane().add(videoContainer,c);
	    
	  //Ligne 1 colonne 2 

	    c.gridx = 1; /* une position horizontalement à droite de l'étiquette */
	    c.gridy = 0; /* sur la même ligne que l'étiquette */
	    c.gridwidth = GridBagConstraints.REMAINDER; /* il est le dernier composant de sa ligne. */
	    c.gridheight = 1; /* une seule cellule verticalement suffit */
	    /* Le composant peut s'étendre sur tout l'espace qui lui est attribué horizontalement. */
	    c.fill = GridBagConstraints.HORIZONTAL;
	    /* Alignons ce composant sur la même ligne d'écriture que son étiquette. */
	    c.anchor = GridBagConstraints.BASELINE;
	    c.insets = new Insets(5,0,0,5);  //top padding
	    
	    buttonBar2.setSize(playerWindow.getWidth() - videoPlayer.getWidth() - 40 ,videoContainerMaxHeight);
	    buttonBar2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
	    JLabel buttonBar2Label = new JLabel("");
	    
	    
	    
	    
	    buttonBar2Label.setIcon(new ImageIcon(getScaledImage(backgroundClean.getImage(),playerWindow.getWidth() - videoPlayer.getWidth() - 40 ,videoContainerMaxHeight)));
	    buttonBar2Label.setSize(playerWindow.getWidth() - videoPlayer.getWidth() - 40 ,videoContainerMaxHeight);
	    
	    
	    
	    
	    
	   
	    
	    
	    
	    
	    JPanel buttonBar2Panel = new JPanel();
	    buttonBar2Panel.setSize(playerWindow.getWidth() - videoPlayer.getWidth() - 40 ,videoContainerMaxHeight);
	    buttonBar2Panel.setOpaque(false);
	    buttonBar2Panel.setLayout(new BoxLayout(buttonBar2Panel, BoxLayout.Y_AXIS));  
	    
	    
	    buttonBar2Panel.add(getEmptyLabel());
	    
	    JLabel commandPanelLabel = new JLabel("Commands");
	    commandPanelLabel.setForeground(Color.white);
	    commandPanelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(commandPanelLabel);
	    
	    buttonBar2Panel.add(getEmptyLabel());
	    
	    JButton lockToTargetButton = new JButton("Target Lock");
	    lockToTargetButton.setSize(buttonBarButtonWidth, buttonBarButtonHeight);
	    lockToTargetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(lockToTargetButton);
	    
	    buttonBar2Panel.add(getEmptyLabel());
	    
	    JButton goToTargetButton = new JButton("Go to Target");
	    goToTargetButton.setSize(buttonBarButtonWidth, buttonBarButtonHeight);
	    goToTargetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(goToTargetButton);
	    
	    buttonBar2Panel.add(getEmptyLabel());
	    
	    JButton cleanFeedtButton = new JButton("Clean Feed");
	    cleanFeedtButton.setSize(buttonBarButtonWidth, buttonBarButtonHeight);
	    cleanFeedtButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonBar2Panel.add(cleanFeedtButton);
	    
	    
	    
	    
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
	    buttonBar.setSize(playerWindow.getWidth() - 5 ,playerWindow.getHeight() - videoPlayer.getHeight() - 5);
	    playerWindow.getContentPane().add(buttonBar,c);
	 

	    playerWindow.setVisible(true);
	    
   
		
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
			
			videoPlayer.setText("Not Connected");
			
		
		} else if(color == Color.YELLOW) {
			
			videoPlayer.setText("Connecting to video stream ... " + getCameraURL(table.getString("Camera1IP", ip),debug));
			
		}else {
		
			
			videoPlayer.setText("");
		}
	    videoPlayer.setBorder(BorderFactory.createLineBorder(color, 1));
	}
	
	public void startPlayback() {
		
		
		
		// Connect NetworkTables, and get access to the publishing table
		System.out.println("Initializing Network Table");
		NetworkTable.setClientMode();
		
		NetworkTable.setTeam(teamnumber);

		NetworkTable.initialize();
		
		table = NetworkTable.getTable("Camera");
		
		table.addTableListener("SWITCH", new StreamingStateListener(), true);
		
		
	
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
	    String cameraName = "USB Camera 0";
	    HttpCamera camera = null;
	    
	    
		if (!debug ) {
	    	camera = setHttpCamera(cameraName, inputStream);
		    // It is possible for the camera to be null. If it is, that means no camera could
		    // be found using NetworkTables to connect to. Create an HttpCamera by giving a specified stream
		    // Note if this happens, no restream will be created
		    if (camera == null) {
		      switchVidpanelBorderColor(Color.YELLOW);
		      camera = new HttpCamera("CoprocessorCamera", getCameraURL(table.getString("Camera1IP", ip),debug));
		      inputStream.setSource(camera);
		    }
	    	
	    } else {
	    	
	    	switchVidpanelBorderColor(Color.YELLOW);
	    	camera = new HttpCamera("CoprocessorCamera", getCameraURL(table.getString("Camera1IP", ip),debug));
		    inputStream.setSource(camera);
	    	
	    	
	    }

	    
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

		System.out.println("Camera Streaming Starting at " + ip + ":" + inputstreamport);
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
			
			
		}
		
		
		
	}
	
	public void stopPlaying() {
		
		
		//TODO Implementer la fonction d'arreter le playback
		switchVidpanelBorderColor(Color.RED);
		
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
	
	//Getters and Setters
	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public String getRobotIP() {
		return robotIP;
	}

	public void setRobotIP(String robotIP) {
		this.robotIP = robotIP;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	private static String getCameraURL(String ip, Boolean debug) {
		
		if (debug) {
			return camera1URL;
		}
		
		String url = "http://";
		url = url + ip;
		url = url + ":1185/stream.mjpg";
		
		return url;
	}

	

}
