package org.usfirst.frc.team5952.robot.visionSystem;

import org.usfirst.frc.team5952.robot.commands.VisionCommunication;

import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class ClientStreamingStateListener implements ITableListener {

	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
		Double valDouble = (Double) value;
		
		
		switch (key) {
      
		 case VisionCommunication.SWITCH_CAMERA:  
       	 if( valDouble.intValue()  == 1) {
   			 
       		StreamManager.getInstance().osd.cameraNameLabel.setText(VisionCommunication.DEFAULT_CAMERA_1_NAME);
       		 System.out.println("Command received Switch to Camera : "  + valDouble.intValue());
   			 
   		 } else if (StreamManager.getInstance().visionCommunication.getCurrentCamera() == 2 ) {
   			 
   			StreamManager.getInstance().osd.cameraNameLabel.setText(VisionCommunication.DEFAULT_CAMERA_2_NAME);
   			 System.out.println("Command received Switch to Camera : "  + valDouble.intValue());
   		 }
            break;
            
		 case VisionCommunication.ONBOARD_ACCEL_X: 
			 System.out.println("Command received ONBOARD_ACCEL_X : "  + value);
			 break;
		 case VisionCommunication.ONBOARD_ACCEL_Y: 
			 System.out.println("Command received ONBOARD_ACCEL_Y : "  + value);
			 break;
		 case VisionCommunication.ONBOARD_ACCEL_Z: 
			 System.out.println("Command received ONBOARD_ACCEL_Z : "  + value);
			 break;
          //TODO Ajouter quoi faire aux autres commande recu
		 
		 
		 }
		
		System.out.println("Raw Command received from robot: " + key + " Value: " + value);
	}

}
