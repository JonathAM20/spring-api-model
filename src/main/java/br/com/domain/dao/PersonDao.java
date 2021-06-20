package br.com.domain.dao;

import br.com.domain.domain.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PersonDao implements IPersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Person save(Person person) {
        entityManager.persist(person);
        return person;
    }

    @Override
    public Person update(Person person) {
        entityManager.merge(person);
        return person;
    }

    @Override
    public Person delete(Person person) {
        entityManager.remove(person);
        return person;
    }

    @Override
    public Person get(Long id) {
        String jpql = "SELECT p FROM Person p WHERE p.id = " + id + "";
        Query query = entityManager.createQuery(jpql);
        return (Person) query.getSingleResult();
    }

    @Override
    public List<Person> list() {
        String jpql = "select p from Person p";
        Query query = entityManager.createQuery(jpql);
        return (List<Person>) query.getResultList();
    }
}