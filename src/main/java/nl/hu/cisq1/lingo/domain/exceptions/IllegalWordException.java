package nl.hu.cisq1.lingo.domain.exceptions;

public class IllegalWordException extends RuntimeException {
	public IllegalWordException(String errorMessage){
		super(errorMessage);
	}
}