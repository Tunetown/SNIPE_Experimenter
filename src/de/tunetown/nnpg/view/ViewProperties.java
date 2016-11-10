package de.tunetown.nnpg.view;

import java.awt.Color;

/**
 * UI properties (colors, constants etc.)
 * 
 * @author xwebert
 *
 */
public class ViewProperties {

	// Colors for value visualizations
	private static final Color COLOR_ZERO = Color.WHITE; //new Color(200,200,200);
	private static final Color COLOR_POSITIVE = new Color(255,150,0);
	private static final Color COLOR_NEGATIVE = new Color(0,150,0);
	private static final Color COLOR_NAN = new Color(0,0,255);
	
	// Color of neurons in topology, and other topology colors
	public static final Color COLOR_NEURON_INPUT = new Color(150,150,250);
	public static final Color COLOR_NEURON_HIDDEN = Color.DARK_GRAY;
	public static final Color TOPOLOGY_LAYERBUTTON_BGCOLOR = Color.LIGHT_GRAY;
	public static final Color TOPOLOGY_LAYERBUTTON_COLOR = Color.WHITE;

	// Colors for error graph
	public static final Color ERRORGRAPH_COLOR_TRAINING_ERROR = Color.BLACK;
	public static final Color ERRORGRAPH_COLOR_TEST_ERROR = COLOR_NEGATIVE;
	public static final Color ERRORGRAPH_COLOR_SPEED = COLOR_POSITIVE;
	
	// General constants for UI elements
	public static final int TOPOLOGY_NEURON_DIAMETER = 14;
	public static final int TOPOLOGY_MAX_SYNAPSE_WIDTH = 4;
	public static final int TOPOLOGY_ARROW_MIN_SIZE = 4;
	public static final int TOPOLOGY_ARROW_MAX_SIZE = 8;
	public static final int TOPOLOGY_BUTTON_HEIGHT = 50;
	public static final int TOPOLOGY_LAYERBUTTON_SIZE = 14;
	
	public static final int DATAPANEL_DEFAULT_SIZE = 400;
	public static final int DATAPANEL_RESOLUTION = 4;
	public static final int DATAPANEL_SAMPLE_DIAMETER = 6;
	
	public static final int STATISTICS_AVERAGE_RANGE = 20;
	

	public Color getDataColor(double weight) {
		if (Double.isNaN(weight)) return COLOR_NAN;  
		double percent;
		Color target;
		
		if(weight > 0) {
			percent = normalize(weight);
			target = COLOR_POSITIVE;
		} else {
			percent = normalize(-weight);
			target = COLOR_NEGATIVE;
		}
		
		double inverse_percent = 1.0 - percent;
		
		int redPart = (int)(target.getRed()*percent + COLOR_ZERO.getRed()*inverse_percent);
		int greenPart = (int)(target.getGreen()*percent + COLOR_ZERO.getGreen()*inverse_percent);
		int bluePart = (int)(target.getBlue()*percent + COLOR_ZERO.getBlue()*inverse_percent);
		return new Color(redPart, greenPart, bluePart);
	}
	
	public double normalize(double weight) {
		if (weight > 1.0) return 1.0;
		if (weight < -1.0) return -1.0;
		return weight;
	}

}
