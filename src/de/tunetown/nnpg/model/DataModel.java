package de.tunetown.nnpg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import de.tunetown.nnpg.model.DataContainer;
import de.tunetown.nnpg.model.DataWrapper;
import de.tunetown.nnpg.model.neuroph.bugfixes.SubSamplingFixed;

/**
 * Data model for all engines
 * 
 * @author Thomas Weber
 *
 */
public class DataModel {

	private DataContainer trainingLesson;
	private DataContainer testLesson;
	
	private Random rand = new Random();

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

	public boolean hasData() {
		return (trainingLesson != null || testLesson != null);
	}

	public void initialize() {
		setLesson(null);
	}

	private void setLesson(DataContainer lesson) {
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
		DataContainer[] split = lesson.split(2, 0.5);
		
		trainingLesson = split.get(0);
		testLesson = split.get(1);
	}

	public DataContainer getTrainingLesson() {
		return getContainerFromTrainingLesson(trainingLesson);
	}

	public DataContainer getTestLesson() {
		return getContainerFromTrainingLesson(testLesson);
	}

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

	public DataContainer getCompleteDataContainer() {
		if (!hasData()) return null;
		return new DataContainer(getMergedInputs(), getMergedDesiredOutputs());
	}

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
