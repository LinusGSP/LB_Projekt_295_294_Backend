package ch.project.quizme.repository;

import ch.project.quizme.databases.LearnSet;
import org.springframework.data.repository.CrudRepository;

/**
 * This class is used to access the LearnSet table.
 *
 * @author Linus Schönbächler
 * @version 1.0
 * @since 2022-10-03
 */
public interface LearnSetRepository extends CrudRepository<LearnSet, Integer> {
}
