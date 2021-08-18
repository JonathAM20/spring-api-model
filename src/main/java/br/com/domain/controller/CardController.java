package br.com.domain.controller;

import br.com.domain.domain.Card;
import br.com.domain.domain.ErrorDetail;
import br.com.domain.service.CardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/card", headers = {"Authorization=Bearer {token}"})
public class CardController {

    @Autowired
    private CardService service;

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "List datas about overall cards")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Card[].class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
    })
    public Iterable<Card> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "{id}", params = "id=1", produces = "application/json")
    @ApiOperation(value = "List datas about an specific card")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
    })
    public Card findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    //@GetMapping("/customer/{id}")
    @GetMapping(value = "/customer/{id}", params = "id=1", produces = "application/json")
    @ApiOperation(value = "List cards of an specific customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
    })
    public List<Card> findByCustomerId(@PathVariable("id") Long id){
        return service.findByCustomerId(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Save datas of an specific card")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorDetail.class),
    })
    public Card save(@Valid @RequestBody Card card){
        return service.save(card);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Update datas of an specific card")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorDetail.class),
    })
    public Card update(@Valid @RequestBody Card card){
        return service.update(card);
    }

    @DeleteMapping(path ={"/{id}"}, params = "id=1", produces = "application/json")
    @ApiOperation(value = "Delete datas of an specific customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
    })
    public Card delete(@PathVariable long id){
        return service.delete(id);
    }
}