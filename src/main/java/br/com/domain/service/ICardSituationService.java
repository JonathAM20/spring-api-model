package br.com.domain.service;

import br.com.domain.domain.CardSituation;

import java.util.List;

public interface ICardSituationService {

    CardSituation save(CardSituation cardSituation);
    CardSituation update(CardSituation cardSituation);
    CardSituation delete(Long id);
    CardSituation findById(Long id);
    List<CardSituation> findAll();
}