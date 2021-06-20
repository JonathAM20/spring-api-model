package br.com.domain.service;

import br.com.domain.dao.IPersonDao;
import br.com.domain.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService implements IPersonService {

    @Autowired
    private IPersonDao dao;

    @Override
    public Person save(Person person) {
        return dao.save(person);
    }

    @Override
    public Person update(Person person) {
        return dao.update(person);
    }

    @Override
    public Person delete(Person person) {
        return dao.delete(person);
    }

    @Override
    public Person get(Long id) {
        return dao.get(id);
    }

    @Override
    public List<Person> list() {
        return dao.list();
    }
}