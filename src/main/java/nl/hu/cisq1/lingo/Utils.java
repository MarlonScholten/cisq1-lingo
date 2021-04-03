package nl.hu.cisq1.lingo;

import nl.hu.cisq1.lingo.words.application.WordService;

import java.util.ArrayList;
import java.util.List;

public final class Utils {

	private Utils(){}

	// Return an ArrayList filled with Characters based on the String input
	public static List<Character> characterListOf(String input){
		ArrayList<Character> charList = new ArrayList<>();

		for(int i = 0; i<input.length(); i++){
			charList.add(input.charAt(i));
		}
		return charList;
	}

	// Return a String based on a List of Characters
	public static String stringOf(List<Character> charList){
		StringBuilder sBuilder = new StringBuilder();

		for(Character c : charList){
			sBuilder.append(c.toString());
		}
		return sBuilder.toString();
	}

	// if by some miracle, we get this word
	// grab another one until it is not this word
	public static String generateTestGuess(WordService wordService, String wordToGuess){
		String testWord = "woord";
		if(wordToGuess.equals(testWord)){
			String newWord = wordService.provideRandomWord(5);
			while(newWord.equals(testWord)){
				newWord = wordService.provideRandomWord(5);
			}
			testWord = newWord;
		}
		return testWord;
	}
}
