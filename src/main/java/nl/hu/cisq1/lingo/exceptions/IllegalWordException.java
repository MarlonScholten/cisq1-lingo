package nl.hu.cisq1.lingo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO: geen response status voor domein exceptions, regel dit in je rest-controller
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="The word to guess cannot be empty or blank, something on our side went wrong")
public class IllegalWordException extends RuntimeException {
	public IllegalWordException(){
		super("The word to guess cannot be empty or blank");
	}
}
