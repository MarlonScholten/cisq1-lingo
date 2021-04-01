package nl.hu.cisq1.lingo.application;

import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.data.repositories.SpringGameRepository;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.domain.State;
import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import nl.hu.cisq1.lingo.words.application.WordService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;


class GameServiceTest {
	private SpringGameRepository gameRepo;
	private WordService wordService;
	private GameService gameService;

	@BeforeEach
	void init(){
		this.gameRepo = mock(SpringGameRepository.class);
		this.wordService = mock(WordService.class);
		this.gameService = new GameService(gameRepo,wordService);

		when(wordService.provideRandomWord(5))
				.thenReturn("woord");
		when(wordService.provideRandomWord(6))
				.thenReturn("hebben");
	}

	@Test
	@DisplayName("start a new game")
	void StartNewGame(){
		LingoGame expectedGame = new LingoGame();
		expectedGame.nextRound("woord");

		LingoGameDM gameDM = new LingoGameDM(expectedGame);
		when(gameRepo.save(any(LingoGameDM.class)))
				.thenReturn(gameDM);

		assertEquals(gameDM, gameService.newGame());
	}

	@Test
	@DisplayName("attempt a guess")
	void StartNewRound(){
		LingoGame expectedGame = new LingoGame();
		expectedGame.nextRound("woord");

		LingoGameDM gameDM = new LingoGameDM(expectedGame);
		when(gameRepo.save(any(LingoGameDM.class)))
				.thenReturn(gameDM);
		when(gameRepo.findById(anyLong()))
				.thenReturn(Optional.of(gameDM));

		gameService.doGuess(anyLong(), "hoort");// 1 guess
		gameService.doGuess(anyLong(), "haalt");// 2 guesses

		assertEquals(2, gameDM.getLingoGame().getCurrentRound().getGivenFeedback().size());// we did 2 guesses, so we expect a length of 2 feedbacks given
		assertEquals(State.PLAYING, gameDM.getLingoGame().getCurrentRound().getState());// we should still be playing the game after 2 guesses
	}

	@Test
	@DisplayName("start a new round when won")
	void StartNewRoundWhenWon(){
		LingoGame expectedGame = new LingoGame();
		expectedGame.nextRound("woord");

		LingoGameDM gameDM = new LingoGameDM(expectedGame);
		when(gameRepo.save(any(LingoGameDM.class)))
				.thenReturn(gameDM);
		when(gameRepo.findById(anyLong()))
				.thenReturn(Optional.of(gameDM));

		gameService.doGuess(anyLong(), "woord");// guess the word
		gameService.nextRound(anyLong());

		assertEquals(2, gameDM.getLingoGame().getRounds().size());// we should now have 2 rounds
		assertEquals("hebben", gameDM.getLingoGame().getCurrentRound().getWordToGuess());
	}

	@Test
	@DisplayName("cannot start a new round when lost")
	void StartNewRoundWhenLost(){
		LingoGame expectedGame = new LingoGame();
		expectedGame.nextRound("woord");

		LingoGameDM gameDM = new LingoGameDM(expectedGame);
		when(gameRepo.save(any(LingoGameDM.class)))
				.thenReturn(gameDM);
		when(gameRepo.findById(anyLong()))
				.thenReturn(Optional.of(gameDM));

		gameService.doGuess(anyLong(), "hoort");// 1
		gameService.doGuess(anyLong(), "hoort");// 2
		gameService.doGuess(anyLong(), "hoort");// 3
		gameService.doGuess(anyLong(), "hoort");// 4
		gameService.doGuess(anyLong(), "hoort");// 5 - failed to guess the word

		assertThrows(
				IllegalMoveException.class,
				()-> gameService.nextRound(anyLong())// try to start a new round
		);
	}

}