package nl.hu.cisq1.lingo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Game with that ID could not be found")
public class GameNotFoundException extends RuntimeException{
	public GameNotFoundException() {
		super("Game with that ID could not be found");
	}
}
