package de.tunetown.nnpg.model.snipe;

import java.util.Iterator;
import java.util.TreeMap;

import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;
import com.dkriesel.snipe.neuronbehavior.NeuronBehavior;

/**
 * Multithreaded version of NeuralNetwork. back propagation training is re-implemented to support a thread pool.
 * 
 * NOTE: To enable this, also all private properies of NeuralNetwork have to be made protected!
 * 
 * @author tweber
 *
 */
public class SNIPEPooleNeuralNetwork extends NeuralNetwork {
	private static final long serialVersionUID = 1L;

	public SNIPEPooleNeuralNetwork(NeuralNetworkDescriptor descriptor) {
		super(descriptor);
	}
	
	/**
	 * This is just a copy of NeuralNetwork.clone() with types replaced
	 * 
	 */
	public SNIPEPooleNeuralNetwork clone() {
		SNIPEPooleNeuralNetwork clonedNeuralNet = new SNIPEPooleNeuralNetwork(descriptor);

		// 2dim arrays have to be cloned manually
		int[][] predecessorsClone = predecessors.clone();
		for (int i = 0; i < predecessorsClone.length; i++) {
			predecessorsClone[i] = predecessorsClone[i].clone();
		}

		double[][] predecessorWeightsClone = predecessorWeights.clone();
		for (int i = 0; i < predecessorWeightsClone.length; i++) {
			predecessorWeightsClone[i] = predecessorWeightsClone[i].clone();
		}

		int[][] successorsClone = successors.clone();
		for (int i = 0; i < successorsClone.length; i++) {
			successorsClone[i] = successorsClone[i].clone();
		}

		int[][] successorWeightIndexInPendantPredecessorArrayClone = successorWeightIndexInPendantPredecessorArray
				.clone();
		for (int i = 0; i < successorWeightIndexInPendantPredecessorArrayClone.length; i++) {
			successorWeightIndexInPendantPredecessorArrayClone[i] = successorWeightIndexInPendantPredecessorArrayClone[i]
					.clone();
		}

		// clone shadows, store them in new shadowTreeMap, use new local fields
		// of the new neural network as keys.
		TreeMap<String, double[][]> synapseShadowsClone = new TreeMap<String, double[][]>();

		for (Iterator<String> keyIterator = shadows.keySet().iterator(); keyIterator
				.hasNext();) {
			String key = keyIterator.next();
			double[][] shadow = shadows.get(key);
			double[][] clone = shadow.clone();
			for (int i = 0; i < clone.length; i++) {
				clone[i] = shadow[i].clone();
			}
			synapseShadowsClone.put(new String(key), clone);

		}

		// 1dim arrays are cloned automatically
		int[] layerStartingNeuronsClone = layerStartingNeurons.clone();
		int[] neuronsPerLayerClone = neuronsPerLayer.clone();
		double[] activationsClone = activations.clone();
		double[] netInputsClone = netInputs.clone();

		NeuronBehavior[] neuronBehaviorsClone = new NeuronBehavior[neuronBehaviors.length];
		for (int i = 0; i < neuronBehaviorsClone.length; i++) {
			if (neuronBehaviors[i] != null) {
				if (neuronBehaviors[i].needsDedicatedInstancePerNeuron()) {
					neuronBehaviorsClone[i] = neuronBehaviors[i]
							.getDedicatedInstance();
				} else {
					neuronBehaviorsClone[i] = neuronBehaviors[i];
				}
			}
		}

		clonedNeuralNet.resilientBackpropagationDeltaZero = resilientBackpropagationDeltaZero;
		clonedNeuralNet.resilientBackpropagationDeltaMax = resilientBackpropagationDeltaMax;
		clonedNeuralNet.resilientBackpropagationDeltaMin = resilientBackpropagationDeltaMin;
		clonedNeuralNet.resilientBackpropagationEtaMinus = resilientBackpropagationEtaMinus;
		clonedNeuralNet.resilientBackpropagationEtaPlus = resilientBackpropagationEtaPlus;
		clonedNeuralNet.predecessors = predecessorsClone;
		clonedNeuralNet.predecessorWeights = predecessorWeightsClone;
		clonedNeuralNet.successors = successorsClone;
		clonedNeuralNet.successorWeightIndexInPendantPredecessorArray = successorWeightIndexInPendantPredecessorArrayClone;
		clonedNeuralNet.shadows = synapseShadowsClone;
		clonedNeuralNet.layerStartingNeurons = layerStartingNeuronsClone;
		clonedNeuralNet.neuronsPerLayer = neuronsPerLayerClone;
		clonedNeuralNet.activations = activationsClone;
		clonedNeuralNet.netInputs = netInputsClone;
		clonedNeuralNet.neuronBehaviors = neuronBehaviorsClone;

		return clonedNeuralNet;

	}
}
