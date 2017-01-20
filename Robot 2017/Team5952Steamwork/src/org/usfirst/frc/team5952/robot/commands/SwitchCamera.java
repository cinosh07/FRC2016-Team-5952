package org.usfirst.frc.team5952.robot.commands;

import org.usfirst.frc.team5952.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SwitchCamera extends Command {

	private int cameraNumber = 1;
    
	public SwitchCamera(int cameraNumber) {
    	
    	if (cameraNumber == 0) {
    		this.cameraNumber = 1;
    	} else {
    		this.cameraNumber = cameraNumber;
    	}
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.cameraTable.putNumber("SWITCH", cameraNumber);
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
    	end();
    }
}
