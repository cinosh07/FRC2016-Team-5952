import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
	static {
		loadProperties();
	}
	static Properties prop;

	private static void loadProperties() {
		prop = new Properties();
		File file = new File("file.txt");
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

		int teamnumber = 5952;
		int inputstreamport = 1185;
		String networktablename = "Camera1";
		String cameraip = "10.1.90.2";

		// Loads our OpenCV library. This MUST be included
		System.loadLibrary("opencv_java310");
		Test test = new Test();
		System.out.println(test.getTestToPrint());
		// Connect NetworkTables, and get access to the publishing table

		if (prop != null) {

			teamnumber = Integer.parseInt(prop.getProperty("teamnumber"));
			inputstreamport = Integer.parseInt(prop.getProperty("inputstreamport"));
			networktablename = prop.getProperty("networktablename");
			cameraip = prop.getProperty("cameraip");

			System.out.println("Team number = " + teamnumber);
			System.out.println("Streaming Port = " + inputstreamport);
			System.out.println("Camera Networktable Name = " + networktablename);
			System.out.println("Camera IP = " + cameraip);
		}

		NetworkTable.setClientMode();
		// Set your team number here
		NetworkTable.setTeam(teamnumber);

		NetworkTable.initialize();

		NetworkTable table = NetworkTable.getTable(networktablename);

		// table.
		// This is the network port you want to stream the raw received image to
		// By rules, this has to be between 1180 and 1190, so 1185 is a good
		// choice
		int streamPort = inputstreamport;

		// This stores our reference to our mjpeg server for streaming the input
		// image
		MjpegServer inputStream = new MjpegServer("MJPEG Server", streamPort);

		// Selecting a Camera
		// Uncomment one of the 2 following camera options
		// The top one receives a stream from another device, and performs
		// operations based on that
		// On windows, this one must be used since USB is not supported
		// The bottom one opens a USB camera, and performs operations on that,
		// along with streaming
		// the input image so other devices can see it.

		// HTTP Camera
		/*
		 * // This is our camera name from the robot. this can be set in your
		 * robot code with the following command //
		 * CameraServer.getInstance().startAutomaticCapture("YourCameraNameHere"
		 * ); // "USB Camera 0" is the default if no string is specified String
		 * cameraName = "USB Camera 0"; HttpCamera camera =
		 * setHttpCamera(cameraName, inputStream); // It is possible for the
		 * camera to be null. If it is, that means no camera could // be found
		 * using NetworkTables to connect to. Create an HttpCamera by giving a
		 * specified stream // Note if this happens, no restream will be created
		 * if (camera == null) { camera = new HttpCamera("CoprocessorCamera",
		 * "YourURLHere"); inputStream.setSource(camera); }
		 */

		/***********************************************/

		// USB Camera

		// This gets the image from a USB camera
		// Usually this will be on device 0, but there are other overloads
		// that can be used
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
			imageSource.putFrame(hsv);
		}
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

	private static UsbCamera setUsbCamera(int cameraId, MjpegServer server) {
		// This gets the image from a USB camera
		// Usually this will be on device 0, but there are other overloads
		// that can be used
		UsbCamera camera = new UsbCamera("CoprocessorCamera", cameraId);
		server.setSource(camera);
		return camera;
	}
}