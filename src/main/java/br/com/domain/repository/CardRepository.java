package br.com.domain.repository;

import br.com.domain.domain.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Long> {

    Card findByPan(String pan);
    List<Card> findByCustomerId(Long id);
}