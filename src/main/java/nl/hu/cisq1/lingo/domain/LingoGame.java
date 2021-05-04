package nl.hu.cisq1.lingo.domain;

import lombok.*;
import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class LingoGame implements Serializable {
	@Getter
	private static final List<Integer> wordLengths = List.of(5,6,7);
	private List<Round> rounds;
	private int score;

	public LingoGame(){
		this.rounds = new ArrayList<>();
		this.score = 0;
	}

	// Starts a new round and adds it to the rounds list
	public void nextRound(String wordToGuess){
		if(this.rounds.size()>0){
			if(getCurrentRound().getState().equals(State.WON)){
				Round round = new Round(wordToGuess);
				this.rounds.add(round);
			} else throw new IllegalMoveException("cannot start a new round");
		} else {
			Round round = new Round(wordToGuess);
			this.rounds.add(round);
		}
	}

	public Integer calcNextWordLength(){
		int prevLen = this.getCurrentRound().getWordToGuess().length();
		if(prevLen < wordLengths.get(wordLengths.size()-1)){
			return wordLengths.get(wordLengths.indexOf(prevLen)+1);
		}
		return wordLengths.get(0);
	}

	public Integer calcAndSetScore(){
		return this.score += 5 * (5 - getCurrentRound().getGivenFeedback().size()) + 5;
	}

	public Round getCurrentRound(){
		return this.rounds.get(rounds.size()-1);
	}

	public void doGuess(String attempt){
		this.getCurrentRound().doGuess(attempt);
	}
}
