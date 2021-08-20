package br.com.domain.controller;

import br.com.domain.domain.CardSituation;
import br.com.domain.domain.ErrorDetail;
import br.com.domain.service.CardSituationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/card/situation", headers = {"Authorization=Bearer {token}"})
public class CardSituationController {

    @Autowired
    private CardSituationService service;

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "List datas about overall card situations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CardSituation[].class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
    })
    public Iterable<CardSituation> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "{id}", params = "id=1", produces = "application/json")
    @ApiOperation(value = "List datas about an specific card situation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CardSituation.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
    })
    public CardSituation findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Save datas of an specific card situation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CardSituation.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorDetail.class),
    })
    public CardSituation save(@Valid @RequestBody CardSituation cardSituation){
        return service.save(cardSituation);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Update datas of an specific card situation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CardSituation.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorDetail.class),
    })
    public CardSituation update(@Valid @RequestBody CardSituation cardSituation){
        return service.update(cardSituation);
    }

    @DeleteMapping(path ={"/{id}"}, params = "id=1", produces = "application/json")
    @ApiOperation(value = "Delete datas of an specific card situation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CardSituation.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorDetail.class),
            @ApiResponse(code = 404, message = "Bad Request", response = ErrorDetail.class),
    })
    public CardSituation delete(@PathVariable long id){
        return service.delete(id);
    }
}