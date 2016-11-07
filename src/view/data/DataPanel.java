package view.data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import view.topology.SynapsePainter;
import main.Main;

public class DataPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int defaultSize = 400;
	private int resolution = 4;
	private int sampleDia = 6;
	private double samplesRange = 10.0;
	
	private Main main;
	
	public DataPanel(Main main) {
		this.main = main;
		
		Dimension dim = new Dimension(defaultSize, defaultSize);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		
		final DataPanel wrapper = this;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				wrapper.main.getData().addSample(
						wrapper.convertToModel(e.getPoint().x), 
						wrapper.convertToModel(e.getPoint().y), 
						(e.getButton() == 1) ? 1.0 : ((e.getButton() == 2) ? 0 : -1.0));
				wrapper.repaint();
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		paintGraph(g);
		paintSamples(g);
	}

	private void paintSamples(Graphics g) {
		if(main.getData().getNumOfSamples() == 0) return;
		
		for(int n=0; n<main.getData().getInputs().length; n++) {
			double[][] in = main.getData().getInputs();
			double[][] out = main.getData().getDesiredOutputs();
			paintSample(g, in[n][0], in[n][1], out[n][0]);
		}
	}

	/**
	 * 
	 * @param x in [-1,1]
	 * @param y in [-1,1]
	 * @param out
	 */
	private void paintSample(Graphics g, double x, double y, double out) {
		g.setColor(SynapsePainter.getDataColor(Color.LIGHT_GRAY, Color.green, Color.red, out));
		g.fillOval(convertToView(x) - sampleDia/2, convertToView(y) - sampleDia/2, sampleDia, sampleDia);
		g.setColor(Color.BLACK);
		g.drawOval(convertToView(x) - sampleDia/2, convertToView(y) - sampleDia/2, sampleDia, sampleDia);
		
	}

	private int getDimension() {
		int minDim = 0;
		if (this.getWidth() > this.getHeight()) {
			minDim = this.getHeight();
		} else {
			minDim = this.getWidth();
		}
		return minDim;
	}
	
	private void paintGraph(Graphics g) {
		int minDim = getDimension();
		for(int x = 0; x<minDim; x+=resolution) {
			for(int y = 0; y<minDim; y+=resolution) {
				g.setColor(getOutColor(convertToModel(x), convertToModel(y)));
				g.fillRect(x, y, resolution, resolution);
			}			
		}
	}
	
	private double convertToModel(int in) {
		return (samplesRange*2 * (double)in / (double)getDimension()) - samplesRange;
	}
	
	private int convertToView(double in) {
		return (int)((in + samplesRange) / (samplesRange*2) * getDimension());
	}

	/**
	 * 
	 * @param x in [-1, 1]
	 * @param y in [-1, 1]
	 * @return
	 */
	private Color getOutColor(double x, double y) {
		double[] in = {x, y};
		double[] out = main.getNetwork().propagate(in);
		//System.out.println(x+"                "+y+"     "+out[0]);
		return SynapsePainter.getDataColor(Color.LIGHT_GRAY, Color.green, Color.red, out[0]);
	}
}
