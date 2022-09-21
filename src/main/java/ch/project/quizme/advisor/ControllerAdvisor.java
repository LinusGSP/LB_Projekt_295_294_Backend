package ch.project.quizme.advisor;

import ch.project.quizme.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LanguageFailedToSaveException.class)
    public ResponseEntity<Object> handleLanguageFailedToSaveException(LanguageFailedToSaveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LanguageIdenticalException.class)
    public ResponseEntity<Object> handleLanguageIdenticalException(LanguageIdenticalException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LanguageNotFoundException.class)
    public ResponseEntity<Object> handleLanguageNotFoundException(LanguageNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LearnSetFailedToSaveException.class)
    public ResponseEntity<Object> handleLearnSetFailedToSaveException(LearnSetFailedToSaveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LearnSetNotFoundException.class)
    public ResponseEntity<Object> handleLearnSetNotFoundException(LearnSetNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WordFailedToSaveException.class)
    public ResponseEntity<Object> handleWordFailedToSaveException(WordFailedToSaveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WordNotFoundException.class)
    public ResponseEntity<Object> handleWordNotFoundException(WordNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
