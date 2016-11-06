package com.dkriesel.snipe.examples;

import java.text.DecimalFormat;

import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;
import com.dkriesel.snipe.training.ErrorMeasurement;
import com.dkriesel.snipe.training.TrainingSampleLesson;

/**
 * Very simple example program that trains an 8-3-8 multilayer perceptron
 * encoder problem with backpropagation of error.
 * 
 * @author David Kriesel / dkriesel.com
 * 
 */
public class MultilayerPerceptron838ProblemBackprop {

	/**
	 * Executes the example.
	 * 
	 * @param args
	 *            no args are parsed.
	 */
	public static void main(String[] args) {

		/*
		 * Create and configure a descriptor for feed forward networks without
		 * shortcut connections and with fastprop, an identity activity
		 * functions in the input layer and tangens hyperbolicus functions in
		 * hidden and output layers. To learn about fastprop, have a look into
		 * the NeuralNetworkDescriptor documentation.
		 */
		NeuralNetworkDescriptor desc = new NeuralNetworkDescriptor(8, 3, 8);
		desc.setSettingsTopologyFeedForward();

		/*
		 * If you want, remove the comment slashes from the following two lines
		 * to use a tuned tangens hyperbolicus approximation that is computed
		 * faster and provides better learning sometimes.
		 */
		// desc.setNeuronBehaviorHiddenNeurons(new TangensHyperbolicusAnguita());
		// desc.setNeuronBehaviorOutputNeurons(new TangensHyperbolicusAnguita());

		/*
		 * Create neural network using the descriptor (we could as well generate
		 * thousands of similar networks at this point of time). The network
		 * will be automatically added all allowed synapses according to the
		 * default settings in the descriptor.
		 */
		NeuralNetwork net = new NeuralNetwork(desc);

		/*
		 * Prepare Training Data: 8-Dimensional encoder problem with 1 as
		 * positive and -1 as negative value
		 */
		TrainingSampleLesson lesson = TrainingSampleLesson
				.getEncoderSampleLesson(8, 1, -1);

		/*
		 * Train that sucker with backprop in three phases with different
		 * learning rates. In between, display progress, and measure overall
		 * time.
		 */
		long startTime = System.currentTimeMillis();
		System.out.println("Root Mean Square Error before training:\t"
				+ ErrorMeasurement.getErrorRootMeanSquareSum(net, lesson));
		net.trainBackpropagationOfError(lesson, 250000, 0.2);
		System.out.println("Root Mean Square Error after phase 1:\t"
				+ ErrorMeasurement.getErrorRootMeanSquareSum(net, lesson));
		net.trainBackpropagationOfError(lesson, 250000, 0.05);
		System.out.println("Root Mean Square Error after phase 2:\t"
				+ ErrorMeasurement.getErrorRootMeanSquareSum(net, lesson));
		net.trainBackpropagationOfError(lesson, 250000, 0.01);
		System.out.println("Root Mean Square Error after phase 3:\t"
				+ ErrorMeasurement.getErrorRootMeanSquareSum(net, lesson));
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;

		/*
		 * Print out what the network learned (in a formatted way) and the time
		 * it took.
		 */
		DecimalFormat df = new DecimalFormat("#.#");
		System.out.println("\nNetwork output:");
		for (int i = 0; i < lesson.countSamples(); i++) {
			double[] output = net.propagate(lesson.getInputs()[i]);
			for (int j = 0; j < output.length; j++) {
				System.out.print(df.format(output[j]) + "\t");
			}
			System.out.println("");
		}

		System.out.println("\nTime taken: " + time + "ms");
	}
}
