package de.tunetown.nnpg.model;

import java.util.ArrayList;
import java.util.List;

import de.tunetown.nnpg.view.ViewProperties;

/**
 * Tracker, which keeps tracking info about the learning process.
 * 
 * @author Thomas Weber
 *
 */
public class TrainingTracker {

	/**
	 * Amount of runs to be used for determining statistics 
	 */
	private int measurementLength = ViewProperties.STATISTICS_AVERAGE_RANGE;
	
	private List<Double> trainingErrors = new ArrayList<Double>(); 
	private List<Double> testErrors = new ArrayList<Double>(); 
	private List<Long> netRuntimes = new ArrayList<Long>(); 
	private List<Integer> batchSizes = new ArrayList<Integer>();
	private List<Long> grossRuntimes = new ArrayList<Long>(); 
	private List<Long> speeds = new ArrayList<Long>(); 
	
	
	private long trainingStartNanoTime;
	
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
	 * Returns a list of run time nano seconds, which holds exactly one per iteration.
	 * 
	 * @return
	 */
	public List<Long> getRuntimes() {
		return netRuntimes;
	}

	/**
	 * Returns the list of speeds recorded
	 * 
	 * @return
	 */
	public List<Long> getSpeeds() {
		return speeds;
	}

	/**
	 * Returns the current (last) iteration. The processing of one batch is counted as one iteration.
	 * 
	 * @return
	 */
	public int getIterations() {
		return trainingErrors.size();
	}

	/**
	 * Has to be called before training start, to get statistics about CPU usage.
	 * 
	 */
	public void setTrainingStart() {
		trainingStartNanoTime = System.nanoTime();
	}

	/**
	 * Adds one training run to the log. This is called after each training method call to the network.
	 * 
	 * @param trainingError
	 * @param testError
	 * @param l
	 */
	public void addRun(double trainingError, double testError, long runtimeNanos, int batchSize) {
		long t = System.nanoTime();
		grossRuntimes.add(t - trainingStartNanoTime);
		trainingStartNanoTime = t;

		trainingErrors.add(trainingError);
		testErrors.add(testError);
		netRuntimes.add(runtimeNanos);
		batchSizes.add(batchSize);
		speeds.add(determineCurrentSpeed());		
	}

	/**
	 * Get the amount of CPU percentage (measured by training time against system time) used by training.
	 * 
	 * @return [0..1]
	 */
	public double getCPUUsage() {
		if (getIterations() < 10) return 0;
		int b = (getIterations() <= measurementLength) ? 0 : (getIterations() - measurementLength);

		long net = 0;
		long gross = 0;
		for(int i = getIterations()-1; i > b; i--) {
			net += netRuntimes.get(i);
			gross += grossRuntimes.get(i);
		}
		
		return (double)net / (double)gross;
	}

	/**
	 * Returns the current training speed in number of training runs per second of system time, measured 
	 * using the last measurementLength runs.
	 * 
	 * @return
	 */
	public long getCurrentSpeed() {
		if (speeds.size() < 1) return 0;
		return speeds.get(speeds.size()-1);
	}
	
	/**
	 * Returns the current training speed in number of training runs per second of system time, measured 
	 * using the last measurementLength runs. (Internal determination routine)
	 * 
	 * @return
	 */
	private long determineCurrentSpeed() {
		int b = (getIterations() <= measurementLength) ? 0 : (getIterations() - measurementLength);

		long runtime = 0;
		long batches = 0;
		for(int i = getIterations()-1; i > b; i--) {
			runtime += grossRuntimes.get(i);
			batches += batchSizes.get(i);
		}
		
		return (long)((double)batches / (double)runtime * 1000000000.0);
	}
}
