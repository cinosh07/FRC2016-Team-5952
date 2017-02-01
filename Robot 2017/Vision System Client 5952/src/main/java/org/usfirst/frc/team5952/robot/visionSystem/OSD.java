package org.usfirst.frc.team5952.robot.visionSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OSD extends JPanel {

	public final String G_FORCE_OVERLOAD_LABEL_HEADER = "G Force ";
	public String distanceToTarget = null;
	public JLabel distanceToTargetLabel = null;
	public JLabel cameraNameLabel = null;
	public JLabel gForceLabel = null;
	public Boolean gForceOverload = false;
	public BorderLayout layoutBorder = new BorderLayout();
	
	public Compass robotCompassLabel;
	public Radar radarCompassLabel;

	private JPanel osd_TEXT = new JPanel(new GridLayout(5, 5, 10, 10));

	

	
	private JLabel sight = null;
	
	private int osdWidth;
	private int osdHeight;
	
	private String path = "";

	public OSD(String path) {

		this.path = path;
		this.setLayout(layoutBorder);
		this.setSize(osdWidth, osdHeight);
		osd_TEXT.setOpaque(false);
		osd_TEXT.setSize(osdWidth, osdHeight);
		osd_TEXT.setBorder(new EmptyBorder(0, 5, 0, 5));


		//*********************************************************************************************
		//
		//          OSD - TEXT
		//
		//*********************************************************************************************

		// First ROW

		distanceToTargetLabel = new JLabel("DistTarg: N/A", JLabel.CENTER);// Label target
		distanceToTargetLabel.setForeground(Color.white);
		osd_TEXT.add(distanceToTargetLabel);

		osd_TEXT.add(getEmptyLabel());// Label Vide

		cameraNameLabel = new JLabel("Searching ...", JLabel.CENTER);// Label target
		cameraNameLabel.setForeground(Color.GREEN);
		osd_TEXT.add(cameraNameLabel);

		osd_TEXT.add(getEmptyLabel());// Label Vide

		gForceLabel = new JLabel(G_FORCE_OVERLOAD_LABEL_HEADER + "0.0", JLabel.CENTER);// Label Target Locked
		gForceLabel.setForeground(Color.GREEN);
		osd_TEXT.add(gForceLabel);

		// Second ROW

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		// Second ROW

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		// Second ROW

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		// Second ROW

		robotCompassLabel = new Compass(64,64,path);// Label Robot Compass
		
		robotCompassLabel.setForeground(Color.GREEN);
		osd_TEXT.add(robotCompassLabel);

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		radarCompassLabel = new Radar(64,64,path);// Label Robot Compass
		radarCompassLabel.setForeground(Color.GREEN);
		osd_TEXT.add(radarCompassLabel);
		//*********************************************************************************************
		
		
		
		this.add(osd_TEXT, BorderLayout.CENTER);


	}

	
	public OSD(LayoutManager layout) {
		super(layout);
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

	

	private JLabel getEmptyLabel() {
		JLabel emptylLabel = new JLabel("     ");

		return emptylLabel;
	}

	/**
	 * Set the color to red of the G Force Label in the osd. The label is red when
	 * is overloadded G and green when not
	 * <p>
	 *
	 * @param gForceOverload
	 *            Set the G Force Label apperance to Overloaded true or false
	 * @return VOID
	 * @see
	 */
	public void putGForceOverloadLabel(Boolean gForceOverload) {
		this.gForceOverload = gForceOverload;
		if (this.gForceOverload) {

			gForceLabel.setForeground(Color.RED);
			
		} else {

			gForceLabel.setForeground(Color.GREEN);

		}
	}
	
	/**
	 * Set the G Force value to the G Forece Label 
	 * <p>
	 *
	 * @param gForce
	 *            Double GForce. Set the G Force value to label
	 * @return VOID
	 * @see
	 */
	public void putGForce(double gForce) {
		gForceLabel.setText(G_FORCE_OVERLOAD_LABEL_HEADER + gForce);
	}
	
	/**
	 * Set the Robot Compass Angle Widget. 
	 * <p>
	 *
	 * @param angle
	 *            Double angle
	 * @return VOID
	 * @see
	 */
	
	public void putCompassAngle(double angle) {
		robotCompassLabel.putAngle(angle);
	}
	
	/**
	 *Set the Radar Compass Angle Widget. 
	 * <p>
	 *
	 * @param angle
	 *            Double angle
	 * @return VOID
	 * @see
	 */
	
	public void putRadarAngle(double angle) {
		radarCompassLabel.putAngle(angle);
	}
	
	/**
	 * Set Distance to Target label text to the distance value between the camera and the target
	 * <p>
	 *
	 * @param putDistanceToTarget
	 *            String distance
	 * @return VOID
	 * @see
	 */

	public void putDistanceToTarget(String distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
		distanceToTargetLabel.setText("DistTarg: " + this.distanceToTarget);
	}
	
	/**
	 * Set the size of the osd to fit the actual video screen
	 * <p>
	 *
	 * @param width
	 *            int width
	 * @param height
	 *            int height
	 * @return VOID
	 * @see
	 */
	
	public void putScreenSize(int width, int height) {
	
		this.osdWidth = width;
		this.osdHeight = height;
		
	}

	/**
	 * Set the position of the target in the OSD
	 * <p>
	 *
	 * @param x
	 *            int x
	 * @param y
	 *            int y
	 * @return VOID
	 * @see
	 */
	
	public void putTargetPosition(int x, int y) {
	
		StreamManager.getInstance().targetPanel.moveTarget(x, y);
		
	}
	/**
	 * Set the size of the osd to fit the actual video screen
	 * 
	 * 		Target.UNLOCK_STATE // Target appear in red
	 *  	Target.CAM_LOCK_STATE // Target appear in yellow
	 *  	Target.ROBOT_LOCK_STATE // Target appear in green
	 * <p>
	 *
	 * @param state
	 *            String state
	 * @return VOID
	 * @see
	 */
	
	public void putTargetState(String state) {
	
		StreamManager.getInstance().targetPanel.putTargetState(state);
		
	}

}
