package org.usfirst.frc.team5952.robot.commands;

import org.usfirst.frc.team5952.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CloseTrap extends Command {


	
    public CloseTrap() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.trap);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.trap.closeTrap();

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

    	if (Robot.trap.checkClosed()  && Robot.isAutonomous) {
    		
    		return true;
    		
    	} else {
    		
    		return false;
    		
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	if (!Robot.isAutonomous) {
    		Robot.trap.stop();
    	}
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
