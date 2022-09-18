package ch.project.quizme.exceptions;

public class LearnSetCouldNotBeSavedException extends RuntimeException{
    public LearnSetCouldNotBeSavedException(String name){
        super("The LearnSet with name: " + name + " could not be saved.");
    }
}
