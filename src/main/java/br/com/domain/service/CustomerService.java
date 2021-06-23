package br.com.domain.service;

import br.com.domain.domain.Customer;
import br.com.domain.exception.NotExistException;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements ICustomerService{

    @Autowired
    private ICustomerRepository repository;

    @Override
    public Customer save(Customer customer) {
        Customer save = repository.findByPersonalCode(customer.getPersonalCode());
        if(save != null) throw new ViolationConstraintException("Customer already exist: " + customer.getPersonalCode());

//        List<String> nullProperties = new ArrayList<>();
//        if(customer.getPersonalCode().isEmpty() || customer.getPersonalCode() == null) nullProperties.add("personalCode");
//        if(customer.getFirstName().isEmpty() || customer.getFirstName() == null) nullProperties.add("fisrtName");
//        if(customer.getLastName().isEmpty() || customer.getLastName() == null) nullProperties.add("lastName");
//        if(nullProperties.size() > 0) throw new NullPointerException("Required properties: " + nullProperties.toString());

        return repository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        Customer update = repository.findById(customer.getId());
        if(update != null){

//            List<String> nullProperties = new ArrayList<>();
//            if(customer.getPersonalCode().isEmpty() || customer.getPersonalCode() == null) nullProperties.add("personalCode");
//            if(customer.getFirstName().isEmpty() || customer.getFirstName() == null) nullProperties.add("fisrtName");
//            if(customer.getLastName().isEmpty() || customer.getLastName() == null) nullProperties.add("lastName");
//            if(nullProperties.size() > 0) throw new NullPointerException("Required properties: " + nullProperties.toString());

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