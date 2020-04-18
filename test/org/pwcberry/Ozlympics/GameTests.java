package org.pwcberry.Ozlympics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

	@Test
	public void testGenerateId() {
		// Arrange
		Game game1 = new Game(10, 20, Sport.RUNNING);
		Game game2 = new Game(10, 20, Sport.RUNNING);
		Game game3 = new Game(100, 200, Sport.SWIMMING);
		Game game4 = new Game(600, 800, Sport.CYCLING);

		// Act
		String id1 = game1.getId();
		String id2 = game2.getId();
		String id3 = game3.getId();
		String id4 = game4.getId();

		// Assert
		assertEquals("R04", id1);
		assertEquals("R05", id2);
		assertEquals("S06", id3);
		assertEquals("C07", id4);
	}

	@Test
	public void testReplaceOfficial() {
		// Arrange
		Game game1 = new Game(10, 20, Sport.RUNNING);
		Official official1 = new Official("Ralph Fiennes", 37, "WA");
		Official official2 = new Official("Ben Kingsley", 51, "VIC");

		// Act
		game1.appointOfficial(official1);
		game1.appointOfficial(official2);

		// Assert
		assertTrue(game1.getOfficial() == official2);
		assertTrue(official1.getCurrentGame() == null);
		assertTrue(official2.getCurrentGame() == game1);
	}

	@Test
	public void testGameIsFull() {
		// Arrange
		Game game1 = new Game(10, 20, Sport.RUNNING);

		// Act
		boolean exceptionWasThrown = false;

		try {
			for (int i = 1; i <= 7; i++) {
				Athlete athlete = new FakeAthlete(Sport.RUNNING);
				game1.addAthlete(athlete);
			}
		} catch (GameIsFullException e) {
			exceptionWasThrown = true;
		}

		// Assert
		assertTrue(exceptionWasThrown);
	}

	@Test
	public void testGameResults() {
		// Arrange
		Game game = new Game(10, 20, Sport.RUNNING);

		FakeRunner runner1 = new FakeRunner("Robert Carlyle", 19, "TAS", 11);
		FakeRunner runner2 = new FakeRunner("Ewen McGregor", 21, "VIC", 14);
		FakeRunner runner3 = new FakeRunner("Dennis Lillee", 19, "WA", 13);
		FakeRunner runner4 = new FakeRunner("David Boon", 21, "TAS", 12);

		Official official = new Official("Ralph Fiennes", 37, "WA");
		GameState gameState = GameState.NOT_STARTED;
		Game.GameResult[] results = null;

		// Act
		game.appointOfficial(official);

		try {
			game.addAthlete(runner1);
			game.addAthlete(runner2);
			game.addAthlete(runner3);
			game.addAthlete(runner4);
			game.play();

			gameState = game.getGameState();
			results = game.getResults();

		} catch (GameIsFullException e) {
			fail("GameIsFullException is not expected");
		}

		assertEquals(GameState.FINISHED, gameState);
		assertNotNull(results);
		assertEquals(4, results.length);
		assertEquals(runner1, results[0].getAthlete());
		assertEquals(runner4, results[1].getAthlete());
		assertEquals(runner3, results[2].getAthlete());
	}

	@Test
	public void testAthleteScores() {
		// Arrange
		Game game = new Game(10, 20, Sport.RUNNING);

		FakeRunner runner1 = new FakeRunner("Robert Carlyle", 19, "TAS", 11);
		FakeRunner runner2 = new FakeRunner("Ewen McGregor", 21, "VIC", 14);
		FakeRunner runner3 = new FakeRunner("Dennis Lillee", 19, "WA", 13);
		FakeRunner runner4 = new FakeRunner("David Boon", 21, "TAS", 12);

		Official official = new Official("Ralph Fiennes", 37, "WA");
		Game.GameResult[] results = null;

		// Act
		game.appointOfficial(official);

		try {
			game.addAthlete(runner1);
			game.addAthlete(runner2);
			game.addAthlete(runner3);
			game.addAthlete(runner4);
			game.play();

			results = game.getResults();

		} catch (GameIsFullException e) {
			fail();
		}

		assertEquals(5, results[0].getAthlete().getScore());
		assertEquals(2, results[1].getAthlete().getScore());
		assertEquals(1, results[2].getAthlete().getScore());
	}

	@Test
	public void testDeadHeatForFirst() {
		// Arrange
		Game game = new Game(10, 20, Sport.RUNNING);

		FakeRunner runner1 = new FakeRunner("Robert Carlyle", 19, "TAS", 11);
		FakeRunner runner2 = new FakeRunner("Ewen McGregor", 21, "VIC", 14);
		FakeRunner runner3 = new FakeRunner("Dennis Lillee", 19, "WA", 13);
		FakeRunner runner4 = new FakeRunner("David Boon", 21, "TAS", 11);

		Official official = new Official("Ralph Fiennes", 37, "WA");
		Game.GameResult[] results = null;

		// Act
		game.appointOfficial(official);

		try {
			game.addAthlete(runner1);
			game.addAthlete(runner2);
			game.addAthlete(runner3);
			game.addAthlete(runner4);
			game.play();

			results = game.getResults();

		} catch (GameIsFullException e) {
			fail();
		}

		assertEquals(3, results[0].getAthlete().getScore());
		assertEquals(3, results[1].getAthlete().getScore());
		assertEquals(1, results[2].getAthlete().getScore());

		assertEquals(runner1, results[0].getAthlete());
		assertEquals(runner4, results[1].getAthlete());
		assertEquals(runner3, results[2].getAthlete());
	}
	
	@Test
	public void testDeadHeatForSecond() {
		// Arrange
		Game game = new Game(10, 20, Sport.RUNNING);

		FakeRunner runner1 = new FakeRunner("Robert Carlyle", 19, "TAS", 11);
		FakeRunner runner2 = new FakeRunner("Ewen McGregor", 21, "VIC", 14);
		FakeRunner runner3 = new FakeRunner("Dennis Lillee", 19, "WA", 13);
		FakeRunner runner4 = new FakeRunner("David Boon", 21, "TAS", 13);

		Official official = new Official("Ralph Fiennes", 37, "WA");
		Game.GameResult[] results = null;

		// Act
		game.appointOfficial(official);

		try {
			game.addAthlete(runner1);
			game.addAthlete(runner2);
			game.addAthlete(runner3);
			game.addAthlete(runner4);
			game.play();

			results = game.getResults();

		} catch (GameIsFullException e) {
			fail();
		}
		
		assertEquals(5, results[0].getAthlete().getScore());
		assertEquals(1, results[1].getAthlete().getScore());
		assertEquals(1, results[2].getAthlete().getScore());

		assertEquals(runner1, results[0].getAthlete());
		assertEquals(runner3, results[1].getAthlete());
		assertEquals(runner4, results[2].getAthlete());
	}
}
