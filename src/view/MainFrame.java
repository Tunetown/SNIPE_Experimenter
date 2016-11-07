package view;

import javax.swing.JFrame;

import view.control.ControlPanel;
import main.Main;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Main main;
	private MainPanel mainPanel;
	
	private TrainingWorker trainWorker;
	
	public MainFrame(Main main) {
		super("Neural Network Playground");
		this.main = main;
	}
	
	public void init() {
		mainPanel = new MainPanel(main, this);
		
		// Add this main GUI instance to the main frame (this contains all elements)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(mainPanel);

		// Do some size and location stuff
		pack();
		setLocationByPlatform(true);
		setVisible(true);
	}

	public ControlPanel getControlPanel() {
		return mainPanel.getControlPanel();
	}
	
	public TrainingWorker getTrainingWorker() {
		return trainWorker;
	}
	
	public void setTrainingWorker(TrainingWorker w) {
		trainWorker = w;
	}
}
