package nl.hu.cisq1.lingo.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Game {
	private int id;
	private List<Round> rounds;
	private Score score;
//	private GameState state;

//	public void startGame(){
//
//	}
//
//	public void nextRound(){
//
//	}
//
//	public void endGame(){
//
//	}
}
