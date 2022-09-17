package ch.project.quizme.controller;

import ch.project.quizme.databases.Language;
import ch.project.quizme.databases.LearnSet;
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

    @GetMapping(path = "") // map only GET request
    public ResponseEntity<Iterable<LearnSet>> getAllLearnSets() {
        Optional<Iterable<LearnSet>> learnSets = Optional.of(learnSetRepository.findAll());
        return learnSets.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<LearnSet> getLearnSetById(@Valid @PathVariable("id") Integer id){
        Optional<LearnSet> learnSet = learnSetRepository.findById(id);
        return learnSet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @PostMapping(path = "")
    public ResponseEntity<String> createLearnSet(@Valid @RequestBody String name,
                                                 @Valid @RequestBody Language first_language_id,
                                                 @Valid @RequestBody Language second_language_id){
        LearnSet learnSet = new LearnSet();
        learnSet.setName(name);
        learnSet.setFirstLanguage(first_language_id);
        learnSet.setSecondLanguage(second_language_id);

        learnSetRepository.save(learnSet);

        return ResponseEntity.ok("Success");
    }
}
