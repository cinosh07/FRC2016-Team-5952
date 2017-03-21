package org.usfirst.frc.team5952.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearDropCenterPosition extends CommandGroup {

    public GearDropCenterPosition() {

    	//Avance de 80 pouces
    	addSequential(new DriveStraight(80.0,-0.5));
    	// Ouvrir la trappe
    	addSequential(new OpenTrap());
    	// Recule de 20 pouces
    	addSequential(new DriveStraight(20.0, 0.5));	
    	
    }
}
