package br.com.domain.service;

import br.com.domain.domain.CardSituation;
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
class CardSituationServiceTest {

    @Autowired
    CardSituationService service;

    @Test
    void all(){
        AtomicReference<Long> idCardSituation = new AtomicReference<>(1L);

        assertAll("save()",
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new CardSituation(null, null));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new CardSituation(null, "T"));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new CardSituation(null, "TESTETESTETESTETESTETESTETESTETESTETESTETESTETESTETESTE"));}),
                () -> assertDoesNotThrow(()->{idCardSituation.set(service.save(new CardSituation(null, "TESTE")).getId());}),
                () -> assertThrows(ViolationConstraintException.class, ()->{service.save(new CardSituation(null, "TESTE"));})
        );

        assertAll("findById()",
                () -> assertThrows(NoSuchElementException.class, ()->{service.findById(3L);}),
                () -> assertDoesNotThrow(()->{service.findById(idCardSituation.get());})
        );

        assertAll("findAll()",
                () -> assertTrue(service.findAll().iterator().hasNext())
        );

        assertAll("update()",
                () -> assertThrows(InvalidDataAccessApiUsageException.class, ()->{service.update(new CardSituation(null, "TESTE"));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new CardSituation(idCardSituation.get(), null));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new CardSituation(idCardSituation.get(), "T"));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new CardSituation(idCardSituation.get(), "TESTETESTETESTETESTETESTETESTETESTETESTETESTETESTETESTE"));}),
                () -> assertDoesNotThrow(()->{idCardSituation.set(service.update(new CardSituation(idCardSituation.get(), "TESTE")).getId());}),
                () -> assertThrows(ViolationConstraintException.class, ()->{service.update(new CardSituation(idCardSituation.get(), "ATIVO"));}),
                () -> assertThrows(NoSuchElementException.class, ()->{service.update(new CardSituation(3L, "TESTE"));})
        );

        assertAll("delete()",
                () -> assertThrows(NoSuchElementException.class, ()->{service.delete(3L);}),
                () -> assertDoesNotThrow(()->{service.delete(idCardSituation.get());})
        );
    }
}