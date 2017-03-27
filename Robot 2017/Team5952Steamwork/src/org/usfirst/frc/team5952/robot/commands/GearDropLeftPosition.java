package org.usfirst.frc.team5952.robot.commands;

import org.usfirst.frc.team5952.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearDropLeftPosition extends CommandGroup {

    public GearDropLeftPosition() {
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(80.0,-0.7));
    	// Rotation 45 degrees a droite
    	addSequential(new DriveTurn(20.0,-0.5,60.0));
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(40.0, -0.5));
    	// Ouvrir la trappe
    	addSequential(new OpenTrap());
    	//Delais
    	addSequential(new Delay(0.5));
    	//recule de 20 pouces
    	addSequential(new DriveStraightReverse(24, 0.6));
    	// Rotation 45 degrees a gauche
    	addSequential(new DriveTurn(20.0,-0.5,-60.0));
    	//Avance de 20 pouces
    	addSequential(new DriveStraight(30.0,-0.5));
    	// Fermer la trappe
    	addSequential(new CloseTrap());
    	
    }
}
