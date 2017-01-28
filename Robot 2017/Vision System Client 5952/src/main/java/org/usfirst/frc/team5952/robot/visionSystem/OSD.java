package org.usfirst.frc.team5952.robot.visionSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OSD extends JPanel {

	private String distanceToTarget = null;
	private JLabel distanceToTargetLabel = null;
	private JLabel targetLockedLabel = null;
	private Boolean targetLocked = false;
	private BorderLayout layoutBorder = new BorderLayout();
	
	public JLabel robotCompassLabel;
	public JLabel radarCompassLabel;

	private JPanel osd_TEXT = new JPanel(new GridLayout(5, 5, 10, 10));

	

	
	private JLabel sight = null;
	
	private int osdWidth;
	private int osdHeight;

	public OSD() {

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

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		targetLockedLabel = new JLabel("Target Locked", JLabel.CENTER);// Label Target Locked
		targetLockedLabel.setForeground(Color.RED);
		osd_TEXT.add(targetLockedLabel);

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

		robotCompassLabel = new JLabel();// Label Robot Compass
		robotCompassLabel.setIcon(StreamManager.getInstance().robotCompass_ICON);
		robotCompassLabel.setForeground(Color.GREEN);
		osd_TEXT.add(robotCompassLabel);

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		osd_TEXT.add(getEmptyLabel());// Label Vide

		radarCompassLabel = new JLabel("Radar Compass", JLabel.CENTER);// Label Robot Compass
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

	public void setDistanceToTarget(String distanceToTarget) {
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
	public void setTargetLocked(Boolean targetLocked) {
		this.targetLocked = targetLocked;
		if (this.targetLocked) {

			targetLockedLabel.setForeground(Color.GREEN);

		} else {

			targetLockedLabel.setForeground(Color.RED);

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
