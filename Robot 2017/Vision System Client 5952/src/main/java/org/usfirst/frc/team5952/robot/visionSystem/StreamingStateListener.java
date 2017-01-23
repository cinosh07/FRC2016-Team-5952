package org.usfirst.frc.team5952.robot.visionSystem;

import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class StreamingStateListener implements ITableListener {

	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
		
		if (((int)value) == -2 ) {
			
			System.out.println("Command received: Stop Playing Camera 2");
			
		} else if (((int)value) == -1 ) {
			
			System.out.println("Command received: Stop Playing Camera 1");
			
		} else if (((int)value) == 0 ) {
			
			System.out.println("Command received: Stop Playing All Cameras");
			
		} else if (((int)value) == 1 ) {
			
			System.out.println("Command received: Start Playing Camera 1");
			
		} else if (((int)value) == 2 ) {
			
			System.out.println("Command received: Start Playing Camera 2");
			
		} else if (((int)value) == 3 ) {
			
			System.out.println("Command received: Start Playing All Cameras");
			
		}
		
		System.out.println("Raw Command received from robot: " + key + " Value: " + value);
	}

}
