package model.snipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dkriesel.snipe.training.TrainingSampleLesson;

import model.DataWrapper;

public class SNIPEDataWrapper extends DataWrapper{

	private TrainingSampleLesson lesson;
	
	@Override
	public Serializable getSerializable() {
		return lesson;
	}

	@Override
	public void setFromSerializable(Object object) {
		setLesson((TrainingSampleLesson)object);
	}

	@Override
	public void addSample(double x, double y, double value) {
		System.out.println("Adding data: "+x+", "+y+" -> "+value);
		
		if(lesson == null) {
			createLesson(x, y, value);
			return;
		}
		
		double[][] in = lesson.getInputs();
		double[][] teach = lesson.getDesiredOutputs();
		
		double[][] nin = new double[in.length + 1][2];
		double[][] nteach = new double[in.length + 1][1];
		
		for(int n=0; n<in.length; n++) {
			nin[n] = in[n];
			nteach[n] = teach[n];
			
		}
		double[] ninl = {x, y};
		nin[in.length] = ninl;
		double[] ntl = {value};
		nteach[in.length] = ntl;
		
		setLesson(new TrainingSampleLesson(nin, nteach));		
	}
	
	@Override
	public void deleteSamplesAroundPoint(double x, double y, double radius) {
		System.out.println("Removing data around: "+x+", "+y+" Radius: "+ radius);
		
		if(lesson == null || lesson.countSamples() == 0) {
			return;
		}
		
		double[][] in = lesson.getInputs();
		double[][] teach = lesson.getDesiredOutputs();

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
			setLesson(new TrainingSampleLesson(nin, nteach));
		} else {
			setLesson(null);
		}
	}

	private void createLesson(double x, double y, double value) {
		/*
		double[][] in = {{0.5, -0.5},
				         {-0.7, 0.3},
				         {-1, 1},
				         {-1, -1},
				         {1, 1},
				         {1, -1},
		                 {0.1, 0.1}};
		double[][] teach = {{1},
				            {1},
				            {1},
				            {1},
				            {1},
				            {1},
		                    {-1}};
*/
		double[][] in = {{x, y}};
		double[][] teach = {{value}};
		setLesson(new TrainingSampleLesson(in, teach));
	}

	@Override
	public int getNumOfSamples() {
		if (lesson == null) return 0;
		return lesson.countSamples();
	}

	@Override
	public double[][] getInputs() {
		if (lesson == null) return null;
		return lesson.getInputs();
	}

	@Override
	public double[][] getDesiredOutputs() {
		if (lesson == null) return null;
		return lesson.getDesiredOutputs();
	}

	@Override
	public void initialize() {
		setLesson(null);
	}

	public TrainingSampleLesson getLesson() {
		return lesson;
	}
	
	private void setLesson(TrainingSampleLesson lesson) {
		this.lesson = lesson;
		//this.lesson.optimizeDesiredOutputsForClassificationProblem(net);
	}
}
