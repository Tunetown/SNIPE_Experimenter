package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SEControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private SEFrame frame;
	private SETrainingWorker trainWorker;
	
	public JButton trainData;
	public JButton trainStop;
	
	private JPanel stats;
	private JPanel trainControls;
	private JPanel dataControls;
	
	private JLabel iterations;
	private JLabel rmsError;
	private SEErrorGraph errorGraph;
	
	public SEControlPanel(SEFrame frame) {
		super(new BorderLayout(3,3));
		this.frame = frame;
		
		initTrainingControls();
		initDataControls();
		initStats();
	}
	
	private void initStats() {
		stats = new JPanel();
		add(stats, BorderLayout.WEST);
		
		errorGraph = new SEErrorGraph(frame);
		stats.add(errorGraph);

		JPanel statsR = new JPanel();
		statsR.setLayout(new BoxLayout(statsR, BoxLayout.PAGE_AXIS));
		stats.add(statsR);
		
		iterations = new JLabel();
		statsR.add(iterations);
		setIteration(0);

		rmsError = new JLabel();
		statsR.add(rmsError);
		setRmsError(1);
	}
	
	private void initDataControls() {
		dataControls = new JPanel();
		add(dataControls, BorderLayout.CENTER);
		
		JButton resetData = new JButton("Reset Data");
		resetData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					resetData();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		dataControls.add(resetData);
	}
	
	public void setIteration(int i) {
		iterations.setText("Iterations: " + i);
	}

	public void setRmsError(double i) {
		DecimalFormat df = new DecimalFormat("#.##");
		rmsError.setText("RMS Error: " + df.format(i));
	}

	public void updateStats() {
		setIteration(frame.getNetwork().getIterations());
		setRmsError(frame.getNetwork().getRmsError());
	}

	private void initTrainingControls() {
		trainControls = new JPanel();
		add(trainControls, BorderLayout.EAST);
		
		trainData = new JButton("Train");
		trainData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					trainData();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		trainControls.add(trainData);

		trainStop = new JButton("Stop");
		trainStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					trainStop();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		trainControls.add(trainStop);
		trainStop.setEnabled(false);
	}
	
	private void resetData() {
		frame.getNetwork().setLesson(null);
		frame.repaint();
	}
	
	private void trainData() {
		trainWorker = new SETrainingWorker(frame);
		trainWorker.execute();
		
		trainData.setEnabled(false);
		trainStop.setEnabled(true);
	}
	
	private void trainStop() {
		trainWorker.kill();
	}

	public void trainingStopped() {
		trainData.setEnabled(true);
		trainStop.setEnabled(false);
	}
}
