package org.pwcberry.Ozlympics;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OzlympicsFileReaderTests {

	@Test
	public void testLoad() {
		OzlympicsFileReader fileReader = new OzlympicsTextFileReader();
		fileReader.load("save.dat");
		assertTrue(fileReader.getSuccess());
	}

	@Test
	public void testAthleteCount() {
		OzlympicsFileReader fileReader = new OzlympicsTextFileReader();
		OzlympicsManager ozManager = fileReader.load("save.dat");
		assertTrue(fileReader.getSuccess());

		Athlete[] athletes = ozManager.getAthletes();
		assertEquals(26, athletes.length);
	}

	@Test
	public void testForAthleteData() {
		OzlympicsFileReader fileReader = new OzlympicsTextFileReader();
		OzlympicsManager ozManager = fileReader.load("save.dat");
		assertTrue(fileReader.getSuccess());

		try {
			Athlete[] athletes = ozManager.getAthletes();
			SpecialistAthlete crockford = (SpecialistAthlete) athletes[0];
			SpecialistAthlete sussman = (SpecialistAthlete) athletes[25];

			assertEquals("Doug Crockford", crockford.getName());
			assertEquals(Sport.CYCLING, crockford.getSportPlayed());
			assertEquals("VIC", crockford.getState());
			assertEquals(22, crockford.getAge());

			assertEquals("Julie Sussman", sussman.getName());
			assertEquals(Sport.SWIMMING, sussman.getSportPlayed());
			assertEquals("TAS", sussman.getState());
			assertEquals(25, sussman.getAge());

		} catch (ClassCastException ex) {
			fail("Unexpected cast error");
		}
	}

	@Test
	public void testOfficialCount() {
		OzlympicsFileReader fileReader = new OzlympicsTextFileReader();
		OzlympicsManager ozManager = fileReader.load("save.dat");
		assertTrue(fileReader.getSuccess());
		
		Official[] officials = ozManager.getOfficials();
		assertEquals(3, officials.length);
	}
	
	@Test
	public void testOfficialData() {
		OzlympicsFileReader fileReader = new OzlympicsTextFileReader();
		OzlympicsManager ozManager = fileReader.load("save.dat");
		assertTrue(fileReader.getSuccess());
		
		Official[] officials = ozManager.getOfficials();
		Official fiennes = officials[0];
		Official pearson = officials[2];

		assertEquals("Ralph Fiennes", fiennes.getName());
		assertEquals("WA", fiennes.getState());
		assertEquals(37, fiennes.getAge());

		assertEquals("Leonie Pearson", pearson.getName());
		assertEquals("SA", pearson.getState());
		assertEquals(41, pearson.getAge());
	}
	
	@Test
	public void testGameCount() {
		OzlympicsFileReader fileReader = new OzlympicsTextFileReader();
		OzlympicsManager ozManager = fileReader.load("save.dat");
		assertTrue(fileReader.getSuccess());
		
		Game[] games = ozManager.getGames();
		assertEquals(11, games.length);
	}
	
	@Test
	public void testGameOfficials() {
		OzlympicsFileReader fileReader = new OzlympicsTextFileReader();
		OzlympicsManager ozManager = fileReader.load("save.dat");
		assertTrue(fileReader.getSuccess());
		
		Game[] games = ozManager.getGames();
		Official[] officials = ozManager.getOfficials();
		Official fiennes = officials[0];
		Official goldstein = officials[1];
		Official pearson =officials[2];
		
		assertEquals(fiennes, games[0].getOfficial());
		assertEquals(goldstein, games[1].getOfficial());
		assertEquals(pearson, games[2].getOfficial());

		assertEquals(goldstein, games[7].getOfficial());
		assertEquals(pearson, games[8].getOfficial());
		assertEquals(fiennes, games[9].getOfficial());
	}
}
