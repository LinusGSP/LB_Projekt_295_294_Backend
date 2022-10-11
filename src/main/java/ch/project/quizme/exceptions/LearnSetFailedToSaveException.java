package ch.project.quizme.exceptions;

public class LearnSetFailedToSaveException extends RuntimeException {
    public LearnSetFailedToSaveException(String name) {
        super("The LearnSet with name: '" + name + "' could not be saved.");
    }
}
