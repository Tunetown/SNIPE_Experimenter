package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import com.dkriesel.snipe.core.NeuralNetwork;

public class SENeuron extends JComponent {
	private static final long serialVersionUID = 1L;

	private Color inputColor = Color.CYAN;
	private Color hiddenColor = Color.orange;
	
	private int neuronDia = 20;
	
	private NeuralNetwork net;
	private SENetView view;
	
	private int num;
	private int x;
	private int y;
	
	public SENeuron(SENetView view, NeuralNetwork net, int num) {
		this.net = net;
		this.num = num;
		this.view = view;
		updateCoords();
	}

	public void updateCoords() {
		this.x = view.getGridSize() + net.getLayerOfNeuron(num) * view.getGridSize() * 2;
		this.y = view.getGridSize() + (num - net.getNeuronFirstInLayer(net.getLayerOfNeuron(num))) * view.getGridSize() * 2;
		this.setBounds(x - neuronDia / 2, y - neuronDia / 2, neuronDia, neuronDia);
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
		
		if (net.getLayerOfNeuron(num) == 0) {
			g.setColor(inputColor); 
		} else {
			g.setColor(hiddenColor); 
		}
		
		g.fillOval(0, 0, neuronDia, neuronDia);
	}
}
