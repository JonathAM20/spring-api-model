package br.com.domain.dao;

import br.com.domain.domain.Customer;

import java.util.List;

public interface ICustomerDao {

    Customer save(Customer customer);
    Customer update(Customer customer);
    Customer delete(Customer customer);
    Customer get(Long id);
    List<Customer> list();
}