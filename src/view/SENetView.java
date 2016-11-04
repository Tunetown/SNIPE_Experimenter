package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.dkriesel.snipe.core.NeuralNetwork;

public class SENetView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public SENeuron[] neurons;
	private SEFrame frame;
	private NeuralNetwork net;
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
	
	public int getGridSize() {
		int minDim = 0;
		if (this.getWidth() > this.getHeight()) {
			minDim = this.getHeight();
		} else {
			minDim = this.getWidth();
		}
		if (getMaxNeuronsInLayer() > net.countLayers()) {
			return (int)(minDim / (getMaxNeuronsInLayer()) / 2);
		} else {
			return (int)(minDim / (net.countLayers()) / 2);
		}
	}

	private int getMaxNeuronsInLayer() {
		int max = 0;
		for(int i=0; i<net.countLayers(); i++) {
			if (net.countNeuronsInLayer(i) > max) max = net.countNeuronsInLayer(i);
		}
		return max;
	}

	private void init() {
		this.setLayout(null);
		
		int nc = net.countNeurons() + 1;
		neurons = new SENeuron[nc];
		
		for(int n = 1; n<nc; n++) {
			neurons[n] = new SENeuron(this, net, n);
			add(neurons[n]);
		}
		
		synapsePainter = new SESynapses(this, net);
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		synapsePainter.paint(g);
		synapsePainter.paintLegend(g, this.getWidth() - 100, this.getHeight() - 10, 100, 10);
		
	}
}
