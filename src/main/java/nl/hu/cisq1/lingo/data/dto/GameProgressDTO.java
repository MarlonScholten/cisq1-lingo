package nl.hu.cisq1.lingo.data.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameProgressDTO implements GameDTOStrategy{
	private long id;
	private int roundNumber;
	private int score;

}
