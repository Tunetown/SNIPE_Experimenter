package de.tunetown.nnpg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.data.DataSet;

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

	public DataContainer[] split(int num) {
    	shuffle();

    	DataContainer[] subSets = new DataContainer[num];

        int samples = size() / num;
        
        for(int i=0; i<num; i++) {
        	subSets[i] = new DataContainer();
        }
        
        int c = 0;
        int f = 0;
        for(int i=0; i<size(); i++) {
        	DataContainer s = get(c);
        	s.addRow(dataSet.getRowAt(i));

        	f++;
        	if (f >= samples && c < subSets.size() -1) {
        		f=0;
        		c++;
        	}
        }
        
        return subSets;
	}
}
