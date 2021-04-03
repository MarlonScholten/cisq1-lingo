package nl.hu.cisq1.lingo.domain;

import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LingoGameTest {

	private LingoGame game = new LingoGame();

	// All tests will start with a new round
	// with "woord" as the wordToGuess
	@BeforeEach
	void init(){
		game.nextRound("woord");
	}

	@Test
	@DisplayName("start a new round and immediately try starting another")
	void startNewRound(){
		assertThrows(
				IllegalMoveException.class,
				()-> game.nextRound("poort")
		);
	}

	@Test
	@DisplayName("start a new round, lose the round, and try starting a new round anyway")
	void startNewRoundWhenLost(){
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
		// We use a different game here, because we are dependent
		// on the wordToGuess of the methodSource
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
		game.getCurrentRound().doGuess("woord");// 1 - Win the round
		assertEquals( 25, game.calcAndSetScore());
	}

	@Test
	@DisplayName("Guess the word in 2 tries, score should be 20")
	void calculateAndSetScoreGuessOnSecondTry(){
		game.getCurrentRound().doGuess("boort");// 1
		game.getCurrentRound().doGuess("woord");// 2 - Win the round
		assertEquals( 20, game.calcAndSetScore());
	}

	@Test
	@DisplayName("Guess the word in 3 tries, score should be 15")
	void calculateAndSetScoreGuessOnThirdTry(){
		game.getCurrentRound().doGuess("boort");// 1
		game.getCurrentRound().doGuess("hoort");// 2
		game.getCurrentRound().doGuess("woord");// 3 - Win the round
		assertEquals( 15, game.calcAndSetScore());
	}

	@Test
	@DisplayName("Guess the word in 4 tries, score should be 10")
	void calculateAndSetScoreGuessOnFourthTry(){
		game.getCurrentRound().doGuess("boort");// 1
		game.getCurrentRound().doGuess("hoort");// 2
		game.getCurrentRound().doGuess("horen");// 3
		game.getCurrentRound().doGuess("woord");// 4 - Win the round
		assertEquals( 10, game.calcAndSetScore());
	}

	@Test
	@DisplayName("Guess the word in 5 tries, score should be 5")
	void calculateAndSetScoreGuessOnFifthTry(){
		game.getCurrentRound().doGuess("boort");// 1
		game.getCurrentRound().doGuess("hoort");// 2
		game.getCurrentRound().doGuess("horen");// 3
		game.getCurrentRound().doGuess("haven");// 4
		game.getCurrentRound().doGuess("woord");// 5 - Win the round
		assertEquals( 5, game.calcAndSetScore());
	}

	@Test
	@DisplayName("get the current Round of the game after starting")
	void getCurrentRoundAfterNewGame(){
		Round expected = new Round("woord");

		assertEquals(expected, game.getCurrentRound());
	}

	@Test
	@DisplayName("get the current Round of the game after 1 round")
	void getCurrentRoundAfterOneRound(){
		game.getRounds().get(0).doGuess("woord");// guess the word without the getCurrentRound method
		game.nextRound("hoort");
		Round expected = new Round("hoort");

		assertEquals(expected, game.getCurrentRound());
	}

	@Test
	@DisplayName("do a guess on the current round")
	void doGuessOnCurrentRound(){
		game.doGuess("hoort");

		assertEquals(1, game.getCurrentRound().getGivenFeedback().size());
	}

	@Test
	@DisplayName("do two guesses on the current round")
	void doTwoGuessOnCurrentRound(){
		game.doGuess("hoort");
		game.doGuess("haven");

		assertEquals(2, game.getCurrentRound().getGivenFeedback().size());
	}
}