package org.usfirst.frc.team5952.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearDropRightPosition extends CommandGroup {

    public GearDropRightPosition() {
       
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(20.0,-0.5));
    	// Rotation 45 degrees a gauche
    	addSequential(new DriveTurn(20.0,-0.5,45.0));
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(20.0,-0.5));
    	// Rotation 45 degrees a Droite
    	addSequential(new DriveTurn(20.0,-0.5,-45.0));
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(20.0,-0.5));
    	// Ouvrir la trappe
    	addSequential(new OpenTrap());
    	// Recule de 20 pouces
    	addSequential(new DriveStraight(20.0, 0.5));
    	
    }
}
