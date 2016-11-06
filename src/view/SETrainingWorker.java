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
				frame.getNetwork().getLesson().optimizeDesiredOutputsForClassificationProblem(frame.getNetwork().getNetwork());
				frame.getNetwork().getNetwork().trainBackpropagationOfError(frame.getNetwork().getLesson(), 10, 0.03);
				publish();
				
				Thread.sleep(5);
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
