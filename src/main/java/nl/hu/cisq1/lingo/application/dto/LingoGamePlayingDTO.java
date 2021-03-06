package nl.hu.cisq1.lingo.application.dto;

import lombok.Data;
import nl.hu.cisq1.lingo.Utils;
import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.domain.Feedback;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.domain.State;

import java.util.ArrayList;
import java.util.List;

@Data
public class LingoGamePlayingDTO implements LingoGameDTOStrategy {
	private long id;
	private int roundNumber;
	private State status;
	private int score;
	private String hint;
	private List<FeedbackDTO> feedbackHistory = new ArrayList<>();

	public LingoGamePlayingDTO(LingoGameDM gameDM){
		LingoGame game = gameDM.getLingoGame();
		this.id = gameDM.getId();
		this.roundNumber = game.getRounds().size();
		this.score = game.getScore();
		this.hint = Utils.stringOf(game.getCurrentRound().getCurrentHint().getCharacters());
		for(Feedback feedback : game.getCurrentRound().getGivenFeedback()){
			this.feedbackHistory.add(new FeedbackDTO(feedback));
		}
		this.status = game.getCurrentRound().getState();
	}
}
