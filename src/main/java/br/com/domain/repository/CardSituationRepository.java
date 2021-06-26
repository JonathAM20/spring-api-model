package br.com.domain.repository;

import br.com.domain.domain.CardSituation;
import org.springframework.data.repository.CrudRepository;

public interface CardSituationRepository extends CrudRepository<CardSituation, Long> {

    CardSituation findBySituation(String situation);
}