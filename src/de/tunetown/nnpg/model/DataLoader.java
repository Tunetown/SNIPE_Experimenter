package de.tunetown.nnpg.model;

import java.io.File;

import de.tunetown.nnpg.main.ParamFile;

/**
 * Loads the application specific last used data from the temp file
 * 
 * @author tweber
 *
 */
public class DataLoader {

	private DataWrapper data;
	
	public DataLoader(DataWrapper data) {
		this.data = data;
	}
	
	/**
	 * Save data to a file
	 * 
	 * @param file
	 */
	public void saveToFile(File file) {
		try {
			ParamFile var = new ParamFile(file);

			var.set("data", data.getContainer());
    	
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

			data.initialize();
			data.setFromContainer((DataContainer)vars.get("data"));
			
		} catch (Throwable e) {
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
