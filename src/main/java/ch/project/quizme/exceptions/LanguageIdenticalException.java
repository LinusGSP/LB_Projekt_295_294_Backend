package ch.project.quizme.exceptions;

public class LanguageIdenticalException extends RuntimeException {
    public LanguageIdenticalException(Integer id1, Integer id2) {
        super("Cant enter the same id twice. IDs provided: " + id1 + ", " + id2);
    }
}
