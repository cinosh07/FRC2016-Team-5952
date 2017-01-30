package org.usfirst.frc.team5952.robot.commands;




import org.usfirst.frc.team5952.robot.visionSystem.CameraStreamingStateListener;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class CameraVisionCommunication extends VisionCommunication {

	private NetworkTable cameraTable;
	
	public CameraVisionCommunication(NetworkTable cameraTable) {
		super(cameraTable);
		this.cameraTable = cameraTable;
		
		cameraTable.addTableListener(VisionCommunication.SWITCH_CAMERA, new CameraStreamingStateListener(), true);
		// TODO Ajouter les lister qu'il faut suivre
	}
	
//TODO mettre les executions faite par les cameras seulement ici
	



}
