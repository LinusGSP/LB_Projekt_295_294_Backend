package ch.project.quizme.exceptions;

public class InvalidLanguageNameException extends RuntimeException {
    public InvalidLanguageNameException() {
        super("Invalid Name");
    }
}