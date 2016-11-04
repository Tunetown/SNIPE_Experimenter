package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.SENetwork;

import com.dkriesel.snipe.core.NeuralNetwork;

public class SEOutputView extends JPanel {
	private static final long serialVersionUID = 1L;

	private int defaultSize = 400;
	private int resolution = 4;
	
	private SENetwork net;
	private SEFrame frame;
	
	public SEOutputView(SEFrame frame) {
		this.frame = frame;
		this.net = frame.getNetwork();
		
		Dimension dim = new Dimension(defaultSize, defaultSize);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK); 
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		paintGraph(g);
	}

	private void paintGraph(Graphics g) {
		int minDim = 0;
		if (this.getWidth() > this.getHeight()) {
			minDim = this.getHeight();
		} else {
			minDim = this.getWidth();
		}
		
		for(int x = 0; x<minDim; x+=resolution) {
			for(int y = 0; y<minDim; y+=resolution) {
				g.setColor(getOutColor((2 * (double)x / minDim) - 1.0, (2 * (double)y / minDim) - 1.0));
				g.fillRect(x, y, resolution, resolution);
			}			
		}
	}

	/**
	 * 
	 * @param x in [-1, 1]
	 * @param y in [-1, 1]
	 * @return
	 */
	private Color getOutColor(double x, double y) {
		double[] in = {x, y};
		double[] out = net.getNetwork().propagate(in);
		return SESynapses.getWeightColor(Color.LIGHT_GRAY, Color.green, Color.red, out[0]);
	}
}
