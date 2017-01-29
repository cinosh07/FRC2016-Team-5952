package org.usfirst.frc.team5952.robot.commands;

import org.usfirst.frc.team5952.robot.Robot;



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
	
	public static final String CAMERA1_DIST_TARGET = "Camera1DistanceFromTarget";
	public static final String CAMERA1_DELTA_TARGET = "Camera1DeltaFromTarget";
	public static final String CAMERA2_DIST_TARGET = "Camera2DistanceFromTarget";
	public static final String CAMERA2_DELTA_TARGET = "Camera2DeltaFromTarget";
	
	
	//TODO definition des listeners sur la network table
	
	
	public VisionCommunication() {
		// TODO Auto-generated constructor stub
	}
	
	public void updateData() {
    	// Data qui doit etre change sur la NetworkTable a chaque loop du robot
    	Robot.cameraTable.putNumber(ONBOARD_ACCEL_X, Robot.onBoardAccelerometer.readValue()[0]);
    	Robot.cameraTable.putNumber(ONBOARD_ACCEL_Y, Robot.onBoardAccelerometer.readValue()[1]);
    	Robot.cameraTable.putNumber(ONBOARD_ACCEL_Z, Robot.onBoardAccelerometer.readValue()[2]);
    	
    }
	//***************************************************************************************
	//Getters
	//***************************************************************************************
	public Double getCamera1DistTarget() {
		
		return Robot.cameraTable.getNumber(CAMERA1_DIST_TARGET,0);
	}
	public Double getCamera2DistTarget() {
		
		return Robot.cameraTable.getNumber(CAMERA2_DIST_TARGET,0);
	}
	
	public Double getCamera1DeltaTarget() {
		
		return Robot.cameraTable.getNumber(CAMERA1_DELTA_TARGET,0);
	}
	public Double getCamera2DeltaTarget() {
		
		return Robot.cameraTable.getNumber(CAMERA2_DELTA_TARGET,0);
	}
	public Boolean getRobotTargetLocked() {
		
		return Robot.cameraTable.getBoolean(ROBOT_TARGET_LOCKED, false);
	}
	public Boolean getRadarTargetLocked() {
		
		return Robot.cameraTable.getBoolean(RADAR_TARGET_LOCKED, false);
		
	}
	public String getCamera1IP(String ip) {
		
		return  Robot.cameraTable.getString(CAMERA1_IP,null);
	}
	public String getCamera2IP(String ip) {
		
		return Robot.cameraTable.getString(CAMERA2_IP,null);
	}
	public Double[] getOnBoardAccelData() {
    	
		Double[] accelData = new Double[3];
		accelData[0] = Robot.cameraTable.getNumber(ONBOARD_ACCEL_X,0);
		accelData[1] = Robot.cameraTable.getNumber(ONBOARD_ACCEL_Y,0);
		accelData[2] = Robot.cameraTable.getNumber(ONBOARD_ACCEL_Z,0);
    	
    	return accelData;
    }
	
	
	//***************************************************************************************
	//Setters
	//***************************************************************************************
	public void putRobotTargetLocked(Boolean locked) {
		
		Robot.cameraTable.putBoolean(ROBOT_TARGET_LOCKED, locked);
	}
	public void putRadarTargetLocked(Boolean locked) {
		
		Robot.cameraTable.putBoolean(RADAR_TARGET_LOCKED, locked);
	}
	
	public void putCamera1DistTarget(Double distance) {
		
		Robot.cameraTable.putNumber(CAMERA1_DIST_TARGET,distance);
	}
	public void putCamera2DistTarget(Double distance) {
		
		Robot.cameraTable.putNumber(CAMERA2_DIST_TARGET,distance);
	}
	
	public void putCamera1DeltaTarget(Double delta) {
		
		Robot.cameraTable.putNumber(CAMERA1_DELTA_TARGET,delta);
	}
	public void putCamera2DeltaTarget(Double delta) {
		
		Robot.cameraTable.putNumber(CAMERA2_DELTA_TARGET,delta);
	}
	public void putCamera1IP(String ip) {
		
		Robot.cameraTable.putString(CAMERA1_IP,ip);
	}
	public void putCamera2IP(String ip) {
		
		Robot.cameraTable.putString(CAMERA2_IP,ip);
	}
	public void putOnBoardAccelData() {
    	
    	Robot.cameraTable.putNumber(ONBOARD_ACCEL_X, Robot.onBoardAccelerometer.readValue()[0]);
    	Robot.cameraTable.putNumber(ONBOARD_ACCEL_Y, Robot.onBoardAccelerometer.readValue()[1]);
    	Robot.cameraTable.putNumber(ONBOARD_ACCEL_Z, Robot.onBoardAccelerometer.readValue()[2]);
    	
    }

}