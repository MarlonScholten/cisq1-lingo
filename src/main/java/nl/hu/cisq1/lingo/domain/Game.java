package nl.hu.cisq1.lingo.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Game {
	private long id;
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

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	public Long getId() {
		return id;
	}
}
