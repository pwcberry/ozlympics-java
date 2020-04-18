package org.pwcberry.Ozlympics;

/**
 * This interface provides the contract required to save and load the data
 * associated with an OzlympicsManager object. 
 * 
 * The location and type of storage is dependent on the implementation.
 */
public interface OzlympicsFileReader {

	/**
	 * Load an Ozlympics Manager from a given location.
	 * @return An initialized OzlympicsManager.
	 */
	OzlympicsManager load(String location);
	
	/**
	 * A flag that indicates the load or save was successful.
	 */
	boolean getSuccess();
}
