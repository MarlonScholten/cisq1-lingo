package nl.hu.cisq1.lingo.presentation;

import nl.hu.cisq1.lingo.application.GameService;
import nl.hu.cisq1.lingo.application.dto.GameDTOStrategy;
import nl.hu.cisq1.lingo.application.dto.GamePlayingDTO;
import nl.hu.cisq1.lingo.application.dto.GameStartedDTO;
import nl.hu.cisq1.lingo.data.LingoGameDM;
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
		return new GameStartedDTO(gameDM);
	}

	@PostMapping("{gameId}/guess/{attempt}")
	public GameDTOStrategy doGuess(@PathVariable(value="gameId") Long gameId, @PathVariable(value="attempt") String attempt){
		LingoGameDM gameDM = this.service.doGuess(gameId, attempt);
		return new GamePlayingDTO(gameDM);
	}

	@GetMapping("/{id}")
	public GameDTOStrategy getGameById(@PathVariable(value="id") Long id) {
		LingoGameDM gameDM = this.service.getGameById(id);
		return new GameStartedDTO(gameDM);
	}

}
