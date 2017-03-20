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
	static final int TOLERANCE = 1;
	static final double MAX_OUTPUT = 0.75;
	
	private int timeToDrive = 0;
	private long endTime = 0;
	//boolean gearauto = false;
	double targetDistance;
	double secondsTimeout;
	
    public DriveStraight(double secondsTimeout, double d) {
   
    	
    	this.secondsTimeout = secondsTimeout;
    	this.targetDistance = targetDistance;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//Robot.drivetrain.driveTest(0.25, 0.25);
    	//Timer.delay(duration);
    	//Robot.drivetrain.driveTest(0, 0);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(secondsTimeout);
    	Robot.drivetrain.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	 double currentDistance = Robot.drivetrain.right_encoder.getDistance();
    	//double angle = Robot.ahrs.getAngle();
    	//Robot.drivetrain.driveTest(-0.5, -angle * Kp);
    	//if(gearauto == true){
    	  
    	  double motorOutput = Kp *(targetDistance - currentDistance);
    	  
    	  if(motorOutput > MAX_OUTPUT )
    		  motorOutput = MAX_OUTPUT;
    	  if(motorOutput < -MAX_OUTPUT)
    		  motorOutput = -MAX_OUTPUT;
    	  
    	  Robot.drivetrain.driveTest(motorOutput, 0); /** turn modifie*/
    	  Timer.delay(0.01);
    		//if(current_distance == (0 - Robot.distance)){
    	//	}
    		
    	} 
    	//if(gearauto == false){
    //	Robot.drivetrain.driveTest(-0.75, 0.0);
		//Timer.delay(0.01);
		//}
		//Robot.trap.openTrap();
    	//Robot.drivetrain.driveTest(0,0);
    	
  //  }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(isTimedOut())
    		return true;
    	
    	double current_distance = Robot.drivetrain.right_encoder.getDistance();
    	double delta = Math.abs(current_distance - targetDistance);
    	
		if (delta < TOLERANCE) {
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
    	//if(gearauto == true){
    		//Robot.light.openLight();
    	//}
    	//Robot.trap.openTrap();
    	Robot.drivetrain.driveTest(0.0, 0.0);
    	
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
