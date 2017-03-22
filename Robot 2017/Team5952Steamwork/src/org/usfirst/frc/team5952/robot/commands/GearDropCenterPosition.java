package org.usfirst.frc.team5952.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearDropCenterPosition extends CommandGroup {


    public GearDropCenterPosition() {

    	//Avance de 80 pouces
    	addSequential(new DriveStraight(80.0,-0.5));
    	// Ouvrir la trap
    	addSequential(new OpenTrap());
    	//Delais
    	addSequential(new Delay(1.0));
    	// Recule de 20 pouces
    	addSequential(new DriveStraightReverse(20.0, 0.2));
    	// Fermer la trap
    	addSequential(new CloseTrap());
    	
    }
}
