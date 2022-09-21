package ch.project.quizme.controller;


import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.databases.LearnWord;
import ch.project.quizme.exceptions.LearnSetNotFoundException;
import ch.project.quizme.exceptions.WordFailedToSaveException;
import ch.project.quizme.exceptions.WordNotFoundException;
import ch.project.quizme.repository.LearnSetRepository;
import ch.project.quizme.repository.LearnWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/word")
public class LearnWordController {

    @Autowired
    LearnWordRepository learnWordRepository;

    @Autowired
    LearnSetRepository learnSetRepository;

    @GetMapping(path = "")
    public ResponseEntity<Iterable<LearnWord>> getAllWords(){
        Optional<Iterable<LearnWord>> words = Optional.of(learnWordRepository.findAll());
        return words.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LearnWord> getWord(@PathVariable("id") Integer id){
        Optional<LearnWord> word = learnWordRepository.findById(id);
        return ResponseEntity.ok(word.orElseThrow(() -> new WordNotFoundException(id)));
    }

    @GetMapping(path = "/set/{id}")
    public ResponseEntity<Iterable<LearnWord>> getLearnSetWords(@PathVariable("id") Integer id){
        Optional<Iterable<LearnWord>> words = Optional.of(learnWordRepository.findByLearnSetId(id));
        return words.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<String> createNewWord(@PathVariable("id") Integer id, @RequestParam String translation, @RequestParam String word){
        LearnWord learnWord = LearnWordFactory(id, translation, word);
        try {
            learnWordRepository.save(learnWord);
        } catch (Exception e){
            throw new WordFailedToSaveException();
        }
        return ResponseEntity.ok("Success: saved");
    }

    @PostMapping(path = "")
    public ResponseEntity<String> createNewWords(@RequestBody Iterable<LearnWord> words){
        Iterable<LearnWord> LearnWords = LearnWordsFactory(words);
        try {
            learnWordRepository.saveAll(LearnWords);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new WordFailedToSaveException();
        }
        return ResponseEntity.ok("Success: saved");
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteWord(@PathVariable("id") Integer id){
        try {
            learnWordRepository.deleteById(id);
        } catch (Exception e){
            throw new WordNotFoundException(id);
        }
        return ResponseEntity.ok("Success: deleted");
    }


    private Iterable<LearnWord> LearnWordsFactory(Iterable<LearnWord> words){
         Set<LearnWord> newLearnWords = new HashSet<>();

        for (LearnWord learnWord : words) {
            LearnWord newLearnWord =  LearnWordFactory(
                    learnWord.getLearnSetId(),
                    learnWord.getTranslation(),
                    learnWord.getWord()
            );
            newLearnWords.add(newLearnWord);
        }
        return newLearnWords;
    }

    private LearnWord LearnWordFactory(Integer learnSetId, String translation, String word){
        LearnSet learnSet = learnSetRepository.findById(learnSetId).orElseThrow(() -> new LearnSetNotFoundException(learnSetId));

        LearnWord newLearnWord = new LearnWord();
        newLearnWord.setLearnSetId(learnSetId);
        newLearnWord.setLearnSet(learnSet);
        newLearnWord.setTranslation(translation);
        newLearnWord.setWord(word);
        newLearnWord.setMarked(false);

        return newLearnWord;
    }
}
