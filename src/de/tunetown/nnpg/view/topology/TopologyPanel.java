package de.tunetown.nnpg.view.topology;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.tunetown.nnpg.main.Main;

/**
 * UI component holding the topology view.
 * 
 * @author xwebert
 *
 */
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
	
	/**
	 * Determines the grid size for the topology dynamically by the given area size.
	 * 
	 * @return
	 */
	public int getGridSize() {
		int minDim = 0;
		if (this.getWidth() > this.getHeight()) {
			minDim = this.getHeight();
		} else {
			minDim = this.getWidth();
		}
		if (main.getNetwork().getMaxNeuronsInLayers() > main.getNetwork().countLayers()) {
			return (int)(minDim / (main.getNetwork().getMaxNeuronsInLayers()) / 2);
		} else {
			return (int)(minDim / (main.getNetwork().countLayers()) / 2);
		} 
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
