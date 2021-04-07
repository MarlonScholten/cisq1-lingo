package nl.hu.cisq1.lingo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
	@ParameterizedTest
	@DisplayName("generate some character lists")
	@MethodSource("provideUtilExamples")
	void makeCharacterList(String input, List<Character> expected){
		assertEquals(expected, Utils.characterListOf(input));
	}

	static Stream<Arguments> provideUtilExamples() {
		// kabel
		List<Character> kabelChars = new ArrayList<>();
		kabelChars.add('k');kabelChars.add('a');kabelChars.add('b');kabelChars.add('e');kabelChars.add('l');

		// dwerg
		List<Character> dwergChars = new ArrayList<>();
		dwergChars.add('d');dwergChars.add('w');dwergChars.add('e');dwergChars.add('r');dwergChars.add('g');

		// breken
		List<Character> brekenChars = new ArrayList<>();
		brekenChars.add('b');brekenChars.add('r');brekenChars.add('e');brekenChars.add('k');brekenChars.add('e');brekenChars.add('n');

		return Stream.of(
				Arguments.of(
						"kabel",
						kabelChars
				),
				Arguments.of(
						"dwerg",
						dwergChars
				),
				Arguments.of(
						"breken",
						brekenChars
				)
		);
	}

	@ParameterizedTest
	@DisplayName("generate some strings from character lists")
	@MethodSource("provideCharacterListExamples")
	void makeStringFromCharacterList(List<Character> input, String expected){
		assertEquals(expected, Utils.stringOf(input));
	}

	static Stream<Arguments> provideCharacterListExamples() {
		return Stream.of(
				Arguments.of(
						Utils.characterListOf("kabel"),
						"kabel"// expected
				),
				Arguments.of(
						Utils.characterListOf("dwerg"),
						"dwerg"// expected
				),
				Arguments.of(
						Utils.characterListOf("breken"),
						"breken"// expected
				)
		);
	}
}