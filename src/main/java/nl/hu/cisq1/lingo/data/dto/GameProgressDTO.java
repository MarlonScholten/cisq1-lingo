package nl.hu.cisq1.lingo.data.dto;

import lombok.AllArgsConstructor;
import nl.hu.cisq1.lingo.domain.Feedback;

import java.util.List;

@AllArgsConstructor
public class GameProgressDTO implements GameDTOStrategy{
	private int roundNumber;
	private int score;

}
