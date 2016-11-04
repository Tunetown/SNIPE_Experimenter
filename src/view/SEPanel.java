package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.dkriesel.snipe.core.NeuralNetwork;

public class SEPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private SENeuron[] neurons;
	private SEFrame frame;
	private NeuralNetwork net;
	
	public SEPanel(SEFrame frame) {
		this.frame = frame;
		net = frame.getNetwork();
		
		// Set up all neurons and synapses
		init();
	}

	private void init() {
		this.setLayout(null);
		
		int nc = net.countNeurons() + 1;
		neurons = new SENeuron[nc];
		
		for(int n = 1; n<nc; n++) {
			neurons[n] = new SENeuron(net, n);
			add(neurons[n]);
		}
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		
		paintSynapses(g);
		
		paintLegend(g, 0, 0, 100, 20);
	}
	
	private void paintLegend(Graphics g, int x, int y, int w, int h) {
		for(int i=0; i<w; i++) {
			double we = (double)i/w * 2.0 - 1.0;
			g.setColor(this.getWeightColor(Color.white, Color.GREEN, Color.ORANGE, we));
			g.drawLine(x+i, y, x+i, y+h);
		}
	}

	private void paintSynapses(Graphics g) {
		for(int n = 1; n<neurons.length; n++) {
			for(int n2 = 1; n2<neurons.length; n2++) {
				if (net.isSynapseExistent(n, n2)) {
					paintSynapse(g, n, n2);
				}
			}
		}
	}

	private void paintSynapse(Graphics g, int n1, int n2) {
		g.setColor(getWeightColor(Color.white, Color.GREEN, Color.ORANGE, net.getWeight(n1, n2)));
		SENeuron ne1 = neurons[n1];
		SENeuron ne2 = neurons[n2];
		g.drawLine(ne1.getOutX(), ne1.getOutY(), ne2.getOutX(), ne2.getOutY());
	}
	
	private double normalize(double weight) {
		if (weight > 1.0) return 1.0;
		if (weight < -1.0) return -1.0;
		return weight;
	}

	public Color getWeightColor(Color colorZero, Color colorNeg, Color colorPos, double weight) { 
		double percent;
		Color target;
		
		if(weight > 0) {
			percent = normalize(weight);
			target = colorPos;
		} else {
			percent = normalize(-weight);
			target = colorNeg;
		}
		double inverse_percent = 1.0 - percent;
		
		int redPart = (int)(target.getRed()*percent + colorZero.getRed()*inverse_percent);
		int greenPart = (int)(target.getGreen()*percent + colorZero.getGreen()*inverse_percent);
		int bluePart = (int)(target.getBlue()*percent + colorZero.getBlue()*inverse_percent);
		return new Color(redPart, greenPart, bluePart);
		}
}
