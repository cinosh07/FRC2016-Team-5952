package org.usfirst.frc.team5952.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearDropRightPositionRed extends CommandGroup {

    public GearDropRightPositionRed() {
       
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(68.891 ,-0.7));
    	// Rotation 45 degrees a droite
    	addSequential(new DriveTurn(0.0,0.3,56.11));
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(78.604, -0.65));
    	// Ouvrir la trappe
    	addSequential(new OpenTrap());
    	//Delais
    	addSequential(new Delay(0.6));
    	//recule de 20 pouces
    	addSequential(new DriveStraightReverse(80, 0.6));
    	// Rotation 45 degrees a gauche
    	addSequential(new DriveTurn(20.0,0.3,-56.11));
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(72.0,-0.8));
    	// Fermer la trappe
    	addSequential(new CloseTrap());
    	
    	
    }
}
