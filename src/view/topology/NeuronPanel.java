package view.topology;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import main.Main;

public class NeuronPanel extends JComponent {
	private static final long serialVersionUID = 1L;

	private Color inputColor = Color.CYAN;
	private Color hiddenColor = Color.ORANGE;
	private int neuronDiameter = 20;
	
	private Main main;
	private TopologyPanel view;
	private int num;
	private int x;
	private int y;
	
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
			g.setColor(inputColor); 
		} else {
			g.setColor(hiddenColor); 
		}
		
		g.fillOval(0, 0, neuronDiameter, neuronDiameter);
		
		// TODO visualize bias weight to this neuron somehow
	}
}
