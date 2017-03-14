package org.usfirst.frc.team5952.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Autonomous extends CommandGroup {

    public Autonomous() {
    	//addSequential(new DriveStraight(500.0));
//        addSequential(new Pickup());
//        addSequential(new SetDistanceToBox(0.10));
//        // addSequential(new DriveStraight(4)); // Use Encoders if ultrasonic is broken
//        addSequential(new Place());
//        addSequential(new SetDistanceToBox(0.60));
//        // addSequential(new DriveStraight(-2)); // Use Encoders if ultrasonic is broken
//        addParallel(new SetWristSetpoint(-45));
//        addSequential(new CloseClaw());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
