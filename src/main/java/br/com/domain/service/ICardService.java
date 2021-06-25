package br.com.domain.service;

import br.com.domain.domain.Card;

import java.util.List;

public interface ICardService {

    Card save(Card card);
    Card update(Card card);
    Card delete(Long id);
    Card findById(Long id);
    List<Card> findByCustomerId(Long id);
    List<Card> findAll();
}