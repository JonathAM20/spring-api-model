package br.com.domain.dao;

import br.com.domain.domain.Card;

import java.util.List;

public interface ICardDao {

    Card save(Card card);
    Card update(Card card);
    Card delete(Card card);
    Card get(Long id);
    List<Card> list();
}