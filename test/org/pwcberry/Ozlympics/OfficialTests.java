package org.pwcberry.Ozlympics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OfficialTests {

	@Test
	public void appointToGame() {
		// Arrange
		Official official = new Official("Ralph Fiennes", 37, "WA");
		Game game = new Game(10, 10, Sport.RUNNING);

		// Act
		game.appointOfficial(official);
		boolean officialAppointed = official.getCurrentGame() == game;

		// Assert
		assertTrue(officialAppointed);
	}

	@Test
	public void removeFromGame() {
		// Arrange
		Official official1 = new Official("Ralph Fiennes", 37, "WA");
		Official official2 = new Official("Ben Kingsley", 51, "VIC");
		
		Game game = new Game(10, 10, Sport.RUNNING);

		// Act
		game.appointOfficial(official1);
		
		// Business rule: New official replaces appointed official.
		game.appointOfficial(official2);
		
		boolean officialIsRemoved = official1.getCurrentGame() == null;

		// Assert
		assertTrue(officialIsRemoved);
	}

	@Test
	public void objectToString() {
		// Arrange
		Official official = new Official("Ralph Fiennes", 37, "WA");

		// Act
		String description = official.toString();

		// Assert
		assertEquals("Ralph Fiennes, 37, WA", description.substring(6));
	}

}
