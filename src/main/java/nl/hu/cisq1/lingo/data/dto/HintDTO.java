package nl.hu.cisq1.lingo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.hu.cisq1.lingo.Utils;
import nl.hu.cisq1.lingo.domain.Hint;

@Data
public class HintDTO {
	private String hint;

	public HintDTO(Hint hint){
		this.hint = Utils.stringOf(hint.getCharacters());
	}
}
