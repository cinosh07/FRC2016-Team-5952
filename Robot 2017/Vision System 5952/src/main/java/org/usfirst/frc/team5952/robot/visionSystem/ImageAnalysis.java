package org.usfirst.frc.team5952.robot.visionSystem;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ImageAnalysis {

	public ImageAnalysis() {
		// TODO Auto-generated constructor stub
	}
	
	public Mat analyse(Mat input, Mat output) {
		
		//TODO Analyse d<image
		Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HSV);
		
		
		return output;
	}

}
