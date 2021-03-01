package nl.hu.cisq1.lingo.domain;

import nl.hu.cisq1.lingo.Utils;
import nl.hu.cisq1.lingo.exceptions.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.internal.matchers.Equals;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
	@Test
	@DisplayName("word is guessed if all letters are correct")
	void wordIsGuessed(){
		List marks = List.of(Mark.CORRECT, Mark.CORRECT,Mark.CORRECT,Mark.CORRECT,Mark.CORRECT);
		Feedback feedback = new Feedback("woord", marks);

		assertTrue(feedback.isWordGuessed());
	}

	@Test
	@DisplayName("word is not guessed if not all letters are correct")
	void wordIsNotGuessed(){
		List marks = List.of(Mark.ABSENT, Mark.ABSENT,Mark.PRESENT,Mark.ABSENT,Mark.CORRECT);
		Feedback feedback = new Feedback("woord", marks);

		assertFalse(feedback.isWordGuessed());
	}

	@Test
	@DisplayName("the attempt is invalid")
	void attemptIsInvalid(){
		List marks = List.of(Mark.INVALID, Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID,Mark.INVALID);
		Feedback feedback = new Feedback("woord", marks);

		assertFalse(feedback.attemptIsValid());
	}

	@Test
	@DisplayName("the attempt is valid")
	void attemptIsNotInvalid(){
		List marks = List.of(Mark.ABSENT, Mark.ABSENT,Mark.PRESENT,Mark.ABSENT,Mark.CORRECT);
		Feedback feedback = new Feedback("woord", marks);

		assertTrue(feedback.attemptIsValid());
	}

	@Test
	@DisplayName("the feedback is invalid")
	void feedbackIsInvalid(){
		assertThrows(
				InvalidFeedbackException.class,
				() -> Feedback.correct("woord", List.of(Mark.CORRECT)));
	}

//	// Giving a hint parameters
//	static Stream<Arguments> provideHintExamples() {
//		return Stream.of(
//				Arguments.of(
//						Utils.characterListOf("k...."),
//						"kabel",
//						"kegel",
//						Utils.characterListOf("k..el"))
//		);
//	}
//
//	@ParameterizedTest
//	@MethodSource("provideHintExamples")
//	@DisplayName("give a hint")
//	void giveHint(List<Character> previousHint, String wordToGuess, String attempt, List<Character> expected){
//		Feedback.correct(attempt, );
//		assertEquals(Feedback);
//	}
}