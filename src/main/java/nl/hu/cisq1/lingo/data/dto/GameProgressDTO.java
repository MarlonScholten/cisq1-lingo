package nl.hu.cisq1.lingo.data.dto;

import lombok.Data;
import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.domain.LingoGame;

@Data
public class GameProgressDTO implements GameDTOStrategy{
	private long id;
	private int roundNumber;
	private int score;
	private HintDTO hintDTO;
	private FeedbackDTO feedbackDTO;

	public GameProgressDTO(LingoGameDM gameDM){
		LingoGame game = gameDM.getLingoGame();
		this.id = gameDM.getId();
		this.roundNumber = game.getRounds().size();
		this.score = game.getScore();
		this.hintDTO = new HintDTO(game.getCurrentRound().getCurrentHint());
	}
}
