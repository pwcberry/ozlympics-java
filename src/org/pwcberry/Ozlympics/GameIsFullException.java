package org.pwcberry.Ozlympics;

/**
 * Thrown by a Game class when it has reached its maximum number of Athletes.
 */

@SuppressWarnings("serial")
public class GameIsFullException extends Exception {
	
	/**
	 * Constructs a GameIsFullException with the Game ID 
	 * that threw the exception.
	 */
	public GameIsFullException(String id) {
		super("The Game with id: '" + id + "' is full.");
	}
	
}
