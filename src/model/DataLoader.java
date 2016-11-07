package model;

import java.io.File;
import main.ParamFile;

/**
 * Loads the application specific last used values from the temp file
 * 
 * @author tweber
 *
 */
public class DataLoader {

	private static final File FILE = new File(System.getProperty("user.home") + File.separator + "SE.tmp");
	
	private DataWrapper data;
	
	public DataLoader(DataWrapper data) {
		this.data = data;
	}
	
	/**
	 * Adds and defines the shutdown hook, which stores the parameters
	 * 
	 */
	public void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
	    		System.out.print("Shutting down, saving last used data...");
	    		try {
	    			ParamFile var = new ParamFile(FILE);

	    			var.set("data", data.getSerializable());
		    	
	    			var.store();
		    	
	    		} catch (Throwable e) {
	    			e.printStackTrace();
	    		}
		    	System.out.println("finished.");
		    }
		});
	}
	
	/**
	 * Load Parameters: Load the last opened path etc, stored in a temporary file in the user�s home directory.
	 * After loading, this also applies the parameters (if existent) to the program.
	 * 
	 */
	public void loadParams() {
		try {
			ParamFile vars = new ParamFile(FILE);

			data.setFromSerializable(vars.get("data"));

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
