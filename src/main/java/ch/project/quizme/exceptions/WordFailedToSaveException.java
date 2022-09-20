package ch.project.quizme.exceptions;

public class WordFailedToSaveException extends RuntimeException{
    public WordFailedToSaveException(){
        super("Failed to save words");
    }
}
