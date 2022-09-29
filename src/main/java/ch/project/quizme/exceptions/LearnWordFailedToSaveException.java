package ch.project.quizme.exceptions;

public class LearnWordFailedToSaveException extends RuntimeException {
    public LearnWordFailedToSaveException() {
        super("Failed to save words");
    }
}
