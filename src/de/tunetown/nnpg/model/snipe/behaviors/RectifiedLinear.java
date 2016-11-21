package de.tunetown.nnpg.model.snipe.behaviors;

import com.dkriesel.snipe.neuronbehavior.NeuronBehavior;


/**
 * Implements the rectified identity and its derivative.
 * 
 * @author Thomas Weber
 *
 */
public class RectifiedLinear implements NeuronBehavior{
	private static final long serialVersionUID = 1L;
	
	private double negativeScaling;
	
	public RectifiedLinear(double negativeScaling) {
		this.negativeScaling = negativeScaling;
	}

	@Override
	public double computeDerivative(double x) {
		if(x > 0) return 1;
		else return negativeScaling;
	}

	@Override
	public double computeActivation(double x) {
		if(x > 0) return x;
		else return negativeScaling * x;
	}

	@Override
	public NeuronBehavior getDedicatedInstance() {
		return new RectifiedLinear(negativeScaling);
	}

	@Override
	public boolean needsDedicatedInstancePerNeuron() {
		return false;
	}


	@Override
	public double getAbsoluteMaximumLocationOfSecondDerivative() {
		return Double.NaN;
	}
	
}
