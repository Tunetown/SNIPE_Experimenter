package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.SENetwork;

import com.dkriesel.snipe.core.NeuralNetwork;

public class SENetView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public SENeuron[] neurons;
	private SEFrame frame;
	private SENetwork net;
	private SESynapses synapsePainter; 
	
	public SENetView(SEFrame frame) {
		this.frame = frame;
		net = frame.getNetwork();
		
		Dimension dim = new Dimension(400, 400);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setSize(dim);

		// Set up all neurons and synapses
		init();
	}
	
	// TODO algorithm is not flexible enough (just try)
	public int getGridSize() {
		int minDim = 0;
		if (this.getWidth() > this.getHeight()) {
			minDim = this.getHeight();
		} else {
			minDim = this.getWidth();
		}
		if (getMaxNeuronsInLayer() > net.getNetwork().countLayers()) {
			return (int)(minDim / (getMaxNeuronsInLayer()) / 2);
		} else {
			return (int)(minDim / (net.getNetwork().countLayers()) / 2);
		} 
	}

	private int getMaxNeuronsInLayer() {
		int max = 0;
		for(int i=0; i<net.getNetwork().countLayers(); i++) {
			if (net.getNetwork().countNeuronsInLayer(i) > max) max = net.getNetwork().countNeuronsInLayer(i);
		}
		return max;
	}

	private void init() {
		this.setLayout(null);
		
		int nc = net.getNetwork().countNeurons() + 1;
		neurons = new SENeuron[nc];
		
		for(int n = 1; n<nc; n++) {
			neurons[n] = new SENeuron(this, n);
			add(neurons[n]);
		}
		
		synapsePainter = new SESynapses(this);
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		synapsePainter.paint(g);
		synapsePainter.paintLegend(g, this.getWidth() - 100, this.getHeight() - 10, 100, 10);
		
	}
	
	public SENetwork getNetwork() {
		return net;
	}
}
