package main;

import java.util.Arrays;

import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;
import com.dkriesel.snipe.training.TrainingSampleLesson;

public class SENetwork {

	private NeuralNetwork net;
	private TrainingSampleLesson samples;
	
	public SENetwork() {
		net = createNetwork();
	}
	
	public NeuralNetwork getNetwork() {
		return net;
	}
	
	private void createSamples(double x, double y, double value) {
		/*
		double[][] in = {{0.5, -0.5},
				         {-0.7, 0.3},
				         {-1, 1},
				         {-1, -1},
				         {1, 1},
				         {1, -1},
		                 {0.1, 0.1}};
		double[][] teach = {{1},
				            {1},
				            {1},
				            {1},
				            {1},
				            {1},
		                    {-1}};
*/
		double[][] in = {{x, y}};
		double[][] teach = {{value}};
		TrainingSampleLesson lesson = new TrainingSampleLesson(in, teach);
		setLesson(lesson);
	}
	
	public TrainingSampleLesson getLesson() {
		return samples;
	}
	
	private NeuralNetwork createNetwork() {
		System.out.println("SNIPE Experiment");

		int[] layers = {2,4,4, 1};
		NeuralNetworkDescriptor desc = new NeuralNetworkDescriptor(layers);
		desc.setInitializeAllowedSynapses(false);
		desc.setSynapseInitialRange(0.1);
		desc.setSettingsTopologyFeedForward();
		NeuralNetwork net = desc.createNeuralNetwork();
		desc.setFrequency(0); //layers.length - 1);
		
		net.createSynapsesFromLayerToLayer(0, 1);
		net.createSynapsesFromLayerToLayer(1, 2);
		net.createSynapsesFromLayerToLayer(2, 3);
		//net.createSynapsesFromLayerToLayer(3, 4);
		//net.createSynapsesFromLayerToLayer(4, 5);
 
		return net;
	}

	public void addSample(double x, double y, double value) {
		System.out.println("Adding data: "+x+", "+y+" -> "+value);
		
		if(samples == null) {
			createSamples(x, y, value);
			return;
		}
		
		double[][] in = samples.getInputs();
		double[][] teach = samples.getDesiredOutputs();
		
		double[][] nin = new double[in.length + 1][2];
		double[][] nteach = new double[in.length + 1][1];
		
		for(int n=0; n<in.length; n++) {
			nin[n] = in[n];
			nteach[n] = teach[n];
			
		}
		double[] ninl = {x, y};
		nin[in.length] = ninl;
		double[] ntl = {value};
		nteach[in.length] = ntl;
		
		setLesson(new TrainingSampleLesson(nin, nteach));		
	}

	public void setLesson(TrainingSampleLesson trainingSampleLesson) {
		samples = trainingSampleLesson;
		if(samples == null) return;
		
		//samples.optimizeDesiredOutputsForClassificationProblem(net);
		
		for(int i=0; i<samples.getDesiredOutputs().length; i++){
			//System.out.println(Arrays.toString(samples.getDesiredOutputs()[i]));
		}
		for(int i=0; i<samples.getInputs().length; i++){
			//System.out.println(Arrays.toString(samples.getInputs()[i]));
		}
		
	}

}



































// TODO xxx
