package org.usfirst.frc.team5952.robot.visionSystem;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

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

	
	private boolean debug = true;
	
	
	private static CameraManager instance = null;

	private int teamnumber = 5952;
	

	private int inputstreamport = 1185;
	

	private String cameraName = "Camera1";
	private String robotIP = "10.59.52.2";
	private String ip = "192.168.1.54";
	private NetworkTable table = null;
	private JFrame jframe = null;
	private JLabel vidpanel = null;
	private ImageIcon imageVideo = null;
	private ImageIcon defaultImageVideo = new ImageIcon("/home/pi/Robot2017/default.png");
	private boolean cleanVideo = true;
	private static String camera1URL = "http://192.168.1.54:1185/stream.mjpg";
	private static String camera2URL = "http://192.168.1.54:1185/stream.mjpg";
	
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
		
		
		//Construction du GUI
		imageVideo = defaultImageVideo;
		jframe = new JFrame("Video");
		jframe.setSize(800, 600);
	    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    vidpanel = new JLabel(" ", imageVideo, JLabel.CENTER);
	    vidpanel.setSize(640, 480);
	    vidpanel.setBackground(Color.BLACK);
	    jframe.getContentPane().add(vidpanel, BorderLayout.CENTER);
	    
	    jframe.setVisible(true);
    
	    //TODO ajouter les boutons et controles du GUI
		
	}
	
	public void startPlayback() {
		
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("Cannot found Network Card");
			e.printStackTrace();
		}
		
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
	
		      camera = new HttpCamera("CoprocessorCamera", getCameraURL(table.getString("Camera1IP", ip)));
		      inputStream.setSource(camera);
		    }
	    	
	    } else {
	    	
	    	camera = new HttpCamera("CoprocessorCamera", camera1URL);
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
			
			vidpanel.setIcon(imageVideo);
			vidpanel.repaint();
			
			
		}
		
		
		
	}
	
	public void stopPlaying() {
		
		
		//TODO Implementer la fonction d'arreter le playback
		
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
		return jframe;
	}

	public void setJframe(JFrame jframe) {
		this.jframe = jframe;
	}

	public JLabel getVidpanel() {
		return vidpanel;
	}

	public void setVidpanel(JLabel vidpanel) {
		this.vidpanel = vidpanel;
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

	

}
