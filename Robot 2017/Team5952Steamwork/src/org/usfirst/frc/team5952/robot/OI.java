package org.usfirst.frc.team5952.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5952.robot.commands.CloseLight;
import org.usfirst.frc.team5952.robot.commands.CloseTrap;
import org.usfirst.frc.team5952.robot.commands.DescendLaCorde;
import org.usfirst.frc.team5952.robot.commands.DriveSlowTurn;
import org.usfirst.frc.team5952.robot.commands.DriveStraight;
import org.usfirst.frc.team5952.robot.commands.DriveTurn;
import org.usfirst.frc.team5952.robot.commands.MonteLaCorde;
import org.usfirst.frc.team5952.robot.commands.OpenLight;
import org.usfirst.frc.team5952.robot.commands.OpenTrap;
import org.usfirst.frc.team5952.robot.commands.StopLaCorde;
import org.usfirst.frc.team5952.robot.commands.SwitchCamera;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	
	private Joystick joy = new Joystick(0);

    public OI() {
    	
    	// Put Some buttons on the SmartDashboard
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
        
        //joy.getTwist()

        // Connect the buttons to commands
        b_1.toggleWhenPressed(new OpenTrap());
        //b_3.toggleWhenPressed(new DescendLaCorde());
        b_2.toggleWhenPressed(new MonteLaCorde());
        b_5.toggleWhenPressed(new DriveSlowTurn(true));
       // b_6.toggleWhenPressed(new DriveStraight(500.0));

        

    }
    
    public Joystick getJoystick() {
        return joy;
    }
}
