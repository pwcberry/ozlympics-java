package org.pwcberry.Ozlympics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AthleteTests  {

	@Test
	public void objectToString() {
		// Arrange
		SpecialistAthlete runner = new SpecialistAthlete("Robert Carlyle", 19, "TAS", Sport.RUNNING);

		// Act
		String description = runner.toString();

		// Assert
		assertEquals("Robert Carlyle, 19, TAS", description.substring(6));
	}

	@Test
	public void compete() {
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
