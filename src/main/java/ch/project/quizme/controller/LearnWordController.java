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

/**This class is the controller for the LearnWord entity.
 * This class contains the methods to get, create and delete a language.
 *
 * @author Linus Schönbächler
 * @version 1.0
 * @since 2022-10-03
 *
 */


@RestController
@RequestMapping(path = "/api/word")
public class LearnWordController {

    @Autowired
    LearnWordRepository learnWordRepository;

    @Autowired
    LearnSetRepository learnSetRepository;

    @GetMapping(path = "")
    public ResponseEntity<Iterable<LearnWord>> getAllWords() {
        Optional<Iterable<LearnWord>> words = Optional.of(learnWordRepository.findAll());
        return words.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LearnWord> getWord(@PathVariable("id") Integer id) {
        Optional<LearnWord> word = learnWordRepository.findById(id);
        return ResponseEntity.ok(word.orElseThrow(() -> new LearnWordNotFoundException(id)));
    }

    @GetMapping(path = "/set/{id}")
    public ResponseEntity<Iterable<LearnWord>> getLearnSetWords(@PathVariable("id") Integer id) {
        learnSetRepository.findById(id).orElseThrow(() -> new LearnSetNotFoundException(id));
        Iterable<LearnWord> words = learnWordRepository.findByLearnSetId(id);
        return ResponseEntity.ok(words);
    }

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

    @PostMapping(path = "/set")
    public ResponseEntity<String> createNewWords(@RequestBody Iterable<LearnWord> learnWords) {
        try {
            learnWordRepository.saveAll(learnWords);
        } catch (Exception e) {
            throw new LearnWordFailedToSaveException();
        }
        return ResponseEntity.ok("Success: saved");
    }

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

    @DeleteMapping(path = "")
    public ResponseEntity<String> deleteWord(@Valid @RequestBody LearnWord learnWord) {
        int id = learnWord.getId();
        learnWordRepository.findById(id).orElseThrow(() -> new LearnWordNotFoundException(id));

        learnWordRepository.deleteById(id);
        updateLearnSetLastEdited(learnWord.getLearnSetId());

        return ResponseEntity.ok("Success: deleted");
    }

    private void updateLearnSetLastEdited(int learnSetId){
        LearnSet learnSet = learnSetRepository.findById(learnSetId).orElseThrow(() -> new LearnSetNotFoundException(learnSetId));
        learnSet.setLastEdited();
        learnSetRepository.save(learnSet);
    }
}
