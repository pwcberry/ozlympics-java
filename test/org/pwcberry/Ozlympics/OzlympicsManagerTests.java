package org.pwcberry.Ozlympics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OzlympicsManagerTests {

	/*
	 * private OzlympicsManager manager_;
	 * 
	 * @Before public void setupManager() { manager_ = new OzlympicsManager(); }
	 */
	@Test
	public void addAthletes() {
		OzlympicsManager manager = new OzlympicsManager();

		try {
			// Arrange & act
			Athlete swimmer = manager.addAthlete("Robert Carlyle", 19, "TAS",
							Sport.SWIMMING);

			Athlete cyclist = manager.addAthlete("Ewen McGregor", 21, "VIC",
							Sport.CYCLING);

			Athlete runner = manager.addAthlete("Dennis Lille", 20, "WA",
							Sport.RUNNING);

			Athlete triathlete = manager.addAthlete("Helena Bonham Carter", 28,
							"NSW", Sport.TRIATHLON);

			Athlete[] athletesInOzlympics = manager.getAthletes();

			// Assert
			assertTrue(swimmer.getSportPlayed() == Sport.SWIMMING);
			assertTrue(cyclist.getSportPlayed() == Sport.CYCLING);
			assertTrue(runner.getSportPlayed() == Sport.RUNNING);
			assertTrue(triathlete instanceof Triathlete);
			assertEquals(4, athletesInOzlympics.length);

		} catch (OzlympicsIsFullException ex) {
			fail("OzlympicsIsFullException not expected");
		}
	}

	@Test
	public void addGames() {
		OzlympicsManager manager = new OzlympicsManager();

		try {
			Game swim = manager.addGame(Sport.SWIMMING);
			Game run = manager.addGame(Sport.RUNNING);
			Game cycle = manager.addGame(Sport.CYCLING);

			Game[] gamesInOzlympics = manager.getGames();

			assertEquals(3, gamesInOzlympics.length);
			assertEquals(10, run.getLowerTimeLimit());
			assertEquals(20, run.getUpperTimeLimit());
			assertEquals(Sport.RUNNING, run.getSportPlayed());

			assertEquals(100, swim.getLowerTimeLimit());
			assertEquals(200, swim.getUpperTimeLimit());
			assertEquals(Sport.SWIMMING, swim.getSportPlayed());

			assertEquals(600, cycle.getLowerTimeLimit());
			assertEquals(800, cycle.getUpperTimeLimit());
			assertEquals(Sport.CYCLING, cycle.getSportPlayed());

		} catch (OzlympicsIsFullException ex) {
			fail("OzlympicsIsFullException not expected");
		}
	}

	@Test
	public void isIdValid() {
		boolean validAthleteId1 = OzlympicsManager.isIdValid("A001", "Athlete");
		boolean validAthleteId2 = OzlympicsManager.isIdValid("A025", "Athlete");
		boolean validAthleteId3 = OzlympicsManager.isIdValid("A129", "Athlete");

		boolean validOfficialId1 = OzlympicsManager.isIdValid("O001",
						"Official");
		boolean validOfficialId2 = OzlympicsManager.isIdValid("O026",
						"Official");
		boolean validOfficialId3 = OzlympicsManager.isIdValid("O259",
						"Official");

		boolean validGameId1 = OzlympicsManager.isIdValid("R01", "Game");
		boolean validGameId2 = OzlympicsManager.isIdValid("S23", "Game");
		boolean validGameId3 = OzlympicsManager.isIdValid("C92", "Game");

		boolean invalidAthleteId1 = OzlympicsManager.isIdValid("A1", "Athlete");
		boolean invalidAthleteId2 = OzlympicsManager
						.isIdValid("A31", "Athlete");
		boolean invalidAthleteId3 = OzlympicsManager.isIdValid("O031",
						"Athlete");

		boolean invalidOfficialId1 = OzlympicsManager.isIdValid("O1",
						"Official");
		boolean invalidOfficialId2 = OzlympicsManager.isIdValid("O31",
						"Official");
		boolean invalidOfficialId3 = OzlympicsManager.isIdValid("A031",
						"Official");

		boolean invalidGameId1 = OzlympicsManager.isIdValid("G1", "Game");
		boolean invalidGameId2 = OzlympicsManager.isIdValid("O001", "Game");
		boolean invalidGameId3 = OzlympicsManager.isIdValid("A031", "Game");

		assertTrue(validAthleteId1);
		assertTrue(validAthleteId2);
		assertTrue(validAthleteId3);

		assertTrue(validOfficialId1);
		assertTrue(validOfficialId2);
		assertTrue(validOfficialId3);

		assertTrue(validGameId1);
		assertTrue(validGameId2);
		assertTrue(validGameId3);

		assertFalse(invalidAthleteId1);
		assertFalse(invalidAthleteId2);
		assertFalse(invalidAthleteId3);

		assertFalse(invalidOfficialId1);
		assertFalse(invalidOfficialId2);
		assertFalse(invalidOfficialId3);

		assertFalse(invalidGameId1);
		assertFalse(invalidGameId2);
		assertFalse(invalidGameId3);
	}

	@Test
	public void isStateValid() {
		String[] states = new String[] { "ACT", "Nsw", "nt", "qld", "SA",
						"Tas", "Vic", "wa" };
		String[] invalidStates = new String[] { "atc", "swn", "no", "as", "taz" };

		for (String test : states) {
			boolean result = OzlympicsManager.isStateValid(test);
			assertTrue(result);
		}

		for (String test : invalidStates) {
			boolean result = OzlympicsManager.isStateValid(test);
			assertFalse(result);
		}
	}
	
	@Test
	public void isSportValid() {
		String[] sports = new String[]{"Swimming","Cycling","Running","Triathlon"};
		String[] invalidSports = new String[]{"Swmmng","Cyclng","Triathalon","Equestrian","Runing"};
		
		for (String test : sports) {
			boolean result = OzlympicsManager.isSportValid(test);
			assertTrue(result);
		}

		for (String test : invalidSports) {
			boolean result = OzlympicsManager.isSportValid(test);
			assertFalse(result);
		}
	}
	
	@Test
	public void isAgeValid() {
		String[] ages = new String[]{"18","20","23","27","50","59","60"};
		String[] invalidAges = new String[]{"-1","0","17","61","as",""};
		
		for (String test : ages) {
			boolean result = OzlympicsManager.isAgeValid(test);
			assertTrue(result);
		}

		for (String test : invalidAges) {
			boolean result = OzlympicsManager.isAgeValid(test);
			assertFalse(result);
		}
	}
}
