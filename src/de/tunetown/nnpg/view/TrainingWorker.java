package de.tunetown.nnpg.view;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.model.NetworkWrapper;

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
			// Start tracking of statistics about this training process
			//main.getTracker().startTracking(); TODO cleanup

			while (!isKilled()) {
				// Create a working clone of the network for training. This is necessary 
				// to be able to update the UI in parallel.
				NetworkWrapper clone;
				synchronized (main.getNetworkLock()) {
					clone = main.getNetwork().clone();
				}

				// Train the working clone.
				clone.train(main.getData(), main.getTracker());
				
				// Set the trained clone "productive"
				synchronized (main.getNetworkLock()) {
					clone.setParametersFrom(main.getNetwork());
					main.setNetwork(clone);
				}
				
				// Update statistics in the UI
				main.updateStats();
				
				// Trigger that repainting will happen in the EDT. The repainting routines
				// will use the new "productive" network instance, while we will continue training
				// on the next clone here.
				frame.repaint(); 
			};
			
		} catch(Throwable t) {
			t.printStackTrace(); 
		}
		return null;
	}
	
	public boolean isKilled() {
		return killed;
	}

	/** TODO cleanup
	@Override
	protected void process(List chunks) {
		main.updateStats();
		frame.repaint();
	}
	*/

	@Override
	protected void done() {
		main.setTrainingStopped();
	}

	public void kill() {
		killed = true;
	}

}
