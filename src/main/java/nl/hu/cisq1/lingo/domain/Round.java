package nl.hu.cisq1.lingo.domain;

import lombok.*;
import nl.hu.cisq1.lingo.domain.exceptions.IllegalMoveException;
import nl.hu.cisq1.lingo.domain.exceptions.IllegalWordException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Round implements Serializable {
	private String wordToGuess;
	private static final int MAX_ATTEMPTS = 5;
	private boolean wordWasGuessed;
	private List<Hint> givenHints;
	private Hint currentHint;
	private List<Feedback> givenFeedback;
	private State state;

	public static Round newRound(String wordToGuess) throws IllegalWordException{
		if( (wordToGuess.trim().length()>0) ){
			List<Hint> initialHints = new ArrayList<>();
			initialHints.add(Hint.initialHintFor(wordToGuess));
			Round round = new Round(wordToGuess, false, initialHints, null, new ArrayList<>(), State.PLAYING);
			round.currentHint = round.givenHints.get(round.givenHints.size()-1);
			return round;
		}
		else throw new IllegalWordException();
	}

	public void doAttempt(String attempt){
		if(state.equals(State.PLAYING)){
			Feedback feedback = Feedback.newFeedback(attempt,this);
			givenFeedback.add(feedback);
			currentHint = Hint.giveHint(currentHint,wordToGuess,feedback.getMarks());
			this.wordWasGuessed = feedback.isWordGuessed();
			updateState();
		} else throw new IllegalMoveException();
	}

	public void updateState(){
		if(wordWasGuessed){
			state=State.WON;
		}
		if(!wordWasGuessed && givenFeedback.size()==MAX_ATTEMPTS) state=State.LOST;
	}

	public Feedback getLastFeedback(){
		return this.givenFeedback.get(givenFeedback.size()-1);
	}
}
