package view;

import java.awt.Color;

public class ViewProperties {

	public static final int TOPOLOGY_NEURON_DIAMETER = 14;

	public static final int TOPOLOGY_MAX_SYNAPSE_WIDTH = 4;
	public static final int TOPOLOGY_ARROW_MIN_SIZE = 4;
	public static final int TOPOLOGY_ARROW_MAX_SIZE = 8;
	
	public static final int DATAPANEL_DEFAULT_SIZE = 400;
	public static final int DATAPANEL_RESOLUTION = 4;
	public static final int DATAPANEL_SAMPLE_DIAMETER = 6;
	public static final double DATAPANEL_SAMPLES_RANGE = 10.0;
	public static final double DATAPANEL_ERASE_RADIUS = 0.5;

	// Colors for value visualizations
	private static final Color COLOR_ZERO = new Color(200,200,200);
	private static final Color COLOR_POSITIVE = new Color(255,150,0);
	private static final Color COLOR_NEGATIVE = new Color(0,150,0);
	private static final Color COLOR_NAN = new Color(0,0,255);
	
	// Color of neurons in topology
	public static final Color COLOR_NEURON_INPUT = new Color(150,150,250);
	public static final Color COLOR_NEURON_HIDDEN = Color.DARK_GRAY;

	// Other colors
	public static final Color ERRORGRAPH_COLOR_TRAINING_ERROR = Color.BLACK;
	
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
