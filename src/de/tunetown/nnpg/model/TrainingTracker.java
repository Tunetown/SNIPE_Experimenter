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

	private List<Double> rmsErrors = new ArrayList<Double>(); 
	private long processingNanoTime = 0;
	private long startNanoTime = 0;

	/**
	 * Returns a list of errors, which holds exactly one error per iteration.
	 * 
	 * @return
	 */
	public List<Double> getRmsErrors() {
		return rmsErrors;
	}

	/**
	 * Returns the current iteration. The processing of one batch is counted as one iteration.
	 * 
	 * @return
	 */
	public int getIterations() {
		return rmsErrors.size();
	}

	/**
	 * Add an error to the error list.
	 * 
	 * @param error
	 */
	public void addRecord(double error) {
		rmsErrors.add(error);
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

	/**
	 * Start tracking of general CPU time
	 * 
	 */
	public void startTracking() {
		startNanoTime = System.nanoTime();
	}
	
	/**
	 * Returns the overall percentage of CPU used for training (TODO: Not correct!)
	 * @return
	 */
	public double getProcessingPercentage() {
		long all = System.nanoTime() - startNanoTime;
		return (double)getProcessingTime() / (double)all;
	}
}
