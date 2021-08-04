package br.com.domain.controller;

import br.com.domain.domain.User;
import br.com.domain.service.AuthenticateService;
import io.swagger.annotations.ApiKeyAuthDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@ApiKeyAuthDefinition(key = "Teste", in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER, name = "Teste 2")
public class AuthenticateController {

    @Autowired
    private AuthenticateService service;

    @PostMapping("/authenticate")
    public User generateToken(@Valid @RequestBody User user) throws Exception {
        return service.generateToken(user);
    }
}