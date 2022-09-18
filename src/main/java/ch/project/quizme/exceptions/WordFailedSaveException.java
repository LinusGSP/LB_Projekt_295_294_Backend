package ch.project.quizme.exceptions;

public class WordFailedSaveException extends RuntimeException{
    public WordFailedSaveException(){
        super("Failed to save words");
    }
}
