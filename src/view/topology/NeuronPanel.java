package view.topology;

import java.awt.Graphics;

import javax.swing.JComponent;

import view.ViewProperties;
import main.Main;

/**
 * UI model for painting one neuron in the topology area.
 * 
 * @author xwebert
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
		this.x = view.getGridSize() + main.getNetwork().getLayerOfNeuron(num) * view.getGridSize() * 2;
		this.y = view.getGridSize() + (num - main.getNetwork().getFirstNeuronInLayer(main.getNetwork().getLayerOfNeuron(num))) * view.getGridSize() * 2;
		this.setBounds(x - ViewProperties.TOPOLOGY_NEURON_DIAMETER / 2, y - ViewProperties.TOPOLOGY_NEURON_DIAMETER / 2, ViewProperties.TOPOLOGY_NEURON_DIAMETER, ViewProperties.TOPOLOGY_NEURON_DIAMETER);
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
	public void paintComponent(Graphics g) {
		updateCoords();
		
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
