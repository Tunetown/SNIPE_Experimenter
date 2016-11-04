package view;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.dkriesel.snipe.core.NeuralNetwork;

public class SEFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private SEPanel mainPanel;
	private NeuralNetwork net;

	public SEFrame(String title, NeuralNetwork net) {
		super(title);
		this.net = net;
	}
	
	public void init() {
		// Set up GUI elements (this results in a container holding all elements,
		// which will be embedded in a major JFrame in the following lines) by creating
		// the main GUI instance
		mainPanel = new SEPanel(this);
		add(mainPanel);
		
		// Add this main GUI instance to the main frame (this contains all elements)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainPanel);

		// Do some size and location stuff
		//pack();
		setLocationByPlatform(true);
		setMinimumSize(new Dimension(1000, 500));
		setVisible(true);
	}
	
	public NeuralNetwork getNetwork() {
		return net;
	}
}
