package ch.project.quizme.exceptions;
public class LanguageFailedSaveException extends RuntimeException{
    public LanguageFailedSaveException(String name){
        super("The Language '" + name + "' could not be saved.");
    }
}
