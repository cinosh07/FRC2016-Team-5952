package org.usfirst.frc.team5952.robot.commands;


import org.usfirst.frc.team5952.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurn extends Command {
	
	static final double Kp = 0.03;
	double targetDistance;
	double targetSpeed;
	double targetAngle;
	double startAngle;

    public DriveTurn(double distance, double speed, double angle) {
    	
    	targetDistance = distance;
    	targetSpeed = speed;
    	targetAngle = angle;
    	startAngle = Robot.ahrs.getAngle();
    }
    
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	   	
    	Robot.drivetrain.left_encoder.reset();
    	Robot.drivetrain.right_encoder.reset();
    	Robot.drivetrain.reset();
    	 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    	Robot.drivetrain.driveAuto(targetSpeed, targetAngle * Kp);
		Timer.delay(0.01);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	if (Robot.ahrs.getAngle() > (startAngle - targetAngle)) {
    		
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
