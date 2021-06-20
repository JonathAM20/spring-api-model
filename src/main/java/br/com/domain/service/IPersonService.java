package br.com.domain.service;

import br.com.domain.domain.Person;

import java.util.List;

public interface IPersonService {

    Person save(Person person);
    Person update(Person person);
    Person delete(Person person);
    Person get(Long id);
    List<Person> list();
}
