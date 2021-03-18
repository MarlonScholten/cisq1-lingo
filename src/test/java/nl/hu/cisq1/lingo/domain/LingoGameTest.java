package nl.hu.cisq1.lingo.domain;

import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LingoGameTest {

	@Test
	@DisplayName("create a new game")
	void makeNewGame(){
		LingoGame expected = new LingoGame("haven");
		//this is a bit of a work-around because i don't want an empty constructor
		List<Round> expectedRounds = new ArrayList<>();
		expectedRounds.add(Round.newRound("woord"));
		expected.setRounds(expectedRounds);

		assertEquals(expected, new LingoGame("woord"));
	}

	@Test
	@DisplayName("start a new round and immediately try starting another")
	void startNewRound(){
		LingoGame game = new LingoGame("woord");
		assertThrows(
				IllegalMoveException.class,
				()-> game.nextRound("poort")
		);
	}

	@Test
	@DisplayName("start a new round, lose the round, and try starting a new round anyway")
	void startNewRoundWhenLost(){
		LingoGame game = new LingoGame("woord");
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
		LingoGame game = new LingoGame("woord");
		game.getCurrentRound().doAttempt("hoopt");// 1
		game.getCurrentRound().doAttempt("loopt");// 2
		game.getCurrentRound().doAttempt("haven");// 3
		game.getCurrentRound().doAttempt("woord");// 4 - Win the round
		game.nextRound("haven");// start a new round when we won
		assertEquals(2, game.getRounds().size());// we should have 2 rounds in our list now, as we are allowed to make a new round
	}
}