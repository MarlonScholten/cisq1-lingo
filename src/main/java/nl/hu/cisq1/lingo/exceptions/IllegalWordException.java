package nl.hu.cisq1.lingo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class IllegalWordException extends RuntimeException {
	public IllegalWordException(){
		super("The word to guess cannot be empty or blank");
	}
}
