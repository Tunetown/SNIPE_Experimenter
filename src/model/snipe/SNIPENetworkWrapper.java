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
		//samples.optimizeDesiredOutputsForClassificationProblem(frame.getNetwork().getNetwork()); TODO needed?
		net.trainBackpropagationOfError(lesson, 100, 0.03);
	}

	@Override
	public double getRmsError(DataWrapper data) {
		TrainingSampleLesson lesson = ((SNIPEDataWrapper)data).getLesson();
		return ErrorMeasurement.getErrorRootMeanSquareSum(net, lesson);
	}

}
