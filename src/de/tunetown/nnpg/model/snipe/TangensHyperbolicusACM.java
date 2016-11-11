package de.tunetown.nnpg.model.snipe;

import org.apache.commons.math3.util.FastMath;

import com.dkriesel.snipe.neuronbehavior.NeuronBehavior;


/**
 * Implements the Tangens Hyperbolicus and its derivative.
 * 
 * @author Thomas Weber
 *
 */
public class TangensHyperbolicusACM implements NeuronBehavior{

	@Override
	public double computeDerivative(double x) {
		double t = FastMath.tanh(x);
		return 1 - (t*t);
	}

	@Override
	public double computeActivation(double x) {
		return FastMath.tanh(x);
	}

	@Override
	public NeuronBehavior getDedicatedInstance() {
		return new TangensHyperbolicusACM();
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
