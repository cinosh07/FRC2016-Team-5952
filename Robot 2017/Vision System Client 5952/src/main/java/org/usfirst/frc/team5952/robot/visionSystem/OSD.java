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

	public void putGForce(double gForce) {
		gForceLabel.setText(G_FORCE_OVERLOAD_LABEL_HEADER + gForce);
	}
	public void putCompassAngle(double angle) {
		robotCompassLabel.putAngle(angle);
	}
	public void putRadarAngle(double angle) {
		radarCompassLabel.putAngle(angle);
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

	public void putDistanceToTarget(String distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
		distanceToTargetLabel.setText("DistTarg: " + this.distanceToTarget);
	}

	private JLabel getEmptyLabel() {
		JLabel emptylLabel = new JLabel("     ");

		return emptylLabel;
	}

	/**
	 * Set the status of the targetLockedLabel in the osd The label is red when
	 * not locked and green when locked
	 * <p>
	 *
	 * @param targetLocked
	 *            Set the OSD targetLockedLabel to locked true or false
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

	public JPanel getOsd_TEXT() {
		return osd_TEXT;
	}

	public void setOsd_TEXT(JPanel osd_TEXT) {
		this.osd_TEXT = osd_TEXT;
	}

	
	public void setScreenSize(int width, int height) {
	
		this.osdWidth = width;
		this.osdHeight = height;
		
	}


}
