package de.tunetown.nnpg.model.snipe;

import org.apache.commons.math3.util.FastMath;

import com.dkriesel.snipe.neuronbehavior.NeuronBehavior;


/**
 * Implements the rectified identity and its derivative.
 * 
 * @author Thomas Weber
 *
 */
public class RectLin implements NeuronBehavior{

	@Override
	public double computeDerivative(double x) {
		if(x > 0) return 1;
		else return 0;
	}

	@Override
	public double computeActivation(double x) {
		if(x > 0) return x;
		else return 0;
	}

	@Override
	public NeuronBehavior getDedicatedInstance() {
		return new RectLin();
	}

	@Override
	public boolean needsDedicatedInstancePerNeuron() {
		return false;
	}


	@Override
	public double getAbsoluteMaximumLocationOfSecondDerivative() {
		return 0;
	}
	
}
