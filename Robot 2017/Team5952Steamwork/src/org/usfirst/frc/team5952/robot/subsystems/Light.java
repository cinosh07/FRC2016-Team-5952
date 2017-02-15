package org.usfirst.frc.team5952.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Light extends Subsystem {
	
	Relay Light = new Relay(0);

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void openLight(){
    Light.set(Relay.Value.kForward);
    }
    
    public void closeLight(){
    	Light.set(Relay.Value.kOff);
    }
    
    public void Stop(){
    	Light.set(Relay.Value.kOff);
    }
  
}

