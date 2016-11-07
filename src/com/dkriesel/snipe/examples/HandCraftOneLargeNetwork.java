package com.dkriesel.snipe.examples;

import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;

/**
 * Simple example program that shows how to hand-craft even large numbers of
 * networks.
 * 
 * @author David Kriesel / dkriesel.com
 * 
 */
public class HandCraftOneLargeNetwork {

	/**
	 * Executes the example.
	 * 
	 * @param args
	 *            no args are parsed.
	 */
	public static void main(String[] args) {

		System.out.println("Create descriptor ...");
		NeuralNetworkDescriptor desc = new NeuralNetworkDescriptor(2500,
				10000, 10000, 2500);
		desc.setInitializeAllowedSynapses(true);
		desc.setSynapseInitialRange(0.1);

		System.out.println("Create network ...");
		NeuralNetwork net = new NeuralNetwork(desc);

		System.out.println(net.countSynapses() + " synapses created.");
	}
}
