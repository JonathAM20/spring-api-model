package br.com.domain.service;

import br.com.domain.domain.User;
import br.com.domain.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User generateToken(User user) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (Exception ex) {
            throw new InvalidUserException("User: " + user.getUsername());
        }

        user.setToken(jwtService.generateToken(user.getUsername()));
        user.setPassword(null);

        return user;
    }
}