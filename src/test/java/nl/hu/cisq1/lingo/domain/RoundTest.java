package nl.hu.cisq1.lingo.domain;

import nl.hu.cisq1.lingo.Utils;
import nl.hu.cisq1.lingo.domain.exceptions.IllegalMoveException;
import nl.hu.cisq1.lingo.domain.exceptions.IllegalWordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
	@Test
	@DisplayName("construct a new Round")
	void constructNewRound(){
		Round actual = Round.newRound("woord");
		Round expected = new Round("woord", false, Utils.characterListOf("w...."), new ArrayList<>(), State.PLAYING);

		assertEquals(expected,actual);
	}

	@Test
	@DisplayName("construct a new Round with a blank word")
	void constructNewRoundwithBlankWord(){
		assertThrows(
				IllegalWordException.class,
				() -> Round.newRound("    "));
	}

	@Test
	@DisplayName("construct a new Round with an empty word")
	void constructNewRoundwithEmptyWord(){
		assertThrows(
				IllegalWordException.class,
				() -> Round.newRound(""));
	}

	@ParameterizedTest
	@DisplayName("Generate initial hints")
	@MethodSource("provideInitialHintExamples")
	void generateInitialHints(String wordToGuess, List<Character> expectedHint){
		Round round = Round.newRound(wordToGuess);
		assertEquals(expectedHint, round.getCurrentHint());
	}

	static Stream<Arguments> provideInitialHintExamples() {
		return Stream.of(
				Arguments.of(
						"woord",
						Utils.characterListOf("w....")
				),
				Arguments.of(
						"parken",
						Utils.characterListOf("p.....")
				),
				Arguments.of(
						"groente",
						Utils.characterListOf("g......")
				)
		);
	}

	@Test
	@DisplayName("Try an attempt while the round is over")
	void illegalMoveTest(){
		Round round = Round.newRound("woord");
		// The game has ended with this guess
		round.doAttempt("woord");
		// So this should throw an exception
		assertThrows(
				IllegalMoveException.class,
				() -> round.doAttempt("woord")
		);
	}

	@Test
	@DisplayName("Do an attempt and win the game")
	void doAnAttemptAndWin(){
		Round round = Round.newRound("woord");
		round.doAttempt("woord");
		assertEquals(State.WON, round.getState());
	}

	@Test
	@DisplayName("Do some attempts and lose the game")
	void doSomeAttemptsAndLose(){
		Round round = Round.newRound("woord");
		round.doAttempt("hoort");
		round.doAttempt("moord");
		round.doAttempt("vaart");
		round.doAttempt("baard");
		round.doAttempt("haard");
		assertEquals(State.LOST, round.getState());
	}

	@Test
	@DisplayName("Do some attempts and win the game on the fifth try")
	void doSomeAttemptsAndWinOnFifthTry(){
		Round round = Round.newRound("woord");
		round.doAttempt("hoort");
		round.doAttempt("moord");
		round.doAttempt("vaart");
		round.doAttempt("baard");
		round.doAttempt("woord");
		assertEquals(State.WON, round.getState());
	}

	@Test
	@DisplayName("Do an attempt and check if we are still playing")
	void checkForStillPlayingAfterAttempt(){
		Round round = Round.newRound("woord");
		round.doAttempt("hoort");
		round.doAttempt("moord");
		assertEquals(State.PLAYING, round.getState());
	}
}