package nl.hu.cisq1.lingo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.hu.cisq1.lingo.domain.Feedback;
import nl.hu.cisq1.lingo.domain.Mark;

import java.util.List;


@AllArgsConstructor
@Data
public class FeedbackDTO {
	private String attempt;
	private List<Mark> feedback;

	public FeedbackDTO(Feedback feedback){
		this.attempt = feedback.getAttempt();
		this.feedback = feedback.getMarks();
	}
}
