package com.dkriesel.snipe.neuronbehavior;

/**
 * This class represents an numerically very simple approximation of the tanh
 * function proposed by Anguita in
 * "Speed Improvement of the Back-Propagation on Current Generation Workstations"
 * by D. Anguita, G. Parodi and R. Zunino in Proceedings of the World Congress
 * on Neural Networking, Portland, Oregon, 1993, volume 1 pages 165-168,
 * Lawrence Erlbaum/INNS Press, 1993.
 * 
 * <p>It outperforms the TangensHyperbolicus by a factor of more than 200 when
 * arguments for derivative and the function itself are chosen out of the
 * interval from -10 to 10, and by about factor 190 if the arguments are chosen
 * out of the interval from -1.9 to 1.9.
 * 
 * <p>In the interval [-1.92033,1.92033], the tanh function is approximated by two
 * parts of parabolic functions. Out of this interval, it is defined by a
 * constant (0.96016, respectively -0.96016).
 * 
 * <p>In the interval [-1.92033,1.92033], the derivative is defined as the
 * derivative of the corresponding parabola. Out of this interval, the
 * derivative is defined constant 0.0781 so that the entire derivative is
 * continuous.
 * 
 * <p>The asymptotically non-zero derivative has a clear advantage: Even if the
 * neuron activities are saturated, the network is still able to learn with
 * gradient descent methods. Consequently, there is no need for strategies like
 * flat spot elimination or similar.
 * 
 * <p>The absolute maximum location of the second derivative is just copied from
 * Tangens hyperbolicus.
 * 
 * @author David Kriesel / dkriesel.com
 * 
 */
public class TangensHyperbolicusAnguita implements NeuronBehavior {
	private static final long serialVersionUID = 1L;

	// a = 0.26037
	// b = 1.92033
	// c = 0.96016

	@Override
	public double computeDerivative(double x) {
		if (x > 0) {
			if (x <= 1.92033) {
				return -2 * 0.26037 * x + 2 * 0.26037 * 1.92033;
				// -a2x+a2b
			} else {
				return 0.0781;
			}
		} else {
			if (x >= -1.92033) {
				return 2 * 0.26037 * x + 2 * 0.26037 * 1.92033;
				// 2ax+2ab
			} else {
				return 0.0781;
			}
		}
	}

	@Override
	public double computeActivation(double x) {
		if (x > 0) {
			if (x <= 1.92033) {
				return 0.96016 - 0.26037 * (x - 1.92033) * (x - 1.92033);
				// c-a*(x-b)^2
			} else {
				return 0.96016;
			}
		} else {
			if (x >= -1.92033) {
				return 0.26037 * (x + 1.92033) * (x + 1.92033) - 0.96016;
				// a*(x+b)^2-c
			} else {
				return -0.96016;
			}
		}
	}

	@Override
	public NeuronBehavior getDedicatedInstance() {
		return new TangensHyperbolicusAnguita();
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
