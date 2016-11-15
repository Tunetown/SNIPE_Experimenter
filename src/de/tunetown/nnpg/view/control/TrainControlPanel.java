package de.tunetown.nnpg.view.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.tunetown.nnpg.main.Main;

/**
 * UI panel holding all training options and buttons
 * 
 * @author xwebert
 *
 */
public class TrainControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Main main;
	private JFrame frame;
	
	private JButton trainData;
	private JButton trainStop;
	
	private JComboBox behaviors;
	private JSlider etaSlider;
	private JSlider batchSlider;

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

		// Activation functions
		behaviors = new JComboBox(main.getNetwork().getBehaviorDescriptions());
		behaviors.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					int choice = ((JComboBox)ae.getSource()).getSelectedIndex();
					setBehavior(choice);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		behaviors.setSelectedIndex(main.getNetwork().getBehavior());
		buttons.add(behaviors);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initControls() {
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.PAGE_AXIS));
		add(controls);
		
		// Learning rate (eta)
		JLabel etaLabel = new JLabel("Learning Rate (Eta):");
		controls.add(etaLabel);
		
		etaSlider = new JSlider(JSlider.HORIZONTAL, 0, 120, convertModelToSlider(main.getNetwork().getEta()));
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
		etaSlider.setMinorTickSpacing(20);
		etaSlider.setPaintTicks(true);
		
		etaSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				double eta = convertSliderToModel(value);
				main.getNetwork().setEta(eta);
				main.updateView(false, false, false);
			}
		});
		controls.add(etaSlider);

		// Batch size
		JLabel batchLabel = new JLabel("Batch Size:");
		controls.add(batchLabel);
		
		batchSlider = new JSlider(JSlider.HORIZONTAL, 0, 100000, main.getNetwork().getBatchSize());
		Hashtable labelTable2 = new Hashtable();
		for(int i=0; i<=100000; i+=50000) {
			labelTable2.put( i, new JLabel(""+i));
		}
		batchSlider.setLabelTable( labelTable2 );
		batchSlider.setPaintLabels(true);
		batchSlider.setMinorTickSpacing(10000);
		batchSlider.setPaintTicks(true);
		
		batchSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider)e.getSource()).getValue();
				if (value == 0) value = 1;
				main.getNetwork().setBatchSize(value);
				main.updateView(false, false, false);
			}
		});
		controls.add(batchSlider);

	}
	
	/**
	 * Update the controls from the network
	 * 
	 */
	public void update() {
		setEtaSlider(main.getNetwork().getEta());
		batchSlider.setValue(main.getNetwork().getBatchSize());
		behaviors.setSelectedIndex(main.getNetwork().getBehavior());
	}

	/**
	 * Set slider to a given learning rate
	 * 
	 * @param eta
	 */
	public void setEtaSlider(double eta) {
		etaSlider.setValue(convertModelToSlider(eta));
	}
	
	/**
	 * Convert slider value to learning rate (exponential)
	 * 
	 * @param i
	 * @return
	 */
	private double convertSliderToModel(int i) {
		return Math.exp((double)i/10) / 10000;
	}
	
	/**
	 * Convert learning rate to slider value (log)
	 * 
	 * @param d
	 * @return
	 */
	private int convertModelToSlider(double d) {
		return (int)(Math.log(d * 10000) * 10);
	}
	
	/**
	 * Reset network
	 * 
	 */
	private void trainReset() {
		main.stopTraining(true);
		main.initNetwork();
		frame.repaint();
	}
	
	/**
	 * Trigger training start
	 * 
	 */
	private void trainData() {
		main.startTraining();
		
		trainData.setEnabled(false);
		trainStop.setEnabled(true);
	}
	
	/**
	 * Kill training thread
	 * 
	 */
	private void trainStop() {
		main.stopTraining(true);
	}

	/**
	 * Tell the UI that training has been stopped
	 * 
	 */
	public void setTrainingStopped() {
		trainData.setEnabled(true);
		trainStop.setEnabled(false);
	}
	
	/**
	 * Set a behavior by index
	 * 
	 * @param choice
	 */
	private void setBehavior(int choice) {
		main.stopTraining(true);
		main.getNetwork().setBehavior(choice);
		main.updateView(false, false, false);
		frame.repaint();
	}
}
