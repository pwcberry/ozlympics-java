package org.pwcberry.Ozlympics;

/**
 * This interface provides the contract required to save the data
 * associated with an OzlympicsManager object. 
 * 
 * The location and type of storage is dependent on the implementation.
 */
public interface OzlympicsFileWriter {
	
	/**
	 * Saves the specified OzlympicsManager to a given location.
	 */
	void save(String location, OzlympicsManager manager);
	
	/**
	 * A flag that indicates the load or save was successful.
	 */
	boolean getSuccess();
}

