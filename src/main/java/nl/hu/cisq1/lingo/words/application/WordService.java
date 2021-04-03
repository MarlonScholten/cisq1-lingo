package nl.hu.cisq1.lingo.words.application;

import nl.hu.cisq1.lingo.exceptions.WordNotFoundException;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class WordService {
    private final SpringWordRepository wordRepository;

    public WordService(SpringWordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public String provideRandomWord(Integer length) {
        return this.wordRepository
                .findRandomWordByLength(length)
                .orElseThrow(() -> new WordLengthNotSupportedException(length))
                .getValue();
    }

    public String getSpecifiedWord(String word){
        return this.wordRepository
                .findWordByValue(word)
                .orElseThrow(WordNotFoundException::new)
                .getValue();
    }

    public boolean wordExists(String word) throws WordNotFoundException{
        try{
            this.getSpecifiedWord(word);
            return true;
        } catch (WordNotFoundException wordNotFoundException){
            return false;
        }
    }
}
