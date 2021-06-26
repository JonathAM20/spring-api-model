package br.com.domain.controller;

import br.com.domain.domain.CardSituation;
import br.com.domain.service.CardSituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/card/situation")
public class CardSituationController {

    @Autowired
    private CardSituationService service;

    @GetMapping
    public Iterable<CardSituation> findAll(){
        return service.findAll();
    }

    @GetMapping("{id}")
    public CardSituation findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping
    public CardSituation save(@Valid @RequestBody CardSituation cardSituation){
        return service.save(cardSituation);
    }

    @PutMapping
    public CardSituation update(@Valid @RequestBody CardSituation cardSituation){
        return service.update(cardSituation);
    }

    @DeleteMapping(path ={"/{id}"})
    public CardSituation delete(@PathVariable long id){
        return service.delete(id);
    }
}