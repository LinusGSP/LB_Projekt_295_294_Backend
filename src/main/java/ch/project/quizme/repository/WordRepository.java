package ch.project.quizme.repository;

import ch.project.quizme.databases.LearnWord;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<LearnWord, Integer> {
    Iterable<LearnWord> findByLearnSetId(Integer learnSetId);
}
