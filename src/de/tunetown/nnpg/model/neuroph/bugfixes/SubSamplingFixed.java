/**
 * Copyright 2013 Neuroph Project http://neuroph.sourceforge.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.tunetown.nnpg.model.neuroph.bugfixes;

import java.util.ArrayList;
import java.util.List;
import org.neuroph.core.data.DataSet;
import org.neuroph.util.data.sample.Sampling;

/**
 * This class provides subsampling of a data set, and creates a specified number of subsets of a
 * specified number of samples form given data set.
 * 
 * Bugfix: Strange determination of item count in subsets
 * 
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class SubSamplingFixed implements Sampling {

    
    /**
     * Number of sub sets
     */
    private int subSetCount;
        
    /**
     * Sampling will produce a specified number of subsets of equal sizes
     * Handy for K Fold subsampling
     * 
     * @param subSetCount number of subsets to produce
     */
    public SubSamplingFixed(int subSetCount) { // without repetition        
        this.subSetCount = subSetCount;
    }
    
    @Override
    public List<DataSet> sample(DataSet dataSet) {
    	// shuffle dataset in order to randomize rows that will be used to fill subsets
    	dataSet.shuffle();

    	List<DataSet> subSets = new ArrayList<DataSet>();
        int inputSize = dataSet.getInputSize();
        int outputSize = dataSet.getOutputSize();

        int samples = dataSet.size() / this.subSetCount;
        
        for(int i=0; i<this.subSetCount; i++) {
        	DataSet s = new DataSet(inputSize, outputSize);
        	subSets.add(s);
        }
        
        int c = 0;
        int f = 0;
        for(int i=0; i<dataSet.size(); i++) {
        	DataSet s = subSets.get(c);
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
