package nl.hu.cisq1.lingo.presentation;

import nl.hu.cisq1.lingo.application.GameService;
import nl.hu.cisq1.lingo.application.dto.LingoGameDTOStrategy;
import nl.hu.cisq1.lingo.application.dto.LingoGameEndedDTO;
import nl.hu.cisq1.lingo.application.dto.LingoGamePlayingDTO;
import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.domain.State;
import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

//TODO:Write Tests
@RestController
@RequestMapping("game")
public class GameController {
	private final GameService service;

	public GameController(GameService service) {
		this.service = service;
	}

	@PostMapping("/new")
	public LingoGameDTOStrategy newGame() {
		LingoGameDM gameDM = this.service.newGame();
		// TODO: Remove this for production
		System.out.println("DEBUG: WORDTOGUESS: "+ gameDM.getLingoGame().getCurrentRound().getWordToGuess());
		return new LingoGamePlayingDTO(gameDM);
	}

	@PostMapping("{gameId}/guess/{attempt}")
	public LingoGameDTOStrategy doGuess(@PathVariable(value="gameId") Long gameId, @PathVariable(value="attempt") String attempt) throws IllegalMoveException {
		try{
			LingoGameDM gameDM = this.service.doGuess(gameId, attempt);
			LingoGame game = gameDM.getLingoGame();
			if(game.getCurrentRound().getState().equals(State.LOST)){
				return new LingoGameEndedDTO(gameDM);
			} else {
				return new LingoGamePlayingDTO(gameDM);
			}
		} catch(IllegalMoveException illegalMove){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot guess for this round");
		}
	}

	@GetMapping("{gameId}/round/next")
	public LingoGameDTOStrategy nextRound(@PathVariable(value="gameId") Long gameId) throws IllegalMoveException {
		try{
			LingoGameDM gameDM = this.service.nextRound(gameId);
			// TODO: Remove this for production
			System.out.println("DEBUG: WORDTOGUESS: "+ gameDM.getLingoGame().getCurrentRound().getWordToGuess());
			return new LingoGamePlayingDTO(gameDM);
		} catch(IllegalMoveException illegalMove){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot start a new round");
		}
	}

	@GetMapping("/{id}")
	public LingoGameDTOStrategy getGameById(@PathVariable(value="id") Long id) {
		LingoGameDM gameDM = this.service.getGameById(id);
		return new LingoGamePlayingDTO(gameDM);
	}

}
