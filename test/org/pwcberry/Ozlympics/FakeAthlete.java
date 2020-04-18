package org.pwcberry.Ozlympics;

public class FakeAthlete implements Athlete {

	private int time_;
	private int score_;
	private Sport sport_;

	public FakeAthlete(Sport sportPlayed) {
		sport_ = sportPlayed;
	}

	public void setTime(int time) {
		time_ = time;
	}

	public int compete(Game game) {
		return time_;
	}

	public void awardScore(int score) {
		score_ = score;
	}
	
	public int getScore() {
		return score_;
	}

	public Sport getSportPlayed() {
		return sport_;
	}
}
