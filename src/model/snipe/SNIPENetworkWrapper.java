package model.snipe;

import model.DataWrapper;
import model.NetworkWrapper;
import model.TrainingTracker;
import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;
import com.dkriesel.snipe.neuronbehavior.Identity;
import com.dkriesel.snipe.neuronbehavior.TangensHyperbolicus;
import com.dkriesel.snipe.training.ErrorMeasurement;
import com.dkriesel.snipe.training.TrainingSampleLesson;

public class SNIPENetworkWrapper extends NetworkWrapper {

	private NeuralNetwork net;
	private double eta = 0.03;
	private int batchSize = 10;
	
	public SNIPENetworkWrapper() {
		net = createNetwork();
	}
	
	private NeuralNetwork createNetwork() {
		int[] layers = {2,4,4, 1};
		NeuralNetworkDescriptor desc = new NeuralNetworkDescriptor(layers);
		desc.setSettingsTopologyFeedForward();
		desc.setSynapseInitialRange(0.4);
		
		desc.setNeuronBehaviorInputNeurons(new Identity());
		desc.setNeuronBehaviorHiddenNeurons(new TangensHyperbolicus());
		desc.setNeuronBehaviorOutputNeurons(new TangensHyperbolicus());
		
		NeuralNetwork net = desc.createNeuralNetwork();
 
		return net;
	}

	@Override
	public int countNeurons() {
		return net.countNeurons();
	}

	@Override
	public int countLayers() {
		return net.countLayers();
	}

	@Override
	public int countNeuronsInLayer(int layer) {
		return net.countNeuronsInLayer(layer);
	}

	@Override
	public boolean isSynapseExistent(int fromNeuron, int toNeuron) {
		return net.isSynapseExistent(fromNeuron + 1, toNeuron + 1);
	}

	@Override
	public double getWeight(int fromNeuron, int toNeuron) {
		return net.getWeight(fromNeuron + 1,  toNeuron + 1);
	}

	@Override
	public int getLayerOfNeuron(int num) {
		return net.getLayerOfNeuron(num + 1);
	}

	@Override
	public int getFirstNeuronInLayer(int layer) {
		return net.getNeuronFirstInLayer(layer) - 1;
	}

	@Override
	public double[] propagate(double[] in) {
		return net.propagate(in);
	}

	@Override
	public void train(DataWrapper data, TrainingTracker tracker) {
		if (data.getNumOfSamples() == 0) return;
		
		tracker.addRecord(getRmsError(data));
		
		TrainingSampleLesson lesson = ((SNIPEDataWrapper)data).getLesson();
		net.trainBackpropagationOfError(lesson, batchSize, eta);
	}

	@Override
	public double getRmsError(DataWrapper data) {
		TrainingSampleLesson lesson = ((SNIPEDataWrapper)data).getLesson();
		if (lesson == null || lesson.countSamples() == 0) return 0;
		return ErrorMeasurement.getErrorRootMeanSquareSum(net, lesson);
	}

	@Override
	public double getBiasWeight(int num) {
		if (!net.isSynapseExistent(0, num + 1)) return Double.NaN;
		return net.getWeight(0, num + 1);
	}

	@Override
	public double getEta() {
		return eta;
	}

	@Override
	public void setEta(double eta) {
		this.eta = eta;
	}

	@Override
	public int getBatchSize() {
		return batchSize;
	}

	@Override
	public void setBatchSize(int size) {
		batchSize = size;
	}
}
