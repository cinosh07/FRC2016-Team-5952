package org.usfirst.frc.team5952.robot.visionSystem;

import java.io.File;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class Target extends JPanel {
	
	private int targetx=0;
	private int targety=0;
	private int targetxCoord=0;
	private int targetyCoord=0;
	
	private JLabel target = new JLabel();
	
	private Rectangle2D rectangle;


	
	public Target(String targetText, boolean useBgImage, boolean allowAnimate, ImageIcon icon) {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		target.setText(targetText);
		target.setIcon(icon);
		// this.foreground = defaultForeground;
		
		setOpaque(false);
		add(target);
		
	}

	
	public JLabel getTarget() {
		return target;
	}

	public void setText(String text) {
		target.setText(text);
	}

	public void paintComponent(Graphics g) {
		Graphics2D graphics2 = (Graphics2D) g;
		rectangle = new Rectangle2D.Float(targetxCoord, targetyCoord, 64, 64);
		//graphics2.drawImage(CameraManager.getInstance().getLocalImageIcon("target_rouge.png",64,64), targetx, targety, 64, 64, Color.BLACK,null);
	
			
	}


	public int getTargetx() {
		return targetx;
	}


	public void setTargetx(int targetx) {
		this.targetx = targetx;
	}


	public int getTargety() {
		return targety;
	}


	public void setTargety(int targety) {
		this.targety = targety;
	}

	
}
