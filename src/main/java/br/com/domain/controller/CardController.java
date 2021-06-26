package br.com.domain.controller;

import br.com.domain.domain.Card;
import br.com.domain.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
        value = "/card",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class CardController {

    @Autowired
    private CardService service;

    @GetMapping
    public List<Card> findAll(){
        return service.findAll();
    }

    @GetMapping("{id}")
    public Card findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping("/customer/{id}")
    public List<Card> findByCustomerId(@PathVariable("id") Long id){
        return service.findByCustomerId(id);
    }

    @PostMapping
    public Card save(@Valid @RequestBody Card card){
        return service.save(card);
    }

    @PutMapping
    public Card update(@Valid @RequestBody Card card){
        return service.update(card);
    }

    @DeleteMapping(path ={"/{id}"})
    public Card delete(@PathVariable long id){
        return service.delete(id);
    }
}