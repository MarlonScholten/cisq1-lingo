package nl.hu.cisq1.lingo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Could not find a suitable word")
public class WordNotFoundException extends RuntimeException{
}
