package de.tunetown.nnpg.view.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.view.ViewProperties;

/**
 * UI panel for statistics about the network training process
 * 
 * @author xwebert
 *
 */
public class StatisticsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Main main;
	
	private JLabel eta;
	private JLabel batchSize;
	private JLabel dataSize;
	private JLabel iterations;
	private JLabel trainingError;
	private JLabel testError;
	private JLabel procTime;
	//private JLabel procPercentage;
	private ErrorGraphPanel errorGraph;

	public StatisticsPanel(Main main) {
		this.main = main;
		
		setLayout(new BorderLayout(3,3));
		
		errorGraph = new ErrorGraphPanel(this.main);
		add(errorGraph, BorderLayout.CENTER);

		JPanel statsR = new JPanel();
		statsR.setLayout(new BoxLayout(statsR, BoxLayout.PAGE_AXIS));
		add(statsR,BorderLayout.EAST);
		
		eta = new JLabel();
		statsR.add(eta);

		batchSize = new JLabel();
		statsR.add(batchSize);

		dataSize = new JLabel();
		statsR.add(dataSize);

		iterations = new JLabel();
		statsR.add(iterations);

		trainingError = new JLabel();
		statsR.add(trainingError);
		trainingError.setForeground(ViewProperties.ERRORGRAPH_COLOR_TRAINING_ERROR);
		trainingError.setPreferredSize(new Dimension(150, trainingError.getPreferredSize().height));

		testError = new JLabel();
		testError.setForeground(ViewProperties.ERRORGRAPH_COLOR_TEST_ERROR);
		statsR.add(testError);
		
		procTime = new JLabel();
		statsR.add(procTime);

		//procPercentage = new JLabel();
		//statsR.add(procPercentage);

		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		
		update();
	}
	
	/**
	 * Update all statistics according to the network
	 * 
	 */
	public void update() {
		synchronized (main.getNetworkLock()) {
			setEta(main.getNetwork().getEta());
			setBatchSize(main.getNetwork().getBatchSize());
			setDataSize(main.getData().getNumOfSamples(false) + main.getData().getNumOfSamples(true));
			setIteration(main.getTracker().getIterations());
			setTrainingError(main.getNetwork().getTrainingError(main.getData()));
			setTestError(main.getNetwork().getTestError(main.getData()));
			setProcTime(main.getTracker().getProcessingTime());
			//setProcPercentage(main.getTracker().getProcessingPercentage());
		}
	}

	private void setEta(double value) {
		DecimalFormat df = new DecimalFormat("#.#####");
		eta.setText("Eta: " + df.format(value));
	}

	private void setBatchSize(int value) {
		batchSize.setText("Batch Size: " + value);
	}

	private void setDataSize(int size) {
		dataSize.setText("Data Size: " + size);
	}

	private void setIteration(int i) {
		iterations.setText("Iterations: " + i);
	}

	private void setTrainingError(double i) {
		DecimalFormat df = new DecimalFormat("#.##");
		trainingError.setText("Training Error: " + df.format(i));
	}

	private void setTestError(double i) {
		DecimalFormat df = new DecimalFormat("#.##");
		testError.setText("Test Error: " + df.format(i));
	}

	private void setProcTime(long t) {
		if (t < 1000000000) {
			procTime.setText("CPU Time: " + (t / 1000000) + "ms");
		} else {
			procTime.setText("CPU Time: " + (t / 1000000000) + "s");
		}
	}
	
	/*
	private void setProcPercentage(double perc) {
		DecimalFormat df = new DecimalFormat("#.#####");
		procPercentage.setText("CPU: " + df.format(perc) + "%");
	}*/
}
