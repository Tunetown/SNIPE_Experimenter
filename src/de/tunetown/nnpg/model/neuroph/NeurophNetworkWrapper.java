package de.tunetown.nnpg.model.neuroph;

import java.util.List;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;

import de.tunetown.nnpg.model.DataWrapper;
import de.tunetown.nnpg.model.ModelProperties;
import de.tunetown.nnpg.model.NetworkWrapper;

/**
 * Wrapper for Neuroph network. (TODO: To Implement...)
 * 
 * @author Thomas Weber
 *
 */
public class NeurophNetworkWrapper extends NetworkWrapper {

	private double eta = ModelProperties.NETWORK_DEFAULT_ETA;
	private int batchSize = ModelProperties.NETWORK_DEFAULT_BATCHSIZE;
	private double initialRange = ModelProperties.NETWORK_INITIAL_RANGE;

	private NeuralNetwork net;
	private int[] topology;
	
	private int behavior = 0;
	/*
	
	private String[] behaviorDescriptions = { 
			"TanH", 
			"TanH (ACM)",
			"TanH (Jafama)",
			"Tanh (Ang.)", 
			"TanH (AngLeCun)", 
			"Tanh (LeCun)", 
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
			new Fermi(),
			new Identity(),
			new LeakyIntegratorExponential(-1),
			new LeakyIntegratorLinear(-1)};
*/
	public NeurophNetworkWrapper() {
		createNetwork(ModelProperties.NETWORK_DEFAULT_TOPOLOGY);
	}
		
	public NeurophNetworkWrapper(int[] topology) {
		createNetwork(topology);
	}
	
	public NeurophNetworkWrapper(int[] topology, double initialRange, int behavior) {
		this.behavior = behavior;
		//this.initialRange = initialRange; // TODO
		createNetwork(topology, initialRange, behavior);
	}

	@Override
	public void createNetwork(int[] topology) {
		createNetwork(topology, initialRange, behavior); // TODO
	}
		
	private void createNetwork(int[] topology, double initialRange, int behavior) {
		this.topology = topology;
		net = new MultiLayerPerceptron(2,10,1); // TODO
		
		/*
		NeuralNetworkDescriptor desc = new NeuralNetworkDescriptor(topology);
		desc.setSettingsTopologyFeedForward();
		desc.setSynapseInitialRange(initialRange);
		
		desc.setNeuronBehaviorInputNeurons(new Identity());
		desc.setNeuronBehaviorHiddenNeurons(behaviors[behavior]);
		desc.setNeuronBehaviorOutputNeurons(behaviors[behavior]);

		net = new NeuralNetwork(desc);
		*/
	}

	@Override
	public int countNeurons() { // TODO put to networkwrapper?
		int ret = 0;
		for(int i=0; i<topology.length; i++) ret += topology[i];
		return ret;
	}

	@Override
	public int countLayers() {
		return topology.length; // TODO put to networkwrapper?
	}

	@Override
	public int countNeuronsInLayer(int layer) { // TODO put to networkwrapper?
		return topology[layer];
	}

	@Override
	public boolean isSynapseExistent(int fromNeuron, int toNeuron) {
		List<Layer> layers = net.getLayers();
		Layer l1 = null;
		return false;
	}

	@Override
	public double getWeight(int fromNeuron, int toNeuron) {
		//if (!net.isSynapseExistent(fromNeuron + 1, toNeuron + 1)) return Double.NaN;
		return 0; //net.getWeight(fromNeuron + 1,  toNeuron + 1);
	}

	@Override
	public int getLayerOfNeuron(int num) {
		return 0;
	}

	@Override
	public int getFirstNeuronInLayer(int layer) {
		return 0;
	}

	@Override
	public double[] propagate(double[] in) {
		return new double[1]; //net.propagate(in);
	}

	@Override
	public void train(DataWrapper data) {
		if (data.getTrainingLesson() == null || data.getTrainingLesson().size() == 0) return;

		//TrainingSampleLesson lesson = ((NeurophDataWrapper)data).getSNIPETrainingLesson();
		//net.trainBackpropagationOfError(lesson, batchSize, eta);
	}

