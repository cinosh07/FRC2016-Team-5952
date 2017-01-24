package org.usfirst.frc.team5952.robot.visionSystem;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class CameraManager {

	//TODO Pour Tester sur l'ordi mettre a true sinon a false
	private boolean debug = true;
	private static String camera1URL = "http://192.168.1.54:1185/stream.mjpg";
	private static String camera2URL = "http://192.168.1.54:1185/stream.mjpg";
	// ***********************************************************************
	
	private String title = "Robuck Team 5962 - Navigation System";
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
	private boolean cleanVideo = true;
	
	
	
	protected CameraManager() {
	      // Exists only to defeat instantiation.
	   }

	public static CameraManager getInstance() {
		if (instance == null) {
			instance = new CameraManager();
		}
		return instance;
	}
	
	
	public void init() {
		
		//Definir l'image d'arriere plan de la fenetre video
		File sourceimage = null;
	    Image image = null;
	    if (debug) {
	    	sourceimage = new File("c:\\temp\\back.jpg");
	    	
	    	try {
				image = ImageIO.read(sourceimage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	defaultImageVideo = new ImageIcon(image);
	    } else {
	    	sourceimage = new File("/home/pi/Robot2017/default.png");
	    	
	    	try {
				image = ImageIO.read(sourceimage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	defaultImageVideo = new ImageIcon(image);
	    }
	    
	    
	    //Construction du GUI
	    GridBagConstraints c = new GridBagConstraints();
	    playerWindow = new JFrame("");
	  	playerWindow.getContentPane().setLayout(new GridBagLayout());
	  	playerWindow.setSize(800, 600);
	  	playerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	playerWindow.setTitle(title);

	     
	    //*********************************************************
	    //TODO ajouter les boutons et controles du Video Player vers le robot
	    
	    JPanel buttonBar = new JPanel(new FlowLayout());
	    JButton OKButton = new JButton("OK");
	    OKButton.setSize(30, 20);
	    OKButton.setMaximumSize(new Dimension(30,
                20));
	    //OKButton.addActionListener(new MyAction());
	    buttonBar.add(OKButton);
	   // playerWindow.getContentPane().add(videoPlayer, BorderLayout.CENTER);
	    
	    
	    
	   //***********************************************
 
	  //*********************************************************
	    //TODO ajouter l'OSD du Video Player qui affiche les datas du robot
	    JPanel videoContainer = new JPanel(new BorderLayout());
	    
	    videoContainer.setSize(640, 480);
	    
	    
	    videoPlayer = new JLabel(" ", imageVideo, JLabel.CENTER);
	    videoPlayer.setSize(640, 480);
	    videoPlayer.setBackground(Color.BLACK);
	    videoPlayer.setForeground(Color.white);
	    
	    switchVidpanelBorderColor(Color.RED);
	    
	    videoContainer.add(videoPlayer,BorderLayout.CENTER);
	    
	    JLabel backgroundImage = new JLabel(" ", defaultImageVideo, JLabel.CENTER);
	    videoContainer.add(backgroundImage,BorderLayout.CENTER);
	    
	    //***********************************************
	    
	    
	    
	    
	    
	    
	    
	    //Placement des composantes dans le gui
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 0;       //reset to default
	    c.weighty = 1.0;   //request any extra vertical space
	    c.anchor = GridBagConstraints.PAGE_START; //bottom of space
	    c.insets = new Insets(10,0,0,0);  //top padding
	    c.gridx = 1;       //aligned with button 2
	    c.gridwidth = 3;   //2 columns wide
	    c.gridy = 0;       //third row
	
	    playerWindow.getContentPane().add(videoContainer,c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 0;       //reset to default
	    c.weighty = 1.0;   //request any extra vertical space
	    c.anchor = GridBagConstraints.PAGE_END; //bottom of space
	    c.insets = new Insets(10,0,0,0);  //top padding
	    c.gridx = 1;       //aligned with button 2
	    c.gridwidth = 3;   //2 columns wide
	    c.gridy = 2;       //third row
	    
	    playerWindow.getContentPane().add(buttonBar,c);
	 

	    playerWindow.setVisible(true);
	    
   
		
	}
	
	private void switchVidpanelBorderColor(Color color) {

		if (color == Color.RED) {
			
			videoPlayer.setText("Not Connected");
			
		
		} else if(color == Color.YELLOW) {
			
			videoPlayer.setText("Connecting to video stream ... " + getCameraURL(table.getString("Camera1IP", ip),debug));
			
		}else {
		
			
			videoPlayer.setText("");
		}
	    videoPlayer.setBorder(BorderFactory.createLineBorder(color, 5));
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
