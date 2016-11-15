package de.tunetown.nnpg.model;

import java.util.Random;

/**
 * Facade wrapper for integration of data containers, which are then used against the defined
 * neural network.
 * 
 * @author Thomas Weber
 *
 */
public abstract class DataWrapper {

	private Random rand = new Random();
	
	/**
	 * Returns a data container containing the training samples, or null if no 
	 * training samples are there.
	 * 
	 * @return
	 */
	public abstract DataContainer getTrainingLesson();
	
	/**
	 * Returns a data container containing the test samples, or null if no 
	 * test samples are there.
	 * 
	 * @return
	 */
	public abstract DataContainer getTestLesson();
	
	/**
	 * Add a new sample. This merges all samples (training and test), add the sample, 
	 * and re-split the data then.
	 * 
	 * @param x
	 * @param y
	 * @param value
	 */
	public abstract void addSample(double x, double y, double value);

	/**
	 * Delete samples around a given coordinate. This merges all samples (training and test), 
	 * add the sample, and re-split the data then.
	 * 
	 * @param x
	 * @param y
	 * @param eraseRadius
	 */
	public abstract void deleteSamplesAroundPoint(double x, double y, double eraseRadius);

	/**
	 * Returns the number of samples
	 * 
	 * @param training set to true to get training samples, false for test samples
	 * 
	 * @return
	 */
	public abstract int getNumOfSamples(boolean training);

	/**
	 * Reset all data.
	 * 
	 */
	public abstract void initialize();

	/**
	 * Returns a DataContainer instance holding the data in a generalized form. The data returned
	 * will be merged from the training and test samples.
	 * 
	 * @return
	 */
	public abstract DataContainer getCompleteDataContainer();

	/**
	 * Set the data from a given generalized data container. The samples will be split into
	 * training and test samples.
	 * 
	 * @param c
	 */
	public abstract void setFromCompleteDataContainer(DataContainer c);

	/**
	 * Merges the training and test data and splits it again.
	 *  
	 */
	public abstract void resplitData();

	/**
	 * Returns true if there are some training or test data.
	 * 
	 * @return
	 */
	public abstract boolean hasData();

	/**
	 * The amount of samples is scaled by the factor given (rate). If rate is 1, nothing happens. If it is > 1, new samples 
	 * are added randomly around the given radius around randomly chosen existing samples. If < 1, random samples are taken
	 * away.  
	 *  
	 * @param rate 
	 * @param radius (only relevant if rate > 1)
	 */
	public void growData(double rate, double radius) {
		if (!hasData()) return;
		
		double[][] in = this.getMergedInputs();
		double[][] out = this.getMergedDesiredOutputs();

		int tar = (int)(in.length * rate);
		
		if (tar == 0) {
			setFromCompleteDataContainer(null);
			return;
		}
		
		double[][] nin = new double[tar][2];
		double[][] nout = new double[tar][1];

		if (tar < in.length) {
			// Reduce
			for(int nn=0; nn<tar; nn++) {
				int n = rand.nextInt(in.length);
				nin[nn][0] = getRandomValue(in[n][0], radius);
				nin[nn][1] = getRandomValue(in[n][1], radius); 
				nout[nn][0] = out[n][0];
			}
		} else {
			// Grow
			for(int n=0; n<in.length; n++) {
				nin[n] = in[n];
				nout[n] = out[n];
			}
			
			for(int nn=in.length; nn<tar; nn++) {
				int n = rand.nextInt(in.length);
				nin[nn][0] = getRandomValue(in[n][0], radius);
				nin[nn][1] = getRandomValue(in[n][1], radius); 
				nout[nn][0] = out[n][0];
			}
		}
		
		setFromCompleteDataContainer(new DataContainer(nin, nout));
	}

	/**
	 * Helper for growing data
	 * 
	 * @param d
	 * @param radius
	 * @return
	 */
	private double getRandomValue(double d, double radius) {
		return d + (Math.random() * radius * 2) - radius;
	}
	
	/**
	 * Returns all desired outputs merged from training and test data sets.
	 * 
	 * @return
	 */
	protected double[][] getMergedDesiredOutputs() {
		if (!hasData()) return null;
		
		int num = 0;
		if (getTrainingLesson() != null) num += getTrainingLesson().size();
		if (getTestLesson() != null) num += getTestLesson().size();
		
		int outDim = 0;
		if (getTrainingLesson() != null) {
			outDim = getTrainingLesson().getDimensionalityDesiredOutputs();
		} else if (getTestLesson() != null) {
			outDim = getTestLesson().getDimensionalityDesiredOutputs();
		}
		
		double[][] nout = new double[num][outDim];
		
		int n = 0;
		if (getTrainingLesson() != null) {
			double[][] out = getTrainingLesson().getDesiredOutputs();
			
			for(int i=0; i<getTrainingLesson().size(); i++) {
				nout[n] = out[i];
				n++;
			}
		}
		if (getTestLesson() != null) {
			double[][] out = getTestLesson().getDesiredOutputs();
			
			for(int i=0; i<getTestLesson().size(); i++) {
				nout[n] = out[i];
				n++;
			}
		}
		
		return nout;
	}

	/**
	 * Returns all inputs merged from training and test data sets.
	 * 
	 * @return
	 */
	protected double[][] getMergedInputs() {
		if (!hasData()) return null;

		int num = 0;
		if (getTrainingLesson() != null) num += getTrainingLesson().size();
		if (getTestLesson() != null) num += getTestLesson().size();
		
		int inDim = 0;
		if (getTrainingLesson() != null) {
			inDim = getTrainingLesson().getDimensionalityInputs();
		} else if (getTestLesson() != null) {
			inDim = getTestLesson().getDimensionalityInputs();
		}
		
		double[][] nin = new double[num][inDim];
		
		int n = 0;
		if (getTrainingLesson() != null) {
			double[][] in = getTrainingLesson().getInputs();
			
			for(int i=0; i<getTrainingLesson().size(); i++) {
				nin[n] = in[i];
				n++;
			}
		}
		if (getTestLesson() != null) {
			double[][] in = getTestLesson().getInputs();
			
			for(int i=0; i<getTestLesson().size(); i++) {
				nin[n] = in[i];
				n++;
			}
		}
		
		return nin;
	}
}
