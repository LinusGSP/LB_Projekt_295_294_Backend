package ch.project.quizme.exceptions;

public class LearnSetNotFoundException extends RuntimeException {
    public LearnSetNotFoundException(Integer id) {
        super("Could not find LearnSet with id=" + id);
    }
}
