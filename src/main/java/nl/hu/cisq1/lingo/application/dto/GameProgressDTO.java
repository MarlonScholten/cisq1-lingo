package nl.hu.cisq1.lingo.application.dto;

import lombok.Data;
import nl.hu.cisq1.lingo.Utils;
import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.domain.LingoGame;

@Data
public class GameProgressDTO implements GameDTOStrategy{
	private long id;
	private int roundNumber;
	private int score;
	private String hint;
	private FeedbackDTO feedbackDTO;

	public GameProgressDTO(LingoGameDM gameDM){
		LingoGame game = gameDM.getLingoGame();
		this.id = gameDM.getId();
		this.roundNumber = game.getRounds().size();
		this.score = game.getScore();
		this.hint = Utils.stringOf(game.getCurrentRound().getCurrentHint().getCharacters());
	}
}
