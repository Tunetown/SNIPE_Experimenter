package de.tunetown.nnpg.model;

/**
 * Facade wrapper to integrate a neural network implementation.
 * 
 * Neurons are numbered from 0 linearly starting from layer 0 (input), 
 * without the bias neuron being counted.
 * 
 * @author xwebert
 *
 */
public abstract class NetworkWrapper {

	/**
	 * Returns the number of neurons in the network.
	 * 
	 * @return
	 */
	public abstract int countNeurons();

	/**
	 * Returns the amount of layers (including inouts and outputs)
	 * 
	 * @return
	 */
	public abstract int countLayers();

	/**
	 * Returns the amount of neurons in a given layer (layers starting with 0, which is the input layer)
	 * 
	 * @param layer
	 * @return
	 */
	public abstract int countNeuronsInLayer(int layer);

	/**
	 * Does a synapse between neuron fromNeuron and neuron toNeuron exist?
	 * 
	 * @param fromNeuron
	 * @param toNeuron
	 * @return
	 */
	public abstract boolean isSynapseExistent(int fromNeuron, int toNeuron);

	/**
	 * Returns the weight of a synapse, or NaN if the synapse does not exist.
	 * 
	 * @param fromNeuron
	 * @param toNeuron
	 * @return
	 */
	public abstract double getWeight(int fromNeuron, int toNeuron);

	/**
	 * Returns the layer of a given neuron
	 * 
	 * @param num
	 * @return
	 */
	public abstract int getLayerOfNeuron(int num);

	/**
	 * Returns the first neuron number in a given layer
	 * 
	 * @param layer
	 * @return
	 */
	public abstract int getFirstNeuronInLayer(int layer);

	/**
	 * Returns the amount of neurons in the largest layer.
	 * 
	 * @return
	 */
	public abstract int getMaxNeuronsInLayers();

	/**
	 * Propagation of inputs through the network
	 * 
	 * @param in
	 * @return
	 */
	public abstract double[] propagate(double[] in);

	/**
	 * Train the network. The training parameters are controlled by the network class itself.
	 * 
	 * @param data Training data
	 * @param tracker Tracker instance, which keeps track of the training process.
	 */
	public abstract void train(DataWrapper data, TrainingTracker tracker);

	/**
	 * Get the current error of the network, regarding the given data.
	 * 
	 * @param data
	 * @return
	 */
	public abstract double getError(DataWrapper data);

	/**
	 * Returns the bias weight for a neuron, or NaN if there is no bias neuron connected 
	 * to the given neuron (for example, inputs have no connection to bias).
	 * 
	 * @param num
	 * @return
	 */
	public abstract double getBiasWeight(int num);
	
	/**
	 * Returns the current learning rate
	 * 
	 * @return
	 */
	public abstract double getEta();
	
	/**
	 * Defines the current learning rate
	 * 
	 * @param eta
	 */
	public abstract void setEta(double eta);

	/**
	 * Returns the current batch size
	 * 
	 * @return
	 */
	public abstract int getBatchSize();
	
	/**
	 * Defines the current batch size
	 * 
	 * @param size
	 */
	public abstract void setBatchSize(int size);
	
	/**
	 * Returns a clone of the whole network
	 * 
	 */
	public abstract NetworkWrapper clone();

	/**
	 * Copy all parameters (not the learning stuff like weight) from a given network instance
	 * 
	 * @param network
	 * @return
	 */
	public abstract void setParametersFrom(NetworkWrapper network);
}
