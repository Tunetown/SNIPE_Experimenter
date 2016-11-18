package de.tunetown.nnpg.model.neuroph;

import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import de.tunetown.nnpg.model.DataContainer;
import de.tunetown.nnpg.model.DataWrapper;
import de.tunetown.nnpg.model.neuroph.bugfixes.SubSamplingFixed;

/**
 * Wrapper for Neuroph training data (TODO: To Implement...)
 * 
 * @author Thomas Weber
 *
 */
public class NeurophDataWrapper extends DataWrapper{

	private DataSet trainingLesson;
	private DataSet testLesson;

	@Override
	public void addSample(double x, double y, double value) {
		double[][] in = getMergedInputs();
		double[][] teach = getMergedDesiredOutputs();

		double[][] nin;
		double[][] nteach;

		if (in == null) {
			nin = new double[1][2];
			nteach = new double[1][1];
			
			nin[0][0] = x;
			nin[0][1] = y;
			nteach[0][0] = value;
		} else {
			nin = new double[in.length + 1][2];
			nteach = new double[in.length + 1][1];
			
			for(int n=0; n<in.length; n++) {
				nin[n] = in[n];
				nteach[n] = teach[n];
				
			}
			
			double[] ninl = {x, y};
			nin[in.length] = ninl;
			double[] ntl = {value};
			nteach[in.length] = ntl;
		}
		
		DataSet ds = new DataSet(2, 1);
		for(int n=0; n<nin.length; n++) {
			ds.addRow(nin[n], nteach[n]);
		}
		setLesson(ds);	
	}
	
	@Override
	public void deleteSamplesAroundPoint(double x, double y, double radius) {
		if(!hasData()) return;
		
		double[][] in = getMergedInputs();
		double[][] teach = getMergedDesiredOutputs();

		List<Integer> toErase = new ArrayList<Integer>();
		
		// See which samples lie inside the given radius around the given coordinates. If so, do
		// not add them to the new arrays.
		// NOTE: For simplicity, the radius is NOT evaluated as a circle, but as a square. This is
		//       sufficient for this application for now.
		for(int n=0; n<in.length; n++) {
			if (in[n][0] >= x - radius && in[n][0] <= x + radius &&
					in[n][1] >= y - radius && in[n][1] <= y + radius) {
				// Erase point (here, we just mark the index to be erased)
				toErase.add(n);
			}
		}
		
		// Now, compose the new arrays and use them
		double[][] nin = new double[in.length - toErase.size()][2];
		double[][] nteach = new double[in.length - toErase.size()][1];
		
		int nn = 0;
		for(int n=0; n<in.length; n++) {
			if (!toErase.contains(n)) {
				nin[nn] = in[n];
				nteach[nn] = teach[n];
				nn++;
			}
		}
		
		if (nin.length > 0) {
			DataSet ds = new DataSet(2, 1);
			for(int n=0; n<nin.length; n++) {
				ds.addRow(nin[n], nteach[n]);
			}
			setLesson(ds);	
		} else {
			setLesson(null);
		}
	}

	@Override
	public boolean hasData() {
		return (trainingLesson != null || testLesson != null);
	}

	@Override
	public void initialize() {
		setLesson(null);
	}

	private void setLesson(DataSet lesson) {
		trainingLesson = null;
		testLesson = null;

		if (lesson == null) {
			// Reset all data to null: We are finished here
			return;
		}
		
		if(lesson.size() < 10) {
			// Less than ten samples: Create training data only
			trainingLesson = lesson;
			return;
		}
		
		// More than 10 samples: Split lesson into training and test data
		List<DataSet> split = lesson.sample(new SubSamplingFixed(2));
		
		trainingLesson = split.get(0);
		testLesson = split.get(1);
	}

	@Override
	public DataContainer getTrainingLesson() {
		return getContainerFromTrainingLesson(trainingLesson);
	}

	@Override
	public DataContainer getTestLesson() {
		return getContainerFromTrainingLesson(testLesson);
	}

	@Override
	public int getNumOfSamples(boolean training) {
		if (training) {
			if (trainingLesson == null) return 0;
			return trainingLesson.size();
		} else {
			if (testLesson == null) return 0;
			return testLesson.size();
		}
	}

	private DataContainer getContainerFromTrainingLesson(DataSet lesson) {
		if (lesson == null || lesson.size() == 0) return null;
		
		double[][] in = new double[lesson.size()][2];
		double[][] out = new double[lesson.size()][1];
		
		for(int i=0; i<lesson.size(); i++) {
			DataSetRow row = lesson.getRowAt(i);
			in[i] = row.getInput();
			out[i] = row.getDesiredOutput();
		}
		
		return new DataContainer(in, out);
	}

	@Override
	public DataContainer getCompleteDataContainer() {
		if (!hasData()) return null;
		return new DataContainer(getMergedInputs(), getMergedDesiredOutputs());
	}

	@Override
	public void setFromCompleteDataContainer(DataContainer c) {
		if (c == null) {
			setLesson(null);
			return;
		}
		
		DataSet ds = new DataSet(2, 1);
		for(int n=0; n<c.size(); n++) {
			ds.addRow(c.getInputs()[n], c.getDesiredOutputs()[n]);
		}
		setLesson(ds);
	}

	@Override
	public void resplitData() {
		if (!hasData()) return;

		setFromCompleteDataContainer(new DataContainer(getMergedInputs(), getMergedDesiredOutputs()));
	}

	public DataSet getNeurophTrainingLesson() {
		return trainingLesson;
	}

	public DataSet getNeurophTestLesson() {
		return testLesson;
	}
}
