package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.dkriesel.snipe.core.NeuralNetwork;

public class SEPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int gridSize = 30;
	private int neuronDia = 20;
	
	private SEFrame frame;
	private NeuralNetwork net;
	
	public SEPanel(SEFrame frame) {
		this.frame = frame;
		net = frame.getNetwork();
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		
		paintSynapses(g);
		paintNeurons(g);
	}
	
	private void paintSynapses(Graphics g) {
		
		int nc = net.countNeurons();
		
		for(int n = 0; n<nc; n++) {
			for(int n2 = 0; n2<nc; n2++) {
				if (net.isSynapseExistent(n, n2)) {
					paintSynapse(g, n, n2);
				}
			}
		}
		
		
		/*int layerbase = 0;
		for(int layer = 0; layer<net.countLayers(); layer++) {
			for(int neuron = 0; neuron<net.countNeuronsInLayer(layer); neuron++) {
				int index = layerbase + neuron;
				int x = gridSize + layer * gridSize * 2;
				int y = gridSize + neuron * gridSize * 2;
				int t = 0;
				if (layer == 0) t = 1; 
				
				paintNeutron(g, index, x, y, t);
			}
			layerbase += net.countNeuronsInLayer(layer);
		}*/
	}
	
	private void paintSynapse(Graphics g, int n, int n2) {
		g.setColor(Color.RED);
		int[] coord1 = getNeuronCoords(n);
		int[] coord2 = getNeuronCoords(n2);
		g.drawLine(coord1[0], coord1[1], coord2[0], coord2[1]);
	}

	private int[] getNeuronCoords(int n) {
		int[] ret = new int[2];
		
		return ret;
	}

	private void paintNeurons(Graphics g) {
		int layerbase = 0;
		for(int layer = 0; layer<net.countLayers(); layer++) {
			for(int neuron = 0; neuron<net.countNeuronsInLayer(layer); neuron++) {
				int index = layerbase + neuron;
				int x = gridSize + layer * gridSize * 2;
				int y = gridSize + neuron * gridSize * 2;
				int t = 0;
				if (layer == 0) t = 1; 
				
				paintNeutron(g, index, x, y, t);
			}
			layerbase += net.countNeuronsInLayer(layer);
		}
	}

	/**
	 * Paints one neuron by its absolute index (neuron number)
	 * 
	 * @param g
	 * @param net
	 * @param index
	 */
	private void paintNeutron(Graphics g, int index, int x, int y, int type) {
		switch(type) {
		case 1: g.setColor(Color.CYAN); break;
		default: g.setColor(Color.ORANGE); break;
		}
		
		g.fillOval(x, y, neuronDia, neuronDia);
	}
}
