package ch.project.quizme.controller;


import ch.project.quizme.databases.LearnWord;
import ch.project.quizme.exceptions.LanguageNotFoundException;
import ch.project.quizme.exceptions.LearnWordFailedToSaveException;
import ch.project.quizme.exceptions.LearnWordNotFoundException;
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
        Optional<Iterable<LearnWord>> words = Optional.of(learnWordRepository.findByLearnSetId(id));
        return words.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "")
    public ResponseEntity<LearnWord> createNewWord(@Valid @RequestBody LearnWord learnWord) {
        LearnWord word;
        try {
            word = learnWordRepository.save(learnWord);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new LearnWordFailedToSaveException();
        }
        System.out.println(word.getId());
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

    @PutMapping(path = "/{id}")
    public ResponseEntity<LearnWord> updateWord(@PathVariable("id") Integer id, @Valid @RequestBody LearnWord learnWord) {
        LearnWord updatedWord = learnWordRepository.findById(id).orElseThrow(() -> new LanguageNotFoundException(id));

        try{
            updatedWord.setWord(learnWord.getWord());
            updatedWord.setTranslation(learnWord.getTranslation());
            learnWordRepository.save(updatedWord);

        } catch(Exception ex) {
            throw new LearnWordNotFoundException(id);
        }
        return ResponseEntity.ok(updatedWord);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteWord(@PathVariable("id") Integer id) {
        try {
            learnWordRepository.deleteById(id);
        } catch (Exception e) {
            throw new LearnWordNotFoundException(id);
        }
        return ResponseEntity.ok("Success: deleted");
    }
}
