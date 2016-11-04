package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import com.dkriesel.snipe.core.NeuralNetwork;

public class SENeuron extends JComponent {
	private static final long serialVersionUID = 1L;

	private int neuronDia = 20;
	private int gridSize = 40;
	
	private NeuralNetwork net;
	
	private int num;
	private int x;
	private int y;
	
	public SENeuron(NeuralNetwork net, int num) {
		this.net = net;
		this.num = num;
		this.x = gridSize + net.getLayerOfNeuron(num) * gridSize * 2;
		this.y = gridSize + (num - net.getNeuronFirstInLayer(net.getLayerOfNeuron(num))) * gridSize * 2;
		this.setBounds(x - neuronDia / 2, y - neuronDia / 2, neuronDia, neuronDia);
	}

	public int getOutX() {
		return x;
	}

	public int getOutY() {
		return y;
	}
	
	/**
	 * Paints one neuron by its absolute index (neuron number)
	 * 
	 * @param g
	 * @param net
	 * @param index
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.ORANGE); 
		
		g.fillOval(0, 0, neuronDia, neuronDia);
	}
}
