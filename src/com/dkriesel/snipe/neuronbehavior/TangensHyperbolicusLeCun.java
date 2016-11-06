package com.dkriesel.snipe.neuronbehavior;

/**
 * Implements the Tangens Hyperbolicus and its derivative in an optimized form
 * by LeCun: f(x) = 1.7159*tanh((2/3)*x). It's maximum second derivative is at x
 * = -1;1 which makes it perfect to learn lessons with desired outputs in this
 * area.
 * 
 * @author David Kriesel / dkriesel.com
 * 
 */
public class TangensHyperbolicusLeCun implements NeuronBehavior {

	@Override
	public double computeDerivative(double x) {
		double t = Math.tanh((2.0 / 3.0) * x);
		t = 1 - (t * t);

		double prefactor = 1.7159 * (2.0 / 3.0);

		return t * prefactor;
	}

	@Override
	public double computeActivation(double x) {
		return 1.7159 * Math.tanh((2.0 / 3.0) * x);
	}

		
	@Override
	public double getAbsoluteMaximumLocationOfSecondDerivative() {
		return 1;
	}

	@Override
	public NeuronBehavior getDedicatedInstance() {
		return new TangensHyperbolicusLeCun();
	}

	@Override
	public boolean needsDedicatedInstancePerNeuron() {
		return false;
	}

}
