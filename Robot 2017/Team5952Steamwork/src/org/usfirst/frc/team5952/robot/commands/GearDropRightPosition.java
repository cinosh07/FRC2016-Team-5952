package org.usfirst.frc.team5952.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearDropRightPosition extends CommandGroup {

    public GearDropRightPosition() {
       
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(81.891 ,-0.7));
    	// Rotation 45 degrees a droite
    	addSequential(new DriveTurn(0.0,-0.5,-63.11));
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(79.604, -0.5));
    	// Ouvrir la trappe
    	addSequential(new OpenTrap());
    	//Delais
    	addSequential(new Delay(0.4));
    	//recule de 20 pouces
    	addSequential(new DriveStraightReverse(80, 0.6));
    	// Rotation 45 degrees a gauche
    	addSequential(new DriveTurn(20.0,-0.5,63.11));
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(32.0,-0.5));
    	// Fermer la trappe
    	addSequential(new CloseTrap());
    	
    	
    }
}
