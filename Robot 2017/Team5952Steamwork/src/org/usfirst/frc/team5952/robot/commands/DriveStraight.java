package org.usfirst.frc.team5952.robot.commands;

import java.util.Date;

import org.usfirst.frc.team5952.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {
	
	static final double Kp = 0.03;
	private int timeToDrive = 0;
	private long endTime = 0;
	
	//boolean gearauto = false;
	double targetDistance;


    public DriveStraight(double distance) {
    	
    	targetDistance = distance;
    	
    }
    
    

    // Called just before this Command runs the first time
    protected void initialize() {
    	 Robot.drivetrain.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.drivetrain.driveTest(0.25, 0.25);
    	double angle = Robot.ahrs.getAngle();
    	Robot.drivetrain.driveTest(-1.0, -angle * Kp);
		Timer.delay(0.01);

    	//Robot.drivetrain.drive(0.25, - 0.25);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	if (Robot.drivetrain.left_encoder.getDistance() > targetDistance) {
    		
    		return true;
    		
    	}
    	
//    	Robot.drivetrain.left_encoder.getDistance();
//    	
//    	long now = (new Date()).getTime();
//    	
//    	return now > endTime;
    	return false;
     
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.drivetrain.driveTest(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
