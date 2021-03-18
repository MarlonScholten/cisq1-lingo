package nl.hu.cisq1.lingo.domain;

import lombok.*;
import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class LingoGame implements Serializable {
	@Getter
	private final static List<Integer> wordLengths = List.of(5,6,7);
	private int prevWordLen;
	private List<Round> rounds;
	private int score;

	public LingoGame(String wordToGuess){
		this.prevWordLen = wordLengths.get(0);
		this.rounds = new ArrayList<>();
		// Always start a new round when making a new game
		this.rounds.add(Round.newRound(wordToGuess));
		this.score = 0;
	}

	// Starts a new round and adds it to the rounds list
	public void nextRound(String wordToGuess){
		if(getCurrentRound().getState().equals(State.WON)){
			Round round = Round.newRound(wordToGuess);
			this.rounds.add(round);
		} else throw new IllegalMoveException("cannot start a new round");
	}

	public Round getCurrentRound(){
		return this.rounds.get(rounds.size()-1);
	}
}
