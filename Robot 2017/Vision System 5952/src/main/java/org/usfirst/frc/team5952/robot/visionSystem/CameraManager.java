package org.usfirst.frc.team5952.robot.visionSystem;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.networktables.ConnectionInfo;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class CameraManager {

	//TODO Pour Tester sur l'ordi mettre a true sinon a false
	private Boolean debug = true;

	
	
	private static String camera1NetbiosName = "raspberrypi.local";
	private static String camera2NetbiosName = "camera2.local";
	private static int socketPort= 2000;
	
	// ***********************************************************************
	private static CameraManager instance = null;
	private int teamnumber = 5952;
	private int inputstreamport = 1185;
	private String cameraName = "Camera1";
	private String robotIP = "10.1.90.2";
	private String hotSpotAddress = "192.168.7.1";
	private String ip = null;
	private NetworkTable table = null;
	
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
	public void readSocketCommant(String command) {
		
		//TODO Traiter les commandes recu sur le socket
		
	}
	
	public void startStreaming() {
		
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

			robotIP = NetworkTable.connections()[i].remote_ip;
			
			System.out.println("Connections Protocol " + i + "::::: "  + NetworkTable.connections()[i].protocol_version);
			System.out.println("Connections remote_id " + i + "::::: "  + NetworkTable.connections()[i].remote_id);
			System.out.println("Connections remote_ip " + i + "::::: "  + NetworkTable.connections()[i].remote_ip);
			System.out.println("Connections remote_port " + i + "::::: "  + NetworkTable.connections()[i].remote_port);
			
			try {
				System.out.println("Local IP is :" + InetAddress.getLocalHost().getHostAddress());
				System.out.println("Local Hostname is :" + InetAddress.getLocalHost().getHostName());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}
		
		
		table = NetworkTable.getTable("VisionCamera");
		
		table.addTableListener("SWITCH", new StreamingStateListener(), true);
		
		
		
	
		Enumeration en = null;
		try {
			en = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String networkInterfaceIP = null;
		
		String ips = "";
		while (en.hasMoreElements()) {
			
			NetworkInterface ni = (NetworkInterface) en.nextElement();
			Enumeration ee = ni.getInetAddresses();
			
			while (ee.hasMoreElements()) {
				
				
				InetAddress ia = (InetAddress) ee.nextElement();
				
				ips = ips + ia.getHostName() + "||";
				
				if (!ia.isLoopbackAddress() && ia.getHostAddress().length() < 16 && !ia.getHostAddress().matches(hotSpotAddress) && ia.getHostName().matches(".local"))  {
					System.out.println("CanonicalHostName ::::::: " + ia.getCanonicalHostName());
					System.out.println("Adressse ::::::: " + ia.getHostAddress());
					System.out.println("Is loopback ::::::: " + ia.isLoopbackAddress());
					System.out.println("Hostname ::::::: " + ia.getHostName());
					
					networkInterfaceIP = ia.getHostName();
					ip = ia.getHostName();
			
				}
			}	
			
		}
		System.out.println("OnBoard Network Card Address ::::::: " + ips.substring(0, ips.length() - 2));
		
		
		
		if (networkInterfaceIP!= null) {
			table.putString(cameraName+"IP", networkInterfaceIP);
			table.putNumber(cameraName+"SocketPort", socketPort);
		}

		
			
		//SocketManager.getInstance().startServer( socketPort);
		//SocketManager.getInstance().startClient("192.168.1.40", socketPort);	
		
	
		// This is the network port you want to stream the raw received image to
		// By rules, this has to be between 1180 and 1190, so 1185 is a good
		// choice
		int streamPort = inputstreamport;

		// This stores our reference to our mjpeg server for streaming the input
		// image
		MjpegServer inputStream = new MjpegServer("MJPEG Server", streamPort);
		
		// USB Camera

		// This gets the image from a USB camera
		// Usually this will be on device 0, but there are other overloads
		// that can be used
		System.out.println("Initializing USB Camera");
		UsbCamera camera = setUsbCamera(0, inputStream);
		// Set the resolution for our camera, since this is over USB
		camera.setResolution(640, 480);

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
			
			//TODO Envoyer le data pour l'alignement sur la cible desirer
			
			
			table.putString(cameraName+"IP", ip);
			table.putNumber(cameraName+"SocketPort", socketPort);
			table.putString(cameraName+"DeltaFromTarget", "TODO delta corection");
			table.putString(cameraName+"DistanceFromTarget", "TODO distance");
			
			table.putBoolean("cameraName"+"foundTarget", true);
			table.putNumber("cameraName"+"offset", cameraOffset);  //TODO camera offset from centrer off robot
			
			//SocketManager.getInstance().sendCommand("DeltaFromTarget:34.1");
			
			//TODO Switcher entre les hsv et inputImage dans imageSource.putFrame(hsv) avec un bouton sur la console en changeant l<etat d<une valeur booleen dans la network table
			imageSource.putFrame(hsv);
			//imageSource.putFrame(inputImage);
		}	
		
	}
	
	public void stopStreaming() {
		
		
		//TODO Implementer la fonction d'arreter le streaming de la camera
		
	}
	
	private UsbCamera setUsbCamera(int cameraId, MjpegServer server) {
		// This gets the image from a USB camera
		// Usually this will be on device 0, but there are other overloads
		// that can be used
		UsbCamera camera = new UsbCamera("CoprocessorCamera", cameraId);
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

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	public int getCameraOffset() {
		return cameraOffset;
	}

	public void setCameraOffset(int cameraOffset) {
		this.cameraOffset = cameraOffset;
	}

}
