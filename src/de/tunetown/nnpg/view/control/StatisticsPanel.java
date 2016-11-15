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
 * @author Thomas Weber
 *
 */
public class StatisticsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Main main;
	
	private ErrorGraphPanel errorGraph;
	
	private JLabel dataSize;
	private JLabel eta;
	private JLabel batchSize;
	private JLabel activationFunction;
	private JLabel iterations;
	private JLabel trainingError;
	private JLabel testError;
	private JLabel procTime;
	private JLabel speed;
	
	public StatisticsPanel(Main main) {
		this.main = main;
		
		setLayout(new BorderLayout(3,3));
		
		errorGraph = new ErrorGraphPanel(this.main);
		add(errorGraph, BorderLayout.CENTER);

		JPanel statsR = new JPanel();
		statsR.setLayout(new BoxLayout(statsR, BoxLayout.PAGE_AXIS));
		add(statsR,BorderLayout.EAST);
		
		dataSize = new JLabel();
		statsR.add(dataSize);

		activationFunction = new JLabel();
		statsR.add(activationFunction);

		eta = new JLabel();
		statsR.add(eta);

		batchSize = new JLabel();
		statsR.add(batchSize);

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

		speed = new JLabel();
		speed.setForeground(ViewProperties.ERRORGRAPH_COLOR_SPEED);
		statsR.add(speed);

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
			setActivationFunction(main.getNetwork().getBehaviorDescriptions()[main.getNetwork().getBehavior()]);
			setDataSize(main.getData().getNumOfSamples(false) + main.getData().getNumOfSamples(true));
			setIteration(main.getTracker().getIterations());
			setTrainingError(main.getNetwork().getTrainingError(main.getData()));
			setTestError(main.getNetwork().getTestError(main.getData()));
			setProcTime(main.getTracker().getCPUUsage());
			setSpeed(main.getTracker().getCurrentSpeed());
		}
	}

	private void setActivationFunction(String string) {
		activationFunction.setText("Act.Fnct.: " + string);
	}

	private void setEta(double value) {
		DecimalFormat df = new DecimalFormat("#.#####");
		eta.setText("Eta: " + df.format(value));
	}

	private void setBatchSize(int value) {
		batchSize.setText("Batch Size: " + getReadableAmount(value));
	}

	private void setDataSize(int size) {
		dataSize.setText("Data Size: " + getReadableAmount(size));
	}

	private void setIteration(int i) {
		iterations.setText("Iterations: " + getReadableAmount(i));
	}

	private void setTrainingError(double i) {
		DecimalFormat df = new DecimalFormat("#.##");
		trainingError.setText("Training Error: " + df.format(i));
	}

	private void setTestError(double i) {
		DecimalFormat df = new DecimalFormat("#.##");
		testError.setText("Test Error: " + df.format(i));
	}

	private void setProcTime(double t) {
		DecimalFormat df = new DecimalFormat("#.##");
		procTime.setText("Thread Usage: " + df.format(t*100) + "%");
	}
	
	private void setSpeed(long i) {
		speed.setText("Batches/sec: " + getReadableAmount(i));
	}
	
	private String getReadableAmount(long s) {
	    if(s == 0) return "0";
	    if(s < 0) return ""+s;
	    final String[] units = new String[] { "", "k", "M", "B", "MM", "BB", "MMM" };  
	    int digitGroups = (int) (Math.log10(s)/Math.log10(1000));
	    return new DecimalFormat("#,##0.#").format(s/Math.pow(1000, digitGroups)) + " " + units[digitGroups]; 
	}
}
