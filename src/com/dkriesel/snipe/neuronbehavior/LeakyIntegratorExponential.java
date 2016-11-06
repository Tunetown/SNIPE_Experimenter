package com.dkriesel.snipe.neuronbehavior;

import java.io.Serializable;

/**
 * 
 * This class implements an exponential leaky integrator.
 * 
 * <p>The LeakyIntegratorExponential caches the previously computed activation. The
 * first time the computeActivation function is invoked, the network input is
 * returned as activity, and cached. Every further invokation, the cache is
 * multiplied by a factor x. Then, the network input of the current invocation
 * is added to the cache, and the new cache value is returned as activation. The
 * behavior of this class is mostly defined by the signum and absolute of the
 * factor x.
 * 
 * <p>If the signum of the factor is positive, it doesn't modify the cache signum.
 * If the signum is negative, the cache oscillates between positive and negative
 * values.
 * 
 * <p>If the absolute is smaller than 1, cache absolute values tend to decrease
 * over time, if it is greater, they tend to increase, both in an exponential
 * way.
 * 
 * <p>Usually, x is chosen positive between 0 and 1.
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
public class LeakyIntegratorExponential implements NeuronBehavior, Serializable {


	private static final long serialVersionUID = 1L;
	double cache = 0;
	double multiplier = 0;

	/**
	 * @return the cache
	 */
	public double getCache() {
		return cache;
	}

	/**
	 * @return the multiplier
	 */
	public double getMultiplier() {
		return multiplier;
	}

	/**
	 * @param multiplier
	 */
	public LeakyIntegratorExponential(double multiplier) {
		this.multiplier = multiplier;
	}

	@Override
	public double computeActivation(double x) {
		cache *= multiplier;
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
		return new LeakyIntegratorExponential(multiplier);
	}

	@Override
	public boolean needsDedicatedInstancePerNeuron() {
		return true;
	}

}
