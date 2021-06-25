package br.com.domain.service;

import br.com.domain.domain.Customer;
import br.com.domain.exception.NotExistException;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService{

    @Autowired
    private ICustomerRepository repository;

    @Override
    public Customer save(Customer customer) {
        Customer save = repository.findByPersonalCode(customer.getPersonalCode());
        if(save != null) throw new ViolationConstraintException("Customer already exist: " + customer.getPersonalCode());
        return repository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        Customer update = repository.findById(customer.getId());
        if(update != null){
            repository.update(customer);
        } else {
            throw new NotExistException("Id: " + customer.getId());
        }
        return customer;
    }

    @Override
    public Customer delete(Long id) {
        Customer customer = repository.findById(id);
        if(customer == null) throw new NotExistException("Id: " + id);
        repository.delete(customer);
        return customer;
    }

    public Customer findById(Long id) {
        Customer customer = repository.findById(id);
        if(customer == null) throw new NotExistException("Id: " + id);
        return customer;
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }
}