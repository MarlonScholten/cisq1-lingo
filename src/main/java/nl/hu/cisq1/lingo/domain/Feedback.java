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

	public static Feedback correct(@NotEmpty @NotBlank String attempt, List<Mark> marks) {
		if(attempt.length() != marks.size()){
			throw new InvalidFeedbackException();
		}
		return new Feedback(attempt,marks);
	}

	public boolean isWordGuessed() {
		return this.marks.stream().allMatch(str -> str.equals(Mark.CORRECT));
	}

	public boolean attemptIsValid(){
		return this.marks.stream().noneMatch(str -> str.equals(Mark.INVALID));
	}

	// Previous hint + wordToGuess + marks = new hint
	public List<Character> giveHint(List<Character> previousHint, String wordToGuess){
		List<Character> newHint = Utils.characterListOf("input");
		return newHint;
	}
}
