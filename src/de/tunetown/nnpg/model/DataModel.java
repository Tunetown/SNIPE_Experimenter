package de.tunetown.nnpg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import de.tunetown.nnpg.model.DataContainer;

/**
 * Data model for all engines. This holds training and test data lesson containers, and 
 * manages the splitting of them.
 * 
 * @author Thomas Weber
 *
 */
public class DataModel {

	private DataContainer trainingLesson;
	private DataContainer testLesson;
	
	private Random rand = new Random();

	/**
	 * Add a new sample. This merges all samples (training and test), add the sample, 
	 * and re-split the data then.
	 * 
	 * @param x
	 * @param y
	 * @param value
	 */
	public void addSample(double x, double y, double value) {
		List<Double[]> in = getMergedInputs();
		List<Double[]> teach = getMergedDesiredOutputs();

		Double[] nin = {x, y};
		Double[] nteach = {value};
		
		in.add(nin);
		teach.add(nteach);
		
		set(new DataContainer(in, teach));
	}
	
	/**
	 * Delete samples around a given coordinate. This merges all samples (training and test), 
	 * add the sample, and re-split the data then.
	 * 
	 * @param x
	 * @param y
	 * @param eraseRadius
	 */
	public void deleteSamplesAroundPoint(double x, double y, double radius) {
		if(!hasData()) return;
		
		List<Double[]> in = getMergedInputs();
		List<Double[]> teach = getMergedDesiredOutputs();

		List<Integer> toErase = new ArrayList<Integer>();
		
		// See which samples lie inside the given radius around the given coordinates. If so, do
		// not add them to the new arrays.
		// NOTE: For simplicity, the radius is NOT evaluated as a circle, but as a square. This is
		//       sufficient for this application for now.
		for(int n=0; n<in.size(); n++) {
			if (in.get(n)[0] >= x - radius && in.get(n)[0] <= x + radius &&
					in.get(n)[1] >= y - radius && in.get(n)[1] <= y + radius) {
				// Erase point (here, we just mark the index to be erased)
				toErase.add(n);
			}
		}
		
		// Now, compose the new arrays and use them
		List<Double[]> nin = new ArrayList<Double[]>();
		List<Double[]> nteach = new ArrayList<Double[]>();
		
		for(int n=0; n<in.size(); n++) {
			if (!toErase.contains(n)) {
				nin.add(in.get(n));
				nteach.add(teach.get(n));
			}
		}
		
		if (nin.size() > 0) {
			set(new DataContainer(nin, nteach));
		} else {
			set(null);
		}
	}

	/**
	 * Returns true if there are some training or test data.
	 * 
	 * @return
	 */
	public boolean hasData() {
		if (trainingLesson == null && testLesson == null) return false;
		int s = 0;
		if (trainingLesson != null) s+=trainingLesson.size();
		if (testLesson != null) s+=testLesson.size();
		return (s != 0);
	}

	/**
	 * Reset all data.
	 * 
	 */
	public void initialize() {
		set(null);
	}

	/**
	 * Set the data (complete, including split into training and test data)
	 * 
	 * @param lesson
	 */
	public void set(DataContainer lesson) {
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
		DataContainer[] split = lesson.split(0.5);
		
		trainingLesson = split[0];
		testLesson = split[1];
	}

	/**
	 * Returns the training lesson
	 * 
	 * @return
	 */
	public DataContainer getTrainingLesson() {
		return trainingLesson;
	}

	/**
	 * Returns the test lesson
	 * 
	 * @return
	 */
	public DataContainer getTestLesson() {
		return testLesson;
	}

	/**
	 * Returns the number of samples in the container
	 * 
	 * @param training
	 * @return
	 */
	public int getNumOfSamples(boolean training) {
		if (training) {
			if (trainingLesson == null) return 0;
			return trainingLesson.size();
		} else {
			if (testLesson == null) return 0;
			return testLesson.size();
		}
	}

	/**
	 * Returns a container holding all samples (training AND test)
	 * 
	 * @return
	 */
	public DataContainer getMergedDataContainer() {
		if (!hasData()) return null;
		return new DataContainer(getMergedInputs(), getMergedDesiredOutputs());
	}

	/**
	 * Re-splits the data into training and test lessons
	 * 
	 */
	public void resplitData() {
		if (!hasData()) return;
		set(new DataContainer(getMergedInputs(), getMergedDesiredOutputs()));
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
		
		List<Double[]> in = this.getMergedInputs();
		List<Double[]> out = this.getMergedDesiredOutputs();

		int tar = (int)(in.size() * rate);
		
		if (tar == 0) {
			set(null);
			return;
		}
		
		List<Double[]> nin = new ArrayList<Double[]>();
		List<Double[]> nout = new ArrayList<Double[]>();

		if (tar < in.size()) {
			// Reduce
			for(int nn=0; nn<tar; nn++) {
				int n = rand.nextInt(in.size());
				Double[] nextIn = {getRandomValue(in.get(n)[0], radius), getRandomValue(in.get(n)[1], radius)};
				nin.add(nextIn);
				Double[] nextOut = out.get(n);
				nout.add(nextOut);
			}
		} else {
			// Grow
			nin.addAll(in);
			nout.addAll(out);

			for(int nn=in.size(); nn<tar; nn++) {
				int n = rand.nextInt(in.size());
				Double[] nextIn = {getRandomValue(in.get(n)[0], radius), getRandomValue(in.get(n)[1], radius)};
				nin.add(nextIn);
				Double[] nextOut = out.get(n);
				nout.add(nextOut);
			}
		}
		
		set(new DataContainer(nin, nout));
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
	protected List<Double[]> getMergedDesiredOutputs() {
		if (!hasData()) return new ArrayList<Double[]>();
		
		List<Double[]> ret = new ArrayList<Double[]>();
		if (trainingLesson != null) ret.addAll(trainingLesson.getDesiredOutputs());
		if (testLesson != null) ret.addAll(testLesson.getDesiredOutputs());
		return ret;		
	}

	/**
	 * Returns all inputs merged from training and test data sets.
	 * 
	 * @return
	 */
	protected List<Double[]> getMergedInputs() {
		if (!hasData()) return new ArrayList<Double[]>();

		List<Double[]> ret = new ArrayList<Double[]>();
		if (trainingLesson != null) ret.addAll(trainingLesson.getInputs());
		if (testLesson != null) ret.addAll(testLesson.getInputs());
		return ret;		
	}
}
