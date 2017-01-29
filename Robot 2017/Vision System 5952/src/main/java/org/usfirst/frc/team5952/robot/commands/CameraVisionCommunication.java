package org.usfirst.frc.team5952.robot.commands;




import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class CameraVisionCommunication extends VisionCommunication {

	private NetworkTable cameraTable;
	
	public CameraVisionCommunication(NetworkTable cameraTable) {
		super(cameraTable);
		this.cameraTable = cameraTable;
		// TODO Auto-generated constructor stub
	}
	
//TODO mettre les executions faite par les cameras seulement ici
	



}
