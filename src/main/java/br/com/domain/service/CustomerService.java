package br.com.domain.service;

import br.com.domain.domain.Customer;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CustomerService{

    @Autowired
    private CustomerRepository repository;

    public Customer save(Customer customer) {
        Customer save = repository.findByPersonalCode(customer.getPersonalCode());
        if(save != null) throw new ViolationConstraintException("personalCode-already exist");
        return repository.save(customer);
    }

    public Customer update(Customer customer) {
        try{
            Customer update = repository.findById(customer.getId()).get();
            if(update.getPersonalCode().equals(customer.getPersonalCode())){
                repository.save(customer);
            } else {
                Customer personalCodeValidation = repository.findByPersonalCode(customer.getPersonalCode());
                if(personalCodeValidation != null) throw new ViolationConstraintException("personalCode-already exist");
                repository.save(customer);
            }
        }catch (NoSuchElementException ex){
            throw new NoSuchElementException("id-invalid");
        }
        return customer;
    }

    public Customer delete(Long id) {
        try{
            Customer customer = repository.findById(id).get();
            repository.delete(customer);
            return customer;
        }catch (NoSuchElementException ex){
            throw new NoSuchElementException("id-invalid");
        }
    }

    public Customer findById(Long id) {
        try{
            return repository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new NoSuchElementException("id-invalid");
        }
    }

    public Iterable<Customer> findAll() {
        return repository.findAll();
    }
}