package de.tunetown.nnpg.model;

import de.tunetown.nnpg.model.neuroph.NeurophNetworkWrapper;
import de.tunetown.nnpg.model.snipe.SNIPENetworkWrapper;

/**
 * This class manages and delivers the available network engines.
 * 
 * @author Thomas Weber
 *
 */
public class NetworkManager {

	private NetworkWrapper[] engines = {
			new SNIPENetworkWrapper(),
			new NeurophNetworkWrapper()
	};
	
	/**
	 * Returns an instance of a given engine
	 * 
	 * @param num index of the engine
	 * @return
	 */
	public NetworkWrapper getEngineInstance(int num) {
		return engines[num].clone();
	}
	
	/**
	 * Determines the engine by a given network wrapper instance, or -1 if
	 * the instance is not known.
	 * 
	 * @param n
	 * @return
	 */
	public int determineEngine(NetworkWrapper n) {
		for(int i=0; i<engines.length; i++) {
			if (engines[i].getClass().equals(n.getClass())) return i;
		}
		return -1;
	}

	public int getNumOfEngines() {
		return engines.length;
	}

	public String getEngineName(int i) {
		return engines[i].getEngineName();
	}
}
