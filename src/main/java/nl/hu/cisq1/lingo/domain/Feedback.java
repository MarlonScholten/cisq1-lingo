package nl.hu.cisq1.lingo.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import nl.hu.cisq1.lingo.Utils;
import nl.hu.cisq1.lingo.exceptions.InvalidFeedbackException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Feedback {
	@NotEmpty
	@NotBlank
	private String attempt;
	private List<Mark> marks;
	private Round round;

	public static Feedback correct(@NotEmpty @NotBlank String attempt, List<Mark> marks, Round round) {
		if(attempt.length() != marks.size()){
			throw new InvalidFeedbackException();
		}
		return new Feedback(attempt,marks,round);
	}

	public boolean isWordGuessed() {
		return this.marks.stream().allMatch(str -> str.equals(Mark.CORRECT));
	}

	public boolean attemptIsValid(){
		return this.marks.stream().noneMatch(str -> str.equals(Mark.INVALID));
	}

	public static List<Mark> markAttempt(String attempt, String wordToGuess){
		List<Mark> markedAttempt = new ArrayList<>();
		List<Character> attemptChars = Utils.characterListOf(attempt);
		List<Character> wordChars = Utils.characterListOf(wordToGuess);

		for(int i=0;i<wordChars.size();i++){
			if( !(attempt.length() == wordToGuess.length()) ){
				for(char c: attempt.toCharArray()) {
					markedAttempt.add(Mark.INVALID);
				}
				return markedAttempt;
			}
			if( wordChars.contains(attemptChars.get(i)) ){
				markedAttempt.set(i, Mark.PRESENT);
			}
			if( !wordChars.contains(attemptChars.get(i)) ){
				markedAttempt.set(i, Mark.ABSENT);
			}
			if( attemptChars.get(i).equals(wordChars.get(i)) ){
				markedAttempt.set(i, Mark.CORRECT);
			}
		}
		return markedAttempt;
	}

//	// Previous hint + wordToGuess + marks = new hint
//	public List<Character> giveHint(List<Character> previousHint, String wordToGuess){
//		for(int i=0;i<this.marks.size();i++){
//
//		}
//		List<Character> newHint = Utils.characterListOf("input");
//		return newHint;
//	}
}
