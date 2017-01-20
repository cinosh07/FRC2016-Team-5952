import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

import edu.wpi.first.wpilibj.networktables.*;
import edu.wpi.first.wpilibj.tables.*;
import edu.wpi.cscore.*;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5952.robot.visionSystem.*;

public class Main {
	
	private static int teamnumber = 5952;
	private static int inputstreamport = 1185;
	private static String cameraName = "Camera1";
	private static String robotIP = "10.1.90.2";
	private static String ip = null;
	
	
	static {
		loadProperties();
	}
	static Properties prop;

	private static void loadProperties() {
		prop = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream("/home/pi/Robot2017/config.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Cannot found properties files");
		}
	}

	public static void main(String[] args) {

		

		// Loads our OpenCV library. This MUST be included
		System.loadLibrary("opencv_java310");
		Test test = new Test();
		//System.out.println(test.getTestToPrint());
		// Connect NetworkTables, and get access to the publishing table

		if (prop != null) {

			teamnumber = Integer.parseInt(prop.getProperty("teamnumber"));
			inputstreamport = Integer.parseInt(prop.getProperty("inputstreamport"));
			cameraName = prop.getProperty("networktablename");
			robotIP = prop.getProperty("cameraip");

			System.out.println("Team number = " + teamnumber);
			System.out.println("Streaming Port = " + inputstreamport);
			System.out.println("Camera Networktable Name = " + cameraName);
			System.out.println("Robot IP = " + robotIP);
			try {
				System.out.println(cameraName+" IP Adress: "+InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Initializing Network Table");
		NetworkTable.setClientMode();
		// Set your team number here
		NetworkTable.setTeam(teamnumber);

		NetworkTable.initialize();

		NetworkTable table = NetworkTable.getTable("Camera");

		
		table.putString(cameraName+"IP", ip);
		// table.
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
			
			//Envoyer le data pour l'alignement sur la cible desirer
			table.putString(cameraName+"DeltaFromTarget", "TODO delta corection");
			table.putString(cameraName+"DistanceFromTarget", "TODO distance");
			
			
			imageSource.putFrame(hsv);
		}
	}


	private static UsbCamera setUsbCamera(int cameraId, MjpegServer server) {
		// This gets the image from a USB camera
		// Usually this will be on device 0, but there are other overloads
		// that can be used
		UsbCamera camera = new UsbCamera("CoprocessorCamera", cameraId);
		server.setSource(camera);
		return camera;
	}
}