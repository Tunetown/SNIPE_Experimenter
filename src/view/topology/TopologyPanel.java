package view.topology;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import main.Main;

public class TopologyPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Main main;
	
	private NeuronPanel[] neurons;
	private SynapsePainter synapsePainter; 
	
	public TopologyPanel(Main main) {
		this.main = main;
		
		this.setLayout(null);
		
		Dimension dim = new Dimension(400, 400);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setSize(dim);

		// Set up all neurons and synapses
		init();
	}
	
	private void init() {
		int nc = main.getNetwork().countNeurons();
		neurons = new NeuronPanel[nc];
		
		for(int n = 0; n<nc; n++) {
			neurons[n] = new NeuronPanel(main, this, n);
			add(neurons[n]);
		}
		
		synapsePainter = new SynapsePainter(main, this);
	}
	
	public int getGridSize() {
		int minDim = 0;
		if (this.getWidth() > this.getHeight()) {
			minDim = this.getHeight();
		} else {
			minDim = this.getWidth();
		}
		if (getMaxNeuronsInLayer() > main.getNetwork().countLayers()) {
			return (int)(minDim / (getMaxNeuronsInLayer()) / 2);
		} else {
			return (int)(minDim / (main.getNetwork().countLayers()) / 2);
		} 
	}

	// TODO this can be done by the network also
	private int getMaxNeuronsInLayer() {
		int max = 0;
		for(int i=0; i<main.getNetwork().countLayers(); i++) {
			if (main.getNetwork().countNeuronsInLayer(i) > max) max = main.getNetwork().countNeuronsInLayer(i);
		}
		return max;
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		synapsePainter.paint(g);
		synapsePainter.paintLegend(g, this.getWidth() - 100, this.getHeight() - 10, 100, 10);
	}
	
	public NeuronPanel[] getNeurons() {
		return neurons;
	}
}
