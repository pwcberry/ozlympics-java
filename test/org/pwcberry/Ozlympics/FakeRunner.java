package org.pwcberry.Ozlympics;

public class FakeRunner extends SpecialistAthlete {
	private int time_;
	
	public FakeRunner(String name, int age, String state, int time) {
		super(name, age, state, Sport.RUNNING);
		time_ = time;
	}

	@Override
	public int compete(Game game) {
		return time_;
	}
}
