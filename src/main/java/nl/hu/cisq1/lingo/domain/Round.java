package nl.hu.cisq1.lingo.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.hu.cisq1.lingo.domain.exceptions.IllegalWordException;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Round {
	@Getter
	private String wordToGuess;
	private int amountOfAttempts;
	private boolean wordWasGuessed;
	@Getter
	private List<Character> currentHint;
	private List<Feedback> givenFeedback;

	public static Round newRound(String wordToGuess){
		if( (wordToGuess.trim().length()>0) ){
			return new Round(wordToGuess, 0,false,initialHintFor(wordToGuess), new ArrayList<>());
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
}
