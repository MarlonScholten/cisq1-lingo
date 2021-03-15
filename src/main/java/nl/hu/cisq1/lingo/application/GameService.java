package nl.hu.cisq1.lingo.application;

import nl.hu.cisq1.lingo.data.dto.GameDTOStrategy;
import nl.hu.cisq1.lingo.data.repositories.SpringGameRepository;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	private final SpringGameRepository gameRepo;
	private final SpringWordRepository wordRepo;

	public GameService(SpringGameRepository gameRepo, SpringWordRepository wordRepo) {
		this.gameRepo = gameRepo;
		this.wordRepo = wordRepo;
	}

//	public GameDTOStrategy newGame(){
//		LingoGame game = new LingoGame();
//
//	}
}
