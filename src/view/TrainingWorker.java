package view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import main.Main;

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
				frame.repaint(); // TODO optimize screen flickering and thread concept -> With sleep = 0, nothing works!
				// Concept: Use cloned instance for display rendering. BEWARE: No instance cloning during repaint! 
				//publish();
				
				Thread.sleep(5);
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
