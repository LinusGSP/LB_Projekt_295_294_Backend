package ch.project.quizme.exceptions;

public class LearnSetFailedSaveException extends RuntimeException{
    public LearnSetFailedSaveException(String name){
        super("The LearnSet with name: " + name + " could not be saved.");
    }
}
