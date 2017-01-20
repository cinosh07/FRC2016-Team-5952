package org.usfirst.frc.team5952.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;



import org.usfirst.frc.team5952.robot.commands.ExampleCommand;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	private Joystick joy = new Joystick(0);

    public OI() {
    	// Put Some buttons on the SmartDashboard
//        SmartDashboard.putData("Elevator Bottom", new SetElevatorSetpoint(0));
//        SmartDashboard.putData("Elevator Platform", new SetElevatorSetpoint(0.2));
//        SmartDashboard.putData("Elevator Top", new SetElevatorSetpoint(0.3));
//        
//        SmartDashboard.putData("Wrist Horizontal", new SetWristSetpoint(0));
//        SmartDashboard.putData("Raise Wrist", new SetWristSetpoint(-45));
//        
//        SmartDashboard.putData("Open Claw", new OpenClaw());
//        SmartDashboard.putData("Close Claw", new CloseClaw());
//        
//        SmartDashboard.putData("Deliver Soda", new Autonomous());
        
        // Create some buttons
        JoystickButton d_up = new JoystickButton(joy, 5);
        JoystickButton d_right= new JoystickButton(joy, 6);
        JoystickButton d_down= new JoystickButton(joy, 7);
        JoystickButton d_left = new JoystickButton(joy, 8);
        JoystickButton l2 = new JoystickButton(joy, 9);
        JoystickButton r2 = new JoystickButton(joy, 10);
        JoystickButton l1 = new JoystickButton(joy, 11);
        JoystickButton r1 = new JoystickButton(joy, 12);

//        // Connect the buttons to commands
//        d_up.whenPressed(new SetElevatorSetpoint(0.2));
//        d_down.whenPressed(new SetElevatorSetpoint(-0.2));
//        d_right.whenPressed(new CloseClaw());
//        d_left.whenPressed(new OpenClaw());
//        
//        r1.whenPressed(new PrepareToPickup());
//        r2.whenPressed(new Pickup());
//        l1.whenPressed(new Place());
//        l2.whenPressed(new Autonomous());
    }
    
    public Joystick getJoystick() {
        return joy;
    }
}