package br.com.domain.resource.rest;

import br.com.domain.domain.CardSituation;
import br.com.domain.service.CardSituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
        value = "/card/situation",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class CardSituationController {

    @Autowired
    private CardSituationService service;

    @GetMapping
    public List<CardSituation> findAll(){
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