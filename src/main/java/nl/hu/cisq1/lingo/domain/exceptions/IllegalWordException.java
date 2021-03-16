package nl.hu.cisq1.lingo.domain.exceptions;

public class IllegalWordException extends RuntimeException {
	public IllegalWordException(){
		super("The word to guess cannot be empty or blank");
	}
}
