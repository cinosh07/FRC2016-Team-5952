package org.usfirst.frc.team5952.robot.commands;


import org.usfirst.frc.team5952.robot.Robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveSlowTurn extends Command {
	
	static final double kP = 0.03;
	static final double kI = 0.0;
	static final double kD = 0.0;
	static final double kF = 0.0;
	double targetDistance;
	double targetSpeed;
	double targetAngle;
	double startAngle;
	
	static final double kToleranceDegree = 2.0f;
	private boolean activated = false;

    public DriveSlowTurn(boolean active) {
    	
    	
    	activated = active;
    	//targetDistance = distance;
    	//targetSpeed = speed;
    	//targetAngle = angle;
    	
    	
    }
    
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	   	
    	//Robot.drivetrain.left_encoder.reset();
    	//Robot.drivetrain.right_encoder.reset();
    	Robot.drivetrain.reset();
    	//Robot.ahrs.zeroYaw();
    	//startAngle = Robot.ahrs.getAngle();
    	Robot.isSlow = true;
    	 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(activated){
    	Robot.drivetrain.driveAuto(Robot.oi.getJoystick().getY()/1.7, Robot.oi.getJoystick().getTwist() / 1.7);
		Timer.delay(0.01);
		
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	if (!activated) {
    		
    		
    		Robot.isSlow = false;
        		return true;
        		
        	
    		
    	}
    	
    	
    	
        
    	return false;
     
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.isSlow = false;
    	Robot.drivetrain.driveAuto(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
