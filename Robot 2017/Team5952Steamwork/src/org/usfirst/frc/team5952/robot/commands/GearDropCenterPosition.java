package org.usfirst.frc.team5952.robot.commands;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearDropCenterPosition extends CommandGroup {


    public GearDropCenterPosition() {

//    	addSequential(new DriveStraight(80.0,-0.58));
    	
    	//Avance de 80 pouces
    	addSequential(new DriveStraight(82.0,-0.58));
    	//Delais
    	addSequential(new Delay(0.5));
    	// Ouvrir la trap
    	addSequential(new OpenTrap());
    	//Delais
    	addSequential(new Delay(0.5));
    	// Recule de 20 pouces
    	addSequential(new DriveStraightReverse(40.0, 0.6));
    	// Fermer la trap
    	addSequential(new CloseTrap());
    	
    }
}
