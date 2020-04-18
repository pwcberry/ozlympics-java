package org.pwcberry.Ozlympics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OfficialTests {

	@Test
	public void testGenerateId() {
		// Arrange
		Official official1 = new Official("Ralph Fiennes", 37, "WA");
		Official official2 = new Official("Ben Kingsley", 51, "VIC");

		// Act
		String id1 = official1.getId();
		String id2 = official2.getId();

		// Assert
		assertEquals("O001", id1);
		assertEquals("O002", id2);
	}

	@Test
	public void testAppointToGame() {
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
	public void testRemoveFromGame() {
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
	public void testToString() {
		// Arrange
		Official official = new Official("Ralph Fiennes", 37, "WA");

		// Act
		String fullId = official.toString();

		// Assert
		assertEquals("O003: Ralph Fiennes, 37, WA", fullId);
	}

}
