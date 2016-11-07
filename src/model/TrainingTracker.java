package model;

import java.util.ArrayList;
import java.util.List;

public class TrainingTracker {

	private List<Double> rmsErrors = new ArrayList<Double>(); 

	public List<Double> getRmsErrors() {
		return rmsErrors;
	}

	public int getIterations() {
		return rmsErrors.size();
	}

	public void addRecord(double error) {
		rmsErrors.add(error);
	}

}
