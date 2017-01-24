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
	private JLabel targetLockedLabel = null;
	private Boolean targetLocked = false;
	
	private GridBagLayout layoutGrid = new GridBagLayout();
	public OSD() {
		
		this.setLayout(layoutGrid);
		GridBagConstraints c = new GridBagConstraints();
		
		
		//TODO Finir d'ajouter les fonctions que l'on veux voire apparaitre a l'ecran
		
		// Label Distance to target
		
		distanceToTargetLabel = new JLabel("DistTarg: N/A", JLabel.CENTER);
		distanceToTargetLabel.setForeground(Color.white);	
		c.fill = GridBagConstraints.HORIZONTAL;
	    c.ipady = 0;       //reset to default
	    c.weighty = 1.0;   //request any extra vertical space
	    c.anchor = GridBagConstraints.PAGE_END; //bottom of space
	    c.insets = new Insets(10,10,10,10);  //top padding
	    c.gridx = 0;       //aligned with button 2
	    c.gridwidth = 8;   //2 columns wide
	    c.gridy = 3;       //third row
	    this.add(distanceToTargetLabel,c);
	    
	    // Label Target Locked
		
 		targetLockedLabel = new JLabel("Target Locked", JLabel.CENTER);
 		targetLockedLabel.setForeground(Color.RED);	
 		c.fill = GridBagConstraints.HORIZONTAL;
 	    c.ipady = 0;       //reset to default
 	    c.weighty = 1.0;   //request any extra vertical space
 	    c.anchor = GridBagConstraints.PAGE_END; //bottom of space
 	    c.insets = new Insets(10,10,10,10);  //top padding
 	    c.gridx = 1;       //aligned with button 2
 	    c.gridwidth = 8;   //2 columns wide
 	    c.gridy = 3;       //third row
 	    this.add(targetLockedLabel,c);
	    
	    
	    
	    
		
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


	public void setDistanceToTarget(String distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
		distanceToTargetLabel.setText("DistTarg: " + this.distanceToTarget);
	}

	
	/**
	 * Set the status of the targetLockedLabel in the osd
	 * The label is red when not locked and green when locked
	 * <p>
	 *
	 * @param  targetLocked  Set the OSD targetLockedLabel to locked true or false
	 * @return      VOID
	 * @see         
	 */
	public void setTargetLocked(Boolean targetLocked) {
		this.targetLocked = targetLocked;
		if (this.targetLocked) {
			
			targetLockedLabel.setForeground(Color.GREEN);
			
		} else {
			
			targetLockedLabel.setForeground(Color.RED);
		
		}
	}

}
