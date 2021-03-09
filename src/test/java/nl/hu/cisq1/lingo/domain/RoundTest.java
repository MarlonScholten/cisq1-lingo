package nl.hu.cisq1.lingo.domain;

import nl.hu.cisq1.lingo.Utils;
import nl.hu.cisq1.lingo.exceptions.IllegalWordException;
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
		Round expected = new Round("woord", 0, false, Utils.characterListOf("w...."), new ArrayList<>());

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
}