package nl.hu.cisq1.lingo.domain;

import nl.hu.cisq1.lingo.Utils;
import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import nl.hu.cisq1.lingo.exceptions.IllegalWordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
	@Test
	@DisplayName("construct a new Round")
	void constructNewRound(){
		Round actual = new Round("woord");
		Round expected = new Round();
		expected.setWordToGuess("woord");
		Hint expectedHint = new Hint(Utils.characterListOf("w...."));
		expected.setCurrentHint(expectedHint);
		expected.getGivenHints().add(expectedHint);
		expected.setWordGuessed(false);
		expected.setState(State.PLAYING);

		assertEquals(expected,actual);
	}

	@Test
	@DisplayName("construct a new Round with a blank word")
	void constructNewRoundwithBlankWord(){
		assertThrows(
				IllegalWordException.class,
				() -> new Round("    "));
	}

	@Test
	@DisplayName("construct a new Round with an empty word")
	void constructNewRoundwithEmptyWord(){
		assertThrows(
				IllegalWordException.class,
				() -> new Round(""));
	}

	@ParameterizedTest
	@DisplayName("Generate initial hints")
	@MethodSource("provideInitialHintExamples")
	void generateInitialHints(String wordToGuess, Hint expectedHint){
		Round round = new Round(wordToGuess);
		assertEquals(expectedHint, round.getCurrentHint());
	}

	static Stream<Arguments> provideInitialHintExamples() {
		return Stream.of(
				Arguments.of(
						"woord",
						new Hint(Utils.characterListOf("w...."))
				),
				Arguments.of(
						"parken",
						new Hint(Utils.characterListOf("p....."))
				),
				Arguments.of(
						"groente",
						new Hint(Utils.characterListOf("g......"))
				)
		);
	}

	@Test
	@DisplayName("Try an attempt while the round is over")
	void illegalMoveTest(){
		Round round = new Round("woord");
		// The game has ended with this guess
		round.doGuess("woord");
		// So this should throw an exception
		assertThrows(
				IllegalMoveException.class,
				() -> round.doGuess("woord")
		);
	}

	@Test
	@DisplayName("Do an attempt and win the game")
	void doAnAttemptAndWin(){
		Round round = new Round("woord");
		round.doGuess("woord");
		assertEquals(State.WON, round.getState());
	}

	@Test
	@DisplayName("Do some attempts and lose the game")
	void doSomeAttemptsAndLose(){
		Round round = new Round("woord");
		round.doGuess("hoort");
		round.doGuess("moord");
		round.doGuess("vaart");
		round.doGuess("baard");
		round.doGuess("haard");
		assertEquals(State.LOST, round.getState());
	}

	@Test
	@DisplayName("Do some attempts and win the game on the fifth try")
	void doSomeAttemptsAndWinOnFifthTry(){
		Round round = new Round("woord");
		round.doGuess("hoort");
		round.doGuess("moord");
		round.doGuess("vaart");
		round.doGuess("baard");
		round.doGuess("woord");
		assertEquals(State.WON, round.getState());
	}

	@Test
	@DisplayName("Do an attempt and check if we are still playing")
	void checkForStillPlayingAfterAttempt(){
		Round round = new Round("woord");
		round.doGuess("hoort");
		round.doGuess("moord");
		assertEquals(State.PLAYING, round.getState());
	}
}