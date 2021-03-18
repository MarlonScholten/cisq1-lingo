package nl.hu.cisq1.lingo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FeedbackDTO {
	private String attempt;
	private List<Character> feedback;
}
