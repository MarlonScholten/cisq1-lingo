package nl.hu.cisq1.lingo.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.domain.State;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(CiTestConfiguration.class)
class GameServiceIntegrationTest {
	@Autowired
	private WordService wordService;
	@Autowired
	private GameService gameService;

	// if by some miracle, we get this word
	// grab another one until it is not this word
	String generateTestGuess(String wordToGuess){
		String testWord = "woord";
		if(wordToGuess.equals(testWord)){
			String newWord = wordService.provideRandomWord(5);
			while(newWord.equals(testWord)){
				newWord = wordService.provideRandomWord(5);
			}
			testWord = newWord;
		}
		return testWord;
	}

	@Test
	@DisplayName("start a new game")
	void startNewGame(){
		LingoGameDM gameDM = gameService.newGame();
		LingoGame game = gameDM.getLingoGame();

		assertEquals(State.PLAYING, game.getCurrentRound().getState());
		assertEquals(5, game.getCurrentRound().getCurrentHint().getCharacters().size());
		assertEquals(0, game.getCurrentRound().getGivenFeedback().size());
		assertEquals(0, game.getScore());
	}

	@Test
	@DisplayName("retrieve a new game by its id")
	void retrieveById(){
		LingoGameDM newGameDM = gameService.newGame();
		LingoGame game = gameService.getGameById(newGameDM.getId()).getLingoGame();

		assertEquals(State.PLAYING, game.getCurrentRound().getState());
		assertEquals(5, game.getCurrentRound().getCurrentHint().getCharacters().size());
		assertEquals(0, game.getCurrentRound().getGivenFeedback().size());
		assertEquals(0, game.getScore());
	}

	@Test
	@DisplayName("do some guesses")
	void doSomeGuesses(){
		LingoGameDM newGameDM = gameService.newGame();
		LingoGame game = newGameDM.getLingoGame();

		String testWord = generateTestGuess(game.getCurrentRound().getWordToGuess());

		gameService.doGuess(newGameDM.getId(), testWord);
		gameService.doGuess(newGameDM.getId(), testWord);
		gameService.doGuess(newGameDM.getId(), testWord);

		// update the variable
		game = gameService.getGameById(newGameDM.getId()).getLingoGame();

		assertEquals(State.PLAYING, game.getCurrentRound().getState());
		assertEquals(5, game.getCurrentRound().getCurrentHint().getCharacters().size());
		assertEquals(3, game.getCurrentRound().getGivenFeedback().size());
		assertEquals(0, game.getScore());
	}

	@Test
	@DisplayName("win the round in 1 guess")
	void winTheRound(){
		LingoGameDM newGameDM = gameService.newGame();
		LingoGame game = newGameDM.getLingoGame();

		String testWord = game.getCurrentRound().getWordToGuess();

		gameService.doGuess(newGameDM.getId(), testWord);

		// update the variable
		game = gameService.getGameById(newGameDM.getId()).getLingoGame();

		assertEquals(State.WON, game.getCurrentRound().getState());
		assertEquals(5, game.getCurrentRound().getCurrentHint().getCharacters().size());
		assertEquals(1, game.getCurrentRound().getGivenFeedback().size());
		assertEquals(25, game.getScore());
	}

	@Test
	@DisplayName("win the round and start a new one")
	void startNewRoundAfterWin(){
		LingoGameDM newGameDM = gameService.newGame();
		LingoGame game = newGameDM.getLingoGame();

		String testWord = game.getCurrentRound().getWordToGuess();

		gameService.doGuess(newGameDM.getId(), testWord);
		gameService.nextRound(newGameDM.getId());

		// update the variable
		game = gameService.getGameById(newGameDM.getId()).getLingoGame();

		assertEquals(State.PLAYING, game.getCurrentRound().getState());
		assertEquals(6, game.getCurrentRound().getCurrentHint().getCharacters().size());
		assertEquals(0, game.getCurrentRound().getGivenFeedback().size());
		assertEquals(25, game.getScore());
	}

}