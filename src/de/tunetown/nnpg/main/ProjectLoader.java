package de.tunetown.nnpg.main;

import java.io.File;

import de.tunetown.nnpg.model.DataContainer;

/**
 * Loads the application specific last used data from the temp file
 * 
 * @author tweber
 *
 */
public class ProjectLoader {

	private Main main;
	
	public ProjectLoader(Main main) {
		this.main = main;
	}
	
	/**
	 * Save data to a file
	 * 
	 * @param file
	 */
	public void saveToFile(File file) {
		try {
			ParamFile var = new ParamFile(file);

			var.set("data", main.getData().getCompleteDataContainer());
			var.set("network", main.getNetwork().getTopology());
			var.set("behavior", main.getNetwork().getBehavior());
			var.set("eta", main.getNetwork().getEta());
			var.set("batchsize", main.getNetwork().getBatchSize());
    	
			var.store();
    	
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load training data from a file
	 * 
	 */
	public void loadFromFile(File file) {
		try {
			ParamFile vars = new ParamFile(file);

			main.getData().initialize();
			main.getData().setFromCompleteDataContainer((DataContainer)vars.get("data"));
			
			if (vars.get("network") != null) 
				main.getNetwork().createNetwork((int[])vars.get("network")); 
			
			main.getNetwork().setEta((Double)(vars.get("eta")));
			main.getNetwork().setBatchSize((Integer)(vars.get("batchsize")));
			main.getNetwork().setBehavior((Integer)(vars.get("behavior")));
			
		} catch (Throwable e) {
			System.out.println("Error loading project file:");
			e.printStackTrace();
		}
	}

	/**
	 * Adds and defines the shutdown hook, which stores the data after exiting.
	 * 
	 */
	public void addShutdownHook(File file) {
		final File tempFileWrapper = file;
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
	    		System.out.print("Shutting down, saving last used data...");
	    		saveToFile(tempFileWrapper);
		    	System.out.println("finished.");
		    }
		});
	}
}
