package org.usfirst.frc.team5952.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Trap extends Subsystem {

    
	//TODO Initialiser le solenoid
	DoubleSolenoid solenoid1 = new DoubleSolenoid(1,2);

	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());rwa
    }
    
    public void openTrap() {
    	
    	//TODO Ouvrir le solenoid
    	solenoid1.set(DoubleSolenoid.Value.kForward);
    	
    }
    public void closeTrap() {
    	
    	//TODO Fermer le solenoid
    	solenoid1.set(DoubleSolenoid.Value.kReverse);
    	
    }
    public void stop() {
    	
    	solenoid1.set(DoubleSolenoid.Value.kOff);
    	
    }
}

