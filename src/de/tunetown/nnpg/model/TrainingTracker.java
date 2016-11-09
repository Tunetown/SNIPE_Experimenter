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
	private long processingNanoTime = 0;

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
	 * Add an error to the error list.
	 * 
	 * @param error
	 */
	public void addRecord(double trainingError, double testError) {
		trainingErrors.add(trainingError);
		testErrors.add(testError);
	}

	/**
	 * Add processing time
	 * 
	 * @param nanoTime
	 */
	public void addNanoTime(long nanoTime) {
		processingNanoTime += nanoTime;
	}
	
	/**
	 * Get processing time
	 * 
	 * @return
	 */
	public long getProcessingTime() {
		return processingNanoTime;
	}
}
