package model;

import java.io.Serializable;

public abstract class DataWrapper {

	public abstract Serializable getSerializable();

	public abstract void setFromSerializable(Object object);

	public abstract void addSample(double x, double y, double value);

	public abstract int getNumOfSamples();

	public abstract double[][] getInputs();

	public abstract double[][] getDesiredOutputs();

	public abstract void initialize();

	public abstract void deleteSamplesAroundPoint(double x, double y, double eraseRadius);
}
