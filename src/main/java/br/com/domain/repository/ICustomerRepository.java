package br.com.domain.repository;

import br.com.domain.domain.Customer;

import java.util.List;

public interface ICustomerRepository {

    Customer save(Customer customer);
    Customer update(Customer customer);
    Customer delete(Customer customer);
    Customer findById(Long id);
    Customer findByPersonalCode(String personalCode);
    List<Customer> findAll();
}