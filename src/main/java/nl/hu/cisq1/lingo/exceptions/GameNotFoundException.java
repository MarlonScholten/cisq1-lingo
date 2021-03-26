package nl.hu.cisq1.lingo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GameNotFoundException extends RuntimeException{
	public GameNotFoundException() {
		super("Game with that ID could not be found");
	}
}
