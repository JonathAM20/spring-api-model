package br.com.domain.resource.rest;

import br.com.domain.domain.Person;
import br.com.domain.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(
//        value = "/people",
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
//)
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping
    public List<Person> list(){
        return service.list();
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> get(@PathVariable("id") Long id){
        return service.get(id);
    }

    @PostMapping
    public Person save(@RequestBody Person pessoa){
        return service.save(pessoa);
    }

    @PutMapping
    public ResponseEntity<Person> update(@RequestBody Person pessoa){
        return service.update(pessoa);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id){
        return service.delete(id);
    }
}