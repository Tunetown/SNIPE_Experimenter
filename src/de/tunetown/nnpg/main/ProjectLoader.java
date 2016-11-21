package de.tunetown.nnpg.main;

import java.io.File;

import de.tunetown.nnpg.model.DataModel;
import de.tunetown.nnpg.model.NetworkWrapper;

/**
 * Loads the application specific last used data from the temp file
 * 
 * @author Thomas Weber
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

			var.set("data", main.getData());
			var.set("network", main.getNetwork());
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
		System.out.println("Loading network project from " + file);
		ParamFile vars = null;
		try {
			vars = new ParamFile(file);
			vars.load();

		} catch (Throwable e) {
			System.out.println("Error loading project file:");
			e.printStackTrace();
			return;
		}

		try {
			main.setData((DataModel)vars.get("data"));
			
		} catch (Throwable e) {
			System.out.println("Error loading data container");
		}

		try {
			if (vars.get("network") != null) 
				main.setNetwork((NetworkWrapper)vars.get("network"));
			
		} catch (Throwable e) {
			System.out.println("Error loading network instance");
		}

		try {
			main.getNetwork().setEta((Double)(vars.get("eta")));
			
		} catch (Throwable e) {
			System.out.println("Error loading eta");
		}

		try {
			main.getNetwork().setBatchSize((Integer)(vars.get("batchsize")));
			
		} catch (Throwable e) {
			System.out.println("Error loading batch size");
		}

		try {
			main.getNetwork().setBehavior((Integer)(vars.get("behavior")));
			
		} catch (Throwable e) {
			System.out.println("Error loading activation function");
		}

		main.updateView(true, true, true);
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
