package nl.hu.cisq1.lingo.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.hu.cisq1.lingo.domain.exceptions.IllegalMoveException;
import nl.hu.cisq1.lingo.domain.exceptions.IllegalWordException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Round implements Serializable {
	@Getter
	private String wordToGuess;
	private final int maxAttempts = 5;
	private boolean wordWasGuessed;
	@Getter
	private List<Character> currentHint;
	private List<Feedback> givenFeedback;
	private State state;

	public static Round newRound(String wordToGuess){
		if( (wordToGuess.trim().length()>0) ){
			return new Round(wordToGuess, false,initialHintFor(wordToGuess), new ArrayList<>(), State.PLAYING);
		}
		else throw new IllegalWordException("The word to guess cannot be empty or blank");
	}

	private static List<Character> initialHintFor(String input){
		List<Character> initialHint = new ArrayList<>();
		initialHint.add(input.charAt(0));
		for(int i=1;i<input.length();i++){
			initialHint.add('.');
		}
		return initialHint;
	}

	//TODO: tests schrijven
	public void doAttempt(String attempt){
		if(state.equals(State.PLAYING)){
			Feedback feedback = Feedback.newFeedback(attempt,this);
			givenFeedback.add(feedback);
			currentHint = feedback.giveHint(currentHint,wordToGuess);
			this.wordWasGuessed = feedback.isWordGuessed();
			updateState();
		}
		throw new IllegalMoveException("Cannot attempt a guess when not playing");
	}

	//TODO: tests schrijven
	public void updateState(){
		if(wordWasGuessed){
			state=State.WON;
		}
		if(!wordWasGuessed && givenFeedback.size()==maxAttempts) state=State.LOST;
	}
}
