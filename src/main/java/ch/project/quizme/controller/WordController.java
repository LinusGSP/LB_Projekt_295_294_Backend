package ch.project.quizme.controller;


import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.databases.Word;
import ch.project.quizme.exceptions.LearnSetNotFoundException;
import ch.project.quizme.exceptions.WordFailedSaveException;
import ch.project.quizme.exceptions.WordNotFoundException;
import ch.project.quizme.repository.LearnSetRepository;
import ch.project.quizme.repository.WordRepository;
import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/word")
public class WordController {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    LearnSetRepository learnSetRepository;

    @GetMapping(path = "")
    public ResponseEntity<Iterable<Word>> getAllWords(){
        Optional<Iterable<Word>> words = Optional.of(wordRepository.findAll());
        return words.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Word> getWord(@Valid @PathVariable("id") Integer word_id){
        Optional<Word> word = wordRepository.findById(word_id);
        return ResponseEntity.ok(word.orElseThrow(() -> new WordNotFoundException(word_id)));
    }

    @GetMapping(path = "/set/{id}")
    public ResponseEntity<Iterable<Word>> getLearnSetWords(@Valid @PathVariable("id") Integer id){
        Optional<Iterable<Word>> words = Optional.of(wordRepository.findByLearnSetId(id));
        return words.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<String> createNewWord(@Valid @PathVariable("id") Integer id,
                                                @Valid @RequestParam String word1,
                                                @Valid @RequestParam String word2){

        Optional<LearnSet> learnSet = learnSetRepository.findById(id);

        if (learnSet.isEmpty()) {throw new LearnSetNotFoundException(id); }

        Word word = new Word();
        word.setLearnSet(learnSetRepository.findById(id).orElseThrow(() -> new LearnSetNotFoundException(id)));
        word.setLearnSetId(id);
        word.setWord1(word1);
        word.setWord2(word2);
        word.setMarked(false);

        wordRepository.save(word);

        return ResponseEntity.ok("Success: saved");
    }

    @PostMapping(path = "")
    public ResponseEntity<String> createNewWords(@RequestBody Iterable<Word> words){

        for (Word word: words){
            Integer learnSetId = word.getLearnSetId();
            LearnSet learnSet = learnSetRepository.findById(learnSetId).orElseThrow(() -> new WordNotFoundException(learnSetId));
            String word1 = word.getWord1();
            String word2 = word.getWord1();
            Boolean marked = word.getMarked();

            Word newWord = new Word();
            newWord.setLearnSetId(learnSetId);
            newWord.setLearnSet(learnSet);
            newWord.setWord1(word1);
            newWord.setWord2(word2);
            newWord.setMarked(marked);

            try {
                wordRepository.save(newWord);
            } catch (Exception e){
                throw new WordFailedSaveException();
            }
        }
        return ResponseEntity.ok("Success: saved");
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteWord(@Valid @PathVariable("id") Integer id){
        try {
            wordRepository.deleteById(id);
        } catch (IllegalArgumentException e){
            throw new WordNotFoundException(id);
        }
        return ResponseEntity.ok("Success: Deleted word with id=" + id);
    }
}
