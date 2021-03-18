package nl.hu.cisq1.lingo.presentation;

import nl.hu.cisq1.lingo.application.GameService;
import nl.hu.cisq1.lingo.application.dto.GameDTOStrategy;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("game")
public class GameController {
	private final GameService service;

	public GameController(GameService service) {
		this.service = service;
	}

	@PostMapping("/new")
	public GameDTOStrategy newGame() {
		return this.service.newGame();
	}

	@GetMapping("/{id}")
	public GameDTOStrategy getGameById(@PathVariable(value="id") Long id) {
		return this.service.getGameById(id);
	}

}
