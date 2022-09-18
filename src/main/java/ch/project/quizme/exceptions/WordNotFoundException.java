package ch.project.quizme.exceptions;

public class WordNotFoundException extends RuntimeException{
    public WordNotFoundException(Integer id){
        super("Word with id:" + id + " could not be found");
    }
}
