package main;

import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;
import com.dkriesel.snipe.training.TrainingSampleLesson;

public class SENetwork {

	private NeuralNetwork net;
	private TrainingSampleLesson samples;
	
	public SENetwork() {
		net = createNetwork();
		samples = createSamples();
	}
	
	public NeuralNetwork getNetwork() {
		return net;
	}
	
	private TrainingSampleLesson createSamples() {
		TrainingSampleLesson lesson = TrainingSampleLesson.getEncoderSampleLesson(8, 1, -1);
		lesson.optimizeDesiredOutputsForClassificationProblem(net);
		
		
		
		
		return lesson;
	}
	
	private NeuralNetwork createNetwork() {
		System.out.println("SNIPE Experiment");

		/*
		 * Create a NeuralNetworkDescriptor outlining Networks with two input
		 * neurons, one hidden neuron and two output neurons. Tell the networks
		 * not to automatically initialize synapses that are allowed. Tell them
		 * to choose synaptic weight values out of [-0.1;0.1] when initializing
		 * synapse weights randomly.
		 */
		int[] layers = {2,4,4,4,4, 1};
		NeuralNetworkDescriptor desc = new NeuralNetworkDescriptor(layers);
		desc.setInitializeAllowedSynapses(false);
		desc.setSynapseInitialRange(1);
		desc.setFrequency(layers.length - 1);
		
		NeuralNetwork net = desc.createNeuralNetwork();

		/*
		 * Step 0: We now have 10000 neural networks with 2 input neurons
		 * (numbered 1,2), one hidden neuron (3) and two output neurons (4
		 * and 5). There are no synapses yet. The input layer is numbered 0,
		 * the hidden layer 1 and the output layer is numbered 2. We will
		 * see that the NeuralNetwork class maintains the ascending
		 * numbering of neurons and layers.
		 */
 
		/*
		 * Step 1: Now, in each network create three additional hidden
		 * neurons. The hidden layer now contains neurons with indices 3, 4,
		 * 5 and 6, while the indices of the output neurons have been
		 * increased.
		 */
		//net.createNeuronInLayer(1);
		//net.createNeuronInLayer(1);
		//int thirdOne = net.createNeuronInLayer(1);
 
			/*
		 * Step 2: Create a synapse from the BIAS to the hidden neuron just
		 * added.
		 */
		//net.setSynapse(0, thirdOne, 2.0);
 
			/*
		 * Step 3: Remove the hidden neuron that was added last. This
		 * decreases all following neuron indices and removes the incident
		 * synapse we added as well.
		 */
		//net.removeNeuron(thirdOne);
 
			/*
		 * Step 4: Now, create a full connection set from hidden to output
		 * layer. Connections will be initialized with weight values out of
		 * [-0.1;0.1], as we defined in the descriptor.
		 */
		net.createSynapsesFromLayerToLayer(0, 1);
		net.createSynapsesFromLayerToLayer(1, 2);
		net.createSynapsesFromLayerToLayer(2, 3);
		net.createSynapsesFromLayerToLayer(3, 4);
		net.createSynapsesFromLayerToLayer(4, 5);
 
			/*
		 * Step 5: Add three forward connections from input layer to hidden
		 * layer.
		 *
		net.setSynapse(1, 3, 5.0);
		net.setSynapse(1, 4, 2.0);
		net.setSynapse(2, 5, 3.0);
 
			/*
		 * Step 6: Add two forward shortcuts from input layer to output
		 * layer.
		 *
		net.setSynapse(1, 6, 4.0);
		net.setSynapse(2, 7, 5.0);
 
			/*
		 * Step 7: Add two self-connections in hidden layer.
		 *
			net.setSynapse(3, 3, 6.0);
			net.setSynapse(5, 5, 7.0);
 
			/*
		 * Step 8: Add two lateral connections between the two output
		 * neurons. Now, we're done.
		 *
		net.setSynapse(6, 7, 8.0);
		net.setSynapse(7, 6, 9.0);
 
		/*
		 * Print out GraphViz Code of one network. Weight labels of weak
		 * synapses (namely, the ones that were initialized with weight
		 * absolutes smaller or equal to 0.1) are suppressed. Those synapses are
		 * printed lighter as well.
		 *
		GraphVizEncoder graph = new GraphVizEncoder();
		String code = graph.getGraphVizCode(net, "NeuralNetwork");
		System.out.println(code);
		*/
		return net;
	}

}
