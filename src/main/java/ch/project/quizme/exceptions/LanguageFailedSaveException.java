package ch.project.quizme.exceptions;

import ch.project.quizme.controller.LanguageController;

public class LanguageFailedSaveException extends RuntimeException{
    public LanguageFailedSaveException(String name){
        super("The Language '" + name + "' could not be saved.");
    }
}
