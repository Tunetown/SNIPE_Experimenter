package view;

import java.awt.Color;

public class ViewProperties {

	// Colors for value visualizations
	private static final Color COLOR_ZERO = new Color(200,200,200);
	private static final Color COLOR_POSITIVE = new Color(255,150,0);
	private static final Color COLOR_NEGATIVE = new Color(0,150,0);
	private static final Color COLOR_NAN = new Color(0,0,255);
	
	// Color of neurons in topology
	public static final Color COLOR_NEURON_INPUT = Color.LIGHT_GRAY;
	public static final Color COLOR_NEURON_HIDDEN = Color.DARK_GRAY;

	public Color getDataColor(double weight) {
		if (Double.isNaN(weight)) return COLOR_NAN; // TODO 
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
