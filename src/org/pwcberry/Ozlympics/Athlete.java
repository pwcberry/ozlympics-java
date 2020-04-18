package org.pwcberry.Ozlympics;

/**
 * This interface encapsulates the athlete who can play any Game.
 */
public interface Athlete {

	/**
	 * Find the duration of the Athlete's efforts in the specified game.
	 * @param game the athlete is participating in.
	 * @return The time of the Athlete's performance.
	 */
	public int compete(Game game);

	/**
	 * Award the score to the athlete if they made a top-three placing.
	 * 
	 * @param score that was calculated by the game
	 */
	public void awardScore(int score);

	/**
	 * @return the sport played by the athlete
	 */
	public int getScore();
	
	/**
	 * @return the sport played by the athlete
	 */
	public Sport getSportPlayed();
}
