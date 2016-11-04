package view;

import javax.swing.JFrame;
import com.dkriesel.snipe.core.NeuralNetwork;

public class SEFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private SEMainPanel mainPanel;
	private NeuralNetwork net;

	public SEFrame(String title, NeuralNetwork net) {
		super(title);
		this.net = net;
	}
	
	public void init() {
		mainPanel = new SEMainPanel(this);
		
		// Add this main GUI instance to the main frame (this contains all elements)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainPanel);

		// Do some size and location stuff
		pack();
		setLocationByPlatform(true);
		setVisible(true);
	}
	
	public NeuralNetwork getNetwork() {
		return net;
	}
}
