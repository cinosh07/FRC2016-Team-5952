package org.usfirst.frc.team5952.robot.visionSystem;

import org.usfirst.frc.team5952.robot.commands.VisionCommunication;

import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class ClientStreamingStateListener implements ITableListener {

	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
		
		switch (key) {
        
		 case VisionCommunication.SWITCH_CAMERA:  
       	 if( (int)value == 1) {
   			 
       		StreamManager.getInstance().osd.cameraNameLabel.setText(StreamManager.getInstance().DEFAULT_CAMERA_1_NAME);
       		 System.out.println("Command received Switch to Camera : "  + value);
   			 
   		 } else if (StreamManager.getInstance().visionCommunication.getCurrentCamera() == 2 ) {
   			 
   			StreamManager.getInstance().osd.cameraNameLabel.setText(StreamManager.getInstance().DEFAULT_CAMERA_2_NAME);
   			 System.out.println("Command received Switch to Camera : "  + value);
   		 }
            break;
          //TODO Ajouter quoi faire aux autres commande recu
		 
		 
		 }
		
		System.out.println("Raw Command received from robot: " + key + " Value: " + value);
	}

}
