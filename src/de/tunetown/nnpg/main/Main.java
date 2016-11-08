package de.tunetown.nnpg.main;

import java.io.File;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.tunetown.nnpg.model.DataLoader;
import de.tunetown.nnpg.model.DataWrapper;
import de.tunetown.nnpg.model.NetworkWrapper;
import de.tunetown.nnpg.model.TrainingTracker;
import de.tunetown.nnpg.model.snipe.SNIPEDataWrapper;
import de.tunetown.nnpg.model.snipe.SNIPENetworkWrapper;
import de.tunetown.nnpg.view.MainFrame;
import de.tunetown.nnpg.view.Menu;
import de.tunetown.nnpg.view.TrainingWorker;

/**
 * Application class for neural network experimenter
 * 
 *  
 * - TODO Optimize screen flickering and thread concept -> With sleep = 0, nothing works anymore!
 * 		- Concept: Use cloned instance for display rendering. BEWARE: No instance cloning during repaint! (synchronize)
 * 
 * - TODO Adaptive adding/removing of neurons
 * - TODO Adaptive eta determination
 * 
 * - TODO Concept for splitting training and test data (also have to be edited separately!)
 * 
 * - TODO Multi-dimensional visualization
 * 
 * - TODO Github documentation
 *  
 * 
 * @author Thomas Weber, 2016
 * @see www.tunetown.de
 *
 */
public class Main {

	private static final File TEMP_FILE = new File(System.getProperty("user.home") + File.separator + "SE.tmp");
	
	private NetworkWrapper net;
	private DataWrapper data;
	private TrainingTracker tracker;
	private DataLoader dataLoader;
	
	private MainFrame frame;
	private Menu menu;
	private TrainingWorker trainWorker;
	
	/**
	 * Initialize the application (called by main() method)
	 * 
	 */
	private void init() {
		// Create training data wrapper. Here it is possible to invoke also different network implementations.
		data = new SNIPEDataWrapper();
		dataLoader = new DataLoader(data);
		
		initNetwork();
		
		// Load data from temporary file and take care that it is being saved on exit
		
		dataLoader.loadFromFile(TEMP_FILE);
		dataLoader.addShutdownHook(TEMP_FILE);
		
		// Create application frame and menu.
		frame = new MainFrame(this);
		menu = new Menu(this, frame);
		
		menu.init();
		frame.init();
	}

	/**
	 * Returns the network wrapper
	 * 
	 * @return
	 */
	public NetworkWrapper getNetwork() {
		return net;
	}
	
	/**
	 * Returns the training data wrapper
	 * 
	 * @return
	 */
	public DataWrapper getData() {
		return data;
	}
	
	/**
	 * Returns the tracker instance
	 * 
	 * @return
	 */
	public TrainingTracker getTracker() {
		return tracker;
	}
	
	/**
	 * Returns the data loader
	 * 
	 * @return
	 */
	public DataLoader getDataLoader() {
		return dataLoader;
	}
	
	/**
	 * Update statistics on UI elements according to the network state
	 * 
	 */
	public void updateStats() {
		if (frame != null && frame.getControlPanel() != null) frame.getControlPanel().updateStats();
	}

	/**
	 * Tell the UI elements that training has been stopped
	 * 
	 */
	public void setTrainingStopped() {
		frame.getControlPanel().setTrainingStopped();
	}

	/**
	 * Set the selected tool for data editing
	 * 
	 * @param tool
	 */
	public void setDataTool(int tool) {
		frame.getDataPanel().setTool(tool);
	}
	
	/**
	 * Starts the training Thread
	 *  
	 */
	public void startTraining() {
		trainWorker = new TrainingWorker(this, frame);
		trainWorker.execute();		
	}
	
	/**
	 * Stops the training Thread
	 * 
	 */
	public void stopTraining() {
		if (trainWorker != null) {
			trainWorker.kill();
		}
	}
	
	/**
	 * Sets up the network
	 * 
	 */
	public void initNetwork() {
		double eta = 0;
		int batchSize = 0;
		if (net != null) {
			eta = net.getEta();
			batchSize = net.getBatchSize();
		}
		
		// Create network instance wrapper. Here it is possible to invoke also different network implementations.
		net = new SNIPENetworkWrapper();
		
		if (eta != 0) net.setEta(eta);
		if (batchSize != 0) net.setBatchSize(batchSize);
		
		// Create training tracker. This stores information about the learning process (errors, iteration counter etc.)
		tracker = new TrainingTracker();
		
		updateStats();
	}

	/**
	 * Main method
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Use the native menu bar on mac os x
			System.setProperty("apple.laf.useScreenMenuBar", "true"); //$NON-NLS-1$
		
			// Set native look and feel 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Throwable t) {
			t.printStackTrace();
		} 
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Main appl = new Main();
				appl.init();
			}
		});	
	}
}

