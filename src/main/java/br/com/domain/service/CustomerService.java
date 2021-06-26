package br.com.domain.service;

import br.com.domain.domain.Customer;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService{

    @Autowired
    private CustomerRepository repository;

    public Customer save(Customer customer) {
        Customer save = repository.findByPersonalCode(customer.getPersonalCode());
        if(save != null) throw new ViolationConstraintException("Customer already exist: " + customer.getPersonalCode());
        return repository.save(customer);
    }

    public Customer update(Customer customer) {
        Customer update = repository.findById(customer.getId()).get();
        if(update.getPersonalCode().equals(customer.getPersonalCode())){
            repository.save(customer);
        } else {
            Customer personalCodeValidation = repository.findByPersonalCode(customer.getPersonalCode());
            if(personalCodeValidation != null) throw new ViolationConstraintException("Customer already exist: " + customer.getPersonalCode());
            repository.save(customer);
        }
        return customer;
    }

    public Customer delete(Long id) {
        Customer customer = repository.findById(id).get();
        repository.delete(customer);
        return customer;
    }

    public Customer findById(Long id) {
        return repository.findById(id).get();
    }

    public Iterable<Customer> findAll() {
        return repository.findAll();
    }
}