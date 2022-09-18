package ch.project.quizme.exceptions;

public class IdenticalLanguageException extends RuntimeException{
    public IdenticalLanguageException(Integer id1, Integer id2){
        super("Cant enter the same id twice. IDs provided: " + id1 + ", " + id2);
    }
}
