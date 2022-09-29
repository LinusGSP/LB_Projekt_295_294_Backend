package ch.project.quizme.exceptions;

public class InvalidLanguageFlagException extends RuntimeException {
    public InvalidLanguageFlagException() {
        super("Invalid Flag");
    }
}
