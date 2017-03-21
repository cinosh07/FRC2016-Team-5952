package org.usfirst.frc.team5952.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearDropCenterPosition extends CommandGroup {

    public GearDropCenterPosition() {

    	addSequential(new DriveStraight(80.0,-0.5));
    	addSequential(new OpenTrap());
    	addSequential(new DriveStraight(20.0, 0.5));	
    	
    }
}
