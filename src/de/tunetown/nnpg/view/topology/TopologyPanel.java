package de.tunetown.nnpg.view.topology;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.view.MainFrame;
import de.tunetown.nnpg.view.ViewProperties;

/**
 * UI component holding the topology view.
 * 
 * @author xwebert
 *
 */
public class TopologyPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Main main;
	private JFrame frame; 
	
	private NeuronPanel[] neurons;
	private LayerButtongroup[] layerButtons;
	private SynapsePainter synapsePainter;
	
	private int heightBuffer = 0;
	private int widthBuffer = 0;
	private int gridSizeBuffer = 0;

	public TopologyPanel(Main main, JFrame frame) {
		this.main = main;
		this.frame = frame;
		
		this.setLayout(null);
		
		Dimension dim = new Dimension(400, 400);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setSize(dim);

		synapsePainter = new SynapsePainter(main, this);
		
		// Set up all neurons and synapses
		update();
	}
	
	public void update() {
		if (neurons != null && neurons.length > 0) {
			for(NeuronPanel n : neurons) if(n!=null) remove(n);
			for(LayerButtongroup n : layerButtons) if(n!=null) remove(n);
		}
		
		int nc = main.getNetwork().countNeurons();
		neurons = new NeuronPanel[nc];
		layerButtons = new LayerButtongroup[nc];
		
		for(int n = 0; n<nc; n++) {
			neurons[n] = new NeuronPanel(main, this, n);
			add(neurons[n]);
		}

		for(int n = 0; n<main.getNetwork().countLayers(); n++) {
			layerButtons[n] = new LayerButtongroup(main, this, frame, n);
			add(layerButtons[n]);
		}
	}
	
	public NeuronPanel[] getNeurons() {
		return neurons;
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Synapses
		synapsePainter.paint(g);
		
		// Legend for colors
		synapsePainter.paintLegend(g, this.getWidth() - 100, this.getHeight() - 30 - ViewProperties.TOPOLOGY_BUTTON_HEIGHT, 100, 10);
		
		// Legend for data area sample symbols
		MainFrame f = (MainFrame)frame;
		f.getDataPanel().paintLegend(g, this.getWidth() - 100, this.getHeight() - 58 - ViewProperties.TOPOLOGY_BUTTON_HEIGHT);
	}
	
	/**
	 * Determines the grid size for the topology dynamically by the given area size.
	 * 
	 * @return
	 */
	public int getGridSize() {
		synchronized (main.getNetworkLock()) {
			if (getWidth() == widthBuffer && getHeight() == heightBuffer) {
				return gridSizeBuffer;
			}
			
			int minDim = 0;
			if (getWidth() > getHeight() - ViewProperties.TOPOLOGY_BUTTON_HEIGHT) {
				minDim = getHeight() - ViewProperties.TOPOLOGY_BUTTON_HEIGHT;
			} else {
				minDim = getWidth();
			}
			if (main.getNetwork().getMaxNeuronsInLayers() > main.getNetwork().countLayers()) {
				gridSizeBuffer = (int)(minDim / (main.getNetwork().getMaxNeuronsInLayers()) / 2);
			} else {
				gridSizeBuffer = (int)(minDim / (main.getNetwork().countLayers()) / 2);
			}
			
			widthBuffer = getWidth();
			heightBuffer = getHeight();
			return gridSizeBuffer;
		}
	}
	
	public void resetGridSize() {
		widthBuffer = 0;
	}
}
