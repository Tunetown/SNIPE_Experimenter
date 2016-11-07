package model.snipe;

import java.io.Serializable;

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
		lesson = (TrainingSampleLesson)object;
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
		
		lesson = new TrainingSampleLesson(nin, nteach);		
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
		lesson = new TrainingSampleLesson(in, teach);
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
		lesson = null;
	}

	public TrainingSampleLesson getLesson() {
		return lesson;
	}
}
