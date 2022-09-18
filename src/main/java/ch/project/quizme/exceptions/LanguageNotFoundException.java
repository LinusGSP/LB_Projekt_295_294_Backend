package ch.project.quizme.exceptions;

import ch.project.quizme.databases.Language;

public class LanguageNotFoundException extends RuntimeException{
    public LanguageNotFoundException(Integer id){
        super("The Language with id: " + id + " could not be found");
    }
}
