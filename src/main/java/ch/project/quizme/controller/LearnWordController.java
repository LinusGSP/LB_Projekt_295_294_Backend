package ch.project.quizme.controller;


import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.databases.LearnWord;
import ch.project.quizme.exceptions.LanguageNotFoundException;
import ch.project.quizme.exceptions.LearnSetNotFoundException;
import ch.project.quizme.exceptions.LearnWordFailedToSaveException;
import ch.project.quizme.exceptions.LearnWordNotFoundException;
import ch.project.quizme.repository.LearnSetRepository;
import ch.project.quizme.repository.LearnWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * This class is the controller for the LearnWord entity.
 * This class contains the methods to get, create and delete a language.
 *
 * @author Linus Schönbächler
 * @version 1.0
 * @since 2022-10-03
 */


@RestController
@RequestMapping(path = "/api/word")
public class LearnWordController {

    @Autowired
    LearnWordRepository learnWordRepository;

    @Autowired
    LearnSetRepository learnSetRepository;

    /**
     * This method gets all learnWords in the database.
     *
     * @return All learnWords
     */
    @GetMapping(path = "")
    public ResponseEntity<Iterable<LearnWord>> getAllWords() {
        Optional<Iterable<LearnWord>> words = Optional.of(learnWordRepository.findAll());
        return words.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * This method gets a learnWord with a unique id.
     *
     * @param id The id of the learnWord.
     * @return The learnWord.
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<LearnWord> getWord(@PathVariable("id") Integer id) {
        Optional<LearnWord> word = learnWordRepository.findById(id);
        return ResponseEntity.ok(word.orElseThrow(() -> new LearnWordNotFoundException(id)));
    }

    /**
     * This method gets all learnWords with a specific id.
     *
     * @param id The id of the learnSet.
     * @return The learnWords of the learnSet.
     */
    @GetMapping(path = "/set/{id}")
    public ResponseEntity<Iterable<LearnWord>> getLearnSetWords(@PathVariable("id") Integer id) {
        learnSetRepository.findById(id).orElseThrow(() -> new LearnSetNotFoundException(id));
        Iterable<LearnWord> words = learnWordRepository.findByLearnSetId(id);
        return ResponseEntity.ok(words);
    }

    /**
     * This method creates a new learnWord.
     *
     * @param learnWord The learnWord to be created.
     * @return The created learnWord
     */
    @PostMapping(path = "")
    public ResponseEntity<LearnWord> createNewWord(@Valid @RequestBody LearnWord learnWord) {
        LearnWord word;
        try {
            word = learnWordRepository.save(learnWord);
            updateLearnSetLastEdited(word.getLearnSetId());
        } catch (Exception e) {
            throw new LearnWordFailedToSaveException();
        }
        return ResponseEntity.ok(word);
    }

    /**
     * This method creates a list of new learnWords.
     *
     * @param learnWords The learnWords to be created.
     * @return Successful
     */
    @PostMapping(path = "/set")
    public ResponseEntity<String> createNewWords(@RequestBody Iterable<LearnWord> learnWords) {
        try {
            learnWordRepository.saveAll(learnWords);
        } catch (Exception e) {
            throw new LearnWordFailedToSaveException();
        }
        return ResponseEntity.ok("Success: saved");
    }

    /**
     * This method edits an existing learnWord.
     *
     * @param learnWord The edited learnWord.
     * @return The newly updated learnWord.
     */
    @PutMapping(path = "")
    public ResponseEntity<LearnWord> updateWord(@Valid @RequestBody LearnWord learnWord) {
        int id = learnWord.getId();
        LearnWord updatedWord = learnWordRepository.findById(id).orElseThrow(() -> new LanguageNotFoundException(id));

        updatedWord.setWord(learnWord.getWord());
        updatedWord.setTranslation(learnWord.getTranslation());
        learnWordRepository.save(updatedWord);
        updateLearnSetLastEdited(updatedWord.getLearnSetId());

        return ResponseEntity.ok(updatedWord);
    }

    /**
     * This method deletes a learnWord.
     *
     * @param id The id of the deleted learnWord.
     * @return Successful.
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteWord(@PathVariable("id") Integer id) {
        LearnWord LearnWord = learnWordRepository.findById(id).orElseThrow(() -> new LearnWordNotFoundException(id));

        learnWordRepository.deleteById(id);
        updateLearnSetLastEdited(LearnWord.getLearnSetId());

        return ResponseEntity.ok("Success: deleted");
    }

    /**
     * This is a helper function to update the lastEdited time of a learnSet.
     *
     * @param learnSetId The id of the learnSet which should be updated.
     */
    private void updateLearnSetLastEdited(int learnSetId) {
        LearnSet learnSet = learnSetRepository.findById(learnSetId).orElseThrow(() -> new LearnSetNotFoundException(learnSetId));
        learnSet.setLastEdited();
        learnSetRepository.save(learnSet);
    }
}
