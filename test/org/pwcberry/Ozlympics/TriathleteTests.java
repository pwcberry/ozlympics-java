package org.pwcberry.Ozlympics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TriathleteTests {

	@Test
	public void addToGame() {
		// Arrange
		FakeTriathlete athlete = new FakeTriathlete();
		Game game = createGame(Sport.RUNNING);

		// Act
		boolean success = false;
		try {
			success = game.addAthlete(athlete);
		} catch (GameIsFullException ex) {
			fail("testAddToGame: GameIsFullException occurred");
		}
		// Assert
		assertTrue(success);
	}

	@Test
	public void competeAndResults() {
		// Arrange
		FakeTriathlete athlete = new FakeTriathlete();

		Game cyclingGame = createGame(Sport.CYCLING);
		addAthletesToGame(cyclingGame, athlete);

		Game runningGame = createGame(Sport.RUNNING);
		addAthletesToGame(runningGame, athlete);

		Game swimmingGame = createGame(Sport.SWIMMING);
		addAthletesToGame(swimmingGame, athlete);

		// Act
		athlete.setTime(647);
		cyclingGame.play();

		athlete.setTime(17);
		runningGame.play();

		athlete.setTime(101);
		swimmingGame.play();

		// Assert
		assertEquals(4, athlete.getScore());
	}

	private Game createGame(Sport sportPlayed) {
		switch (sportPlayed) {
		case SWIMMING:
			return new Game(100, 200, sportPlayed);
		case RUNNING:
			return new Game(10, 20, sportPlayed);
		case CYCLING:
			return new Game(600, 800, sportPlayed);
		}
		return null;
	}

	private void addAthletesToGame(Game game, FakeTriathlete objectUnderTest) {
		Sport sportPlayed = game.getSportPlayed();
		FakeAthlete[] athletes = new FakeAthlete[] { new FakeAthlete(sportPlayed), new FakeAthlete(sportPlayed),
				new FakeAthlete(sportPlayed), new FakeAthlete(sportPlayed) };

		int[] cyclingTimes = new int[] { 631, 680, 660, 643 };
		int[] runningTimes = new int[] { 11, 14, 13, 12 };
		int[] swimmingTimes = new int[] { 114, 123, 117, 140 };

		switch (sportPlayed) {
		case CYCLING:
			for (int i = 0; i < athletes.length; i++) {
				athletes[i].setTime(cyclingTimes[i]);
			}
			break;
		case RUNNING:
			for (int i = 0; i < runningTimes.length; i++) {
				athletes[i].setTime(runningTimes[i]);
			}
			break;
		case SWIMMING:
			for (int i = 0; i < athletes.length; i++) {
				athletes[i].setTime(swimmingTimes[i]);
			}
			break;
		}

		try {
			game.addAthlete(objectUnderTest);
			for (Athlete a : athletes) {
				game.addAthlete(a);
			}
		} catch (GameIsFullException ex) {
			fail("testAddToGame: GameIsFullException occurred");
		}

		game.appointOfficial(createOfficial());
	}

	private Official createOfficial() {
		return new Official("Ralph Fiennes", 37, "WA");
	}

}
