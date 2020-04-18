package org.pwcberry.Ozlympics;

public class FakeTriathlete extends Triathlete {

	int time_;

	public FakeTriathlete() {
		super("Scott Hanselman", 24, "QLD");
	}

	public void setTime(int time) {
		time_ = time;
	}
	
	public int compete(Game game){
		super.compete(game);
		return time_;
	}
}
