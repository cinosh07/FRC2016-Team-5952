package org.usfirst.frc.team5952.robot.commands;




import org.usfirst.frc.team5952.robot.visionSystem.ClientStreamingStateListener;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class ClientVisionCommunication extends VisionCommunication {

	private NetworkTable cameraTable;
	
	public ClientVisionCommunication(NetworkTable cameraTable) {
		super(cameraTable);
		this.cameraTable = cameraTable;
		
		cameraTable.addTableListener(VisionCommunication.SWITCH_CAMERA, new ClientStreamingStateListener(), true);
		cameraTable.addTableListener(VisionCommunication.ONBOARD_ACCEL_X, new ClientStreamingStateListener(), true);
		cameraTable.addTableListener(VisionCommunication.ONBOARD_ACCEL_Y, new ClientStreamingStateListener(), true);
		cameraTable.addTableListener(VisionCommunication.ONBOARD_ACCEL_Z, new ClientStreamingStateListener(), true);
		// TODO Ajouter les lister qu'il faut suivre
	}
	
//TODO mettre les executions faite par le client vision seulement ici
	



}
