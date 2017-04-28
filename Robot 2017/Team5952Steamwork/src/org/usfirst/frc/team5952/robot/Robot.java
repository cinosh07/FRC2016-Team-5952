package org.usfirst.frc.team5952.robot;

import org.usfirst.frc.team5952.robot.commands.DriveStraight;
import org.usfirst.frc.team5952.robot.commands.DriveStraightSimple;
import org.usfirst.frc.team5952.robot.commands.GearDropCenterPosition;
import org.usfirst.frc.team5952.robot.commands.GearDropLeftPosition;
import org.usfirst.frc.team5952.robot.commands.GearDropLeftPositionRed;
import org.usfirst.frc.team5952.robot.commands.GearDropRightPosition;
import org.usfirst.frc.team5952.robot.commands.GearDropRightPositionRed;
import org.usfirst.frc.team5952.robot.commands.RobotVisionCommunication;
import org.usfirst.frc.team5952.robot.commands.VisionCommunication;
import org.usfirst.frc.team5952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5952.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5952.robot.subsystems.Light;
import org.usfirst.frc.team5952.robot.subsystems.MonteCorde;
import org.usfirst.frc.team5952.robot.subsystems.OnBoardAccelerometer;
import org.usfirst.frc.team5952.robot.subsystems.Trap;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
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
	public static final double leftEncoderDistancePerPulse= 0.00987;
	public static final double rightEncoderDistancePerPulse= 0.00987;
	public static final double minThrottle = 0.5;
	public static final double maxThrottle = 1.0;
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
    public static boolean isSlow = false;
    
    public static DigitalInput blueDropLeft;
    public static DigitalInput blueDropRight;
    public static DigitalInput redDropLeft;
    public static DigitalInput redDropRight;
    public static DigitalInput centerDrop;
  
    
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
		chooser.addObject("Franchir ligne", new DriveStraight(100.0,-0.5));
		chooser.addObject("Ligne Troy", new DriveStraightSimple(-0.45));
		chooser.addObject("Poser Gear Gauche", new GearDropLeftPosition());
		chooser.addObject("Poser Gear Droite", new GearDropRightPosition());
        
		blueDropLeft = new DigitalInput(4);
		blueDropRight = new DigitalInput(5);
		redDropLeft = new DigitalInput(6);
		redDropRight = new DigitalInput(7);
		centerDrop = new DigitalInput(8);
		
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
		//autonomousCommand = chooser.getSelected();	
		
		
		//Pose la gear au centre
		if (!centerDrop.get()) {
			autonomousCommand = new GearDropCenterPosition();
		}
		// Gauche cote Bleu
		else if (!blueDropLeft.get()) {
			autonomousCommand = new GearDropLeftPosition();
		}	
		// Droite Cote bleu
		else if (!blueDropRight.get()) {
			autonomousCommand = new GearDropRightPosition();
		}	
		// Gauche cote Rouge
		else if (!redDropLeft.get()) {
			autonomousCommand = new GearDropRightPositionRed();
		}			
		// Droite Cote Rouge
		else if (!redDropRight.get()) {
			autonomousCommand = new GearDropLeftPositionRed();
		}
		else {
			autonomousCommand = new DriveStraight(100.0,-0.75);
			
		}
		
		
		
		
		
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
		
		Robot.drivetrain.left_encoder.reset();
    	Robot.drivetrain.right_encoder.reset();
    	Robot.ahrs.zeroYaw();

	}

	/**
	 * This function is called periodically during operator control 
	 */
	@Override
	public void teleopPeriodic() {
		
		//SmartDashboard.putNumber("TWIST", Robot.oi.getJoystick().getTwist());
		//Robot.oi.getJoystick().getTwist();
		visionCommunication.updateData();
		visionCommunication.putOnBoardAccelData();
		Scheduler.getInstance().run();
		log();
		
		SmartDashboard.putNumber("Joystick X null", Robot.oi.getJoystick().getY(null));
		SmartDashboard.putNumber("Joystick X", Robot.oi.getJoystick().getY());
		SmartDashboard.putBoolean("IS Slow", isSlow);
		
		
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
