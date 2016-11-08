package de.tunetown.nnpg.model.snipe;

import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;
import com.dkriesel.snipe.neuronbehavior.Identity;
import com.dkriesel.snipe.neuronbehavior.TangensHyperbolicus;
import com.dkriesel.snipe.training.ErrorMeasurement;
import com.dkriesel.snipe.training.TrainingSampleLesson;

import de.tunetown.nnpg.model.DataWrapper;
import de.tunetown.nnpg.model.NetworkWrapper;
import de.tunetown.nnpg.model.TrainingTracker;

/**
 * Wrapper for SNIPE network.
 * 
 * @author xwebert
 *
 */
public class SNIPENetworkWrapper extends NetworkWrapper {

	private double eta = 0.002;
	private int batchSize = 10000;

	private NeuralNetwork net;
	
	public SNIPENetworkWrapper() {
		net = createNetwork();
	}
	
	private NeuralNetwork createNetwork() {
		int[] layers = {2,8,8,8, 1};
		NeuralNetworkDescriptor desc = new NeuralNetworkDescriptor(layers);
		desc.setSettingsTopologyFeedForward();
		desc.setSynapseInitialRange(0.1);
		
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
		if (!net.isSynapseExistent(fromNeuron, toNeuron)) return Double.NaN;
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

		tracker.addRecord(getError(data));
		
		TrainingSampleLesson lesson = ((SNIPEDataWrapper)data).getLesson();
		
		long start = System.nanoTime();
		net.trainBackpropagationOfError(lesson, batchSize, eta);
		tracker.addNanoTime(System.nanoTime() - start);
	}

	@Override
	public double getError(DataWrapper data) {
		TrainingSampleLesson lesson = ((SNIPEDataWrapper)data).getLesson();
		if (lesson == null || lesson.countSamples() == 0) return 0;
		return ErrorMeasurement.getErrorSquaredPercentagePrechelt(net, lesson) / 100; //.getErrorRootMeanSquareSum(net, lesson);
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

	@Override
	public int getMaxNeuronsInLayers() {
		int max = 0;
		for(int i=0; i<countLayers(); i++) {
			if (countNeuronsInLayer(i) > max) max = countNeuronsInLayer(i);
		}
		return max;
	}
}

