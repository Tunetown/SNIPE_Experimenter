package com.dkriesel.snipe.neuronbehavior;


/**
 * Implements the Tangens Hyperbolicus and its derivative.
 * 
 * @author David Kriesel / dkriesel.com
 *
 */
public class TangensHyperbolicus implements NeuronBehavior{
	private static final long serialVersionUID = 1L;

	@Override
	public double computeDerivative(double x) {
		double t = Math.tanh(x);
		return 1 - (t*t);
	}

	@Override
	public double computeActivation(double x) {
		return Math.tanh(x);
	}

	@Override
	public NeuronBehavior getDedicatedInstance() {
		return new TangensHyperbolicus();
	}

	@Override
	public boolean needsDedicatedInstancePerNeuron() {
		return false;
	}


	@Override
	public double getAbsoluteMaximumLocationOfSecondDerivative() {
		return 0.66;
	}
	
}
