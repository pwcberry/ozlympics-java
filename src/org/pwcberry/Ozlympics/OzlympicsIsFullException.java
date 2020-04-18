package org.pwcberry.Ozlympics;

/**
 * Thrown by the OzlympicsManager class when the number of 
 * Athletes and Officials have reached the maximum number
 * of participants allowed.
 */

@SuppressWarnings("serial")
public class OzlympicsIsFullException extends Exception {
	
	/**
	 * Default constructor.
	 */
	public OzlympicsIsFullException(){
		super("Ozlympics has reached the maximum number of participants.");
	}
	
	/**
	 * Construct an OzlympicsIsFullException with the specified message.
	 */
	public OzlympicsIsFullException(String message){
		super(message);
	}
}
