package br.com.domain.repository;

import br.com.domain.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByPersonalCode(String personalCode);
}