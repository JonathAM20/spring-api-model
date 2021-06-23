package br.com.domain.dao;

import br.com.domain.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

//    Person save(Person person);
//    Person update(Person person);
//    Person delete(Person person);
//    Person get(Long id);
//    List<Person> list();

    Person findByFirstName(String firstName);
    Person findByLastName(String lastName);
}