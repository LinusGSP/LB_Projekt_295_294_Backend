package ch.project.quizme.repository;

import ch.project.quizme.databases.Language;

import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language, Integer>{
}
