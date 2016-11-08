package de.tunetown.nnpg.model;

/**
 * Facade wrapper for integration of data containers, which are then used against the defined
 * neural network.
 * 
 * @author xwebert
 *
 */
public abstract class DataWrapper {

	/**
	 * Add a new sample
	 * 
	 * @param x
	 * @param y
	 * @param value
	 */
	public abstract void addSample(double x, double y, double value);

	/**
	 * Returns the number of samples
	 * 
	 * @return
	 */
	public abstract int getNumOfSamples();

	/**
	 * Returns the input values. Semantics:
	 * 
	 * in[n][xi]
	 * 
	 * n is the sample index, xi is the sample dimensionality.
	 * 
	 * @return
	 */
	public abstract double[][] getInputs();

	/**
	 * Returns the desired outputs. Semantics: see getInputs().
	 * 
	 * @return
	 */
	public abstract double[][] getDesiredOutputs();

	/**
	 * Reset all data.
	 * 
	 */
	public abstract void initialize();

	/**
	 * Delete samples around a given coordinate.
	 * 
	 * @param x
	 * @param y
	 * @param eraseRadius
	 */
	public abstract void deleteSamplesAroundPoint(double x, double y, double eraseRadius);

	/**
	 * Returns a DataContainer instance holding the data in a generalized form.
	 * 
	 * @return
	 */
	public abstract DataContainer getContainer();

	/**
	 * Set the data from a givend generalized data container
	 * 
	 * @param c
	 */
	public abstract void setFromContainer(DataContainer c);
}
