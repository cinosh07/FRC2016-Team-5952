package org.usfirst.frc.team5952.robot.commands;

import org.usfirst.frc.team5952.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightReverse extends Command {
	
	static final double Kp = 0.03;

	double targetDistance;
	double targetSpeed;
	

    public DriveStraightReverse(double distance, double speed) {
    	
    	
    	targetDistance = distance;
    	targetSpeed = speed;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Reverse Drive is initialized");
    	Robot.drivetrain.left_encoder.reset();
    	Robot.drivetrain.right_encoder.reset();
    	Robot.drivetrain.reset();
    	Robot.ahrs.zeroYaw();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	//System.out.println("Reverse Drive is running");
    	double angle = Robot.ahrs.getAngle();
    	//Robot.drivetrain.driveAuto(targetSpeed, -angle * Kp);
    	Robot.drivetrain.driveAuto(targetSpeed, 0.21);
		Timer.delay(0.01);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
  
    	if (Robot.drivetrain.right_encoder.getDistance() > targetDistance) {
    		
//        if (-Robot.drivetrain.left_encoder.getDistance() > targetDistance || Robot.drivetrain.right_encoder.getDistance() > targetDistance) {
    		
    		
    		System.out.println("Reverse Drive is finished");
    		return true;
    		
    	}

		return false;

	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.driveAuto(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
