package de.tunetown.nnpg.view.control;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tunetown.nnpg.main.Main;

/**
 * Control panel UI component, containing all controls.
 * 
 * @author Thomas Weber
 *
 */
public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Main main;
	private JFrame frame;
	
	private TrainControlPanel trainControls;
	private DataControlPanel dataControls;
	private StatisticsPanel stats;
	
	public ControlPanel(Main main, JFrame frame) {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		this.main = main;
		this.frame = frame;
		
		stats = new StatisticsPanel(this.main);
		add(stats);

		dataControls = new DataControlPanel(this.main, this.frame);
		add(dataControls);

		trainControls = new TrainControlPanel(this.main, this.frame);
		add(trainControls);
	}
	
	/**
	 * Update the network statistics
	 * 
	 */
	public void updateStats() {
		stats.update();
	}

	public void updateControls() {
		trainControls.update();
	}

	/**
	 * Tells the UI that training has been stopped
	 * 
	 */
	public void setTrainingStopped() {
		trainControls.setTrainingStopped();
	}
}
