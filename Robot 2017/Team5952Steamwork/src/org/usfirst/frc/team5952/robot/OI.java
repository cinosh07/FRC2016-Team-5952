package org.usfirst.frc.team5952.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5952.robot.commands.CloseLight;
import org.usfirst.frc.team5952.robot.commands.CloseTrap;
import org.usfirst.frc.team5952.robot.commands.DescendLaCorde;
import org.usfirst.frc.team5952.robot.commands.ExampleCommand;
import org.usfirst.frc.team5952.robot.commands.MonteLaCorde;
import org.usfirst.frc.team5952.robot.commands.OpenLight;
import org.usfirst.frc.team5952.robot.commands.OpenTrap;
import org.usfirst.frc.team5952.robot.commands.SwitchCamera;


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
       
        SmartDashboard.putData("Switch Camera", new SwitchCamera(0));
        SmartDashboard.putData("openLight", new OpenLight());
		SmartDashboard.putData("closeLight", new CloseLight());
		SmartDashboard.putData("CloseTrap", new CloseTrap());
		SmartDashboard.putData("OpenTrap", new OpenTrap());
        // Create some buttons
	    JoystickButton b_1 = new JoystickButton(joy, 1);
	    JoystickButton b_2 = new JoystickButton(joy, 2);
	    JoystickButton b_3 = new JoystickButton(joy, 3);
		JoystickButton b_4 = new JoystickButton(joy, 4);
        JoystickButton b_5 = new JoystickButton(joy, 5);
        JoystickButton b_6 = new JoystickButton(joy, 6);
        JoystickButton b_7 = new JoystickButton(joy, 7);
        JoystickButton b_8 = new JoystickButton(joy, 8);
        JoystickButton b_9 = new JoystickButton(joy, 9);
        JoystickButton b_10 = new JoystickButton(joy, 10);
        JoystickButton b_11 = new JoystickButton(joy, 11);
        JoystickButton b_12 = new JoystickButton(joy, 12);

        // Connect the buttons to commands
        b_1.whenPressed(new OpenTrap());
        b_2.whileHeld(new MonteLaCorde());
        b_3.whenPressed(new DescendLaCorde());
        b_6.whenPressed(new SwitchCamera(0));

    }
    
    public Joystick getJoystick() {
        return joy;
    }
}
