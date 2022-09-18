package ch.project.quizme.controller;


import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.databases.Word;
import ch.project.quizme.exceptions.LearnSetNotFoundException;
import ch.project.quizme.exceptions.WordNotFoundException;
import ch.project.quizme.repository.LearnSetRepository;
import ch.project.quizme.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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

    @GetMapping(path = "/{word_id}")
    public ResponseEntity<Word> getWord(@Valid @PathVariable("word_id") Integer word_id){
        Optional<Word> word = wordRepository.findById(word_id);
        return ResponseEntity.ok(word.orElseThrow(() -> new WordNotFoundException(word_id)));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Iterable<Word>> getLearnSetWords(@Valid @PathVariable("id") Integer id){
        Optional<Iterable<Word>> words = Optional.of(wordRepository.findLearnSetWords(id));
        return words.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<String> createNewWord(@Valid @PathVariable("id") Integer id,
                                                @Valid @RequestParam String word1,
                                                @Valid @RequestParam String word2){

        Optional<LearnSet> learnSet = learnSetRepository.findById(id);

        if (learnSet.isEmpty()) {throw new LearnSetNotFoundException(id); }

        Word word = new Word();
        word.setLearnSetId(id);
        word.setWord1(word1);
        word.setWord2(word2);
        word.setMarked(false);

        Word createdWord = wordRepository.save(word);

        return ResponseEntity.ok("Success: id="+ createdWord.getId() + ", learnSet=" + id + ", word1=" + word1 + ", word2=" + word2);
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
