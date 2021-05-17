package nl.hu.cisq1.lingo.presentation;

import nl.hu.cisq1.lingo.application.GameService;
import nl.hu.cisq1.lingo.application.dto.LingoGameDTOStrategy;
import nl.hu.cisq1.lingo.application.dto.LingoGameOverDTO;
import nl.hu.cisq1.lingo.application.dto.LingoGamePlayingDTO;
import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.domain.State;
import nl.hu.cisq1.lingo.exceptions.GameNotFoundException;
import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import nl.hu.cisq1.lingo.exceptions.WordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
		return new LingoGamePlayingDTO(gameDM);
	}

	@PostMapping("{gameId}/guess/{attempt}")
	public LingoGameDTOStrategy doGuess(@PathVariable(value="gameId") Long gameId, @PathVariable(value="attempt") String attempt) {
		try{
			return this.service.doGuess(gameId, attempt);
		} catch(IllegalMoveException illegalMove){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot guess for this round");
		} catch(WordNotFoundException wordNotFoundException){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The attempt is not a valid word");
		}
	}

	@PostMapping("{gameId}/round/next")
	public LingoGameDTOStrategy nextRound(@PathVariable(value="gameId") Long gameId) {
		try{
			LingoGameDM gameDM = this.service.nextRound(gameId);
			return new LingoGamePlayingDTO(gameDM);
		} catch(IllegalMoveException illegalMove){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot start a new round");
		}
	}

	@GetMapping("/{id}")
	public LingoGameDTOStrategy getGameById(@PathVariable(value="id") Long id) {
		try{
			LingoGameDM gameDM = this.service.getGameById(id);
			return new LingoGamePlayingDTO(gameDM);
		} catch(GameNotFoundException gameNotFoundException){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with that id could not be found");
		}
	}

}
