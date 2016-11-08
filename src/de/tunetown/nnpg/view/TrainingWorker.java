package de.tunetown.nnpg.view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import de.tunetown.nnpg.main.Main;

/**
 * Swing worker which processes the training of the network.
 * 
 * @author xwebert
 *
 */
@SuppressWarnings("rawtypes")
public class TrainingWorker extends SwingWorker {

	private Main main;
	private JFrame frame;
	
	private boolean killed;
	
	public TrainingWorker(Main main, JFrame frame) {
		this.main = main;
		this.frame = frame;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		try {
			main.getTracker().startTracking();

			while (!isKilled()) {
				main.getNetwork().train(main.getData(), main.getTracker());
				
				main.updateStats();
				frame.repaint(); 
				//publish();
				
				Thread.sleep(50);
			};
			
		} catch(Throwable t) {
			t.printStackTrace(); 
		}
		return null;
	}
	
	public boolean isKilled() {
		return killed;
	}

	@Override
	protected void process(List chunks) {
		main.updateStats();
		frame.repaint();
	}

	@Override
	protected void done() {
		main.setTrainingStopped();
	}

	public void kill() {
		killed = true;
	}

}
