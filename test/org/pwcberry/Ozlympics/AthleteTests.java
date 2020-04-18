package org.pwcberry.Ozlympics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AthleteTests  {

	@Test
	public void testGenerateId() {
		// Arrange
		SpecialistAthlete runner1 = new SpecialistAthlete("Robert Carlyle", 19, "TAS", Sport.RUNNING);
		SpecialistAthlete runner2 = new SpecialistAthlete("Ewen McGregor", 21, "VIC", Sport.SWIMMING);

		// Act
		String id1 = runner1.getId();
		String id2 = runner2.getId();

		// Assert
		assertEquals("A001", id1);
		assertEquals("A002", id2);
	}

	@Test
	public void testToString() {
		// Arrange
		SpecialistAthlete runner = new SpecialistAthlete("Robert Carlyle", 19, "TAS", Sport.RUNNING);

		// Act
		String fullId = runner.toString();

		// Assert
		assertEquals("A003: Robert Carlyle, 19, TAS", fullId);
	}

	@Test
	public void testCompete() {
		// Arrange
		int lowerTimeLimit = 10;
		int upperTimeLimit = 20;

		SpecialistAthlete runner = new SpecialistAthlete("Robert Carlyle", 19, "TAS", Sport.RUNNING);
		Game game = new Game(lowerTimeLimit, upperTimeLimit, Sport.RUNNING);

		// Act
		int time = runner.compete(game);

		// Assert
		assertTrue(time >= lowerTimeLimit);
		assertTrue(time <= upperTimeLimit);
	}

}
