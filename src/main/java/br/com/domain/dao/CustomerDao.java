package br.com.domain.dao;

import br.com.domain.domain.Customer;
import br.com.domain.domain.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CustomerDao implements ICustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer save(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        entityManager.merge(customer);
        return customer;
    }

    @Override
    public Customer delete(Customer customer) {
        if (entityManager.contains(customer)) {
            entityManager.remove(customer);
        } else {
            entityManager.remove(entityManager.merge(customer));
        }
        return customer;
    }

    @Override
    public Customer get(Long id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public List<Customer> list() {
        String jpql = "select c from Customer c";
        Query query = entityManager.createQuery(jpql);
        return (List<Customer>) query.getResultList();
    }
}