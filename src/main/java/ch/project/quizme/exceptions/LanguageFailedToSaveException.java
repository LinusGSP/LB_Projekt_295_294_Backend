package ch.project.quizme.exceptions;

public class LanguageFailedToSaveException extends RuntimeException {
    public LanguageFailedToSaveException(String name) {
        super("The Language with name:'" + name + "' could not be saved.");
    }
}
