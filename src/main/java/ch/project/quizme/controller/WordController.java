package ch.project.quizme.controller;


import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.databases.LearnWord;
import ch.project.quizme.exceptions.LearnSetNotFoundException;
import ch.project.quizme.exceptions.WordFailedToSaveException;
import ch.project.quizme.exceptions.WordNotFoundException;
import ch.project.quizme.repository.LearnSetRepository;
import ch.project.quizme.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/word")
public class WordController {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    LearnSetRepository learnSetRepository;

    @GetMapping(path = "")
    public ResponseEntity<Iterable<LearnWord>> getAllWords(){
        Optional<Iterable<LearnWord>> words = Optional.of(wordRepository.findAll());
        return words.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LearnWord> getWord(@PathVariable("id") Integer id){
        Optional<LearnWord> word = wordRepository.findById(id);
        return ResponseEntity.ok(word.orElseThrow(() -> new WordNotFoundException(id)));
    }

    @GetMapping(path = "/set/{id}")
    public ResponseEntity<Iterable<LearnWord>> getLearnSetWords(@PathVariable("id") Integer id){
        Optional<Iterable<LearnWord>> words = Optional.of(wordRepository.findByLearnSetId(id));
        return words.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<String> createNewWord(@PathVariable("id") Integer id,
                                                @RequestParam String translation,
                                                @RequestParam String word){

        LearnSet learnSet = learnSetRepository.findById(id).orElseThrow(() -> new LearnSetNotFoundException(id));

        LearnWord learnWord = new LearnWord();
        learnWord.setLearnSet(learnSet);
        learnWord.setLearnSetId(id);
        learnWord.setTranslation(translation);
        learnWord.setWord(word);
        learnWord.setMarked(false);

        wordRepository.save(learnWord);

        return ResponseEntity.ok("Success: saved");
    }

    @PostMapping(path = "")
    public ResponseEntity<String> createNewWords(@RequestBody Iterable<LearnWord> words){

        for (LearnWord learnWord : words){
            Integer learnSetId = learnWord.getLearnSetId();
            LearnSet learnSet = learnSetRepository.findById(learnSetId).orElseThrow(() -> new WordNotFoundException(learnSetId));
            String translation = learnWord.getTranslation();
            String word = learnWord.getTranslation();
            Boolean marked = learnWord.getMarked();

            LearnWord newLearnWord = new LearnWord();
            newLearnWord.setLearnSetId(learnSetId);
            newLearnWord.setLearnSet(learnSet);
            newLearnWord.setTranslation(translation);
            newLearnWord.setWord(word);
            newLearnWord.setMarked(marked);

            try {
                wordRepository.save(newLearnWord);
            } catch (Exception e){
                throw new WordFailedToSaveException();
            }
        }
        return ResponseEntity.ok("Success: saved");
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteWord(@PathVariable("id") Integer id){
        try {
            wordRepository.deleteById(id);
        } catch (Exception e){
            throw new WordNotFoundException(id);
        }
        return ResponseEntity.ok("Success: deleted");
    }
}
