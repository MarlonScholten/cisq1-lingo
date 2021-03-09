package nl.hu.cisq1.lingo.data;

import nl.hu.cisq1.lingo.domain.LingoGame;

import javax.persistence.*;

@Entity
public class LingoGameDM {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Lob
	private LingoGame lingoGame;

	public int getId() {
		return id;
	}
}

