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
	
	public double putCamera1SensorDistTarget() {

		return cameraTable.getNumber(CAMERA1_SENSOR_DISTANCE, 0.0);
	}

	public double putRadarSensorDistTarget() {

		return cameraTable.getNumber(RADAR_SENSOR_DISTANCE, 0.0);
	}

	public double putRobotBackSensorDistTarget() {
		return cameraTable.getNumber(ROBOT_BACK_SENSOR_DISTANCE, 0.0);

	}

	public double putRadarGyroX() {
		
		return cameraTable.getNumber(RADAR_GYRO_X, 0.0);

	}

	public double putRadarGyroY() {
		
		return cameraTable.getNumber(RADAR_GYRO_Y, 0.0);

	}

	public double putRadarGyroZ() {
		
		return cameraTable.getNumber(RADAR_GYRO_Z, 0.0);

	}

	public double putRadarAccX() {
		
		return cameraTable.getNumber(RADAR_ACC_X, 0.0);

	}

	public double putRadarAccY() {
		
		return cameraTable.getNumber(RADAR_ACC_Y, 0.0);

	}

	public double putRadarAccZ() {
		
		return cameraTable.getNumber(RADAR_ACC_Z, 0.0);

	}

	public double putRadarMagX() {
		
		return cameraTable.getNumber(RADAR_MAG_X, 0.0);

	}

	public double putRadarMagY() {
		
		return cameraTable.getNumber(RADAR_MAG_Y, 0.0);

	}

	public double putRadarMagZ() {
		
		return cameraTable.getNumber(RADAR_MAG_Z, 0.0);

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

}