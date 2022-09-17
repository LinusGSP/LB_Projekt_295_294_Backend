package ch.project.quizme.controller;

import ch.project.quizme.databases.LearnSet;
import ch.project.quizme.repository.LearnSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/learnset")
public class LearnSetController {

    @Autowired
    private LearnSetRepository learnSetRepository;

    @GetMapping(path = "") // map only GET request
    public ResponseEntity<Iterable<LearnSet>> getAllLearnSets(){


        Optional<Iterable<LearnSet>> learnSets = Optional.of(learnSetRepository.findAll());

        return learnSets.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

}
