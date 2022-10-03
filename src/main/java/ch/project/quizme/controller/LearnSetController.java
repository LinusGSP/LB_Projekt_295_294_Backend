package ch.project.quizme.controller;

import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.exceptions.LearnSetNotFoundException;
import ch.project.quizme.exceptions.LearnWordFailedToSaveException;
import ch.project.quizme.repository.LanguageRepository;
import ch.project.quizme.repository.LearnSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**This class is the controller for the LearnSet entity.
 * This class contains the methods to get, create and delete a language.
 *
 * @author Linus Schönbächler
 * @version 1.0
 * @since 2022-10-03
 *
 */
@RestController
@RequestMapping(path = "/api/learnset")
public class LearnSetController {

    @Autowired
    private LearnSetRepository learnSetRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @GetMapping(path = "")
    public ResponseEntity<Iterable<LearnSet>> getAllLearnSets() {
        Optional<Iterable<LearnSet>> learnSets = Optional.of(learnSetRepository.findAll());
        return learnSets.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LearnSet> getLearnSetById(@PathVariable("id") Integer id) {
        Optional<LearnSet> learnSet = learnSetRepository.findById(id);
        return ResponseEntity.ok(learnSet.orElseThrow(() -> new LearnSetNotFoundException(id)));
    }

    @PostMapping(path = "")
    public ResponseEntity<String> createLearnSet(@Valid @RequestBody LearnSet learnSet) {
        try {
            learnSetRepository.save(learnSet);
        } catch (Exception e) {
            throw new LearnWordFailedToSaveException();
        }
        return ResponseEntity.ok("Success: saved");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteLearnSetById(@PathVariable("id") Integer id) {
        try {
            learnSetRepository.deleteById(id);
        } catch (Exception e) {
            throw new LearnSetNotFoundException(id);
        }
        return ResponseEntity.ok("Success: deleted");
    }
}
