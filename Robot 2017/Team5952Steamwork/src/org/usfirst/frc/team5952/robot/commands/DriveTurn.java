package org.usfirst.frc.team5952.robot.commands;


import org.usfirst.frc.team5952.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurn extends Command {
	
	static final double kP = 0.03;
	static final double kI = 0.0;
	static final double kD = 0.0;
	static final double kF = 0.0;
	double targetDistance;
	double targetSpeed;
	double targetAngle;
	double startAngle;
	
	static final double kToleranceDegree = 2.0f;
	

    public DriveTurn(double distance, double speed, double angle) {
    	
    	targetDistance = distance;
    	targetSpeed = speed;
    	targetAngle = angle;
    	
    	
    }
    
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	   	
    	Robot.drivetrain.left_encoder.reset();
    	Robot.drivetrain.right_encoder.reset();
    	Robot.drivetrain.reset();
    	Robot.ahrs.zeroYaw();
    	startAngle = Robot.ahrs.getAngle();
    	 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(targetAngle > 0){
    		//Robot.drivetrain.driveAuto(0.0, targetSpeed);
    	Robot.drivetrain.driveAuto(targetSpeed, 0.65);
		Timer.delay(0.01);
    	}
    	if(targetAngle < 0){
    		Robot.drivetrain.driveAuto(targetSpeed, -0.65);
    		Timer.delay(0.01);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	if (targetAngle > 0) {
    		
    		if (Robot.ahrs.getAngle() > targetAngle) {
        		
        		return true;
        		
        	}
    		
    	}
    	
    	if (targetAngle < 0) {
    		if (Robot.ahrs.getAngle() < targetAngle){
            	return true;
            }
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
