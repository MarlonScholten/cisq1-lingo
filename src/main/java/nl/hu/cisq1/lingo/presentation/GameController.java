package nl.hu.cisq1.lingo.presentation;

import nl.hu.cisq1.lingo.application.GameService;
import nl.hu.cisq1.lingo.application.dto.GameDTOStrategy;
import nl.hu.cisq1.lingo.application.dto.GameEndedDTO;
import nl.hu.cisq1.lingo.application.dto.GamePlayingDTO;
import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.domain.State;
import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import org.springframework.web.bind.annotation.*;

//TODO:Write Tests
@RestController
@RequestMapping("game")
public class GameController {
	private final GameService service;

	public GameController(GameService service) {
		this.service = service;
	}

	@PostMapping("/new")
	public GameDTOStrategy newGame() {
		LingoGameDM gameDM = this.service.newGame();
		// TODO: Remove this for production
		System.out.println("DEBUG: WORDTOGUESS: "+ gameDM.getLingoGame().getCurrentRound().getWordToGuess());
		return new GamePlayingDTO(gameDM);
	}

	@PostMapping("{gameId}/guess/{attempt}")
	public GameDTOStrategy doGuess(@PathVariable(value="gameId") Long gameId, @PathVariable(value="attempt") String attempt) throws IllegalMoveException {
		LingoGameDM gameDM = this.service.doGuess(gameId, attempt);
		LingoGame game = gameDM.getLingoGame();
		if(game.getCurrentRound().getState().equals(State.LOST)){
			return new GameEndedDTO(gameDM);
		} else {
			return new GamePlayingDTO(gameDM);
		}
	}

	@GetMapping("{gameId}/nextround/")
	public GameDTOStrategy nextRound(@PathVariable(value="gameId") Long gameId) throws IllegalMoveException {
		LingoGameDM gameDM = this.service.nextRound(gameId);
		// TODO: Remove this for production
		System.out.println("DEBUG: WORDTOGUESS: "+ gameDM.getLingoGame().getCurrentRound().getWordToGuess());
		return new GamePlayingDTO(gameDM);
	}

	@GetMapping("/{id}")
	public GameDTOStrategy getGameById(@PathVariable(value="id") Long id) {
		LingoGameDM gameDM = this.service.getGameById(id);
		return new GamePlayingDTO(gameDM);
	}

}
