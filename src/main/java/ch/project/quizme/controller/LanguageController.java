package ch.project.quizme.controller;


import ch.project.quizme.databases.Language;
import ch.project.quizme.exceptions.LanguageFailedToSaveException;
import ch.project.quizme.exceptions.LanguageNotFoundException;
import ch.project.quizme.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * This class is the controller for the Language entity.
 * This class contains the methods to get, create and delete a language.
 *
 * @author Linus Schönbächler
 * @version 1.0
 * @since 2022-10-03
 */
@RestController
@RequestMapping(path = "/api/language")
public class LanguageController {

    @Autowired
    private LanguageRepository languageRepository;

    /**
     * This method gets all languages in the database.
     *
     * @return All languages
     */
    @GetMapping(path = "")
    public ResponseEntity<Iterable<Language>> getAllLanguages() {
        Optional<Iterable<Language>> languages = Optional.of(languageRepository.findAll());
        return languages.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * This method gets a languages with a unique id.
     *
     * @param id The id of the language.
     * @return The language.
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Language> getAllLanguageById(@PathVariable Integer id) {
        Optional<Language> languages = languageRepository.findById(id);
        return ResponseEntity.ok(languages.orElseThrow(() -> new LanguageNotFoundException(id)));
    }

    /**
     * This method creates a new language.
     *
     * @param language The language to be created.
     * @return Successful
     */
    @PostMapping(path = "")
    public ResponseEntity<String> createLanguage(@Valid @RequestBody Language language) {
        try {
            languageRepository.save(language);
        } catch (Exception e) {
            throw new LanguageFailedToSaveException(language.getName());
        }
        return ResponseEntity.ok("Success: saved");
    }

    /**
     * This method deletes a language.
     *
     * @param id The id of the deleted language.
     * @return Successful.
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteLanguage(@PathVariable Integer id) {
        try {
            languageRepository.deleteById(id);
        } catch (Exception e) {
            throw new LanguageNotFoundException(id);
        }
        return ResponseEntity.ok("Success: deleted");
    }
}
