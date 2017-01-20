package org.usfirst.frc.team5952.robot.visionSystem;

import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class StreamingStateListener implements ITableListener {

	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
		// TODO Auto-generated method stub
		if (CameraManager.getInstance().getCameraName().matches((String)value)) {
			CameraManager.getInstance().startStreaming();
		} else {
			CameraManager.getInstance().stopStreaming();
		}
		System.out.println("Command received from robot: " + key + " Value: " + value);
	}

}
