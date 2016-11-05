package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SEControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private SEFrame frame;
	private SETrainingWorker trainWorker;
	
	private JButton trainData;
	
	public SEControlPanel(SEFrame frame) {
		super();
		this.frame = frame;
		
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
		add(resetData);
		
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
		add(trainData);
	}
	
	private void resetData() {
		frame.getNetwork().setLesson(null);
		frame.repaint();
	}
	
	private void trainData() {
		if(trainWorker != null) {
			trainWorker.kill(); 
		} else {
			trainWorker = new SETrainingWorker(frame);
			trainWorker.execute();
		}
	}
}
