package org.usfirst.frc.team5952.robot.commands;

import org.usfirst.frc.team5952.robot.Robot;


import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class RobotVisionCommunication extends VisionCommunication {

	private NetworkTable cameraTable;
	
	public RobotVisionCommunication(NetworkTable cameraTable) {
		super(cameraTable);
		this.cameraTable = cameraTable;
		// TODO Auto-generated constructor stub
	}
	
//TODO mettre les executions faite par le robot seulement ici
	
public void putOnBoardAccelData() {
    	
    	cameraTable.putNumber(ONBOARD_ACCEL_X, Robot.onBoardAccelerometer.readValue()[0]);
    	cameraTable.putNumber(ONBOARD_ACCEL_Y, Robot.onBoardAccelerometer.readValue()[1]);
    	cameraTable.putNumber(ONBOARD_ACCEL_Z, Robot.onBoardAccelerometer.readValue()[2]);
    	
    }


}
