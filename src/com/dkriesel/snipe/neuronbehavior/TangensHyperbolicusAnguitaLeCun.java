package com.dkriesel.snipe.neuronbehavior;

/**
 * Like TangensHyperbolicusAnguita, but with scalings analogous to
 * TangensHyperbolicusLeCun.
 * 
 * <p>The absolute maximum location of the second derivative is just copied from
 * Tangens hyperbolicus modified by LeCun and thus is not 100% accurate.
 * However, it's fair enough to work.
 * 
 * 
 * @author David Kriesel / dkriesel.com
 * 
 */
public class TangensHyperbolicusAnguitaLeCun implements NeuronBehavior {
	private static final long serialVersionUID = 1L;

	// a = 0.26037
	// b = 1.92033
	// c = 0.96016

	double y = 0; // assigned because it is faster to assign it here than

	// locally and new with every method invocation

	@Override
	public double computeDerivative(double x) {
		y = (2.0 / 3.0) * x;
		if (y > 0) {
			if (y <= 1.92033) {
				return 1.7159 * -2 * 0.26037 * y + 2 * 0.26037 * 1.92033;
				// -a2x+a2b
			} else {
				return 1.7159 * 0.0781;
			}
		} else {
			if (y >= -1.92033) {
				return 1.7159 * 2 * 0.26037 * y + 2 * 0.26037 * 1.92033;
				// 2ax+2ab
			} else {
				return 1.7159 * 0.0781;
			}
		}
	}

	@Override
	public double getAbsoluteMaximumLocationOfSecondDerivative() {
		return 1;
	}

	@Override
	public double computeActivation(double x) {
		y = (2.0 / 3.0) * x;
		if (y > 0) {
			if (y <= 1.92033) {
				return 1.7159 * 0.96016 - 0.26037 * (y - 1.92033)
						* (y - 1.92033);
				// c-a*(x-b)^2
			} else {
				return 1.7159 * 0.96016;
			}
		} else {
			if (y >= -1.92033) {
				return 1.7159 * 0.26037 * (y + 1.92033) * (y + 1.92033)
						- 0.96016;
				// a*(x+b)^2-c
			} else {
				return 1.7159 * -0.96016;
			}
		}
	}

	@Override
	public NeuronBehavior getDedicatedInstance() {
		return new TangensHyperbolicusAnguitaLeCun();
	}

	@Override
	public boolean needsDedicatedInstancePerNeuron() {
		return false;
	}

}
