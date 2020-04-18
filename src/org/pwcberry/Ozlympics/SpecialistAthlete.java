package org.pwcberry.Ozlympics;

import java.util.Random;

/**
 * This class encapsulates the athlete who can play any Game.
 */
public class SpecialistAthlete extends Participant implements Athlete {

	private static int idCounter = 0;

	private int score;
	private Sport sportPlayed;

	/**
	 * Constructs an Athlete object with the specified name, age, representative state, and sport.
	 */
	public SpecialistAthlete(String name, int age, String state, Sport sportPlayed) {
		super(name, age, state);

		this.sportPlayed = sportPlayed;
	}

	/**
	 * Find the duration of the Athlete's efforts in the specified game.
	 * @param game the athlete is participating in.
	 * @return The time of the Athlete's performance.
	 */
	public int compete(Game game) {
		Random timeGenerator = new Random();

		int lowerLimit = game.getLowerTimeLimit();
		int upperLimit = game.getUpperTimeLimit();

		return lowerLimit + timeGenerator.nextInt(upperLimit - lowerLimit);
	}

	/**
	 * @return the score the athlete achieved for their place in the game
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Award the score to the athlete if they made a top-three placing.
	 * 
	 * @param score that was calculated by the game
	 */
	public void awardScore(int score) {
		this.score += score;
	}

	/**
	 * @return the sport played by the athlete
	 */
	public Sport getSportPlayed() {
		return sportPlayed;
	}

	/**
	 * @return the ID for this athlete.
	 */
	@Override
	protected String generateId() {
		return "A" + formatter.format(++idCounter);
	}
}
