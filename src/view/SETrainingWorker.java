package view;

import java.util.List;

import javax.swing.SwingWorker;

@SuppressWarnings("rawtypes")
public class SETrainingWorker extends SwingWorker {

	private SEFrame frame;
	private boolean killed;
	
	public SETrainingWorker(SEFrame frame) {
		this.frame = frame;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		try {
			while (!isKilled()) {
				frame.getNetwork().train();
				frame.getMainPanel().getControlPanel().updateStats();
				frame.repaint();
				//publish();
				
				Thread.sleep(50);
			};
			
		} catch(Throwable t) {
			t.printStackTrace(); // TODO when quit, exception can occur here because no propagation data is there
		}
		return null;
	}
	
	public boolean isKilled() {
		return killed;
	}

	@Override
	protected void process(List chunks) {
		frame.getMainPanel().getControlPanel().updateStats();
		frame.repaint();
	}

	@Override
	protected void done() {
		frame.getMainPanel().getControlPanel().trainingStopped();
	}

	public void kill() {
		killed = true;
	}

}
