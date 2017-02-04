package org.usfirst.frc.team5952.robot.visionSystem;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5952.robot.commands.CameraVisionCommunication;
import org.usfirst.frc.team5952.robot.commands.VisionCommunication;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class CameraManager {
	
	public Boolean debug = false;
	public boolean multiCamera = false;
	private String currentCamera = VisionCommunication.DEFAULT_CAMERA_1_NAME;
	private Boolean cleanfeed = true;
	private MjpegServer inputStream  = null;
	public String networkName = "";
	private UsbCamera camera = null;
	public int cameraToBroadcast = 1;
	private HttpCamera camera2;
	private String camera1IP = "";
	private String camera2IP = "";
	
	
	// ***********************************************************************
	private static CameraManager instance = null;
	private int teamnumber = 5952;
	private int inputstreamport = 1185;
	public String cameraName = "";

	private String hotSpotAddress = "192.168.7.1";
	private NetworkTable table = null;
	
	public CameraVisionCommunication visionCommunication; 
	
	private int cameraOffset = 0;
	
	protected CameraManager() {
	      // Exists only to defeat instantiation.
	   }

	public static CameraManager getInstance() {
		if (instance == null) {
			instance = new CameraManager();
		}
		return instance;
	}
	
	
	public void startStreaming() {
		
		
		// Connect NetworkTables, and get access to the publishing table
		System.out.println("Initializing Network Table");
		NetworkTable.setClientMode();
		
		NetworkTable.setTeam(teamnumber);

		NetworkTable.initialize();
	
		if (!debug) {
		
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

		for (int i=0; i <= (NetworkTable.connections().length - 1); i++ ) {

			
			System.out.println("Connections Protocol " + i + "::::: "  + NetworkTable.connections()[i].protocol_version);
			System.out.println("Connections remote_id " + i + "::::: "  + NetworkTable.connections()[i].remote_id);
			System.out.println("Connections remote_ip " + i + "::::: "  + NetworkTable.connections()[i].remote_ip);
			System.out.println("Connections remote_port " + i + "::::: "  + NetworkTable.connections()[i].remote_port);
		
		}
		
		
		table = NetworkTable.getTable(VisionCommunication.TABLE_NAME);
		
		
		visionCommunication = new CameraVisionCommunication(table);

		Enumeration en = null;
		try {
			en = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String ips = "";

	
		// This is the network port you want to stream the raw received image to
		// By rules, this has to be between 1180 and 1190, so 1185 is a good
		// choice
		int streamPort = inputstreamport;

		// This stores our reference to our mjpeg server for streaming the input
		// image
		inputStream = new MjpegServer("MJPEG Server", streamPort);
		
		
		MjpegServer inputStream2;
		
		CvSink imageSink2 = null;
		// USB Camera

		// This gets the image from a USB camera
		// Usually this will be on device 0, but there are other overloads
		// that can be used
		System.out.println("Initializing USB Camera");
		camera = setUsbCamera(0, inputStream);
		// Set the resolution for our camera, since this is over USB
		camera.setResolution(640, 480);

		
		if (cameraName.equals(VisionCommunication.DEFAULT_CAMERA_1_NAME) && multiCamera) {
			inputStream2 = new MjpegServer("MJPEG Server", streamPort);
			camera2 = new HttpCamera("CoprocessorCamera", getCameraURL(camera2IP, ""+inputstreamport+""));
		    inputStream2.setSource(camera2);
		    imageSink2 = new CvSink("CV Image Grabber");
			imageSink2.setSource(camera2);
			
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
		Mat inputImage2 = new Mat();
		Mat hsv = new Mat();
		ImageAnalysis analyse = new ImageAnalysis();

		visionCommunication.putCurrentCamera(1);
		System.out.println("Camera Streaming Starting at " + getCameraURL(networkName, ""+inputstreamport+""));
		// Infinitely process image
		while (true) {
			// Grab a frame. If it has a frame time of 0, there was an error.
			// Just skip and continue
			
			long frameTime = imageSink.grabFrame(inputImage);
			if (frameTime == 0)
				continue;

			
			
			
			
			
			// Here is where you would write a processed image that you want to
			// restreams
			// This will most likely be a marked up image of what the camera
			// sees
			// For now, we are just going to stream the HSV image
		
			
			imageSource.putFrame(analyse.analyse(inputImage, hsv));
			
			
			
			
			
			//TODO Envoyer le data pour l'alignement sur la cible desirer
			if (cameraName.equals(VisionCommunication.DEFAULT_CAMERA_1_NAME)) {
				
				visionCommunication.putCamera1IP(camera1IP);
				visionCommunication.putCamera1DeltaTarget(0.0); // "TODO delta corection"
				visionCommunication.putCamera1DistTarget(0.0); //"TODO distace to target"
//				visionCommunication.putCamera1TargetLocked(TODO true or false)				
//				visionCommunication.putCamera1Offset(TOTO offset)		
				
			} else {
				
				visionCommunication.putCamera2IP(camera2IP);
				visionCommunication.putCamera2DeltaTarget( 0.0); //"TODO delta corection"
				visionCommunication.putCamera2DistTarget(0.0);	//"TODO distace to target"
//				visionCommunication.putCamera2TargetLocked(TODO true or false)		//TODO camera offset from centrer off robot		
//				visionCommunication.putCamera2Offset(TOTO offset)
				
			}
	
		}	
		
	}
	
	private static String getCameraURL(String ip,String port) {

		String url = "http://";
		url = url + ip;
		url = url + ":"+port+"/stream.mjpg";
		
		return url;
	}
	
	private UsbCamera setUsbCamera(int cameraId, MjpegServer server) {
		// This gets the image from a USB camera
		// Usually this will be on device 0, but there are other overloads
		// that can be used
		UsbCamera camera = new UsbCamera("CoprocessorCamera", cameraId);
		server.setSource(camera);
		
		return camera;
	}
	
	private void switchCam () {
	
		if (currentCamera.equals(VisionCommunication.DEFAULT_CAMERA_1_NAME)) {
			if (camera != null) {
				inputStream.setSource(camera);
			}
			
		} else if (currentCamera.equals(VisionCommunication.DEFAULT_CAMERA_2_NAME)) {
			
			if (camera2 != null) {
				inputStream.setSource(camera2);
			}
		}
		
	}

	//Getters and Setters
	public void setCurrentCamera (int camera) {
		
		if (cameraName.equals(VisionCommunication.DEFAULT_CAMERA_1_NAME) && multiCamera) {
			
			if (camera == 1) {
				currentCamera = VisionCommunication.DEFAULT_CAMERA_1_NAME;
			} else if(camera == 2) {
				currentCamera = VisionCommunication.DEFAULT_CAMERA_2_NAME;
				
			}
			switchCam ();
		}
		
	}
	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}


	public void setInputstreamport(int inputstreamport) {
		this.inputstreamport = inputstreamport;
	}
	

	public void setTeamnumber(int teamnumber) {
		this.teamnumber = teamnumber;
	}

	

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}


	public void setCameraOffset(int cameraOffset) {
		this.cameraOffset = cameraOffset;
	}

	public void setCamera1IP(String camera1ip) {
		camera1IP = camera1ip;
	}

	public void setCamera2IP(String camera2ip) {
		camera2IP = camera2ip;
	}

}
