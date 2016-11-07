package model;

public abstract class NetworkWrapper {

	public abstract int countNeurons();

	public abstract int countLayers();

	public abstract int countNeuronsInLayer(int layer);

	public abstract boolean isSynapseExistent(int fromNeuron, int toNeuron);

	public abstract double getWeight(int fromNeuron, int toNeuron);

	public abstract int getLayerOfNeuron(int num);

	public abstract int getFirstNeuronInLayer(int layer);

	public abstract double[] propagate(double[] in);

	public abstract void train(DataWrapper data, TrainingTracker tracker);

	public abstract double getRmsError(DataWrapper data);
}
