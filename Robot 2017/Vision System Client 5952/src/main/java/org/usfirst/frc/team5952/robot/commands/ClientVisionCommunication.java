package org.usfirst.frc.team5952.robot.commands;




import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class ClientVisionCommunication extends VisionCommunication {

	private NetworkTable cameraTable;
	
	public ClientVisionCommunication(NetworkTable cameraTable) {
		super(cameraTable);
		this.cameraTable = cameraTable;
		// TODO Auto-generated constructor stub
	}
	
//TODO mettre les executions faite par le client vision seulement ici
	



}
