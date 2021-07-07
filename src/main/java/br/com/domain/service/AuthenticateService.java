package br.com.domain.service;

import br.com.domain.domain.User;
import br.com.domain.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {

    private final String INVALID_USERNAME = "username-invalid";
    private final String INVALID_PASSWORD = "password-invalid";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User generateToken(User user) throws Exception {
        User validationUser = userService.findByUsernameAndUserSituationId(user.getUsername(), 2L);
        if(validationUser != null){
            if(validationUser.getPassword().equals(user.getPassword())){
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
                user.setToken(jwtService.generateToken(user.getUsername()));
                user.setPassword(null);
            } else {
                throw new InvalidUserException(INVALID_PASSWORD);
            }
        } else {
            throw new InvalidUserException(INVALID_USERNAME);
        }

        return user;
    }
}