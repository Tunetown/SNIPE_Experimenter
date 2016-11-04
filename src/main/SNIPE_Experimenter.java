package main;

import view.SEFrame;

import javax.swing.SwingUtilities;

import com.dkriesel.snipe.core.NeuralNetwork;
import com.dkriesel.snipe.core.NeuralNetworkDescriptor;
import com.dkriesel.snipe.training.TrainingSampleLesson;

public class SNIPE_Experimenter {

	public static void main(String[] args) {
		// Run application by initializing the main JFrame
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SENetwork net = new SENetwork();
				SEFrame appl = new SEFrame("SNIPE Playground", net);
				appl.init();
			}
		});	
	}
}
