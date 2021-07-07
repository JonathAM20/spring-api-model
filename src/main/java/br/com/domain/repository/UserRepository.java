package br.com.domain.repository;

import br.com.domain.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByUsernameAndUserSituationId(String username, long userSituationId);
}