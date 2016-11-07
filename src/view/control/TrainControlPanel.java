package view.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Main;

public class TrainControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Main main;
	private JFrame frame;
	
	public JButton trainData;
	public JButton trainStop;

	public TrainControlPanel(Main main, JFrame frame) {
		this.main = main;
		this.frame = frame;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
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
		add(trainStop);
		trainStop.setEnabled(false);
		
		JButton trainReset = new JButton("Reset");
		trainReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					trainReset();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		add(trainReset);
		
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	}
	
	private void trainReset() {
		main.stopTraining();
		main.initNetwork();
		frame.repaint();
	}
	
	private void trainData() {
		main.startTraining();
		
		trainData.setEnabled(false);
		trainStop.setEnabled(true);
	}
	
	private void trainStop() {
		main.stopTraining();
	}

	public void setTrainingStopped() {
		trainData.setEnabled(true);
		trainStop.setEnabled(false);
	}
}
