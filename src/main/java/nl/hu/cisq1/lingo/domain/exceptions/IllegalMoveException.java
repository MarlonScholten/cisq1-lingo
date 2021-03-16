package nl.hu.cisq1.lingo.domain.exceptions;

public class IllegalMoveException extends RuntimeException{
	public IllegalMoveException(){
		super("Cannot attempt a guess when not playing");
	}
}
