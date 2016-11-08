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
	private long procTime = 0;
	
	public TrainingWorker(Main main, JFrame frame) {
		this.main = main;
		this.frame = frame;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Object doInBackground() throws Exception {
		try {
			while (!isKilled()) {
				long start = System.currentTimeMillis();
				main.getNetwork().train(main.getData(), main.getTracker());
				procTime += System.currentTimeMillis() - start;
				
				//main.updateStats();
				
				//frame.repaint(); // TODO optimize screen flickering and thread concept
				publish();
				
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
