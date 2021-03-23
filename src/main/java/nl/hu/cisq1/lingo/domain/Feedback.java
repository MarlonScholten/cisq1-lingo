package nl.hu.cisq1.lingo.domain;

import lombok.*;
import nl.hu.cisq1.lingo.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class Feedback implements Serializable {
	private String attempt;
	private List<Mark> marks;
	private Round round;

	public static Feedback newFeedback(String attempt, Round round) {
		List<Mark> marks = Feedback.markAttempt(attempt, round.getWordToGuess());

		return new Feedback(attempt,marks,round);
	}

	public boolean isWordGuessed() {
		return this.marks.stream().allMatch(str -> str.equals(Mark.CORRECT));
	}

	public boolean attemptIsValid(){
		return this.marks.stream().noneMatch(str -> str.equals(Mark.INVALID));
	}

	public static List<Mark> markAttempt(String attempt, String wordToGuess){
		// Set up the List as INVALID, so we can either replace the values by index
		// or return it if the lengths are not equal
		List<Mark> markedAttempt = new ArrayList<>();
		for(int m=0;m<wordToGuess.length();m++){
			markedAttempt.add(Mark.INVALID);
		}

		// Mark the correct characters, set a temporary absent mark for others
		if( (attempt.length() == wordToGuess.length()) ){
			List<Character> attemptChars = Utils.characterListOf(attempt);
			List<Character> wordChars = Utils.characterListOf(wordToGuess);
			// Contains the characters of the word to guess that were not guessed correctly
			List<Character> absentChars = new ArrayList<>();

			for(int i=0;i<wordChars.size();i++){
				if( attemptChars.get(i).equals(wordChars.get(i)) ){
					markedAttempt.set(i, Mark.CORRECT);
				} else{
					absentChars.add(wordChars.get(i));
					markedAttempt.set(i, Mark.ABSENT);
				}
			}
			// Mark the right characters as present, if any
			for(int i=0;i<wordChars.size();i++){
				if( absentChars.contains(attemptChars.get(i)) && markedAttempt.get(i)==Mark.ABSENT ){
					absentChars.remove(attemptChars.get(i));
					markedAttempt.set(i, Mark.PRESENT);
				}
			}
		}
		return markedAttempt;
	}
}
