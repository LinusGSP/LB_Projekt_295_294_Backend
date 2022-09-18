package ch.project.quizme.controller;

import ch.project.quizme.databases.Language;
import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.exceptions.IdenticalLanguageException;
import ch.project.quizme.exceptions.LanguageNotFoundException;
import ch.project.quizme.exceptions.LearnSetCouldNotBeFoundException;
import ch.project.quizme.exceptions.LearnSetCouldNotBeSavedException;
import ch.project.quizme.repository.LanguageRepository;
import ch.project.quizme.repository.LearnSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<LearnSet> getLearnSetById(@Valid @PathVariable("id") Integer id){
        Optional<LearnSet> learnSet = learnSetRepository.findById(id);
        return ResponseEntity.ok(learnSet.orElseThrow(() -> new LearnSetCouldNotBeFoundException(id)));
    }

    @PostMapping(path = "")
    public ResponseEntity<String> createLearnSet(@Valid @RequestParam String name,
                                                 @Valid @RequestParam Integer language1_id,
                                                 @Valid @RequestParam Integer language2_id) {


        if (language1_id.equals(language2_id)){ throw new IdenticalLanguageException(language1_id, language2_id);}

        Optional<Language> language1 = languageRepository.findById(language1_id);
        Optional<Language> language2 = languageRepository.findById(language2_id);

        LearnSet learnSet = new LearnSet();
        learnSet.setName(name);
        learnSet.setLanguage1(language1.orElseThrow(() -> new LanguageNotFoundException((language1_id))));
        learnSet.setLanguage2(language2.orElseThrow(() -> new LanguageNotFoundException((language2_id))));
        learnSet.setCreationDate();
        learnSet.setLastEdited();

        try {
            learnSetRepository.save(learnSet);
        } catch (Exception e){
            throw new LearnSetCouldNotBeSavedException(name);
        }
        return ResponseEntity.ok(
                "Success: Created new LearnSet with: " +
                        "name="+name+
                        ",language1_id=" + language1_id +
                        ",language2_id=" + language2_id);
    }
}
