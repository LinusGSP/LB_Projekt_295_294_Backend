package ch.project.quizme.controller;


import ch.project.quizme.databases.Language;
import ch.project.quizme.exceptions.LanguageNotFoundException;
import ch.project.quizme.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/language")
public class LanguageController {

    @Autowired
    private LanguageRepository languageRepository;

    @GetMapping(path = "")
    public ResponseEntity<Iterable<Language>> getAllLanguages(){
        Optional<Iterable<Language>> languages = Optional.of(languageRepository.findAll());
        return languages.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Language> getAllLanguageById(@Valid @PathVariable Integer id){
        Optional<Language> languages = languageRepository.findById(id);
        return ResponseEntity.ok(languages.orElseThrow(() -> new LanguageNotFoundException(id)));
    }


    @PostMapping(path = "")
    public ResponseEntity<String> createLanguage(@Valid @RequestParam String name,
                                                 @Valid @RequestParam String flag){

        Language language = new Language();
        language.setName(name);
        language.setFlag(flag);

        languageRepository.save(language);

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteLanguage(@Valid @PathVariable Integer id){
        languageRepository.deleteById(id);
        return ResponseEntity.ok("Success");

    }

}
