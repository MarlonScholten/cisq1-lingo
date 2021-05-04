package nl.hu.cisq1.lingo.application;

import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.data.repositories.SpringGameRepository;
import nl.hu.cisq1.lingo.domain.LingoGame;
import nl.hu.cisq1.lingo.domain.State;
import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import nl.hu.cisq1.lingo.exceptions.WordNotFoundException;
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
	private LingoGame expectedGame;
	private LingoGameDM gameDM;

	@BeforeEach
	void init(){
		this.gameRepo = mock(SpringGameRepository.class);
		this.wordService = mock(WordService.class);
		this.gameService = new GameService(gameRepo,wordService);

		when(wordService.provideRandomWord(5))
				.thenReturn("vraag");
		when(wordService.provideRandomWord(6))
				.thenReturn("hebben");
		when(wordService.wordExists(any(String.class)))
				.thenReturn(true);
		expectedGame = new LingoGame();
		gameDM = new LingoGameDM(expectedGame);
	}

	@Test
	@DisplayName("start a new game")
	void StartNewGame(){
		expectedGame.nextRound("dammer");

		LingoGameDM gameDM = new LingoGameDM(expectedGame);
		when(gameRepo.save(any(LingoGameDM.class)))
				.thenReturn(gameDM);

		assertEquals(gameDM, gameService.newGame());
	}

	@Test
	@DisplayName("guess with an invalid word")
	void invalidWordGuess(){
		expectedGame.nextRound("dammer");

		when(gameRepo.save(any(LingoGameDM.class)))
				.thenReturn(gameDM);
		when(gameRepo.findById(anyLong()))
				.thenReturn(Optional.of(gameDM));
		when(wordService.wordExists(any(String.class)))
				.thenReturn(false);

		assertThrows(
				WordNotFoundException.class,
				()-> gameService.doGuess(1L, "aaaaa")
		);
	}

	@Test
	@DisplayName("attempt a guess")
	void StartNewRound(){
		expectedGame.nextRound("dammer");

		when(gameRepo.save(any(LingoGameDM.class)))
				.thenReturn(gameDM);
		when(gameRepo.findById(anyLong()))
				.thenReturn(Optional.of(gameDM));

		gameService.doGuess(anyLong(), "vraag");// 1 guess
		gameService.doGuess(anyLong(), "vraag");// 2 guesses

		assertEquals(2, gameDM.getLingoGame().getCurrentRound().getGivenFeedback().size());// we did 2 guesses, so we expect a length of 2 feedbacks given
		assertEquals(State.PLAYING, gameDM.getLingoGame().getCurrentRound().getState());// we should still be playing the game after 2 guesses
	}

	@Test
	@DisplayName("start a new round when won")
	void StartNewRoundWhenWon(){
		expectedGame.nextRound("vraag");

		when(gameRepo.save(any(LingoGameDM.class)))
				.thenReturn(gameDM);
		when(gameRepo.findById(anyLong()))
				.thenReturn(Optional.of(gameDM));

		gameService.doGuess(anyLong(), "vraag");// guess the word
		gameService.nextRound(anyLong());

		assertEquals(2, gameDM.getLingoGame().getRounds().size());// we should now have 2 rounds
		assertEquals("hebben", gameDM.getLingoGame().getCurrentRound().getWordToGuess());
	}

	@Test
	@DisplayName("cannot start a new round when lost")
	void StartNewRoundWhenLost(){
		expectedGame.nextRound("vraag");

		when(gameRepo.save(any(LingoGameDM.class)))
				.thenReturn(gameDM);
		when(gameRepo.findById(anyLong()))
				.thenReturn(Optional.of(gameDM));

		gameService.doGuess(anyLong(), "dammer");// 1
		gameService.doGuess(anyLong(), "dammer");// 2
		gameService.doGuess(anyLong(), "dammer");// 3
		gameService.doGuess(anyLong(), "dammer");// 4
		gameService.doGuess(anyLong(), "dammer");// 5 - failed to guess the word

		assertThrows(
				IllegalMoveException.class,
				()-> gameService.nextRound(1L)// try to start a new round
		);
	}

}