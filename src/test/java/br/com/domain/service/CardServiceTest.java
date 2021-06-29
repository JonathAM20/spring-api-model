package br.com.domain.service;

import br.com.domain.domain.Card;
import br.com.domain.domain.CardSituation;
import br.com.domain.domain.Customer;
import br.com.domain.exception.ViolationConstraintException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardServiceTest {

    @Autowired
    CardService service;

    @Test
    void all() {
        AtomicReference<Long> idCard = new AtomicReference<>(1L);

        assertAll("save()",
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Card(null, null, new CardSituation(1L), new Customer(2L)));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Card(null, "0", new CardSituation(1L), new Customer(2L)));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Card(null, "0000000000000000", null, new Customer(2L)));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new Card(null, "0000000000000000", new CardSituation(1L), null));}),
                () -> assertDoesNotThrow(()->{idCard.set(service.save(new Card(null, "0000000000000000", new CardSituation(1L), new Customer(2L))).getId());}),
                () -> assertThrows(ViolationConstraintException.class, ()->{service.save(new Card(null, "0000000000000000", new CardSituation(1L), new Customer(2L)));})
        );

        assertAll("findById()",
                () -> assertThrows(NoSuchElementException.class, ()->{service.findById(1L);}),
                () -> assertDoesNotThrow(()->{service.findById(idCard.get());})
        );

        assertAll("findByCustomerId()",
                () -> assertTrue(service.findByCustomerId(2L).iterator().hasNext())
        );

        assertAll("findAll()",
                () -> assertTrue(service.findAll().iterator().hasNext())
        );

        assertAll("update()",
                () -> assertThrows(InvalidDataAccessApiUsageException.class, ()->{service.update(new Card(null, "0000000000000000", new CardSituation(1L), new Customer(2L)));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Card(idCard.get(), null, new CardSituation(1L), new Customer(2L)));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Card(idCard.get(), "0", new CardSituation(1L), new Customer(2L)));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Card(idCard.get(), "0000000000000000", null, new Customer(2L)));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new Card(idCard.get(), "0000000000000000", new CardSituation(1L), null));}),
                () -> assertDoesNotThrow(()->{idCard.set(service.update(new Card(idCard.get(), "0000000000000000", new CardSituation(1L), new Customer(2L))).getId());}),
                () -> assertThrows(ViolationConstraintException.class, ()->{service.update(new Card(idCard.get(), "1234567898765432", new CardSituation(1L), new Customer(2L)));}),
                () -> assertThrows(NoSuchElementException.class, ()->{service.update(new Card(1L, "0000000000000000", new CardSituation(1L), new Customer(2L)));})
        );

        assertAll("delete()",
                () -> assertThrows(NoSuchElementException.class, ()->{service.delete(1L);}),
                () -> assertDoesNotThrow(()->{service.delete(idCard.get());})
        );
    }
}