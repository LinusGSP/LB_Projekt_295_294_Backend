package ch.project.quizme.repository;

import ch.project.quizme.databases.LearnSet;

import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<LearnSet, Integer>{
}
