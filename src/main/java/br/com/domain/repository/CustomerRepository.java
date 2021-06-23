package br.com.domain.repository;

import br.com.domain.domain.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CustomerRepository implements ICustomerRepository {

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
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public Customer findByPersonalCode(String personalCode) {
        Customer customer = null;
        String jpql = "select c from Customer c WHERE c.personalCode = '" + personalCode + "'";
        Query query = entityManager.createQuery(jpql);
        try{
            customer = (Customer) query.getSingleResult();
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String jpql = "select c from Customer c";
        Query query = entityManager.createQuery(jpql);
        return (List<Customer>) query.getResultList();
    }
}