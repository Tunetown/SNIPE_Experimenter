package com.dkriesel.snipe.neuronbehavior;




/**
 * Implements the Fermi Function and its derivative.
 * 
 * @author David Kriesel / dkriesel.com
 *
 */
public class Fermi implements NeuronBehavior{
	
	private static final long serialVersionUID = 1L;


	@Override
	public double computeDerivative(double x) {
		double v = computeActivation(x);
		double result = v*(1-v);
		return result;
	}

	@Override
	public double computeActivation(double x) {
		double result = 1 / (1 + Math.pow(Math.E, (-x)));
		return result;
	}

	@Override
	public NeuronBehavior getDedicatedInstance() {
		return new Fermi();
	}

	@Override
	public boolean needsDedicatedInstancePerNeuron() {
		return false;
	}


	@Override
	public double getAbsoluteMaximumLocationOfSecondDerivative() {
	
		return 1.3;
	}

}
