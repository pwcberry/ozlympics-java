package org.pwcberry.Ozlympics;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * This class depicts the internal state of a game.
 */
public class Game {
	// Maximum number of games supported by the application.
	public static final int MAX_GAMES = 20;
	public static final int MAX_ATHLETES_PER_GAME = 6;
	public static final int MIN_ATHLETES_PER_GAME = 4;

	public static final int POINTS_FOR_WINNER = 5;
	public static final int POINTS_FOR_SECOND = 2;
	public static final int POINTS_FOR_THIRD = 1;
	public static final int POINTS_FOR_DEAD_HEAT_FIRST = 3;

	// A quick reference to format numbers for IDs of subclasses
	private static final DecimalFormat formatter = new DecimalFormat("00");

	private static int idCounter = 0;

	private int lowerTimeLimit;
	private int upperTimeLimit;
	private Sport sportPlayed;
	private GameState gameState;
	private Official official;
	private Athlete[] athletesPlaying;
	private GameResult[] results;
	private int athleteCount;
	private String gameId;

	/**
	 * Constructs a Game based on the time limits for the specified sport.
	 * 
	 * @param lowerTimeLimit
	 *            : the lower time limit allowed (fastest)
	 * @param upperTimeLimit
	 *            : the upper time limit allowed (slowest)
	 * @param sportPlayed
	 *            : the sport that defines the game
	 */
	public Game(int lowerTimeLimit, int upperTimeLimit, Sport sportPlayed) {
		this.lowerTimeLimit = lowerTimeLimit;
		this.upperTimeLimit = upperTimeLimit;
		this.sportPlayed = sportPlayed;

		// Generate the ID for the game.
		idCounter++;
		switch (sportPlayed) {
		case CYCLING:
			gameId = "C" + formatter.format(idCounter);
			break;
		case RUNNING:
			gameId = "R" + formatter.format(idCounter);
			break;
		case SWIMMING:
			gameId = "S" + formatter.format(idCounter);
			break;
		}

		gameState = GameState.NOT_STARTED;
		athletesPlaying = new Athlete[MAX_ATHLETES_PER_GAME];
		athleteCount = 0;
	}

	/**
	 * Add an athlete to the game.
	 * 
	 * There is a minimum of 4 and a maximum of 6 athletes per game.
	 * 
	 * @param athlete
	 *            : the athlete to add
	 * @return true if the athlete was added; false if the athlete does not play
	 *         that game.
	 * @throws GameIsFullException
	 *             when attempt is made to add more than 6 athletes.
	 */
	public boolean addAthlete(Athlete athlete) throws GameIsFullException {
		if (athleteCount == MAX_ATHLETES_PER_GAME) {
			throw new GameIsFullException(gameId);
		}

		// Athlete is added to game only if they play the sport
		// and if they have not already been added
		if (athlete.getSportPlayed() == Sport.TRIATHLON
						|| athlete.getSportPlayed() == sportPlayed) {
			boolean isAthleteRegistered = false;
			for (int i = 0; i < athleteCount; i++) {
				if (athletesPlaying[i] == athlete) {
					isAthleteRegistered = true;
					break;
				}
			}

			if (!isAthleteRegistered) {
				athletesPlaying[athleteCount++] = athlete;
				return true;
			}
		}

		return false;
	}

	/**
	 * Appoint an official to the Game.
	 * 
	 * If an official is already appointed, that official is replaced with the
	 * specified official.
	 */
	public void appointOfficial(Official official) {
		// Remove the official if one already exits so it can be replaced.
		if (this.official != null) {
			this.official.removeFromGame(this);
		}

		this.official = official;
		official.appointToGame(this);
	}

	/**
	 * Play the game.
	 * 
	 * If there are not enough athletes, or an official has not been appointed,
	 * cancel the game.
	 * 
	 * Otherwise, calculate the times for all athletes, sort the results and
	 * award scores to the top three places.
	 * 
	 */
	public void play() {
		if (athleteCount < MIN_ATHLETES_PER_GAME || official == null) {
			gameState = GameState.CANCELLED;
			return;
		}

		// results remains undefined until the game starts.
		results = new GameResult[athleteCount];

		for (int i = 0; i < athleteCount; i++) {
			// Using default constructor so assignment is explicit
			results[i] = new GameResult();
			results[i].athlete = athletesPlaying[i];
			results[i].time = athletesPlaying[i].compete(this);
		}

		gameState = GameState.FINISHED;

		Arrays.sort(results);
		results = Arrays.copyOf(results, athleteCount);

		awardScores();
	}

	/**
	 * @return the lower time limit expected of the game.
	 */
	public int getLowerTimeLimit() {
		return lowerTimeLimit;
	}

	/**
	 * @return the upper time limit expected of the game.
	 */
	public int getUpperTimeLimit() {
		return upperTimeLimit;
	}

	/**
	 * Get the top three competitors for the completed game.
	 * 
	 * @return an array of Athlete.
	 */
	public GameResult[] getResults() {
		return results;
	}

	/**
	 * @return the current state of the game (Not Started, Finished or
	 *         Cancelled).
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	/**
	 * @return the participating athletes.
	 */
	public Athlete[] getAthletes() {
		return Arrays.copyOf(athletesPlaying, athleteCount);
	}

	/**
	 * @return the appointed official.
	 */
	public Official getOfficial() {
		return official;
	}

	/**
	 * @return the ID of the game.
	 */
	public String getId() {
		return gameId;
	}

	/**
	 * @return the sport that is played in the game.
	 */
	public Sport getSportPlayed() {
		return sportPlayed;
	}

	/*
	 * Award points to 1st, 2nd and 3rd place.
	 */
	private void awardScores() {

		if (results[0].time == results[1].time) {
			// Dead heat for 1st Place
			results[0].athlete.awardScore(POINTS_FOR_DEAD_HEAT_FIRST);
			results[1].athlete.awardScore(POINTS_FOR_DEAD_HEAT_FIRST);
			results[2].athlete.awardScore(POINTS_FOR_THIRD);
		} else {
			// 1st Place
			if (results[0].time != results[1].time) {
				results[0].athlete.awardScore(POINTS_FOR_WINNER);
			}

			// 2nd Place
			if (results[1].time != results[2].time) {
				results[1].athlete.awardScore(POINTS_FOR_SECOND);
			} else {
				// Dead heat for 2nd & 3rd Place
				results[1].athlete.awardScore(POINTS_FOR_THIRD);
			}

			// 3rd Place
			results[2].athlete.awardScore(POINTS_FOR_THIRD);
		}
	}
	

	@Override
	public String toString() {
		return getId() + ": " + getSportPlayed();
	}

	/**
	 * This class provides a linkage between the Athlete and their time recorded
	 * playing the Game.
	 * 
	 * @author Peter Berry (3398328)
	 * 
	 */
	public class GameResult implements Comparable<GameResult> {

		private Athlete athlete;
		private int time;

		/**
		 * Compare two GameResults so the lower time precedes the other in a
		 * list.
		 */
		public int compareTo(GameResult otherResult) {
			return (this.time > otherResult.time) ? 1
							: (this.time < otherResult.time) ? -1 : 0;
		}

		/**
		 * @return the Athlete associated with the result.
		 */
		public Athlete getAthlete() {
			return athlete;
		}

		/**
		 * @return the time the Athlete competed in.
		 */
		public int getTime() {
			return time;
		}
	}
}
