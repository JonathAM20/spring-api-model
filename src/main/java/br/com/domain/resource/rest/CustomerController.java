package br.com.domain.resource.rest;

import br.com.domain.domain.Customer;
import br.com.domain.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
        value = "/customer",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public List<Customer> findAll(){
        return service.findAll();
    }

    @GetMapping("{id}")
    public Customer get(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping
    public Customer save(@Valid @RequestBody Customer customer){
        return service.save(customer);
    }

    @PutMapping
    public Customer update(@Valid @RequestBody Customer customer){
        return service.update(customer);
    }

    @DeleteMapping(path ={"/{id}"})
    public Customer delete(@PathVariable long id){
        return service.delete(id);
    }
}