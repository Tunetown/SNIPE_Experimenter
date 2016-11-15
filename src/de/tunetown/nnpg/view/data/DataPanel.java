package de.tunetown.nnpg.view.data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.model.DataContainer;
import de.tunetown.nnpg.model.ModelProperties;
import de.tunetown.nnpg.view.ViewProperties;

/**
 * Visualization of the output of the network, along with the training data points. This also
 * takes care about sample manipulation (painting/erasing samples)
 * 
 * @author Thomas Weber
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
				try {
					wrapper.mousePressed(e);
				} catch (Throwable t) {
					t.printStackTrace();
				}
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
			if (Math.abs(convertToModel(e.getPoint().x)) <= ModelProperties.DATAPANEL_SAMPLES_RANGE &&
				Math.abs(convertToModel(e.getPoint().y)) <= ModelProperties.DATAPANEL_SAMPLES_RANGE) {
				main.getData().addSample(
						convertToModel(e.getPoint().x), 
						convertToModel(e.getPoint().y), 
						(e.getButton() == 1) ? 1.0 : ((e.getButton() == 2) ? 0 : -1.0));
			}
			break;
		case TOOL_ERASE:
			main.getData().deleteSamplesAroundPoint(
					convertToModel(e.getPoint().x), 
					convertToModel(e.getPoint().y), 
					ModelProperties.DATAPANEL_ERASE_RADIUS);
			break;
		}
		main.updateView(false, false, false);
		repaint();
	}

	@Override
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
		DataContainer data = main.getData().getTrainingLesson();
		if(data != null && data.size() > 0) paintSamples(g, data, true);
		
		data = main.getData().getTestLesson();
		if(data != null && data.size() > 0) paintSamples(g, data, false);
	}

	private void paintSamples(Graphics g, DataContainer data, boolean training) {
		for(int n=0; n<data.getInputs().length; n++) {
			double[][] in = data.getInputs();
			double[][] out = data.getDesiredOutputs();
			paintSample(g, convertToView(in[n][0]), convertToView(in[n][1]), properties.getDataColor(out[n][0]), training);
		}
	}

	/**
	 * Paint one data sample
	 * 
	 * @param x  
	 * @param y 
	 * @param out
	 */
	private void paintSample(Graphics g, int x, int y, Color color, boolean training) {
		g.setColor(color);
		if (!training) {
			g.fillOval(
					x - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
					y - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
					ViewProperties.DATAPANEL_SAMPLE_DIAMETER, 
					ViewProperties.DATAPANEL_SAMPLE_DIAMETER);
		} else {
			g.fillRect(
					x - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
					y - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
					ViewProperties.DATAPANEL_SAMPLE_DIAMETER, 
					ViewProperties.DATAPANEL_SAMPLE_DIAMETER);
		}
		
		g.setColor(Color.BLACK);
		if (!training) {
			g.drawOval(
					x - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
					y - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
					ViewProperties.DATAPANEL_SAMPLE_DIAMETER, 
					ViewProperties.DATAPANEL_SAMPLE_DIAMETER);
		} else {
			g.drawRect(
					x - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
					y - ViewProperties.DATAPANEL_SAMPLE_DIAMETER/2, 
					ViewProperties.DATAPANEL_SAMPLE_DIAMETER, 
					ViewProperties.DATAPANEL_SAMPLE_DIAMETER);
		}
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
		
		synchronized (main.getNetworkLock()) {
			for(int x = 0; x<minDim; x+=ViewProperties.DATAPANEL_RESOLUTION) {
				for(int y = 0; y<minDim; y+=ViewProperties.DATAPANEL_RESOLUTION) {
					g.setColor(getOutColor(convertToModel(x), convertToModel(y)));
					g.fillRect(x, y, ViewProperties.DATAPANEL_RESOLUTION, ViewProperties.DATAPANEL_RESOLUTION);
				}			
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
		return (ModelProperties.DATAPANEL_SAMPLES_RANGE*2 * in / getDimension()) - ModelProperties.DATAPANEL_SAMPLES_RANGE;
	}
	
	/**
	 * Convert coordinates from model to UI
	 * 
	 * @param in
	 * @return
	 */
	private int convertToView(double in) {
		return (int)((in + ModelProperties.DATAPANEL_SAMPLES_RANGE) / (ModelProperties.DATAPANEL_SAMPLES_RANGE*2) * getDimension());
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

	/**
	 * Paints a legend for the data points
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	public void paintLegend(Graphics g, int x, int y) {
		g.setFont(new Font("Sansserif", Font.PLAIN, 10));
		
		g.setColor(Color.BLACK);
		g.drawString("Training Sample", x + 13, y + 5);
		paintSample(g, x+3, y, properties.getDataColor(0), true);
		
		g.setColor(Color.BLACK);
		g.drawString("Test Sample", x + 13, y + 13 + 5);
		paintSample(g, x+3, y + 13, properties.getDataColor(0), false);
	}
}
