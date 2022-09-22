package ch.project.quizme.controller;

import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.exceptions.LearnSetNotFoundException;
import ch.project.quizme.exceptions.LearnWordFailedToSaveException;
import ch.project.quizme.repository.LanguageRepository;
import ch.project.quizme.repository.LearnSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<LearnSet> getLearnSetById(@PathVariable("id") Integer id){
        Optional<LearnSet> learnSet = learnSetRepository.findById(id);
        return ResponseEntity.ok(learnSet.orElseThrow(() -> new LearnSetNotFoundException(id)));
    }

    @PostMapping(path = "")
    public ResponseEntity<String> createLearnSet(@RequestBody LearnSet learnSet){
        try {
            learnSetRepository.save(learnSet);
        } catch (Exception e){
            throw new LearnWordFailedToSaveException();
        }
        return ResponseEntity.ok("Success: saved");
    }

    // TODO ALSO DELETE WORDS IN LEARNSET
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteLearnSetById(@PathVariable("id") Integer id){
        try {
            learnSetRepository.deleteById(id);
        } catch (Exception e){
            throw new LearnSetNotFoundException(id);
        }
        return ResponseEntity.ok("Success: deleted");
    }
}
