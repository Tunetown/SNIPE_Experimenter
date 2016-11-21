package de.tunetown.nnpg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Data container used for loading and saving training/test data. This is used to
 * also enable usage of existing test data files with different neural network implementations.
 * 
 * @author Thomas Weber
 *
 */
public class DataContainer implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Double[]> inputs;
	private List<Double[]> desiredOutputs;
	
	public DataContainer() {
		inputs = new ArrayList<Double[]>();
		desiredOutputs = new ArrayList<Double[]>();
	}
	
	public DataContainer(List<Double[]> inputs, List<Double[]> desiredOutputs) {
		this.inputs = inputs;
		this.desiredOutputs = desiredOutputs;
	}
	
	public List<Double[]> getInputs() {
		return inputs;
	}
	
	public List<Double[]> getDesiredOutputs() {
		return desiredOutputs;
	}

	public int size() {
		return inputs.size();
	}

	public int getDimensionalityDesiredOutputs() {
		if (desiredOutputs == null || desiredOutputs.size() == 0) return -1;
		return desiredOutputs.get(0).length;
	}

	public int getDimensionalityInputs() {
		if (inputs == null || inputs.size() == 0) return -1;
		return inputs.get(0).length;
	}

	public DataContainer[] split(double dist) {
		if (size() == 0) return null;

    	DataContainer[] subSets = new DataContainer[2];
        subSets[0] = new DataContainer();
        subSets[1] = new DataContainer();

        if (size() == 1) {
        	subSets[0] = this;
			return subSets;
		}
		
		List<Integer> indices = new ArrayList<Integer>();
		for(int i=0; i<inputs.size(); i++) indices.add(i);
		Collections.shuffle(indices);

        
        int samples = inputs.size() / 2;
        
        for (int i=0; i<samples; i++) subSets[0].add(inputs.get(indices.get(i)), desiredOutputs.get(indices.get(i)));
        for (int i=samples; i<size(); i++) subSets[1].add(inputs.get(indices.get(i)), desiredOutputs.get(indices.get(i)));
        
        return subSets;
	}

	private void add(Double[] input, Double[] desiredOutput) {
		inputs.add(input);
		desiredOutputs.add(desiredOutput);
	}

	public double[][] getInputsArray() {
		return convertToArray(inputs);
	}

	public double[][] getDesiredOutputsArray() {
		return convertToArray(desiredOutputs);
	}
	
	private double[][] convertToArray(List<Double[]> list) {
		if (list == null || list.size() == 0) return null;
		
		double[][] ret = new double[list.size()][list.get(0).length];
		for(int i=0; i<list.size(); i++) {
			ret[i] = ArrayUtils.toPrimitive(list.get(i));
		}
		return ret;
	}

}
