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
		assertEquals(expectedNextLen, game.calcNextWordLength());
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

	@Test
	@DisplayName("Guess the word in 1 try, score should be 25")
	void calculateAndSetScoreGuessOnFirstTry(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		game.getCurrentRound().doGuess("woord");// 1 - Win the round
		assertEquals( 25, game.calcAndSetScore());
	}

	@Test
	@DisplayName("Guess the word in 2 tries, score should be 20")
	void calculateAndSetScoreGuessOnSecondTry(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		game.getCurrentRound().doGuess("boort");// 1
		game.getCurrentRound().doGuess("woord");// 2 - Win the round
		assertEquals( 20, game.calcAndSetScore());
	}

	@Test
	@DisplayName("Guess the word in 3 tries, score should be 15")
	void calculateAndSetScoreGuessOnThirdTry(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		game.getCurrentRound().doGuess("boort");// 1
		game.getCurrentRound().doGuess("hoort");// 2
		game.getCurrentRound().doGuess("woord");// 3 - Win the round
		assertEquals( 15, game.calcAndSetScore());
	}

	@Test
	@DisplayName("Guess the word in 4 tries, score should be 10")
	void calculateAndSetScoreGuessOnFourthTry(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		game.getCurrentRound().doGuess("boort");// 1
		game.getCurrentRound().doGuess("hoort");// 2
		game.getCurrentRound().doGuess("horen");// 3
		game.getCurrentRound().doGuess("woord");// 4 - Win the round
		assertEquals( 10, game.calcAndSetScore());
	}

	@Test
	@DisplayName("Guess the word in 5 tries, score should be 5")
	void calculateAndSetScoreGuessOnFifthTry(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		game.getCurrentRound().doGuess("boort");// 1
		game.getCurrentRound().doGuess("hoort");// 2
		game.getCurrentRound().doGuess("horen");// 3
		game.getCurrentRound().doGuess("haven");// 4
		game.getCurrentRound().doGuess("woord");// 5 - Win the round
		assertEquals( 5, game.calcAndSetScore());
	}
}