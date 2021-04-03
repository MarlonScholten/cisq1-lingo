package nl.hu.cisq1.lingo.exceptions;

public class GameNotFoundException extends RuntimeException{
	public GameNotFoundException() {
		super("Game with that ID could not be found");
	}
}
