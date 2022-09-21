package ch.project.quizme.controller;

import ch.project.quizme.databases.Language;
import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.exceptions.LanguageIdenticalException;
import ch.project.quizme.exceptions.LanguageNotFoundException;
import ch.project.quizme.exceptions.LearnSetNotFoundException;
import ch.project.quizme.exceptions.LearnSetFailedToSaveException;
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
    public ResponseEntity<String> createLearnSet(@RequestParam String name, @RequestParam Integer language1_id, @RequestParam Integer language2_id) {

        if (language1_id.equals(language2_id)){ throw new LanguageIdenticalException(language1_id, language2_id);}

        Language language1 = languageRepository.findById(language1_id).orElseThrow(() -> new LanguageNotFoundException((language1_id)));
        Language language2 = languageRepository.findById(language2_id).orElseThrow(() -> new LanguageNotFoundException((language2_id)));

        LearnSet learnSet = new LearnSet();
        learnSet.setName(name);
        learnSet.setLanguage1(language1);
        learnSet.setLanguage2(language2);
        learnSet.setCreationDate();
        learnSet.setLastEdited();

        try {
            learnSetRepository.save(learnSet);
        } catch (Exception e){
            throw new LearnSetFailedToSaveException(name);
        }
        return ResponseEntity.ok(
                "Success: saved");
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
