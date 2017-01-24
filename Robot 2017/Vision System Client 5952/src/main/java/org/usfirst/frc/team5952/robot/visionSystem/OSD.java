package org.usfirst.frc.team5952.robot.visionSystem;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class OSD extends JPanel {


	private String distanceToTarget = null;
	private JLabel distanceToTargetLabel = null;
	
	private GridBagLayout layoutGrid = new GridBagLayout();
	public OSD() {
		
		this.setLayout(layoutGrid);
		GridBagConstraints c = new GridBagConstraints();
		
		
		
		// Label Distance to target
		distanceToTargetLabel = new JLabel("DistTarg: N/A", JLabel.CENTER);
		distanceToTargetLabel.setForeground(Color.white);	
		c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 0;       //reset to default
	    c.weighty = 1.0;   //request any extra vertical space
	    c.anchor = GridBagConstraints.PAGE_END; //bottom of space
	    c.insets = new Insets(10,10,10,10);  //top padding
	    c.gridx = 1;       //aligned with button 2
	    c.gridwidth = 8;   //2 columns wide
	    c.gridy = 3;       //third row
	    this.add(distanceToTargetLabel,c);
		
	}

	public OSD(LayoutManager layout) {
		super(new GridBagLayout());
		// TODO Auto-generated constructor stub
	}

	public OSD(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public OSD(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public String getDistanceToTarget() {
		return distanceToTarget;
	}

	public void setDistanceToTarget(String distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
		distanceToTargetLabel.setText("DistTarg: " + this.distanceToTarget);
	}

}
