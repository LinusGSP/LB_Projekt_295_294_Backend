package ch.project.quizme.repository;

import ch.project.quizme.databases.Language;
import org.springframework.data.repository.CrudRepository;

/**
 * This class is used to access the Language table.
 *
 * @author Linus Schönbächler
 * @version 1.0
 * @since 2022-10-03
 */
public interface LanguageRepository extends CrudRepository<Language, Integer> {
}
