package br.com.domain.service;

import br.com.domain.domain.*;
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
class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    void all() {
        AtomicReference<Long> idUser = new AtomicReference<>(1L);

        assertAll("save()",
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new User(null, "un", "password", new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new User(null, null, "password", new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new User(null, "username", "pw", new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new User(null, "username", null, new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new User(null, "username", "password", null, new UserSituation(2L),null));}),
                () -> assertThrows(ConstraintViolationException.class, ()->{service.save(new User(null, "username", "password", new UserProfile(2L), null,null));}),
                () -> assertDoesNotThrow(()->{idUser.set(service.save(new User(null, "username", "password", new UserProfile(2L), new UserSituation(2L),null)).getId());}),
                () -> assertThrows(ViolationConstraintException.class, ()->{service.save(new User(null, "username", "password", new UserProfile(2L), new UserSituation(2L),null));})
        );

        assertAll("findById()",
                () -> assertThrows(NoSuchElementException.class, ()->{service.findById(1L);}),
                () -> assertDoesNotThrow(()->{service.findById(idUser.get());})
        );

        assertAll("findAll()",
                () -> assertTrue(service.findAll().iterator().hasNext())
        );

        assertAll("update()",
                () -> assertThrows(InvalidDataAccessApiUsageException.class, ()->{service.update(new User(null, "username", "password", new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new User(idUser.get(), "un", "password", new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new User(idUser.get(), null, "password", new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new User(idUser.get(), "username", "pw", new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new User(idUser.get(), "username", null, new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new User(idUser.get(), "username", "password", null, new UserSituation(2L),null));}),
                () -> assertThrows(TransactionSystemException.class, ()->{service.update(new User(idUser.get(), "username", "password", new UserProfile(2L), null,null));}),
                () -> assertDoesNotThrow(()->{service.update(new User(idUser.get(), "username", "password", new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(ViolationConstraintException.class, ()->{service.update(new User(idUser.get(), "jonathan", "password", new UserProfile(2L), new UserSituation(2L),null));}),
                () -> assertThrows(NoSuchElementException.class, ()->{service.update(new User(1L, "username", "password", new UserProfile(2L), new UserSituation(2L),null));})
        );

        assertAll("delete()",
                () -> assertThrows(NoSuchElementException.class, ()->{service.delete(1L);}),
                () -> assertDoesNotThrow(()->{service.delete(idUser.get());})
        );
    }
}