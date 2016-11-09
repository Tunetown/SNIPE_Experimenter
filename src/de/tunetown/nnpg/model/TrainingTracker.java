package de.tunetown.nnpg.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracker, which keeps tracking info about the learning process.
 * 
 * @author xwebert
 *
 */
public class TrainingTracker {

	private List<Double> trainingErrors = new ArrayList<Double>(); 
	private List<Double> testErrors = new ArrayList<Double>(); 
	private List<Long> runtimes = new ArrayList<Long>(); 
	private List<Double> batchSizes = new ArrayList<Double>();
	
	/**
	 * Returns a list of errors, which holds exactly one error per iteration.
	 * 
	 * @return
	 */
	public List<Double> getTrainingErrors() {
		return trainingErrors;
	}

	/**
	 * Returns a list of errors, which holds exactly one error per iteration.
	 * 
	 * @return
	 */
	public List<Double> getTestErrors() {
		return testErrors;
	}

	/**
	 * Returns the current iteration. The processing of one batch is counted as one iteration.
	 * 
	 * @return
	 */
	public int getIterations() {
		return trainingErrors.size();
	}

	/**
	 * Get processing time
	 * 
	 * @return
	 */
	public double getCPUPercentage() {
		return processingNanoTime;
	}

	/**
	 * Returns the current training speed in number of training runs per second
	 * @return
	 */
	public long getCurrentSpeed(NetworkWrapper net) {
		return (long)((double)processingNanoTime / (double) / net.getBatchSize());
	}

	/**
	 * Adds one training run to the log
	 * 
	 * @param trainingError
	 * @param testError
	 * @param l
	 */
	public void addRun(double trainingError, double testError, long runtimeNanos) {
		// TODO Auto-generated method stub
		trainingErrors.add(trainingError);
		testErrors.add(testError);
		runtimes.add(runtimeNanos);
	}
}
