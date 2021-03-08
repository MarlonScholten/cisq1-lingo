package nl.hu.cisq1.lingo.domain;

import nl.hu.cisq1.lingo.Utils;
import nl.hu.cisq1.lingo.exceptions.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
	@Test
	@DisplayName("word is guessed if all letters are correct")
	void wordIsGuessed(){
		Round round = Round.newRound("woord");
		List marks = List.of(Mark.CORRECT, Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT);
		Feedback feedback = new Feedback("woord", marks, round);

		assertTrue(feedback.isWordGuessed());
	}

	@Test
	@DisplayName("word is not guessed if not all letters are correct")
	void wordIsNotGuessed(){
		Round round = Round.newRound("woord");
		List marks = List.of(Mark.ABSENT, Mark.ABSENT,Mark.PRESENT,Mark.ABSENT,Mark.CORRECT);
		Feedback feedback = new Feedback("woord", marks, round);

		assertFalse(feedback.isWordGuessed());
	}

	@Test
	@DisplayName("the attempt is invalid")
	void attemptIsInvalid(){
		Round round = Round.newRound("woord");
		List marks = List.of(Mark.INVALID, Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID);
		Feedback feedback = new Feedback("woord", marks, round);

		assertFalse(feedback.attemptIsValid());
	}

	@Test
	@DisplayName("the attempt is valid")
	void attemptIsNotInvalid(){
		Round round = Round.newRound("woord");
		List marks = List.of(Mark.ABSENT, Mark.ABSENT,Mark.PRESENT,Mark.ABSENT,Mark.CORRECT);
		Feedback feedback = new Feedback("woord", marks, round);

		assertTrue(feedback.attemptIsValid());
	}

	@ParameterizedTest
	@DisplayName("mark an attempt")
	@MethodSource("provideAttemptExamples")
	void markAnAttempt(String attempt, String wordToGuess, List<Mark> expectedMarks){
		assertEquals(expectedMarks,Feedback.markAttempt(attempt,wordToGuess));
	}

	// Marking an attempt parameters
	static Stream<Arguments> provideAttemptExamples() {
		return Stream.of(
				Arguments.of(
						"kabel", // attempt
						"kegel", // wordToGuess
						List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT)
				),
				Arguments.of(
						"dieven", // attempt
						"deuren", // wordToGuess
						List.of(Mark.CORRECT, Mark.ABSENT, Mark.PRESENT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT)
				),
				Arguments.of(
						"scholen", // attempt
						"smelten", // wordToGuess
						List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.PRESENT, Mark.CORRECT, Mark.CORRECT)
				),
				Arguments.of(
						"draad",  // attempt
						"baard",  // wordToGuess
						List.of(Mark.ABSENT,Mark.PRESENT,Mark.CORRECT,Mark.PRESENT,Mark.CORRECT)
				),
				Arguments.of(
						"woord",  // attempt
						"woord",  // wordToGuess
						List.of(Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT)
				),
				Arguments.of(
						"dwerg",  // attempt
						"dwars",  // wordToGuess
						List.of(Mark.CORRECT,Mark.CORRECT,Mark.ABSENT,Mark.CORRECT,Mark.ABSENT)
				),
				Arguments.of(
						"aaa",  // attempt
						"hoort",  // wordToGuess
						List.of(Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID)
				),
				Arguments.of(
						"oooo",  // attempt
						"woord",  // wordToGuess
						List.of(Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID)
				),
				Arguments.of(
						"ooooo",  // attempt
						"woord",  // wordToGuess
						List.of(Mark.ABSENT,Mark.CORRECT,Mark.CORRECT,Mark.ABSENT,Mark.ABSENT)
				)
		);
	}

	@ParameterizedTest
	@DisplayName("give a hint")
	@MethodSource("provideHintExamples")
	void giveHint(List<Character> previousHint, String wordToGuess, String attempt, List<Character> expected){
		Round round = Round.newRound(wordToGuess);
		Feedback feedback = Feedback.correct(attempt,round);

		assertEquals(expected, feedback.giveHint(previousHint, wordToGuess));
	}

	// Giving a hint parameters
	static Stream<Arguments> provideHintExamples() {
		return Stream.of(
				Arguments.of(
						Utils.characterListOf("k...."),
						"kabel",
						"kegel",
						Utils.characterListOf("k..el")
				),
				Arguments.of(
						Utils.characterListOf("dw..."),
						"dwerg",
						"dwars",
						Utils.characterListOf("dw.r.")
				)
		);
	}
}