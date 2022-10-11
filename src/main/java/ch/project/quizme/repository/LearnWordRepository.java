package ch.project.quizme.repository;

import ch.project.quizme.databases.LearnWord;
import org.springframework.data.repository.CrudRepository;

/**
 * This class is used to access the LearnWord table.
 *
 * @author Linus Schönbächler
 * @version 1.0
 * @since 2022-10-03
 */

public interface LearnWordRepository extends CrudRepository<LearnWord, Integer> {
    Iterable<LearnWord> findByLearnSetId(Integer learnSetId);
}
