import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;


import org.usfirst.frc.team5952.robot.visionSystem.CameraManager;



public class Main {

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

		// Loads our OpenCV library. This MUST be included
		System.loadLibrary("opencv_java310");		

		if (prop != null) {

			
			CameraManager.getInstance().setTeamnumber(Integer.parseInt(prop.getProperty("teamnumber")));
			CameraManager.getInstance().debug = prop.getProperty("debug").equals("true");
			CameraManager.getInstance().setInputstreamport(Integer.parseInt(prop.getProperty("inputstreamport")));
			CameraManager.getInstance().setCameraName(prop.getProperty("networktablename"));

			CameraManager.getInstance().setCamera1IP(prop.getProperty("networktnameCam1"));
			
			CameraManager.getInstance().setCamera2IP(prop.getProperty("networktnameCam2"));
			
			CameraManager.getInstance().networkName = (prop.getProperty("networktname")); 
			
			CameraManager.getInstance().multiCamera = prop.getProperty("multicam").equals("true");
			
			CameraManager.getInstance().serialRemoteDeviceName = prop.getProperty("serialRemoteDeviceName");
			
			CameraManager.getInstance().serialRemoteBaudrate = Integer.parseInt(prop.getProperty("serialRemoteBaudrate"));
			
			CameraManager.getInstance().serialRemoteDATABITS = Integer.parseInt(prop.getProperty("serialRemoteDATABITS"));
			
			CameraManager.getInstance().serialRemoteSTOPBITS = Integer.parseInt(prop.getProperty("serialRemoteSTOPBITS"));
			
			CameraManager.getInstance().serialRemotePARITY = Integer.parseInt(prop.getProperty("serialRemotePARITY"));
			
			CameraManager.getInstance().startStreaming();
			
			System.out.println("Team number = " + prop.getProperty("teamnumber"));
			System.out.println("Streaming Port = " + prop.getProperty("inputstreamport"));
			System.out.println("Camera Networktable Name = " + prop.getProperty("networktablename"));

			
		}

		
	}

}