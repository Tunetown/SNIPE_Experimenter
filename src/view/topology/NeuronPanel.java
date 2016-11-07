package view.topology;

import java.awt.Graphics;

import javax.swing.JComponent;

import view.ViewProperties;
import main.Main;

public class NeuronPanel extends JComponent {
	private static final long serialVersionUID = 1L;

	// TODO scale with grid size
	private int neuronDiameter = 20; // TODO all constants to ViewProperties class!
	private int neuronBorder = 2;
	
	private Main main;
	private TopologyPanel view;
	private int num;
	private int x;
	private int y;
	
	private ViewProperties properties = new ViewProperties();
	
	public NeuronPanel(Main main, TopologyPanel view, int num) {
		this.main = main;
		this.num = num;
		this.view = view;
		updateCoords();
	}

	public void updateCoords() {
		this.x = view.getGridSize() + main.getNetwork().getLayerOfNeuron(num) * view.getGridSize() * 2;
		this.y = view.getGridSize() + (num - main.getNetwork().getFirstNeuronInLayer(main.getNetwork().getLayerOfNeuron(num))) * view.getGridSize() * 2;
		this.setBounds(x - neuronDiameter / 2, y - neuronDiameter / 2, neuronDiameter, neuronDiameter);
	}

	
	public int getOutX() {
		return x;
	}

	public int getOutY() {
		return y;
	}
	
	/**
	 * Paints one neuron 
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
		
		g.fillOval(0, 0, neuronDiameter, neuronDiameter);
		
		// Bias weight
		if ((main.getNetwork().getBiasWeight(num)) != Double.NaN) { // TODO this does not recognize input neurons properly
			g.setColor(properties.getDataColor(main.getNetwork().getBiasWeight(num)));
			g.fillOval(neuronBorder, neuronBorder, neuronDiameter-2*neuronBorder, neuronDiameter-2*neuronBorder);
		}
	}
}
