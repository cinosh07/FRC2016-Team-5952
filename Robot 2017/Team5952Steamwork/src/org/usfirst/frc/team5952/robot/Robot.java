
package org.usfirst.frc.team5952.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5952.robot.commands.CloseLight;
import org.usfirst.frc.team5952.robot.commands.ExampleCommand;
import org.usfirst.frc.team5952.robot.commands.OpenLight;
import org.usfirst.frc.team5952.robot.commands.OpenTrap;
import org.usfirst.frc.team5952.robot.commands.RobotVisionCommunication;
import org.usfirst.frc.team5952.robot.commands.VisionCommunication;
import org.usfirst.frc.team5952.robot.subsystems.DriveTrain;
import org.usfirst.frc.team5952.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5952.robot.subsystems.Light;
import org.usfirst.frc.team5952.robot.subsystems.MonteCorde;
import org.usfirst.frc.team5952.robot.subsystems.OnBoardAccelerometer;
import org.usfirst.frc.team5952.robot.subsystems.Trap;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	public static DriveTrain drivetrain;
	public static Trap trap;
	public static NetworkTable cameraTable;
    public static MonteCorde montecorde;
    public static OnBoardAccelerometer onBoardAccelerometer;
    public static RobotVisionCommunication visionCommunication;
    public static Light light;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		drivetrain = new DriveTrain();
		onBoardAccelerometer = new OnBoardAccelerometer();
		chooser.addDefault("Default Auto", new ExampleCommand());
		
		light = new Light();
		trap = new Trap();
		montecorde = new MonteCorde();
		
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);

		cameraTable = NetworkTable.getTable(VisionCommunication.TABLE_NAME);
		visionCommunication = new RobotVisionCommunication(cameraTable);

		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(drivetrain);

		SmartDashboard.putString(VisionCommunication.CAMERA1_IP, visionCommunication.getCamera1IP());
		SmartDashboard.putString(VisionCommunication.CAMERA2_IP, visionCommunication.getCamera2IP());

		SmartDashboard.putNumber(VisionCommunication.CAMERA1_DELTA_TARGET, visionCommunication.getCamera1DeltaTarget());
		SmartDashboard.putNumber(VisionCommunication.CAMERA2_DELTA_TARGET, visionCommunication.getCamera2DeltaTarget());

		SmartDashboard.putNumber(VisionCommunication.CAMERA1_DIST_TARGET, visionCommunication.getCamera1DistTarget());
		SmartDashboard.putNumber(VisionCommunication.CAMERA2_DIST_TARGET, visionCommunication.getCamera2DistTarget());
		
		

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

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
	
		visionCommunication.updateData();
		visionCommunication.putOnBoardAccelData();
		Scheduler.getInstance().run();
		log();
		
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
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

		SmartDashboard.putString(VisionCommunication.CAMERA1_IP, visionCommunication.getCamera1IP());
		SmartDashboard.putString(VisionCommunication.CAMERA2_IP, visionCommunication.getCamera2IP());
		SmartDashboard.putNumber(VisionCommunication.CAMERA2_DELTA_TARGET, visionCommunication.getCamera2DeltaTarget());
		SmartDashboard.putNumber(VisionCommunication.CAMERA2_DIST_TARGET,visionCommunication.getCamera2DistTarget());
		
		SmartDashboard.putNumber(VisionCommunication.CAMERA1_DELTA_TARGET, visionCommunication.getCamera1DeltaTarget());
		SmartDashboard.putNumber(VisionCommunication.CAMERA1_DIST_TARGET, visionCommunication.getCamera1DistTarget());
		drivetrain.log();
	}
}
