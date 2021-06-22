package br.com.domain.dao;

import br.com.domain.domain.CardSituation;

import java.util.List;

public interface ICardSituationDao {

    CardSituation save(CardSituation cardSituation);
    CardSituation update(CardSituation cardSituation);
    CardSituation delete(CardSituation cardSituation);
    CardSituation get(Long id);
    List<CardSituation> list();
}