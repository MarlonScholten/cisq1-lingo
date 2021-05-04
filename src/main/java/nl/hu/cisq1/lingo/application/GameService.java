package nl.hu.cisq1.lingo.application;

import nl.hu.cisq1.lingo.application.dto.LingoGameDTOStrategy;
import nl.hu.cisq1.lingo.application.dto.LingoGameOverDTO;
import nl.hu.cisq1.lingo.application.dto.LingoGamePlayingDTO;
import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.data.repositories.SpringGameRepository;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.domain.State;
import nl.hu.cisq1.lingo.exceptions.GameNotFoundException;
import nl.hu.cisq1.lingo.exceptions.WordNotFoundException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	private final SpringGameRepository gameRepo;
	private final WordService wordService;

	public GameService(SpringGameRepository gameRepo, WordService wordService) {
		this.gameRepo = gameRepo;
		this.wordService = wordService;
	}

	public LingoGameDM newGame(){
		String wordToGuess = this.wordService.provideRandomWord(LingoGame.getWordLengths().get(0));
		LingoGame game = new LingoGame();
		game.nextRound(wordToGuess);
		LingoGameDM gameDM = new LingoGameDM(game);
		return gameRepo.save(gameDM);
	}

	public LingoGameDTOStrategy doGuess(Long gameId, String attempt){
		if(wordService.wordExists(attempt)){
			LingoGameDM gameDM = this.getGameById(gameId);
			LingoGame game = gameDM.getLingoGame();
			game.doGuess(attempt);
			if(game.getCurrentRound().getState().equals(State.WON)){
				game.calcAndSetScore();
			}
			gameRepo.save(gameDM);
			return game.getCurrentRound().getState().equals(State.LOST) ?
					new LingoGameOverDTO(gameDM) :
					new LingoGamePlayingDTO(gameDM);
		} else{
			throw new WordNotFoundException();
		}
	}

	public LingoGameDM nextRound(Long gameId){
		LingoGameDM gameDM = this.getGameById(gameId);
		LingoGame game = gameDM.getLingoGame();
		String wordToGuess = this.wordService.provideRandomWord(game.calcNextWordLength());
		game.nextRound(wordToGuess);
		return gameRepo.save(gameDM);
	}

	public LingoGameDM getGameById(Long id){
		return this.gameRepo.findById(id)
				.orElseThrow(GameNotFoundException::new);
	}
}
