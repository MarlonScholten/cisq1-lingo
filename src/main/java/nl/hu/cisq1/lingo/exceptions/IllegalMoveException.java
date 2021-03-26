package nl.hu.cisq1.lingo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO: geen response status voor domein exceptions, regel dit in je rest-controller
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="That move cannot be made right now")
public class IllegalMoveException extends RuntimeException{
	public IllegalMoveException(){
		super("That move cannot be made right now");
	}

	public IllegalMoveException(String message){
		super(message);
	}
}
