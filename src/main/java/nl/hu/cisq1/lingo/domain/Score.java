package nl.hu.cisq1.lingo.domain;

public class Score {
	private int points;
	private Game game;

	public void raisePoints(int value){
		this.points += value;
	}
}
