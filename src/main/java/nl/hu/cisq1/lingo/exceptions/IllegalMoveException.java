package nl.hu.cisq1.lingo.exceptions;

public class IllegalMoveException extends RuntimeException{
	public IllegalMoveException(){
		super("Cannot attempt a guess when not playing");
	}
}
