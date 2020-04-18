package org.pwcberry.Ozlympics;

/**
 * The Triathlete class encapsulates the behaviour for an Athlete in all three
 * sports: Cycling, Running and Swimming.
 */
public class Triathlete extends SpecialistAthlete {

	// Keep track of scores awarded for the three events.
	// The triathlete may not have a score for every game
	// they compete in; thus the separate counter for scores.
	private final int[] scores;
	private int scoreCounter;
	private int gameCounter;

	public Triathlete(String name, int age, String state) {
		super(name, age, state, Sport.TRIATHLON);

		// A triathlete can only compete in three games.
		scores = new int[3];
	}

	@Override
	public int compete(Game game) {
		if (gameCounter < 3) {
			// Athlete class contains logic to produce a time for the given game
			gameCounter++;
			return super.compete(game);
		}

		// So the athlete doesn't appear in the results
		return Integer.MAX_VALUE;
	}

	/**
	 * Award the score for a top-three placing to the triathlete.
	 */
	@Override
	public void awardScore(int score) {
		if (gameCounter <= 3) {
			scores[scoreCounter++] = score;
		}
	}

	@Override
	public int getScore() {
		// Total the points the triathlete was awarded for making to the top 3.
		// If in any games they did not make it, deduct 2 points.
		int total = 0;
		for (int score : scores) {
			total += score;
		}
		
		// Deduction is calculated by 2 points per game the triathlete failed
		// to reach a top-three placing
		total -= (gameCounter - scoreCounter) * 2;
		
		return total;
	}
}
