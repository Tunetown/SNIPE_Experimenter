package de.tunetown.nnpg.view.topology;

import java.awt.Graphics;
import javax.swing.JComponent;
import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.view.ViewProperties;

/**
 * UI model for painting one neuron in the topology area.
 * 
 * @author Thomas Weber
 *
 */
public class NeuronPanel extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private Main main;
	private TopologyPanel view;
	private int num;
	private int x;
	private int y;
	
	private ViewProperties properties = new ViewProperties();
	private int neuronBorder = ViewProperties.TOPOLOGY_NEURON_DIAMETER / 10;
	
	public NeuronPanel(Main main, TopologyPanel view, int num) {
		this.main = main;
		this.num = num;
		this.view = view;
		updateCoords();
	}

	/**
	 * Set the x/y values of the neuron according to grid size and the network topology
	 * 
	 */
	public void updateCoords() {
		int[] gs = view.getGridSize();

		this.x = gs[0] + main.getNetwork().getLayerOfNeuron(num) * gs[0] * 2;
		this.y = gs[1] + (num - main.getNetwork().getFirstNeuronInLayer(main.getNetwork().getLayerOfNeuron(num))) * gs[1] * 2;
		
		this.setBounds(x - ViewProperties.TOPOLOGY_NEURON_DIAMETER / 2, 
				       y - ViewProperties.TOPOLOGY_NEURON_DIAMETER / 2, 
				       ViewProperties.TOPOLOGY_NEURON_DIAMETER, 
				       ViewProperties.TOPOLOGY_NEURON_DIAMETER);
	}

	public int getOutX() {
		return x;
	}

	public int getOutY() {
		return y;
	}
	
	/**
	 * Paints the neuron. All neurons except the inputs also will show their bias weight as inner color.
	 * 
	 * @param g
	 * @param net
	 * @param index
	 */
	@Override
	public void paintComponent(Graphics g) {
		synchronized (main.getNetworkLock()) {
			if (main.getNetwork().getLayerOfNeuron(num) == 0) {
				g.setColor(ViewProperties.COLOR_NEURON_INPUT); 
			} else {
				g.setColor(ViewProperties.COLOR_NEURON_HIDDEN); 
			}
			
			g.fillOval(0, 0, ViewProperties.TOPOLOGY_NEURON_DIAMETER, ViewProperties.TOPOLOGY_NEURON_DIAMETER);
			
			// Bias weight
			if (neuronBorder > 0 && !Double.isNaN(main.getNetwork().getBiasWeight(num))) { 
				g.setColor(properties.getDataColor(main.getNetwork().getBiasWeight(num)));
				g.fillOval(neuronBorder, neuronBorder, ViewProperties.TOPOLOGY_NEURON_DIAMETER-2*neuronBorder, ViewProperties.TOPOLOGY_NEURON_DIAMETER-2*neuronBorder);
			}
		}
	}
}
