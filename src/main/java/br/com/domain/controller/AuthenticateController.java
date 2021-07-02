package br.com.domain.controller;

import br.com.domain.domain.User;
import br.com.domain.service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticateController {

    @Autowired
    private AuthenticateService service;

    @PostMapping("/authenticate")
    public User generateToken(@RequestBody User user) throws Exception {
        return service.generateToken(user);
    }
}