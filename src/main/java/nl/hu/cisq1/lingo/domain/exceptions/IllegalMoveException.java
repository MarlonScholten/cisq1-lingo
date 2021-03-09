package nl.hu.cisq1.lingo.domain.exceptions;

public class IllegalMoveException extends RuntimeException{
	public IllegalMoveException(String errorMessage){
		super(errorMessage);
	}
}
