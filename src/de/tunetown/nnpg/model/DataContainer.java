package de.tunetown.nnpg.model;

import java.io.Serializable;

/**
 * Data container used for loading and saving training/test data. This is used to
 * also enable usage of existing test data files with different neural network implementations.
 * 
 * @author xwebert
 *
 */
public class DataContainer implements Serializable {
	private static final long serialVersionUID = 1L;

	private double[][] inputs;
	private double[][] desiredOutputs;
	
	public DataContainer(double[][] inputs, double[][] desiredOutputs) {
		this.inputs = inputs;
		this.desiredOutputs = desiredOutputs;
	}
	
	public double[][] getInputs() {
		return inputs;
	}
	
	public double[][] getDesiredOutputs() {
		return desiredOutputs;
	}
}
