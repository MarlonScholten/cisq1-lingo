package nl.hu.cisq1.lingo.domain;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LingoGame implements Serializable {
	private List<Round> rounds;
	private int score;


}
