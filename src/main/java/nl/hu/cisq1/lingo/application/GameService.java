package nl.hu.cisq1.lingo.application;

import nl.hu.cisq1.lingo.data.SpringGameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	private final SpringGameRepository gameRepository;

	public GameService(SpringGameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
}
