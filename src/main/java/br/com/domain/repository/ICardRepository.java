package br.com.domain.repository;

import br.com.domain.domain.Card;
import br.com.domain.domain.Customer;

import java.util.List;

public interface ICardRepository {

    Card save(Card card);
    Card update(Card card);
    Card delete(Card card);
    Card findById(Long id);
    Card findByPan(String pan);
    List<Card> findByCustomerId(Long id);
    List<Card> findAll();
}