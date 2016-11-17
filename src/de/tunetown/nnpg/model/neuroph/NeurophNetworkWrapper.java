package de.tunetown.nnpg.model.neuroph;

import java.util.List;

import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.learning.BackPropagation;

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

	@SuppressWarnings("rawtypes")
	private NeuralNetwork net;
	
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
		//this.initialRange = initialRange; 
		createNetwork(topology, initialRange, behavior);
	}

	@Override
	public void createNetwork(int[] topology) {
		createNetwork(topology, initialRange, behavior); 
	}
		
	@SuppressWarnings("unchecked")
	private void createNetwork(int[] topology, double initialRange, int behavior) {
		net = new MultiLayerPerceptron(topology); // TODO
		net.setLearningRule(new BackPropagation());
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
	public int countNeurons() { 
		int ret = 0;
		for(int i=0; i<net.getLayersCount(); i++) {
			ret += net.getLayerAt(i).getNeuronsCount() - getBiasNeuronsInLayer(i); 
		}
		return ret;
	}

	@Override
	public int countLayers() {
		return net.getLayersCount(); 
	}

	@Override
	public int countNeuronsInLayer(int layer) { 
		return net.getLayerAt(layer).getNeuronsCount() - getBiasNeuronsInLayer(layer); 
	}

	@Override
	public boolean isSynapseExistent(int fromNeuron, int toNeuron) {
		Neuron n1 = getNeuron(fromNeuron);
		Neuron n2 = getNeuron(toNeuron);
		Connection c = n2.getConnectionFrom(n1);
		return c == null;
	}

	private Neuron getNeuron(int n) {
		int layer = getLayerOfNeuron(n);
		int index = n - getFirstNeuronInLayer(layer);
		return net.getLayerAt(layer).getNeuronAt(index);
	}

	@Override
	public double getWeight(int fromNeuron, int toNeuron) {
		Neuron n1 = getNeuron(fromNeuron);
		Neuron n2 = getNeuron(toNeuron);
		Connection c = n2.getConnectionFrom(n1);
		if(c != null) return c.getWeight().getValue(); 
		else return 0;
	}

	@Override
	public int getLayerOfNeuron(int num) {
		int l = 0;
		for(int i=0; i<net.getLayersCount(); i++) {
			l += net.getLayerAt(i).getNeuronsCount() - getBiasNeuronsInLayer(i);
			if(num < l) {
				return i;
			}
		}
		return -1; // Error
	}

	@Override
	public int getFirstNeuronInLayer(int layer) {
		int l = 0;
		for(int i=0; i<layer; i++) {
			l += net.getLayerAt(i).getNeuronsCount() - getBiasNeuronsInLayer(i);
		}
		return l;
	}

	@Override
	public double[] propagate(double[] in) {
		net.setInput(in);
		net.calculate();
		return net.getOutput();
	}

	@Override
	public void train(DataWrapper data) {
		if (data.getTrainingLesson() == null || data.getTrainingLesson().size() == 0) return;

		DataSet trainingSet = ((NeurophDataWrapper)data).getNeurophTrainingLesson();

		BackPropagation p = (BackPropagation)net.getLearningRule();
		p.setLearningRate(eta);
		// TODO batch size

		IterativeLearning rule = (IterativeLearning)net.getLearningRule();
		rule.doLearningEpoch(trainingSet);

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
		return Double.NaN; // TODO
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
		@SuppressWarnings("unchecked")
		List<Layer> layers = net.getLayers();
		int[] ret = new int[layers.size()];
		for(int i=0; i<layers.size(); i++) {
			ret[i] = net.getLayerAt(i).getNeuronsCount() - getBiasNeuronsInLayer(i);
		}
		return ret; 
	}

	private int getBiasNeuronsInLayer(int layer) {
		int ret = 0;
		for(int i=0; i<net.getLayerAt(layer).getNeuronsCount(); i++) {
			if (net.getLayerAt(layer).getNeuronAt(i) instanceof BiasNeuron) ret++;
		}
		return ret;
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
		return this;
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
		/* TODO
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
		/* TODO
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
		/* TODO
		int[] t = net.getDescriptor().getNeuronsPerLayer();
		if (layer >= t.length || layer < 0) return;
		t[layer]++;
		createNetwork(t);
		*/
	}

	@Override
	public void removeNeuron(int layer, boolean reset) {
		/* TODO
		int[] t = net.getDescriptor().getNeuronsPerLayer();
		if (layer >= t.length || layer < 0) return;
		if (t[layer] < 2) return;
		t[layer]--;
		createNetwork(t);
		*/
	}

	@Override
	public void setBehavior(int i) {
		/* TODO
		if (i < 0 || i >= behaviors.length) return;
		if (i == behavior) return;
		behavior = i;
		createNetwork();
		*/
	}

	@Override
	public String[] getBehaviorDescriptions() {
		return new String[1]; //behaviorDescriptions; TODO
	}

	@Override
	public int getBehavior() {
		return 0; //behavior; TODO
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

