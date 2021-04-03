package nl.hu.cisq1.lingo.domain;

import lombok.*;
import nl.hu.cisq1.lingo.exceptions.IllegalMoveException;
import nl.hu.cisq1.lingo.exceptions.IllegalWordException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
public class Round implements Serializable {
	private String wordToGuess;
	private static final int MAX_ATTEMPTS = 5;
	private boolean wordGuessed = false;
	private final List<Hint> givenHints = new ArrayList<>();
	private Hint currentHint;
	private final List<Feedback> givenFeedback = new ArrayList<>();
	private State state = State.PLAYING;

	public Round(String wordToGuess) throws IllegalWordException{
		if( (wordToGuess.trim().length()>0) ){
			this.wordToGuess = wordToGuess;
			Hint initialHint = Hint.initialHintFor(wordToGuess);
			this.currentHint = initialHint;
			this.givenHints.add(initialHint);
		}
		else throw new IllegalWordException();
	}

	public void doGuess(String attempt){
		if(state.equals(State.PLAYING)){
			Feedback feedback = Feedback.newFeedback(attempt,this);
			this.givenFeedback.add(feedback);
			if(feedback.attemptIsValid()){
				this.setCurrentHint(Hint.giveHint(currentHint,wordToGuess,feedback.getMarks()));
			}
			this.wordGuessed = feedback.isWordGuessed();
			updateState();
		} else throw new IllegalMoveException();
	}

	public void updateState(){
		if(wordGuessed){
			state=State.WON;
		}
		if(!wordGuessed && givenFeedback.size()==MAX_ATTEMPTS) state=State.LOST;
	}

	public Feedback getLastFeedback(){
		return this.givenFeedback.get(givenFeedback.size()-1);
	}
}
