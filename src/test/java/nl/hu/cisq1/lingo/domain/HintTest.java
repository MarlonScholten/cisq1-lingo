package nl.hu.cisq1.lingo.domain;

import nl.hu.cisq1.lingo.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HintTest {

	@ParameterizedTest
	@DisplayName("give a hint")
	@MethodSource("provideHintExamples")
	void giveHint(Hint previousHint, String wordToGuess, String attempt, Hint expected){
		Round round = Round.newRound(wordToGuess);
		round.doAttempt(attempt);
		List<Mark> marks = round.getLastFeedback().getMarks();

		assertEquals(expected,Hint.giveHint(previousHint,wordToGuess,marks));
	}

	// Giving a hint parameters
	static Stream<Arguments> provideHintExamples() {
		return Stream.of(
				Arguments.of(
						new Hint(Utils.characterListOf("k....")),
						"kabel",
						"kegel",
						new Hint(Utils.characterListOf("k..el"))
				),
				Arguments.of(
						new Hint(Utils.characterListOf("dw...")),
						"dwerg",
						"dwars",
						new Hint(Utils.characterListOf("dw.r."))
				),
				Arguments.of(
						new Hint(Utils.characterListOf("bo...")),
						"breken",
						"boksen",
						new Hint(Utils.characterListOf("bo..en"))
				),
				Arguments.of(
						new Hint(Utils.characterListOf("he...n")),
						"helden",
						"herten",
						new Hint(Utils.characterListOf("he..en"))
				)
		);
	}

}