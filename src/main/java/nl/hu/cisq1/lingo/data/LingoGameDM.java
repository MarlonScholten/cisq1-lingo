package nl.hu.cisq1.lingo.data;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.hu.cisq1.lingo.domain.LingoGame;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LingoGameDM {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Lob
	private LingoGame lingoGame;

	public LingoGameDM(LingoGame lingoGame) {
		this.lingoGame = lingoGame;
	}

	public Long getId() {
		return id;
	}

	public LingoGame getLingoGame() {
		return lingoGame;
	}
}
