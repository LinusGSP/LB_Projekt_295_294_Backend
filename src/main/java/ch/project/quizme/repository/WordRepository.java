package ch.project.quizme.repository;

import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.databases.Word;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<Word, Integer> {
    Iterable<Word> findByLearnSetId(Integer learnSetId);
}
