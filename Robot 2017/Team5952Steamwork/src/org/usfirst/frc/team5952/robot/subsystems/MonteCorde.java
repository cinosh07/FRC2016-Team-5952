package org.usfirst.frc.team5952.robot.subsystems;
import org.usfirst.frc.team5952.robot.Robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class MonteCorde extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	//TODO ajouter le senseur
    private SpeedController moteurCorde;
    

    public MonteCorde() {
    	moteurCorde = new Talon(2);
    	
    }
    
   
	public void monte(){
		//Robot.oi.getJoystick().getThrottle()
		moteurCorde.set(-1.0);
    	//moteurCorde.set(calibrateThrottle(Robot.oi.getJoystick().getThrottle()));
    }
    
    public void arrete(){
    	moteurCorde.set(0.0);
    }
    
    public void recule(){
    	moteurCorde.set(1.0);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    
    private double calibrateThrottle(double throttleValue){

		
		if (throttleValue > Robot.maxThrottle) {
			
			return -Robot.maxThrottle;
			
		} else if (throttleValue < Robot.minThrottle) {
			
			return -Robot.minThrottle;
			
		} else {
			return -throttleValue;
		}
		
		
	}
}

