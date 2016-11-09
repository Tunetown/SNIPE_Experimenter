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
			while (!isKilled()) {
				// Stop training if no data is present
				if (!main.getData().hasData()) kill();

				// Create a working clone of the network for training. This is necessary 
				// to be able to update the UI in parallel.
				NetworkWrapper clone;
				synchronized (main.getNetworkLock()) {
					clone = main.getNetwork().clone();
				}

				// Train the working clone.
				try {
					long start = System.nanoTime();
					
					clone.train(main.getData());
					
					main.getTracker().addRun(
							main.getNetwork().getTrainingError(main.getData()), 
							main.getNetwork().getTestError(main.getData()),
							System.nanoTime() - start);
				
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
					
				} catch (Exception e) {
					System.out.println("Training glitch occurred, stopped training");
					kill();
				}
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
	protected void done() {
		main.setTrainingStopped();
	}

	public void kill() {
		killed = true;
	}

}
