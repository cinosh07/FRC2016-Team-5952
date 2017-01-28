package org.usfirst.frc.team5952.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Trap extends Subsystem {
	
	DoubleSolenoid solenoid1 = new DoubleSolenoid(1,2);
    DoubleSolenoid solenoid2 = new DoubleSolenoid(1,2);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());rwa
    }
    
    public void openTrap() {
    	
    	solenoid1.set(DoubleSolenoid.Value.kForward);
    	solenoid2.set(DoubleSolenoid.Value.kForward);
    	
    }
    public void closeTrap() {
    	
    	solenoid1.set(DoubleSolenoid.Value.kReverse);
    	solenoid2.set(DoubleSolenoid.Value.kReverse);
    }
    public void stop() {
    	
    	solenoid1.set(DoubleSolenoid.Value.kOff);
    	solenoid2.set(DoubleSolenoid.Value.kOff);
    }
}

