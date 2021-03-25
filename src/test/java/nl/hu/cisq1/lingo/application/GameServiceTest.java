package nl.hu.cisq1.lingo.application;

import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.data.repositories.SpringGameRepository;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.words.application.WordService;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class GameServiceTest {
	private SpringGameRepository gameRepo;
	private WordService wordService;
	private GameService gameService;

	@BeforeEach
	void init(){
		this.gameRepo = mock(SpringGameRepository.class);
		this.wordService = mock(WordService.class);
		this.gameService = new GameService(gameRepo,wordService);
	}

//	@Test
//	@DisplayName("start a new game")
//	void StartNewGameAndSave(){
//		gameService.newGame();
//	}

}