package org.usfirst.frc.team5952.robot.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */


public class OnBoardAccelerometer extends Subsystem {
	
	public BuiltInAccelerometer  builtInAccelerometer = new BuiltInAccelerometer();
	 
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
		 
 
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
		 
    }
    
    public Double[] readValue() {
    	
    	Double[] values = new Double[3];
    	values[0] = builtInAccelerometer.getX();
    	values[1] = builtInAccelerometer.getY();
    	values[2] = builtInAccelerometer.getZ();
    	
    	//System.out.println(builtInAccelerometer.getX() + ", " + builtInAccelerometer.getY() + ", " + builtInAccelerometer.getZ());
    	
    	return values;
    }
    
    
}

