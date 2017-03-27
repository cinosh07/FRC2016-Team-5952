package org.usfirst.frc.team5952.robot;

import org.usfirst.frc.team5952.robot.commands.DriveStraight;
import org.usfirst.frc.team5952.robot.commands.DriveStraightSimple;
import org.usfirst.frc.team5952.robot.commands.GearDropCenterPosition;
import org.usfirst.frc.team5952.robot.commands.GearDropLeftPosition;
import org.usfirst.frc.team5952.robot.commands.GearDropRightPosition;
import org.usfirst.frc.team5952.robot.commands.RobotVisionCommunication;
import org.usfirst.frc.team5952.robot.commands.VisionCommunication;
import org.usfirst.frc.team5952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5952.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5952.robot.subsystems.Light;
import org.usfirst.frc.team5952.robot.subsystems.MonteCorde;
import org.usfirst.frc.team5952.robot.subsystems.OnBoardAccelerometer;
import org.usfirst.frc.team5952.robot.subsystems.Trap;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static final double driveTrainOffSet = 0.21;
	public static OI oi;
	public static DriveTrain drivetrain;
	public static Trap trap;
	public static NetworkTable cameraTable;
    public static MonteCorde montecorde;
    public static OnBoardAccelerometer onBoardAccelerometer;
    public static RobotVisionCommunication visionCommunication;
    public static Light light;
    public static int currentCamera = 1;
    public static AHRS ahrs;
   
  
    
    public static Boolean isAutonomous = false;
    
    
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		
		 try {
				/***********************************************************************
				 * navX-MXP:
				 * - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB.            
				 * - See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
				 * 
				 * navX-Micro:
				 * - Communication via I2C (RoboRIO MXP or Onboard) and USB.
				 * - See http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
				 * 
				 * Multiple navX-model devices on a single robot are supported.
				 ************************************************************************/
	            ahrs = new AHRS(SPI.Port.kMXP);
	        }
		 catch (RuntimeException ex ) {
	            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
	        }
		 
		oi = new OI();
		drivetrain = new DriveTrain();
		onBoardAccelerometer = new OnBoardAccelerometer();
		//chooser.addDefault("Drive Straight Simple",  new DriveStraightSimple(-0.45));
		chooser.addDefault("Poser Gear Centre", new GearDropCenterPosition());
		chooser.addObject("Franchir ligne", new DriveStraight(80.0,-0.5));
		chooser.addObject("Ligne Troy", new DriveStraightSimple(-0.45));
		chooser.addObject("Poser Gear Gauche", new GearDropLeftPosition());
		chooser.addObject("Poser Gear Droite", new GearDropRightPosition());

		
		light = new Light();
		trap = new Trap();
		montecorde = new MonteCorde();
		SmartDashboard.putData("Auto Selector", chooser);

		cameraTable = NetworkTable.getTable(VisionCommunication.TABLE_NAME);
		visionCommunication = new RobotVisionCommunication(cameraTable);	
		
		// Uncomments For Debugging
		//SmartDashboard.putData(drivetrain);

		//SmartDashboard.putString(VisionCommunication.CAMERA1_IP, visionCommunication.getCamera1IP());
		//SmartDashboard.putString(VisionCommunication.CAMERA2_IP, visionCommunication.getCamera2IP());

		//SmartDashboard.putNumber(VisionCommunication.CAMERA1_DELTA_TARGET, visionCommunication.getCamera1DeltaTarget());
		//SmartDashboard.putNumber(VisionCommunication.CAMERA2_DELTA_TARGET, visionCommunication.getCamera2DeltaTarget());

		//SmartDashboard.putNumber(VisionCommunication.CAMERA1_DIST_TARGET, visionCommunication.getCamera1DistTarget());
		//SmartDashboard.putNumber(VisionCommunication.CAMERA2_DIST_TARGET, visionCommunication.getCamera2DistTarget());
		
		//Robot.ahrs.

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		
		isAutonomous = true;
		autonomousCommand = chooser.getSelected();		
		autonomousCommand.start();
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		//System.out.println("Runing auton periodic");
		Scheduler.getInstance().run();	
		log();
		
	}

	@Override
	public void teleopInit() {

		isAutonomous = false;
		if(autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		visionCommunication.updateData();
		visionCommunication.putOnBoardAccelData();
		Scheduler.getInstance().run();
		log();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		visionCommunication.updateData();
		visionCommunication.putOnBoardAccelData();
		LiveWindow.run();
	}

	private void log() {

		//SmartDashboard.putString(VisionCommunication.CAMERA1_IP, visionCommunication.getCamera1IP());
		//SmartDashboard.putString(VisionCommunication.CAMERA2_IP, visionCommunication.getCamera2IP());
		//SmartDashboard.putNumber(VisionCommunication.CAMERA2_DELTA_TARGET, visionCommunication.getCamera2DeltaTarget());
		//SmartDashboard.putNumber(VisionCommunication.CAMERA2_DIST_TARGET,visionCommunication.getCamera2DistTarget());
		
		//SmartDashboard.putNumber(VisionCommunication.CAMERA1_DELTA_TARGET, visionCommunication.getCamera1DeltaTarget());
		//SmartDashboard.putNumber(VisionCommunication.CAMERA1_DIST_TARGET, visionCommunication.getCamera1DistTarget());
		drivetrain.log();
	}
}
