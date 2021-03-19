package nl.hu.cisq1.lingo.domain;

import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LingoGameTest {

	@Test
	@DisplayName("start a new round and immediately try starting another")
	void startNewRound(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		assertThrows(
				IllegalMoveException.class,
				()-> game.nextRound("poort")
		);
	}

	@Test
	@DisplayName("start a new round, lose the round, and try starting a new round anyway")
	void startNewRoundWhenLost(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		game.getCurrentRound().doAttempt("hoopt");// 1
		game.getCurrentRound().doAttempt("loopt");// 2
		game.getCurrentRound().doAttempt("haven");// 3
		game.getCurrentRound().doAttempt("maken");// 4
		game.getCurrentRound().doAttempt("taken");// 5 - failed to guess the word
		assertThrows(
				IllegalMoveException.class,
				()-> game.nextRound("poort")
		);
	}

	@Test
	@DisplayName("start a new round, win the round, and start a new round")
	void startNewRoundWhenWon(){
		LingoGame game = new LingoGame();
		game.nextRound("woord");
		game.getCurrentRound().doAttempt("hoopt");// 1
		game.getCurrentRound().doAttempt("loopt");// 2
		game.getCurrentRound().doAttempt("haven");// 3
		game.getCurrentRound().doAttempt("woord");// 4 - Win the round
		game.nextRound("haven");// start a new round when we won
		assertEquals(2, game.getRounds().size());// we should have 2 rounds in our list now, as we are allowed to make a new round
	}
}