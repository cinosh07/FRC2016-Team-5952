package org.usfirst.frc.team5952.robot.visionSystem;

import java.math.BigDecimal;

import org.usfirst.frc.team5952.robot.commands.VisionCommunication;

import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class ClientStreamingStateListener implements ITableListener {

	private Double accelX = 0.0;
	private Double accelY = 0.0;;
	private Double accelZ = 0.0;;
	
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
			 accelX = valDouble;
			 break;
		 case VisionCommunication.ONBOARD_ACCEL_Y: 
			 accelY = valDouble;
			 break;
		 case VisionCommunication.ONBOARD_ACCEL_Z: 
			 accelZ = valDouble;
			 putGforce();
			 break;
          //TODO Ajouter quoi faire aux autres commande recu
		  
		 }	
		
	}
	private void putGforce() {
		
		double g = Math.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
		
		BigDecimal bd = new BigDecimal(g);
		bd= bd.setScale(2,BigDecimal.ROUND_DOWN);
		g = bd.doubleValue();
		
		StreamManager.getInstance().osd.putGForce(g);
		if (g >= 2.0) {
			StreamManager.getInstance().osd.putGForceOverloadLabel(true);
		} else {
			StreamManager.getInstance().osd.putGForceOverloadLabel(false);
		}	
		
	}

}
