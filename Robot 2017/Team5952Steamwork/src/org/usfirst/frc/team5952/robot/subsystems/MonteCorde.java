package org.usfirst.frc.team5952.robot.subsystems;
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
    	moteurCorde = new Talon(3);
    }
    
    public void monte(){
    	moteurCorde.set(1.0);
    }
    
    public void arrete(){
    	moteurCorde.set(0.0);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
}

