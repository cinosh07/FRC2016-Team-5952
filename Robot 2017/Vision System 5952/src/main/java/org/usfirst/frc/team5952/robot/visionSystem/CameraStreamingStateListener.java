package org.usfirst.frc.team5952.robot.visionSystem;

import org.usfirst.frc.team5952.robot.commands.VisionCommunication;

import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class CameraStreamingStateListener implements ITableListener {

	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
		Double valDouble = (Double) value;
		 switch (key) {
         
		 case VisionCommunication.SWITCH_CAMERA:  
        	 if( CameraManager.getInstance().visionCommunication.getCurrentCamera() == 1) {
    			 
        		 CameraManager.getInstance().setCurrentCamera(valDouble.intValue());
        		 System.out.println("Command received Switch to Camera : "  + value);
    			 
    		 } else if (CameraManager.getInstance().visionCommunication.getCurrentCamera() == 2 ) {
    			 
    			 CameraManager.getInstance().setCurrentCamera(valDouble.intValue());
    			 System.out.println("Command received Switch to Camera : "  + value);
    		 }
             break;
           //TODO Ajouter quoi faire aux autres commande recu
		 
		 
		 }
		
	}

}
