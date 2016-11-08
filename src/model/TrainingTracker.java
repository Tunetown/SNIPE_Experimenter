package model;

import java.util.ArrayList;
import java.util.List;

public class TrainingTracker {

	private List<Double> rmsErrors = new ArrayList<Double>(); 
	private long processingNanoTime = 0;
	private long startNanoTime = 0;
	
	public List<Double> getRmsErrors() {
		return rmsErrors;
	}

	public int getIterations() {
		return rmsErrors.size();
	}

	public void addRecord(double error) {
		rmsErrors.add(error);
	}

	public void addNanoTime(long nanoTime) {
		processingNanoTime += nanoTime;
	}
	
	public long getProcessingTime() {
		return processingNanoTime;
	}

	public void startTracking() {
		startNanoTime = System.nanoTime();
	}
	
	public double getProcessingPercentage() {
		long all = System.nanoTime() - startNanoTime;
		return (double)getProcessingTime() / (double)all;
	}
}
