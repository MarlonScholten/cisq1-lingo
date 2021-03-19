package nl.hu.cisq1.lingo.application;

import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.application.dto.GameDTOStrategy;
import nl.hu.cisq1.lingo.application.dto.GameProgressDTO;
import nl.hu.cisq1.lingo.data.repositories.SpringGameRepository;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.exceptions.GameNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

//TODO: Write tests
@Service
public class GameService {
	private final SpringGameRepository gameRepo;
	private final WordService wordService;

	public GameService(SpringGameRepository gameRepo, WordService wordService) {
		this.gameRepo = gameRepo;
		this.wordService = wordService;
	}

	public GameDTOStrategy newGame(){
		String wordToGuess = this.wordService.provideRandomWord(LingoGame.getWordLengths().get(0));
		LingoGame game = new LingoGame();
		game.nextRound(wordToGuess);
		LingoGameDM gameDM = new LingoGameDM(game);
		gameRepo.save(gameDM);
		return new GameProgressDTO(gameDM);
	}

	public GameDTOStrategy getGameById(Long id){
		LingoGameDM gameDM = this.gameRepo.findById(id)
				.orElseThrow(GameNotFoundException::new);
		return new GameProgressDTO(gameDM);
	}
}
