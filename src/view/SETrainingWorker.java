package view;

import java.util.List;
import javax.swing.SwingWorker;

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
				System.out.println("Train");
				frame.getNetwork().getNetwork().trainBackpropagationOfError(frame.getNetwork().getLesson(), 1, 0.03);
				publish();
				
				Thread.sleep(100);
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
		frame.repaint();
	}

	@Override
	protected void done() {
	}

	public void kill() {
		killed = true;
	}

}
