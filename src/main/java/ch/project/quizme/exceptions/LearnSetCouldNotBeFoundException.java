package ch.project.quizme.exceptions;

public class LearnSetCouldNotBeFoundException extends RuntimeException{
    public LearnSetCouldNotBeFoundException(Integer id){
        super("Could not find LearnSet with id=" + id);
    }
}
