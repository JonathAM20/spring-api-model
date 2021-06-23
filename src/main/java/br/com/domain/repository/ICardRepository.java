package br.com.domain.repository;

import br.com.domain.domain.Card;

import java.util.List;

public interface ICardRepository {

    Card save(Card card);
    Card update(Card card);
    Card delete(Card card);
    Card get(Long id);
    List<Card> list();
}