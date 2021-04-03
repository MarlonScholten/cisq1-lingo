package nl.hu.cisq1.lingo.exceptions;

public class IllegalMoveException extends RuntimeException{
	public IllegalMoveException(){
		super("That move cannot be made right now");
	}

	public IllegalMoveException(String message){
		super(message);
	}
}
