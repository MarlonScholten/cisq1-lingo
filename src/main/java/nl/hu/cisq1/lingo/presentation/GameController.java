package nl.hu.cisq1.lingo.presentation;

import nl.hu.cisq1.lingo.application.GameService;
import nl.hu.cisq1.lingo.data.dto.GameDTOStrategy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("game")
public class GameController {
	private final GameService service;

	public GameController(GameService service) {
		this.service = service;
	}

//	@PostMapping("/new")
//	public GameDTOStrategy newGame() {
//		return this.service.newGame();
//	}

}
