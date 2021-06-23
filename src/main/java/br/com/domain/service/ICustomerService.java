package br.com.domain.service;

import br.com.domain.domain.Customer;

import java.util.List;

public interface ICustomerService {

    Customer save(Customer customer);
    Customer update(Customer customer);
    Customer delete(Long id);
    Customer findById(Long id);
    List<Customer> findAll();
}