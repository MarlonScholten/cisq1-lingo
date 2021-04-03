package nl.hu.cisq1.lingo;

import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(CiTestConfiguration.class)
class UtilsIntegrationTest {

	@Autowired
	private WordService wordService;

	@ParameterizedTest
	@DisplayName("generate some test words that are not the word to guess")
	@MethodSource("generateTestWordExamples")
	void generateTestWords(String wordToGuess){
		assertNotEquals(wordToGuess, Utils.generateTestGuess(wordService, wordToGuess));
		assertEquals(5, Utils.generateTestGuess(wordService, wordToGuess).length());
	}

	static Stream<Arguments> generateTestWordExamples() {
		return Stream.of(
				Arguments.of(
						"woord" // word to guess
				),
				Arguments.of(
						"hoort" // word to guess
				)
		);
	}
}