package ch.project.quizme.exceptions;

public class LearnWordNotFoundException extends RuntimeException {
    public LearnWordNotFoundException(Integer id) {
        super("Word with id=" + id + " could not be found");
    }
}
