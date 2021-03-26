package nl.hu.cisq1.lingo.domain;

import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LingoGameTest {

	@Test
	@DisplayName("start a new round and immediately try starting another")
	void startNewRound(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		assertThrows(
				IllegalMoveException.class,
				()-> game.nextRound("poort")
		);
	}

	@Test
	@DisplayName("start a new round, lose the round, and try starting a new round anyway")
	void startNewRoundWhenLost(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		game.getCurrentRound().doGuess("hoopt");// 1
		game.getCurrentRound().doGuess("loopt");// 2
		game.getCurrentRound().doGuess("haven");// 3
		game.getCurrentRound().doGuess("maken");// 4
		game.getCurrentRound().doGuess("taken");// 5 - failed to guess the word
		assertThrows(
				IllegalMoveException.class,
				()-> game.nextRound("poort")
		);
	}

	@Test
	@DisplayName("start a new round, win the round, and start a new round")
	void startNewRoundWhenWon(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		game.getCurrentRound().doGuess("hoopt");// 1
		game.getCurrentRound().doGuess("loopt");// 2
		game.getCurrentRound().doGuess("haven");// 3
		game.getCurrentRound().doGuess("woord");// 4 - Win the round
		game.nextRound("haven");// start a new round when we won
		assertEquals(2, game.getRounds().size());// we should have 2 rounds in our list now, as we are allowed to make a new round
	}

	@ParameterizedTest
	@DisplayName("the next length should increase by one, when reaching 7, go back to 5")
	@MethodSource("provideLengths")
	void calculateNextWordLength(String wordToGuess, Integer expectedNextLen){
		LingoGame game = new LingoGame();
		game.nextRound(wordToGuess);// start a new round
		assertEquals(game.calcNextWordLength(), expectedNextLen);
	}

	// provide the expected next length of the word
	static Stream<Arguments> provideLengths() {
		return Stream.of(
				Arguments.of(
						"woord",// 5
						6
				),
				Arguments.of(
						"gepiep",// 6
						7
				),
				Arguments.of(
						"dagmenu",// 7
						5
				)
		);
	}
}