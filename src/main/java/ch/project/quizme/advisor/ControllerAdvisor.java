package ch.project.quizme.advisor;

import ch.project.quizme.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles exceptions thrown by the application.
 * It returns a Json object with the corresponding error
 *
 * @author Linus Schönbächler
 * @version 1.0
 * @since 2022-10-03
 */

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler(InvalidLanguageFlagException.class)
    public ResponseEntity<Object> handleInvalidLanguageFlagException(InvalidLanguageFlagException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLanguageNameException.class)
    public ResponseEntity<Object> handleInvalidLanguageNameException(InvalidLanguageNameException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LearnSetFailedToSaveException.class)
    public ResponseEntity<Object> handleLearnSetFailedToSaveException(LearnSetFailedToSaveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LearnSetNotFoundException.class)
    public ResponseEntity<Object> handleLearnSetNotFoundException(LearnSetNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LearnWordFailedToSaveException.class)
    public ResponseEntity<Object> handleWordFailedToSaveException(LearnWordFailedToSaveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LearnWordNotFoundException.class)
    public ResponseEntity<Object> handleWordNotFoundException(LearnWordNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
