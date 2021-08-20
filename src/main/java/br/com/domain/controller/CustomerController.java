package br.com.domain.controller;

import br.com.domain.domain.Customer;
import br.com.domain.domain.ErrorDetail;
import br.com.domain.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/customer", headers = {"Authorization=Bearer {token}"})
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "List datas about overall customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Customer[].class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
    })
    public Iterable<Customer> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "{id}", params = "id=1", produces = "application/json")
    @ApiOperation(value = "List datas about an specific customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
    })
    public Customer get(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Save datas of an specific customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Customer.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorDetail.class),
    })
    public Customer save(@Valid @RequestBody Customer customer){
        return service.save(customer);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Update datas of an specific customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorDetail.class),
    })
    public Customer update(@Valid @RequestBody Customer customer){
        return service.update(customer);
    }

    @DeleteMapping(path ={"/{id}"}, params = "id=1", produces = "application/json")
    @ApiOperation(value = "Delete datas of an specific customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
    })
    public Customer delete(@PathVariable long id){
        return service.delete(id);
    }
}