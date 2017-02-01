import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import org.usfirst.frc.team5952.robot.commands.VisionCommunication;
import org.usfirst.frc.team5952.robot.visionSystem.StreamManager;


public class Main {

	private static Boolean debug = false;
	static {
		loadProperties();
	}
	static Properties prop;

	private static void loadProperties() {
		prop = new Properties();
		InputStream in = null;
		try {
			
			//in = new FileInputStream("/home/pi/Robot2017/config.properties");
			//in = new FileInputStream("/home/pi/FRCVisionClient/config.properties");
			in = new FileInputStream("C:\\Users\\ares-b02\\robot_workspace\\Vision System Client 5952\\output\\config.properties");
			
		} catch (FileNotFoundException e1) {
			System.out.println("Cannot found properties files");
			e1.printStackTrace();
		}
		try {
			
			prop.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println("Cannot found properties files");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Cannot found properties files");
		}
	}

	public static void main(String[] args) {
		
		
		

		if (prop != null) {

			if (!prop.getProperty("windowsos").equals("true")) {
				
				// Loads our OpenCV library. This MUST be included
				System.loadLibrary("opencv_java310");
				
			}
			
			StreamManager.getInstance(prop.getProperty("localpath")).setLocalPath(prop.getProperty("localpath"));
			
			StreamManager.getInstance().debug = prop.getProperty("debug").equals("true");

			StreamManager.getInstance().title = prop.getProperty("title");
			
			StreamManager.getInstance().fullscreen = prop.getProperty("fullscreen").equals("true");
			
			StreamManager.getInstance().setTeamnumber(Integer.parseInt(prop.getProperty("teamnumber")));
			
			StreamManager.getInstance().setInputstreamport(Integer.parseInt(prop.getProperty("inputstreamport")));
			
			StreamManager.getInstance().setCamera1Ip(prop.getProperty("networktnameCam1"));
			
			StreamManager.getInstance().setCamera2Ip(prop.getProperty("networktnameCam2"));
			
			StreamManager.getInstance().setMulticam(prop.getProperty("multicam").equals("true"));
			
			StreamManager.getInstance().init();
			
			StreamManager.getInstance().startPlayback();
			
			
			
		} else if (prop == null && debug) {
			
			//System.loadLibrary("opencv_java310");
			
			System.out.println("Localpath ::::: NOT FOUND");
			
			StreamManager.getInstance("c:\\temp\\").setLocalPath("c:\\temp\\");
			
			StreamManager.getInstance().debug = true;
			
			StreamManager.getInstance().title = "Robuck Team 5952 - Vision System Client";
			
			StreamManager.getInstance().fullscreen = false;
			
			StreamManager.getInstance().setTeamnumber(5952);
			
			StreamManager.getInstance().setInputstreamport(1185);
			
			StreamManager.getInstance().setCamera1Ip("raspberrypi.local");
			
			StreamManager.getInstance().setCamera2Ip("raspberrypi2.local");
			
			StreamManager.getInstance().setMulticam(false);
			
			StreamManager.getInstance().init();
			
			StreamManager.getInstance().startPlayback();
			
		} else {
			
			System.out.println("CONFIGURATION FILE ::::: NOT FOUND");
			System.exit(0);
			
		}
		
		
		
	}

}