package de.tunetown.nnpg.view.data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.view.ViewProperties;

/**
 * Visualization of the output of the network, along with the training data points. This also
 * takes care about sample manipulation (painting/erasing samples)
 * 
 * @author xwebert
 *
 */
public class DataPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int TOOL_PAINT = 0;
	public static final int TOOL_ERASE = 1;
	
	private Main main;
	private ViewProperties properties = new ViewProperties();

	private int tool = TOOL_PAINT;
	
	public DataPanel(Main main) {
		this.main = main;
		
		Dimension dim = new Dimension(ViewProperties.DATAPANEL_DEFAULT_SIZE, ViewProperties.DATAPANEL_DEFAULT_SIZE);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		
		final DataPanel wrapper = this;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				wrapper.mousePressed(e);
				wrapper.repaint();
			}
		});
	}
	
	/**
	 * Painting/erasing functionality
	 * 
	 * @param e
	 */
	protected void mousePressed(MouseEvent e) {
		switch (tool) {
		case TOOL_PAINT:
			main.getData().addSample(
					convertToModel(e.getPoint().x), 
					convertToModel(e.getPoint().y), 
					(e.getButton() == 1) ? 1.0 : ((e.getButton() == 2) ? 0 : -1.0));
			break;
		case TOOL_ERASE:
			main.getData().deleteSamplesAroundPoint(
					convertToModel(e.getPoint().x), 
					convertToModel(e.getPoint().y), 
					ViewProperties.DATAPANEL_ERASE_RADIUS);
			break;
		}
	}

	public void paintComponent(Graphics g) {
		paintGraph(g);
		paintSamples(g);
	}

	/**
	 * Paint the samples of the training data
	 * 
	 * @param g
	 */
	private void paintSamples(Graphics g) {
		if(main.getData().getNumOfSamples() == 0) return;
		
		for(int n=0; n<main.getData().getInputs().length; n++) {
			double[][] in = main.getData().getInputs();
			double[][] out = main.getData().getDesiredOutputs();
			paintSample(g, in[n][0], in[n][1], out[n][0]);
		}
	}

	/**
	 * Paint one data sample
	 * 
	 * @param x  
	 * @param y 
	 * @param out
	 */
	private void paintSample(Graphics g, double x, double y, double out) {
		g.setColor(properties.getDataColor(out));
		g.fillOval(
				convertToView(x) - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
				convertToView(y) - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
				ViewProperties.DATAPANEL_SAMPLE_DIAMETER, 
				ViewProperties.DATAPANEL_SAMPLE_DIAMETER);
		g.setColor(Color.BLACK);
		g.drawOval(
				convertToView(x) - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
				convertToView(y) - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
				ViewProperties.DATAPANEL_SAMPLE_DIAMETER, 
				ViewProperties.DATAPANEL_SAMPLE_DIAMETER);
	}

	/**
	 * Determine the size of the output area by the Swing component size
	 * 
	 * @return
	 */
	private int getDimension() {
		int minDim = 0;
		if (this.getWidth() > this.getHeight()) {
			minDim = this.getHeight();
		} else {
			minDim = this.getWidth();
		}
		return minDim;
	}
	
	/**
	 * Paint the network output 
	 * 
	 * @param g
	 */
	private void paintGraph(Graphics g) {
		int minDim = getDimension();
		for(int x = 0; x<minDim; x+=ViewProperties.DATAPANEL_RESOLUTION) {
			for(int y = 0; y<minDim; y+=ViewProperties.DATAPANEL_RESOLUTION) {
				g.setColor(getOutColor(convertToModel(x), convertToModel(y)));
				g.fillRect(x, y, ViewProperties.DATAPANEL_RESOLUTION, ViewProperties.DATAPANEL_RESOLUTION);
			}			
		}
	}
	
	/**
	 * Convert coordinates from UI to model
	 * 
	 * @param in
	 * @return
	 */
	private double convertToModel(int in) {
		return (ViewProperties.DATAPANEL_SAMPLES_RANGE*2 * (double)in / (double)getDimension()) - ViewProperties.DATAPANEL_SAMPLES_RANGE;
	}
	
	/**
	 * Convert coordinates from model to UI
	 * 
	 * @param in
	 * @return
	 */
	private int convertToView(double in) {
		return (int)((in + ViewProperties.DATAPANEL_SAMPLES_RANGE) / (ViewProperties.DATAPANEL_SAMPLES_RANGE*2) * getDimension());
	}

	/**
	 * Returns the color in which to paint a coordinate. This calls the propagation function through the network!
	 * 
	 * @param x 
	 * @param y 
	 * @return
	 */
	private Color getOutColor(double x, double y) {
		double[] in = {x, y};
		double[] out = main.getNetwork().propagate(in);
		return properties.getDataColor(out[0]);
	}

	/**
	 * Set editing tool
	 * 
	 * @param tool
	 */
	public void setTool(int tool) {
		this.tool = tool;
	}
}
