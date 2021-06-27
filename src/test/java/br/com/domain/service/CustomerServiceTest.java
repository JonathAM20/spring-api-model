package br.com.domain.service;

import br.com.domain.domain.Customer;
import br.com.domain.exception.ViolationConstraintException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    @Test
    void all() {
        AtomicReference<Long> idCustomer = new AtomicReference<>(1L);

        assertAll("save()",
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Customer(null, null,"firstName","lastName"));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Customer(null, "testCode",null,"lastName"));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Customer(null, "testCode","firstName",null));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Customer(null, "testCodetest","firstName","lastName"));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Customer(null, "testCode","f","lastName"));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Customer(null, "testCode","firstNameFirstNameFirstNameFirstNameFirstNameFirstName","lastName"));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Customer(null, "testCode","firstName","l"));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Customer(null, "testCode","firstName","lastNameLastNameLastNameLastNameLastNameLastNameLastName"));}),
                () -> assertDoesNotThrow(()->{idCustomer.set(service.save(new Customer(null, "testCode", "firstName", "lastName")).getId());}),
                () -> assertThrows(ViolationConstraintException.class, ()->{service.save(new Customer(null, "testCode","firstName","lastName"));})
        );

        assertAll("findById()",
                () -> assertThrows(NoSuchElementException.class, ()->{service.findById(1L);}),
                () -> assertDoesNotThrow(()->{service.findById(idCustomer.get());})
        );

        assertAll("findAll()",
                () -> assertTrue(service.findAll().iterator().hasNext())
        );

        assertAll("update()",
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Customer(idCustomer.get(), null, "firstName","lastName"));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Customer(idCustomer.get(), "testCodetest", "firstName","lastName"));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Customer(idCustomer.get(), "testCode", null,"lastName"));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Customer(idCustomer.get(), "testCode", "f","lastName"));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Customer(idCustomer.get(), "testCode", "firstNameFirstNameFirstNameFirstNameFirstNameFirstName","lastName"));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Customer(idCustomer.get(), "testCode", "firstName",null));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Customer(idCustomer.get(), "testCode", "firstName","l"));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Customer(idCustomer.get(), "testCode", "firstName","lastNameLastNameLastNameLastNameLastNameLastNameLastName"));}),
                () -> assertDoesNotThrow(()->{service.update(new Customer(idCustomer.get(), "testCode", "firstName","lastName"));}),
                () -> assertThrows(ViolationConstraintException.class, ()->{service.update(new Customer(idCustomer.get(), "99501651215", "firstName","lastName"));}),
                () -> assertThrows(NoSuchElementException.class, ()->{service.update(new Customer(1L, "testCode", "firstName","lastName"));})
        );

        assertAll("delete()",
                () -> assertThrows(NoSuchElementException.class, ()->{service.delete(1L);}),
                () -> assertDoesNotThrow(()->{service.delete(idCustomer.get());})
        );
    }
}