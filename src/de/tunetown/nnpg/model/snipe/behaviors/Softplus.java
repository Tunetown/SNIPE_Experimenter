package de.tunetown.nnpg.model.snipe.behaviors;

import net.jafama.FastMath;

import com.dkriesel.snipe.neuronbehavior.NeuronBehavior;


/**
 * Implements the softplus behavior
 * 
 * @author Thomas Weber
 *
 */
public class Softplus implements NeuronBehavior {
	private static final long serialVersionUID = 1L;
	
	@Override
	public double computeDerivative(double x) {
		return 1.0 / ( 1.0 + FastMath.exp(-x) );
	}

	@Override
	public double computeActivation(double x) {
		return FastMath.log(1.0 + FastMath.exp(x));
	}

	@Override
	public NeuronBehavior getDedicatedInstance() {
		return new Softplus();
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