	@Override
	public double getTrainingError(DataWrapper data) {
		//TrainingSampleLesson lesson = ((NeurophDataWrapper)data).getSNIPETrainingLesson();
		//if (lesson == null || lesson.countSamples() == 0) return 0;
		return 0; //ErrorMeasurement.getErrorSquaredPercentagePrechelt(net, lesson) / 100; //.getErrorRootMeanSquareSum(net, lesson);
	}

	@Override
	public double getTestError(DataWrapper data) {
		//TrainingSampleLesson lesson = ((NeurophDataWrapper)data).getSNIPETestLesson();
		//if (lesson == null || lesson.countSamples() == 0) return 0;
		return 0; //ErrorMeasurement.getErrorSquaredPercentagePrechelt(net, lesson) / 100; //.getErrorRootMeanSquareSum(net, lesson);
	}

	@Override
	public double getBiasWeight(int num) {
		//if (!net.isSynapseExistent(0, num + 1)) return Double.NaN;
		return 0; //net.getWeight(0, num + 1);
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
		return new int[1]; //net.getDescriptor().getNeuronsPerLayer();
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
		//NeurophNetworkWrapper ret = new NeurophNetworkWrapper(net.getDescriptor().getNeuronsPerLayer(), initialRange, behavior);
		//ret.net = net.clone();
		//ret.setParametersFrom(this);
		return null; //ret;
	}

	@Override
	public void setParametersFrom(NetworkWrapper network) {
		NeurophNetworkWrapper n = (NeurophNetworkWrapper)network;
		
		setEta(n.getEta());
		setBatchSize(n.getBatchSize());
		setBehavior(n.getBehavior());
		setInitialRange(n.getInitialRange());
	}

	/**
	 * NOTE: For SNIPE, the reset flag is ignored in setTopology()!
	 */
	@Override
	public void addLayer(int position, int neurons, boolean reset) {
		/*
		if (position >= net.countLayers()) return;
		
		int[] t = net.getDescriptor().getNeuronsPerLayer();
		int[] nt = new int[t.length + 1];
		
		int nn = 0;
		for(int i=0; i<position; i++) {
			nt[nn] = t[i];
			nn++;
		}
		nt[nn] = neurons;
		nn++;
		for(int i=position; i<t.length; i++) {
			nt[nn] = t[i];
			nn++;
		}
		createNetwork(nt);
		*/
	}

	/**
	 * NOTE: For SNIPE, the reset flag is ignored in setTopology()!
	 */
	@Override
	public void removeLayer(int layer, boolean reset) {
		/*
		if (layer >= net.countLayers()) return;
		
		int[] t = net.getDescriptor().getNeuronsPerLayer();
		int[] nt = new int[t.length - 1];
		
		int nn = 0;
		for(int i=0; i<layer; i++) {
			nt[nn] = t[i];
			nn++;
		}
		for(int i=layer+1; i<t.length; i++) {
			nt[nn] = t[i];
			nn++;
		}
		createNetwork(nt);
		*/
	}

	@Override
	public void addNeuron(int layer, boolean reset) {
		/*
		int[] t = net.getDescriptor().getNeuronsPerLayer();
		if (layer >= t.length || layer < 0) return;
		t[layer]++;
		createNetwork(t);
		*/
	}

	@Override
	public void removeNeuron(int layer, boolean reset) {
		/*
		int[] t = net.getDescriptor().getNeuronsPerLayer();
		if (layer >= t.length || layer < 0) return;
		if (t[layer] < 2) return;
		t[layer]--;
		createNetwork(t);
		*/
	}

	@Override
	public void setBehavior(int i) {
		/*
		if (i < 0 || i >= behaviors.length) return;
		if (i == behavior) return;
		behavior = i;
		createNetwork();
		*/
	}

	@Override
	public String[] getBehaviorDescriptions() {
		return null; //behaviorDescriptions;
	}

	@Override
	public int getBehavior() {
		return 0; //behavior;
	}

	@Override
	public void setInitialRange(double range) {
		initialRange = range;
	}

	@Override
	public double getInitialRange() {
		return initialRange;
	}
}

