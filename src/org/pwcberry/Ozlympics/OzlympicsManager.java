package org.pwcberry.Ozlympics;

import java.util.*;
import java.util.regex.*;

/**
 * This class stores all Officials, Athletes and Games in Ozlympics 2012. This
 * class provides an interface to create Official, Athlete and Game objects.
 * 
 * This class also retrieves information about Athletes and Games.
 */
public class OzlympicsManager {
	// These are the lower and upper time bounds for all the sports played in
	// games.
	private final SportTimespan CYCLING_TIMESPAN = new SportTimespan(600, 800);
	private final SportTimespan RUNNING_TIMESPAN = new SportTimespan(10, 20);
	private final SportTimespan SWIMMING_TIMESPAN = new SportTimespan(100, 200);

	// Prepare the entity storage
	private final List<Athlete> athletes = new ArrayList<Athlete>();
	private final List<Official> officials = new ArrayList<Official>();
	private final List<Game> games = new ArrayList<Game>();

	// Don't need to declare a constructor; using default constructor.
	// All instance variables have been initialised using syntax above.

	/**
	 * Add an athlete to Ozlympics with the specified name, age and state and
	 * sport.
	 * 
	 * If TRIATHALON is specified as the sport, a Triathlete object is created.
	 * 
	 * @return the newly created object that implements Athlete.
	 * 
	 * @throws OzlympicsIsFullException
	 *             when the maximum number of allowed participants is reached.
	 */
	public Athlete addAthlete(String name, int age, String state,
			Sport sportPlayed) throws OzlympicsIsFullException {

		if ((athletes.size() + officials.size()) == Participant.MAXIMUM_PARTICIPANTS) {
			throw new OzlympicsIsFullException();
		}

		Athlete newAthlete = null;

		if (sportPlayed != Sport.TRIATHLON) {
			newAthlete = new SpecialistAthlete(name, age, state, sportPlayed);
		} else {
			newAthlete = new Triathlete(name, age, state);
		}

		athletes.add(newAthlete);

		return newAthlete;
	}

	/**
	 * Add an official to Ozlympics with the specified name, age and state.
	 * 
	 * @return the newly created Official.
	 * 
	 * @throws OzlympicsIsFullException
	 *             when the maximum number of allowed participants is reached.
	 */
	public Official addOfficial(String name, int age, String state)
			throws OzlympicsIsFullException {

		if ((athletes.size() + officials.size()) == Participant.MAXIMUM_PARTICIPANTS) {
			throw new OzlympicsIsFullException();
		}

		Official newOfficial = new Official(name, age, state);

		officials.add(newOfficial);

		return newOfficial;
	}

	/**
	 * Add a game to Ozlympics of the specified sport.
	 * 
	 * @return the newly created Game.
	 * 
	 * @throws OzlympicsIsFullException
	 *             when the maximum number of allowed games is reached.
	 */
	public Game addGame(Sport sport) throws OzlympicsIsFullException {

		if (games.size() == Game.MAX_GAMES) {
			throw new OzlympicsIsFullException(
					"Ozlympics has reached the maximum number of games.");
		}

		Game newGame = null;
		switch (sport) {
		case CYCLING:
			newGame = new Game(CYCLING_TIMESPAN.lowerLimit,
					CYCLING_TIMESPAN.upperLimit, sport);
			break;
		case RUNNING:
			newGame = new Game(RUNNING_TIMESPAN.lowerLimit,
					RUNNING_TIMESPAN.upperLimit, sport);
			break;
		case SWIMMING:
			newGame = new Game(SWIMMING_TIMESPAN.lowerLimit,
					SWIMMING_TIMESPAN.upperLimit, sport);
			break;
		}

		games.add(newGame);

		return newGame;
	}

	/**
	 * @return a copy of the athletes added to Ozlympics.
	 */
	public Athlete[] getAthletes() {
		return athletes.toArray(new Athlete[athletes.size()]);
	}

	/**
	 * @return a copy of the games added to Ozlympics.
	 */
	public Game[] getGames() {
		return games.toArray(new Game[games.size()]);
	}

	/**
	 * @return a copy of the officials added to Ozlympics.
	 */
	public Official[] getOfficials() {
		return officials.toArray(new Official[officials.size()]);
	}

	/**
	 * Find an Athlete with the specified ID. Returns NULL if not found.
	 */
	public Athlete findAthlete(String id) {
		Athlete result = null;
		for (Athlete item : athletes) {
			if (((Participant) item).getId().equalsIgnoreCase(id)) {
				result = item;
				break;
			}
		}
		return result;
	}

	/**
	 * Find an Official with the specified ID. Returns NULL if not found.
	 */
	public Official findOfficial(String id) {
		Official result = null;
		for (Official item : officials) {
			if (item.getId().equalsIgnoreCase(id)) {
				result = item;
				break;
			}
		}
		return result;
	}

	/**
	 * Find an Official with the specified ID. Returns NULL if not found.
	 */
	public Game findGame(String id) {
		Game result = null;
		for (Game item : games) {
			if (item.getId().equalsIgnoreCase(id)) {
				result = item;
				break;
			}
		}

		return result;
	}

	/**
	 * A helper method to confirm the specified name is a recognized sport.
	 * 
	 * @param name
	 *            the string representing the sport.
	 */
	public static boolean isSportValid(String name) {
		boolean result = true;

		try {
			name = name.toUpperCase();
			Sport course = Sport.valueOf(name);
			result = course != Sport.NONE;
		} catch (IllegalArgumentException ex) {
			result = false;
		}

		return result;
	}

	/**
	 * A helper method to confirm if the specified age is valid.
	 * 
	 * @param value
	 *            the string representing a numeric value.
	 */
	public static boolean isAgeValid(String value) {
		boolean result = true;

		try {
			Integer age = Integer.parseInt(value);
			result = age >= 18 && age <= 60;
		} catch (NumberFormatException ex) {
			result = false;
		}

		return result;
	}

	/**
	 * A helper method to confirm if the specified state is valid.
	 * 
	 * @param state
	 *            the string containing the abbreviated form.
	 */
	public static boolean isStateValid(String name) {
		boolean result = true;

		try {
			name = name.toUpperCase();

			// If the method doesn't throw an exception, the text is valid.
			// It's not necessary to retain the enumeration value.
			AustralianState.valueOf(name);
		} catch (IllegalArgumentException ex) {
			result = false;
		}

		return result;
	}

	/**
	 * A helper method to confirm if the specified state is valid.
	 * 
	 * @param state
	 *            the string containing the abbreviated form.
	 */
	public static boolean isIdValid(String id, String className) {
		boolean result = false;

		if (id != null) {
			id = id.trim();

			if (className.equalsIgnoreCase("Game")) {
				// Pattern gamePattern = Pattern.compile("^G\\d{3}$");
				result = Pattern.matches("^[CSR]\\d{2}$", id);
			} else if (className.equalsIgnoreCase("Official")) {
				result = Pattern.matches("^O\\d{3}$", id);
			} else {
				result = Pattern.matches("^A\\d{3}$", id);
			}
		}

		return result;
	}

	/**
	 * A helper class to provide lower and upper time limits for each sport.
	 * 
	 * @author Peter Berry (3398328)
	 * 
	 */
	private class SportTimespan {
		private int lowerLimit;
		private int upperLimit;

		public SportTimespan(int lowerLimit, int upperLimit) {
			this.lowerLimit = lowerLimit;
			this.upperLimit = upperLimit;
		}
	}
}
