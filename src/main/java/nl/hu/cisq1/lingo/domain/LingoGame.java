package nl.hu.cisq1.lingo.domain;

import lombok.*;

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

	public LingoGame(){
		this.prevWordLen = wordLengths.get(0);
		this.rounds = new ArrayList<>();
		this.score = 0;
	}

	// Starts a new round, adds it to the rounds list and returns it
	public Round nextRound(String wordToGuess){
		Round round = Round.newRound(wordToGuess);
		this.rounds.add(round);
		return round;
	}
}
