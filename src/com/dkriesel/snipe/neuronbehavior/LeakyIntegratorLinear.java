package com.dkriesel.snipe.neuronbehavior;

import java.io.Serializable;

/**
 * 
 * This class implements a simple linear leaky integrator. It is mainly
 * implemented for testing purposes, so be sure to check the
 * LeakyIntegratorExponential class documentation in order to determine whether
 * this is the class to take.
 * 
 * <p>The LeakyIntegratorLinear caches the previously computed activation. The
 * first time the computeActivation function is invoked, the network input is
 * returned as activity, and cached. Every further invokation, an addend x is
 * added to the cache. If the cache gets negative after this addition, it is
 * replaced by a zero. Then, the network input of the current invocation is
 * added to the cache, and the cache is returned as activation. Note: Usually, x
 * is set to a negative value.
 * 
 * <p>Since last activations are to be cached per neuron, this class needs
 * dedicated instances per neuron.
 * 
 * <p>The functions computeDerivative and
 * getAbsoluteMaximumLocationOfSecondDerivative return Double.NaN.
 * 
 * @author David Kriesel / dkriesel.com
 * 
 */
public class LeakyIntegratorLinear implements NeuronBehavior, Serializable {

	private static final long serialVersionUID = 1L;
	double cache = 0;
	double addend = 0;

	/**
	 * @return the cache
	 */
	public double getCache() {
		return cache;
	}


	/**
	 * @return the addend
	 */
	public double getAddend() {
		return addend;
	}


	/**
	 * @param addend
	 */
	public LeakyIntegratorLinear(double addend) {
		this.addend = addend;
	}

	@Override
	public double computeActivation(double x) {
		cache += addend;
		if (cache < 0.0) {
			cache = 0.0;
		}
		cache += x;
		return cache;
	}

	
	@Override
	public double computeDerivative(double x) {
		return Double.NaN;
	}

	@Override
	public double getAbsoluteMaximumLocationOfSecondDerivative() {
		return Double.NaN;
	}

	@Override
	public NeuronBehavior getDedicatedInstance() {
		return new LeakyIntegratorLinear(addend);
	}


	@Override
	public boolean needsDedicatedInstancePerNeuron() {
		return true;
	}

}
