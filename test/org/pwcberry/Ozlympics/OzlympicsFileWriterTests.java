package org.pwcberry.Ozlympics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OzlympicsFileWriterTests {

	@Test
	public void saveOlympicsManager() {
		OzlympicsFileWriter writer = new OzlympicsTextFileWriter();
		OzlympicsManager ozManager = createOzlympicsManager();

		writer.save("test_save.dat", ozManager);

		assertTrue(writer.getSuccess());
	}

	@Test
	public void athletesHaveSaved() {
		OzlympicsFileWriter writer = new OzlympicsTextFileWriter();
		OzlympicsManager ozManager = createOzlympicsManager();

		writer.save("test_save.dat", ozManager);

		assertTrue(writer.getSuccess());

		OzlympicsFileReader reader = new OzlympicsTextFileReader();
		OzlympicsManager loadedManager = reader.load("test_save.dat");
		assertTrue(reader.getSuccess());

		Athlete[] athletes = loadedManager.getAthletes();

		assertEquals(4, athletes.length);
		assertEquals(Sport.SWIMMING, athletes[0].getSportPlayed());
		assertEquals(Sport.TRIATHLON, athletes[3].getSportPlayed());
	}

	@Test
	public void officialsHaveSaved() {
		OzlympicsFileWriter writer = new OzlympicsTextFileWriter();
		OzlympicsManager ozManager = createOzlympicsManager();

		writer.save("test_save.dat", ozManager);

		assertTrue(writer.getSuccess());

		OzlympicsFileReader reader = new OzlympicsTextFileReader();
		OzlympicsManager loadedManager = reader.load("test_save.dat");
		assertTrue(reader.getSuccess());

		Official[] officials = loadedManager.getOfficials();
		assertEquals(2, officials.length);
		assertEquals("Ralph Fiennes", officials[0].getName());
		assertEquals("Ben Kingsley", officials[1].getName());
	}
	
	@Test
	public void gamesHaveSaved() {
		OzlympicsFileWriter writer = new OzlympicsTextFileWriter();
		OzlympicsManager ozManager = createOzlympicsManager();

		writer.save("test_save.dat", ozManager);

		assertTrue(writer.getSuccess());

		OzlympicsFileReader reader = new OzlympicsTextFileReader();
		OzlympicsManager loadedManager = reader.load("test_save.dat");
		assertTrue(reader.getSuccess());

		Game[] games = loadedManager.getGames();
		assertEquals(3, games.length);
		assertEquals(Sport.SWIMMING, games[0].getSportPlayed());
		assertEquals(Sport.RUNNING, games[1].getSportPlayed());
		assertEquals(Sport.CYCLING, games[2].getSportPlayed());
	}

	private OzlympicsManager createOzlympicsManager() {
		OzlympicsManager manager = new OzlympicsManager();

		try {
			Athlete swimmer = manager.addAthlete("Robert Carlyle", 19, "TAS",
					Sport.SWIMMING);

			Athlete cyclist = manager.addAthlete("Ewen McGregor", 21, "VIC",
					Sport.CYCLING);

			Athlete runner = manager.addAthlete("Dennis Lille", 20, "WA",
					Sport.RUNNING);

			Athlete triathlete = manager.addAthlete("Helena Bonham Carter", 28,
					"NSW", Sport.TRIATHLON);

			Official official1 = manager.addOfficial("Ralph Fiennes", 37, "WA");
			Official official2 = manager.addOfficial("Ben Kingsley", 51, "VIC");

			try {
				Game swim = manager.addGame(Sport.SWIMMING);
				swim.addAthlete(swimmer);
				swim.addAthlete(triathlete);
				swim.appointOfficial(official1);

				Game run = manager.addGame(Sport.RUNNING);
				run.addAthlete(runner);
				run.addAthlete(triathlete);
				swim.appointOfficial(official1);

				Game cycle = manager.addGame(Sport.CYCLING);
				cycle.addAthlete(cyclist);
				cycle.addAthlete(triathlete);
				swim.appointOfficial(official2);

			} catch (GameIsFullException gameEx) {
				fail("GameIsFullException not expected");
			}
		} catch (OzlympicsIsFullException ex) {
			fail("OzlympicsIsFullException not expected");
		}
		return manager;
	}

}
