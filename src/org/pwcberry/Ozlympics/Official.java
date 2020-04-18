package org.pwcberry.Ozlympics;

/**
 * This class represents a Game referee or other official.
 */
public class Official extends Participant {

	// The source of the Official's unique ID.
	private static int idCounter = 0;

	// An official can only participate one game at a time.
	private Game currentGame;

	public Official(String name, int age, String state) {
		super(name, age, state);
	}

	@Override
	protected String generateId() {
		return "O" + formatter.format(++idCounter);
	}

	public void appointToGame(Game game) {
		currentGame = game;
	}

	public void removeFromGame(Game game) {
		if (currentGame == game) {
			currentGame = null;
		}
	}
	
	public Game getCurrentGame() {
		return currentGame;
	}
}
