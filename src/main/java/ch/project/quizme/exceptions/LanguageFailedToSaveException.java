package ch.project.quizme.exceptions;
public class LanguageFailedToSaveException extends RuntimeException{
    public LanguageFailedToSaveException(String name){
        super("The Language '" + name + "' could not be saved.");
    }
}
