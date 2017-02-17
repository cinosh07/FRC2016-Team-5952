package org.usfirst.frc.team5952.robot.commands;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionCommunication {

	// TODO Definition des constantes du reseau Vision System. Chaque constante
	// represente le nom d'une une valeur a lire ou a ecrire

	public static final String DEFAULT_CAMERA_1_NAME = "camera1";
	public static final String DEFAULT_CAMERA_2_NAME = "camera2";
	public static final String TABLE_NAME = "visioncamera";

	public static final String CAMERA1_IP = "camera1ip";
	public static final String CAMERA2_IP = "camera2ip";

	public static final String ONBOARD_ACCEL_X = "onBoardaccelerometerX";
	public static final String ONBOARD_ACCEL_Y = "onBoardaccelerometerY";
	public static final String ONBOARD_ACCEL_Z = "onBoardaccelerometerZ";

	public static final String ROBOT_TARGET_LOCKED = "robottargetlocked";
	public static final String RADAR_TARGET_LOCKED = "radartargetlocked";

	public static final String CAMERA1_DIST_TARGET = "camera1distancefromtarget";
	public static final String CAMERA1_DELTA_TARGET = "camera1deltafromtarget";
	public static final String CAMERA2_DIST_TARGET = "camera2distancefromtarget";
	public static final String CAMERA2_DELTA_TARGET = "camera2deltafromtarget";

	// TODO Definition des constantes de commandes du Vision System.
	public static final String SWITCH_CAMERA = "switchcamera";
	public static final String CURRENT_CAMERA = "currentcamera";

	public static final String CAMERA1_SENSOR_DISTANCE = "camera1sensordistance";
	public static final String RADAR_SENSOR_DISTANCE = "radarsensordistance";
	public static final String ROBOT_BACK_SENSOR_DISTANCE = "robotbacksensordistance";
	public static final String RADAR_GYRO_X = "radargyrox";
	public static final String RADAR_GYRO_Y = "radargyroy";
	public static final String RADAR_GYRO_Z = "radargyroz";
	public static final String RADAR_ACC_X = "radaraccx";
	public static final String RADAR_ACC_Y = "radaraccy";
	public static final String RADAR_ACC_Z = "radaraccz";
	public static final String RADAR_MAG_X = "radarmagx";
	public static final String RADAR_MAG_Y = "radarmagy";
	public static final String RADAR_MAG_Z = "radarmagz";
	public static final String CURRENT_STATE = "currentstate";
	public static final String REMOTE_VISION_POT_1 = "remotevisionpot1";
	public static final String REMOTE_VISION_POT_2 = "remotevisionpot2";
	public static final String REMOTE_VISION_POT_3 = "remotevisionpot3";
	public static final String REMOTE_VISION_RADAR_TILT = "remotevisionradartilt";
	public static final String REMOTE_VISION_RADAR_PAN = "remotevisionradarpan";
	public static final String REMOTE_VISION_BUTTON2_STATE = "remotevisionbutton2state";
	public static final String REMOTE_VISION_BUTTON3_STATE = "remotevisionbutton3state";
	public static final String REMOTE_VISION_BUTTON4_STATE = "remotevisionbutton4state";
	public static final String REMOTE_VISION_BUTTON5_STATE = "remotevisionbutton5state";
	public static final String FRONT_OPERATION = "frontoperation";
	public static final String ROBOT_COMPASS = "robotcompass";

	// TODO definition des listeners sur la network table
	private NetworkTable cameraTable;

	public VisionCommunication(NetworkTable cameraTable) {
		this.cameraTable = cameraTable;
	}

	public void updateData() {
		// Data qui doit etre change sur la NetworkTable a chaque loop du robot

	}

	// ***************************************************************************************
	// Getters
	// ***************************************************************************************
	public double getCamera1DistTarget() {

		return cameraTable.getNumber(CAMERA1_DIST_TARGET, 0);
	}

	public double getCamera2DistTarget() {

		return cameraTable.getNumber(CAMERA2_DIST_TARGET, 0);
	}

	public double getCamera1DeltaTarget() {

		return cameraTable.getNumber(CAMERA1_DELTA_TARGET, 0);
	}

	public double getCamera2DeltaTarget() {

		return cameraTable.getNumber(CAMERA2_DELTA_TARGET, 0);
	}

	public Boolean getRobotTargetLocked() {

		return cameraTable.getBoolean(ROBOT_TARGET_LOCKED, false);
	}

	public Boolean getRadarTargetLocked() {

		return cameraTable.getBoolean(RADAR_TARGET_LOCKED, false);

	}

	public String getCamera1IP() {

		return cameraTable.getString(CAMERA1_IP, "");
	}

	public String getCamera2IP() {

		return cameraTable.getString(CAMERA2_IP, "");
	}

	public double[] getOnBoardAccelData() {

		double[] accelData = new double[3];
		accelData[0] = cameraTable.getNumber(ONBOARD_ACCEL_X, 0);
		accelData[1] = cameraTable.getNumber(ONBOARD_ACCEL_Y, 0);
		accelData[2] = cameraTable.getNumber(ONBOARD_ACCEL_Z, 0);

		return accelData;
	}

	public int getCurrentCamera() {
		int currentCam = (int) cameraTable.getNumber(CURRENT_CAMERA, 0);
		return currentCam;
	}

	public double getCamera1SensorDistTarget() {

		return cameraTable.getNumber(CAMERA1_SENSOR_DISTANCE, 0.0);
	}

	public double getRadarSensorDistTarget() {

		return cameraTable.getNumber(RADAR_SENSOR_DISTANCE, 0.0);
	}

	public double getRobotBackSensorDistTarget() {
		return cameraTable.getNumber(ROBOT_BACK_SENSOR_DISTANCE, 0.0);

	}

	public double getRadarGyroX() {

		return cameraTable.getNumber(RADAR_GYRO_X, 0.0);

	}

	public double getRadarGyroY() {

		return cameraTable.getNumber(RADAR_GYRO_Y, 0.0);

	}

	public double getRadarGyroZ() {

		return cameraTable.getNumber(RADAR_GYRO_Z, 0.0);

	}

	public double getRadarAccX() {

		return cameraTable.getNumber(RADAR_ACC_X, 0.0);

	}

	public double getRadarAccY() {

		return cameraTable.getNumber(RADAR_ACC_Y, 0.0);

	}

	public double getRadarAccZ() {

		return cameraTable.getNumber(RADAR_ACC_Z, 0.0);

	}

	public double getRadarMagX() {

		return cameraTable.getNumber(RADAR_MAG_X, 0.0);

	}

	public double getRadarMagY() {

		return cameraTable.getNumber(RADAR_MAG_Y, 0.0);

	}

	public double getRadarMagZ() {

		return cameraTable.getNumber(RADAR_MAG_Z, 0.0);

	}
	
	public int getCurrentState() {

		Double val = cameraTable.getNumber(CURRENT_STATE, 0.0);
		return val.intValue();

	}

	public int getRemoteVisionPotentiometer1() {

		Double val = cameraTable.getNumber(REMOTE_VISION_POT_1, 0.0);
		return val.intValue();

	}

	public int getRemoteVisionPotentiometer2() {

		Double val = cameraTable.getNumber(REMOTE_VISION_POT_2, 0.0);
		return val.intValue();

	}

	public int getRemoteVisionPotentiometer3() {

		Double val = cameraTable.getNumber(REMOTE_VISION_POT_3,  0.0);
		return val.intValue();

	}

	public int getRemoteVisionRadarTilt() {

		Double val = cameraTable.getNumber(REMOTE_VISION_RADAR_TILT,  0.0);
		return val.intValue();

	}

	public int getRemoteVisionRadarPan() {

		Double val = cameraTable.getNumber(REMOTE_VISION_RADAR_PAN,  0.0);
		return val.intValue();

	}

	public int getRemoteVisionButton2State() {

		Double val = cameraTable.getNumber(REMOTE_VISION_BUTTON2_STATE,  0.0);
		return val.intValue();

	}

	public int getRemoteVisionButton3State() {

		Double val = cameraTable.getNumber(REMOTE_VISION_BUTTON3_STATE,  0.0);
		return val.intValue();

	}

	public int getRemoteVisionButton4State() {

		Double val = cameraTable.getNumber(REMOTE_VISION_BUTTON4_STATE,  0.0);
		return val.intValue();

	}

	public int getRemoteVisionButton5State() {

		Double val = cameraTable.getNumber(REMOTE_VISION_BUTTON5_STATE,  0.0);
		return val.intValue();

	}
	
	public boolean getFrontOperation() {

		return cameraTable.putBoolean(FRONT_OPERATION, true);

	}
	
	public double getRobotCompass() {
		
		return cameraTable.getNumber(ROBOT_COMPASS, 0.0);
	}


	// ***************************************************************************************
	// Setters
	// ***************************************************************************************
	public void putRobotTargetLocked(Boolean locked) {

		cameraTable.putBoolean(ROBOT_TARGET_LOCKED, locked);
	}

	public void putRadarTargetLocked(Boolean locked) {

		cameraTable.putBoolean(RADAR_TARGET_LOCKED, locked);
	}

	public void putCamera1DistTarget(double distance) {

		cameraTable.putNumber(CAMERA1_DIST_TARGET, distance);
	}

	public void putCamera2DistTarget(double distance) {

		cameraTable.putNumber(CAMERA2_DIST_TARGET, distance);
	}

	public void putCamera1DeltaTarget(double delta) {

		cameraTable.putNumber(CAMERA1_DELTA_TARGET, delta);
	}

	public void putCamera2DeltaTarget(double delta) {

		cameraTable.putNumber(CAMERA2_DELTA_TARGET, delta);
	}

	public void putCamera1IP(String ip) {

		cameraTable.putString(CAMERA1_IP, ip);
	}

	public void putCamera2IP(String ip) {

		cameraTable.putString(CAMERA2_IP, ip);
	}

	public void putCurrentCamera(int cameraNumber) {
		cameraTable.putNumber(CURRENT_CAMERA, cameraNumber);

	}

	public void switchCamera(int cameraNumber) {
		cameraTable.putNumber(SWITCH_CAMERA, cameraNumber);

	}

	public void putCamera1SensorDistTarget(double distance) {

		cameraTable.putNumber(CAMERA1_SENSOR_DISTANCE, distance);
	}

	public void putRadarSensorDistTarget(double distance) {

		cameraTable.putNumber(RADAR_SENSOR_DISTANCE, distance);
	}

	public void putRobotBackSensorDistTarget(double distance) {
		cameraTable.putNumber(ROBOT_BACK_SENSOR_DISTANCE, distance);

	}

	public void putRadarGyroX(double x) {

		cameraTable.putNumber(RADAR_GYRO_X, x);

	}

	public void putRadarGyroY(double y) {

		cameraTable.putNumber(RADAR_GYRO_Y, y);

	}

	public void putRadarGyroZ(double z) {

		cameraTable.putNumber(RADAR_GYRO_Z, z);

	}

	public void putRadarAccX(double x) {

		cameraTable.putNumber(RADAR_ACC_X, x);

	}

	public void putRadarAccY(double y) {

		cameraTable.putNumber(RADAR_ACC_Y, y);

	}

	public void putRadarAccZ(double z) {

		cameraTable.putNumber(RADAR_ACC_Z, z);

	}

	public void putRadarMagX(double x) {

		cameraTable.putNumber(RADAR_MAG_X, x);

	}

	public void putRadarMagY(double y) {

		cameraTable.putNumber(RADAR_MAG_Y, y);

	}

	public void putRadarMagZ(double z) {

		cameraTable.putNumber(RADAR_MAG_Z, z);

	}

	public void putCurrentState(int state) {

		cameraTable.putNumber(CURRENT_STATE, state);

	}

	public void putRemoteVisionPotentiometer1(int value) {

		cameraTable.putNumber(REMOTE_VISION_POT_1, value);

	}

	public void putRemoteVisionPotentiometer2(int value) {

		cameraTable.putNumber(REMOTE_VISION_POT_2, value);

	}

	public void putRemoteVisionPotentiometer3(int value) {

		cameraTable.putNumber(REMOTE_VISION_POT_3, value);

	}

	public void putRemoteVisionRadarTilt(int value) {

		cameraTable.putNumber(REMOTE_VISION_RADAR_TILT, value);

	}

	public void putRemoteVisionRadarPan(int value) {

		cameraTable.putNumber(REMOTE_VISION_RADAR_PAN, value);

	}

	public void putRemoteVisionButton2State(int value) {

		cameraTable.putNumber(REMOTE_VISION_BUTTON2_STATE, value);

	}

	public void putRemoteVisionButton3State(int value) {

		cameraTable.putNumber(REMOTE_VISION_BUTTON3_STATE, value);

	}

	public void putRemoteVisionButton4State(int value) {

		cameraTable.putNumber(REMOTE_VISION_BUTTON4_STATE, value);

	}

	public void putRemoteVisionButton5State(int value) {

		cameraTable.putNumber(REMOTE_VISION_BUTTON5_STATE, value);

	}
	
	public void putFrontOperation(boolean value) {

		cameraTable.putBoolean(FRONT_OPERATION, value);

	}
	
	public void putRobotCompass(double angle) {
		
		cameraTable.putNumber(ROBOT_COMPASS, angle);
	}

}