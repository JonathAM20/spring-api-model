package br.com.domain.resource.rest;

import br.com.domain.domain.Person;
import br.com.domain.exception.NotExistDaoException;
import br.com.domain.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/people",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class PessoaRestController {

    @Autowired
    private IPersonService service;

    @GetMapping
    public List<Person> list(){
        return service.list();
    }

    @GetMapping("{id}")
    public Person get(@PathVariable("id") Long id){
        return service.get(id);
    }

    @PostMapping
    public Person save(@RequestBody Person pessoa){
        return service.save(pessoa);
    }

    @PutMapping
    public Person update(@RequestBody Person pessoa){
        return service.update(pessoa);
    }

    @DeleteMapping
    public Person delete(@RequestBody Person pessoa){
        return service.delete(pessoa);
    }
}