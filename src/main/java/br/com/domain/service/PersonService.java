package br.com.domain.service;

import br.com.domain.dao.PersonRepository;
import br.com.domain.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public Person save(Person person) {
//        try{
//            repository.save(person);
//        }catch (Exception ex){
//            throw new NotExistDaoException(ex.getLocalizedMessage());
//        }
//        return person;

        return repository.save(person);
    }

    public ResponseEntity<Person> update(Person person) {
        return repository.findById(person.getId())
                .map(record -> {
                    record.setFirstName(person.getFirstName());
                    record.setLastName(person.getLastName());
                    Person updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> delete(Long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Person> get(Long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    public List<Person> list() {
        return repository.findAll();
    }
}