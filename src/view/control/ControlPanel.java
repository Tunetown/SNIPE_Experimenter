package view.control;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import main.Main;

public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Main main;
	private JFrame frame;
	
	private TrainControlPanel trainControls;
	private DataControlPanel dataControls;
	private StatisticsPanel stats;
	
	public ControlPanel(Main main, JFrame frame) {
		super(new BorderLayout(3,3));
		this.main = main;
		this.frame = frame;
		
		trainControls = new TrainControlPanel(this.main, this.frame);
		add(trainControls, BorderLayout.EAST);
		
		dataControls = new DataControlPanel(this.main, this.frame);
		add(dataControls, BorderLayout.CENTER);
		
		stats = new StatisticsPanel(this.main);
		add(stats, BorderLayout.WEST);
	}
	
	public void updateStats() {
		stats.update();
	}

	public void setTrainingStopped() {
		trainControls.setTrainingStopped();
	}
}
