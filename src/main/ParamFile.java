package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Parameter loader. Loads and stores (name, value) pairs from a file.
 * 
 * @author xwebert
 *
 */
public class ParamFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * List of parameters (generically typed)
	 */
	@SuppressWarnings("rawtypes")
	private List<Param> content = new ArrayList<Param>();      
	
	/**
	 * File to store(load the parameters
	 */
	private File paramFile;                                     
 
	public ParamFile(File file) throws Throwable {
		this.paramFile = file;
		load();
	}
	
	/**
	 * Returns the parameter file
	 * 
	 * @return
	 */
	public File getFile() {
		return paramFile;
	}
	
	/**
	 * Returns a parameter from memory
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object get(String name) throws Throwable {
		for (Param v : content) {
			if (v.getName().equals(name)) {
				return v.getValue();
			}
		}
		return null;
	}

	/**
	 * Set a value in memory
	 * 
	 * @param name
	 * @param value
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void set(String name, Serializable value) throws Throwable {
		for (Param v : content) {
			if (v.getName().equals(name)) {
				v.setValue(value);	
				return;
			}
		}
		
		// New parameter
		content.add(new Param<Object>(name, value));
	}
	
	/**
	 * Load all parameters from the file
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean load() throws Throwable {
		if (!paramFile.exists()) return false;
		
		FileInputStream fileIn = new FileInputStream(paramFile);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		
		content = (List<Param>) in.readObject();
		
		in.close();
		fileIn.close();
		return true;
	}

	/**
	 * Store all loaded parameters (replacing the whole temp file)
	 * 
	 */
	public void store() throws Throwable {
		FileOutputStream fileOut = new FileOutputStream(paramFile);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		
		out.writeObject(content);
		
		out.close();
		fileOut.close();
	}
	
	/**
	 * Print the loaded parameters on console output
	 *  
	 */
	@SuppressWarnings("rawtypes")
	public void print() throws Throwable {
		for(Param v : content) {
			System.out.println(v);
		}
	}
}
