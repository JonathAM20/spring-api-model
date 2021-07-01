package br.com.domain.service;

import br.com.domain.domain.User;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        User save = repository.findByUsername(user.getUsername());
        if(save != null) throw new ViolationConstraintException("User already exist: " + user.getUsername());
        return repository.save(user);
    }

    public User update(User user) {
        User update = repository.findById(user.getId()).get();
        if(update.getUsername().equals(user.getUsername())){
            repository.save(user);
        } else {
            User usernameValidation = repository.findByUsername(user.getUsername());
            if(usernameValidation != null) throw new ViolationConstraintException("User already exist: " + user.getUsername());
            repository.save(user);
        }
        return user;
    }

    public User delete(Long id) {
        User user = repository.findById(id).get();
        repository.delete(user);
        return user;
    }

    public User findById(Long id) {
        return repository.findById(id).get();
    }

    public Iterable<User> findAll() {
        return repository.findAll();
    }
}