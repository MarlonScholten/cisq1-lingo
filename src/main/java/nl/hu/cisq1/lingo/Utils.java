package nl.hu.cisq1.lingo;

import java.util.ArrayList;
import java.util.List;

public final class Utils {

	// Return an ArrayList filled with Characters based on the String input
	public static List<Character> characterListOf(String input){
		ArrayList<Character> charList = new ArrayList<>();

		for(int i = 0; i<input.length(); i++){
			charList.add(input.charAt(i));
		}
		return charList;
	}
}
