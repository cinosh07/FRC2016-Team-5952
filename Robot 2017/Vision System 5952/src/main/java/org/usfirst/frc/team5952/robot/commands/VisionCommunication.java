package org.usfirst.frc.team5952.robot.commands;

import edu.wpi.first.wpilibj.networktables.NetworkTable;



public class VisionCommunication {

	//TODO Definition des constantes du reseau Vision System. Chaque constante represente le nom d'une une valeur a lire ou a ecrire
	public static final String TABLE_NAME = "visioncamera";
	
	public static final String CAMERA1_IP = "camera1ip";
	public static final String CAMERA2_IP = "camera12p";
	
	
	public static final String ONBOARD_ACCEL_X = "onBoardaccelerometerX";
	public static final String ONBOARD_ACCEL_Y = "onBoardaccelerometerY";
	public static final String ONBOARD_ACCEL_Z = "onBoardaccelerometerZ";
	
	public static final String ROBOT_TARGET_LOCKED = "robottargetlocked";
	public static final String RADAR_TARGET_LOCKED = "radartargetlocked";
	
	public static final String CAMERA1_DIST_TARGET = "camera1distancefromtarget";
	public static final String CAMERA1_DELTA_TARGET = "camera1deltafromtarget";
	public static final String CAMERA2_DIST_TARGET = "camera2distancefromtarget";
	public static final String CAMERA2_DELTA_TARGET = "camera2deltafromtarget";
	
	//TODO Definition des constantes de commandes du Vision System.
	public static final String SWITCH_CAMERA = "switchcamera";
	public static final String CURRENT_CAMERA = "currentcamera";
	
	
	
	
	//TODO definition des listeners sur la network table
	private NetworkTable cameraTable;
	
	public VisionCommunication(NetworkTable cameraTable) {
		this.cameraTable = cameraTable;
	}
	
	public void updateData() {
    	// Data qui doit etre change sur la NetworkTable a chaque loop du robot
    	
    	
    }
	//***************************************************************************************
	//Getters
	//***************************************************************************************
	public Double getCamera1DistTarget() {
		
		return cameraTable.getNumber(CAMERA1_DIST_TARGET,0);
	}
	public Double getCamera2DistTarget() {
		
		return cameraTable.getNumber(CAMERA2_DIST_TARGET,0);
	}
	
	public Double getCamera1DeltaTarget() {
		
		return cameraTable.getNumber(CAMERA1_DELTA_TARGET,0);
	}
	public Double getCamera2DeltaTarget() {
		
		return cameraTable.getNumber(CAMERA2_DELTA_TARGET,0);
	}
	public Boolean getRobotTargetLocked() {
		
		return cameraTable.getBoolean(ROBOT_TARGET_LOCKED, false);
	}
	public Boolean getRadarTargetLocked() {
		
		return cameraTable.getBoolean(RADAR_TARGET_LOCKED, false);
		
	}
	public String getCamera1IP() {
		
		return  cameraTable.getString(CAMERA1_IP,null);
	}
	public String getCamera2IP() {
		
		return cameraTable.getString(CAMERA2_IP,null);
	}
	public Double[] getOnBoardAccelData() {
    	
		Double[] accelData = new Double[3];
		accelData[0] = cameraTable.getNumber(ONBOARD_ACCEL_X,0);
		accelData[1] = cameraTable.getNumber(ONBOARD_ACCEL_Y,0);
		accelData[2] = cameraTable.getNumber(ONBOARD_ACCEL_Z,0);
    	
    	return accelData;
    }
	public int getCurrentCamera() {
		int currentCam = (int) cameraTable.getNumber(CURRENT_CAMERA,0);
		return currentCam;
	}
	
	
	
	//***************************************************************************************
	//Setters
	//***************************************************************************************
	public void putRobotTargetLocked(Boolean locked) {
		
		cameraTable.putBoolean(ROBOT_TARGET_LOCKED, locked);
	}
	public void putRadarTargetLocked(Boolean locked) {
		
		cameraTable.putBoolean(RADAR_TARGET_LOCKED, locked);
	}
	
	public void putCamera1DistTarget(Double distance) {
		
		cameraTable.putNumber(CAMERA1_DIST_TARGET,distance);
	}
	public void putCamera2DistTarget(Double distance) {
		
		cameraTable.putNumber(CAMERA2_DIST_TARGET,distance);
	}
	
	public void putCamera1DeltaTarget(Double delta) {
		
		cameraTable.putNumber(CAMERA1_DELTA_TARGET,delta);
	}
	public void putCamera2DeltaTarget(Double delta) {
		
		cameraTable.putNumber(CAMERA2_DELTA_TARGET,delta);
	}
	public void putCamera1IP(String ip) {
		
		cameraTable.putString(CAMERA1_IP,ip);
	}
	public void putCamera2IP(String ip) {
		
		cameraTable.putString(CAMERA2_IP,ip);
	}
	public void putCurrentCamera(int cameraNumber) {
		cameraTable.putNumber(CURRENT_CAMERA,cameraNumber);
		
	}
	public void switchCamera(int cameraNumber) {
		cameraTable.putNumber(SWITCH_CAMERA,cameraNumber);
		
	}
	

}