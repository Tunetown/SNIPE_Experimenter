package main;

import view.SEFrame;

import javax.swing.SwingUtilities;

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
