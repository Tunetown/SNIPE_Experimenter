package view.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		initControls();
		initButtons();
		
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	}
	
	private void initButtons() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
		add(buttons);
		
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
		buttons.add(trainData);

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
		buttons.add(trainStop);
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
		buttons.add(trainReset);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initControls() {
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
		add(controls);
		
		// Learning rate (eta)
		JLabel etaLabel = new JLabel("Learning Rate (eta):");
		controls.add(etaLabel);
		
		JSlider etaSlider = new JSlider(JSlider.HORIZONTAL, 0, 120, convertModelToSlider(main.getNetwork().getEta()));
		Hashtable labelTable = new Hashtable();
		DecimalFormat df = new DecimalFormat("#.##");
		DecimalFormat df0 = new DecimalFormat(".###");
		for(int i=0; i<=120; i+=20) {
			double value = convertSliderToModel(i);
			if (value < 0.05) 
				labelTable.put( i, new JLabel(df0.format(value)));
			else
				labelTable.put( i, new JLabel(df.format(value)));
		}
		etaSlider.setLabelTable( labelTable );
		etaSlider.setPaintLabels(true);
		etaSlider.setMajorTickSpacing(20);
		etaSlider.setPaintTicks(true);
		
		etaSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				double eta = convertSliderToModel(value);
				main.getNetwork().setEta(eta);
				main.updateStats();
			}
		});
		controls.add(etaSlider);

		// Batch size
		JLabel batchLabel = new JLabel("Batch Size:");
		controls.add(batchLabel);
		
		JSlider batchSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, main.getNetwork().getBatchSize());
		Hashtable labelTable2 = new Hashtable();
		for(int i=0; i<=1000; i+=500) {
			labelTable2.put( i, new JLabel(""+i));
		}
		batchSlider.setLabelTable( labelTable2 );
		batchSlider.setPaintLabels(true);
		batchSlider.setMajorTickSpacing(500);
		batchSlider.setPaintTicks(true);
		
		batchSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				main.getNetwork().setBatchSize(value);
				main.updateStats();
			}
		});
		controls.add(batchSlider);

	}
	
	private double convertSliderToModel(int i) {
		return Math.exp((double)i/10) / 10000;
	}
	
	private int convertModelToSlider(double d) {
		return (int)(Math.log(d * 10000) * 10);
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
