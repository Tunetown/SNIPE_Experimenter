package de.tunetown.nnpg.model.snipe;

import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;
import com.dkriesel.snipe.neuronbehavior.Fermi;
import com.dkriesel.snipe.neuronbehavior.Identity;
import com.dkriesel.snipe.neuronbehavior.LeakyIntegratorExponential;
import com.dkriesel.snipe.neuronbehavior.LeakyIntegratorLinear;
import com.dkriesel.snipe.neuronbehavior.NeuronBehavior;
import com.dkriesel.snipe.neuronbehavior.TangensHyperbolicus;
import com.dkriesel.snipe.neuronbehavior.TangensHyperbolicusAnguita;
import com.dkriesel.snipe.neuronbehavior.TangensHyperbolicusAnguitaLeCun;
import com.dkriesel.snipe.neuronbehavior.TangensHyperbolicusLeCun;
import com.dkriesel.snipe.training.ErrorMeasurement;
import com.dkriesel.snipe.training.TrainingSampleLesson;

import de.tunetown.nnpg.model.DataWrapper;
import de.tunetown.nnpg.model.ModelProperties;
import de.tunetown.nnpg.model.NetworkWrapper;
import de.tunetown.nnpg.model.snipe.behaviors.RectifiedLinear;
import de.tunetown.nnpg.model.snipe.behaviors.Softplus;
import de.tunetown.nnpg.model.snipe.behaviors.TangensHyperbolicusACM;
import de.tunetown.nnpg.model.snipe.behaviors.TangensHyperbolicusJafama;

/**
 * Wrapper for SNIPE network.
 * 
 * @author Thomas Weber
 *
 */
public class SNIPENetworkWrapper extends NetworkWrapper {

	private double eta = ModelProperties.NETWORK_DEFAULT_ETA;
	private int batchSize = ModelProperties.NETWORK_DEFAULT_BATCHSIZE; 
	private double initialRange = ModelProperties.NETWORK_INITIAL_RANGE; // TODO remove
	private int behavior = 0; 

	private NeuralNetwork net;
	
	private String[] behaviorDescriptions = { 
			"TanH", 
			"TanH (ACM)",
			"TanH (Jafama)",
			"Tanh (Ang.)", 
			"TanH (AngLeCun)", 
			"Tanh (LeCun)", 
			"ReLU",
			"Softplus",
            "Fermi", 
            "Identity", 
            "LeakyInt.Lin.", 
            "LeakyInt.Exp." };
	
	private NeuronBehavior[] behaviors = { 
			new TangensHyperbolicus(),
			new TangensHyperbolicusACM(),
			new TangensHyperbolicusJafama(),
			new TangensHyperbolicusAnguita(),
			new TangensHyperbolicusAnguitaLeCun(),
			new TangensHyperbolicusLeCun(),
			new RectifiedLinear(0.1),
			new Softplus(),
			new Fermi(),
			new Identity(),
			new LeakyIntegratorExponential(0.1),
			new LeakyIntegratorLinear(0.1)};

	public SNIPENetworkWrapper() {
		createNetwork(ModelProperties.NETWORK_DEFAULT_TOPOLOGY);
	}
		
	public SNIPENetworkWrapper(int[] topology) {
		createNetwork(topology);
	}
	
	public SNIPENetworkWrapper(int[] topology, double initialRange, int behavior) {
		this.behavior = behavior;
		this.initialRange = initialRange;
		createNetwork(topology, initialRange, behavior);
	}

	@Override
	public void createNetwork(int[] topology) {
		createNetwork(topology, initialRange, behavior);
	}
		
	private void createNetwork(int[] topology, double initialRange, int behavior) {
		NeuralNetworkDescriptor desc = new NeuralNetworkDescriptor(topology);
		desc.setSettingsTopologyFeedForward();
		desc.setSynapseInitialRange(initialRange);
		
		desc.setNeuronBehaviorInputNeurons(new Identity());
		desc.setNeuronBehaviorHiddenNeurons(behaviors[behavior]);
		desc.setNeuronBehaviorOutputNeurons(behaviors[behavior]);

		net = new NeuralNetwork(desc);
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
		if (!net.isSynapseExistent(fromNeuron + 1, toNeuron + 1)) return Double.NaN;
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
	public void train(DataWrapper data) {
		if (data.getTrainingLesson() == null || data.getTrainingLesson().size() == 0) return;

		TrainingSampleLesson lesson = ((SNIPEDataWrapper)data).getSNIPETrainingLesson();
		net.trainBackpropagationOfError(lesson, batchSize, eta);
	}

	@Override
	public double getTrainingError(DataWrapper data) {
		TrainingSampleLesson lesson = ((SNIPEDataWrapper)data).getSNIPETrainingLesson();
		if (lesson == null || lesson.countSamples() == 0) return 0;
		return ErrorMeasurement.getErrorSquaredPercentagePrechelt(net, lesson) / 100; //.getErrorRootMeanSquareSum(net, lesson);
	}

	@Override
	public double getTestError(DataWrapper data) {
		TrainingSampleLesson lesson = ((SNIPEDataWrapper)data).getSNIPETestLesson();
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
	public int[] getTopology() {
		return net.getDescriptor().getNeuronsPerLayer();
	}

	@Override
	public int getMaxNeuronsInLayers() {
		int max = 0;
		for(int i=0; i<countLayers(); i++) {
			if (countNeuronsInLayer(i) > max) max = countNeuronsInLayer(i);
		}
		return max;
	}

	@Override
	public NetworkWrapper clone() {
		SNIPENetworkWrapper ret = new SNIPENetworkWrapper(net.getDescriptor().getNeuronsPerLayer(), initialRange, behavior);
		ret.net = net.clone();
		ret.setParametersFrom(this);
		return ret;
	}

	@Override
	public void setParametersFrom(NetworkWrapper network) {
		SNIPENetworkWrapper n = (SNIPENetworkWrapper)network;
		
		setEta(n.getEta());
		setBatchSize(n.getBatchSize());
		setBehavior(n.getBehavior());
		setInitialRange(n.getInitialRange());
	}

	@Override
	public void setBehavior(int i) {
		if (i < 0 || i >= behaviors.length) return;
		if (i == behavior) return;
		behavior = i;
		createNetwork(net.getDescriptor().getNeuronsPerLayer());
	}

	@Override
	public String[] getBehaviorDescriptions() {
		return behaviorDescriptions;
	}

	@Override
	public int getBehavior() {
		return behavior;
	}

	@Override
	public void setInitialRange(double range) {
		initialRange = range;
	}

	@Override
	public double getInitialRange() {
		return initialRange;
	}

	@Override
	public int getOutputBatchSize() {
		return batchSize;
	}
}

