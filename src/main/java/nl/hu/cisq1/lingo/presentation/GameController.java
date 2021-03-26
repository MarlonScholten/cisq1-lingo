package nl.hu.cisq1.lingo.presentation;

import nl.hu.cisq1.lingo.application.GameService;
import nl.hu.cisq1.lingo.application.dto.GameDTOStrategy;
import nl.hu.cisq1.lingo.application.dto.GameEndedDTO;
import nl.hu.cisq1.lingo.application.dto.GamePlayingDTO;
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
	public GameDTOStrategy newGame() {
		LingoGameDM gameDM = this.service.newGame();
		// TODO: Remove this for production
		System.out.println("DEBUG: WORDTOGUESS: "+ gameDM.getLingoGame().getCurrentRound().getWordToGuess());
		return new GamePlayingDTO(gameDM);
	}

	@PostMapping("{gameId}/guess/{attempt}")
	public GameDTOStrategy doGuess(@PathVariable(value="gameId") Long gameId, @PathVariable(value="attempt") String attempt) throws IllegalMoveException {
		try{
			LingoGameDM gameDM = this.service.doGuess(gameId, attempt);
			LingoGame game = gameDM.getLingoGame();
			if(game.getCurrentRound().getState().equals(State.LOST)){
				return new GameEndedDTO(gameDM);
			} else {
				return new GamePlayingDTO(gameDM);
			}
		} catch(IllegalMoveException illegalMove){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot guess for this round");
		}
	}

	@GetMapping("{gameId}/round/next")
	public GameDTOStrategy nextRound(@PathVariable(value="gameId") Long gameId) throws IllegalMoveException {
		try{
			LingoGameDM gameDM = this.service.nextRound(gameId);
			// TODO: Remove this for production
			System.out.println("DEBUG: WORDTOGUESS: "+ gameDM.getLingoGame().getCurrentRound().getWordToGuess());
			return new GamePlayingDTO(gameDM);
		} catch(IllegalMoveException illegalMove){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot start a new round");
		}
	}

	@GetMapping("/{id}")
	public GameDTOStrategy getGameById(@PathVariable(value="id") Long id) {
		LingoGameDM gameDM = this.service.getGameById(id);
		return new GamePlayingDTO(gameDM);
	}

}
