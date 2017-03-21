package org.usfirst.frc.team5952.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Trap extends Subsystem {
	
	Solenoid solenoid1 = new Solenoid(0);
    Solenoid solenoid2 = new Solenoid(1);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());rwa
    }
    
    public Boolean checkOpenned() {
    	
    	if (solenoid1.get() && solenoid2.get() ) {
    		
    		return true;
    		
    	} else {
    		
    		return false;
    		
    	}
    	
    	
    }
    public Boolean checkClosed() {
    	
    	if (!solenoid1.get() && !solenoid2.get() ) {
    		
    		return true;
    		
    	} else {
    		
    		return false;
    		
    	}
    	
    	
    }
    
    public void openTrap() {
    	
    	solenoid1.set(true);
    	solenoid2.set(true);
    	
    }
    public void closeTrap() {
    	
    	solenoid1.set(false);
    	solenoid2.set(false);
    }
    public void stop() {
    	
    	solenoid1.set(false);
    	solenoid2.set(false);
    }
}

