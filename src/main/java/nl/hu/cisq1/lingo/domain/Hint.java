package nl.hu.cisq1.lingo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Hint implements Serializable {
	private List<Character> characters;

	public static Hint initialHintFor(String input){
		List<Character> initialHint = new ArrayList<>();
		initialHint.add(input.charAt(0));
		for(int i=1;i<input.length();i++){
			initialHint.add('.');
		}
		return new Hint(initialHint);
	}

	// Previous hint + wordToGuess + marks = new hint
	public static Hint giveHint(Hint previousHint, String wordToGuess, List<Mark> marks){
		List<Character> newHint = new ArrayList<>();
		// Fill the new hint with dots, to prep for the copy
		for(int i=0;i<wordToGuess.length();i++){
			newHint.add('.');
		}
		// Copy the hint information that we already have
		Collections.copy(newHint,previousHint.characters);

		// Add all the correct characters to the hint
		for(int i=0;i<marks.size();i++){
			if(marks.get(i).equals(Mark.CORRECT)){
				newHint.set(i, wordToGuess.charAt(i));
			}
		}
		return new Hint(newHint);
	}
}
