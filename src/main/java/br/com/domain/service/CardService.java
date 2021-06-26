package br.com.domain.service;

import br.com.domain.domain.Card;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public Card save(Card card) {
        Card save = repository.findByPan(card.getPan());
        if(save != null) throw new ViolationConstraintException("Card already exist: " + card.getPan());
        return repository.save(card);
    }

    public Card update(Card card) {
        Card update = repository.findById(card.getId()).get();
        if(update.getPan().equals(card.getPan())){
            repository.save(card);
        } else {
            Card panValidation = repository.findByPan(card.getPan());
            if(panValidation != null) throw new ViolationConstraintException("Card already exist: " + card.getPan());
            repository.save(card);
        }
        return card;
    }

    public Card delete(Long id) {
        Card card = repository.findById(id).get();
        repository.delete(card);
        return card;
    }

    public Card findById(Long id) {
        return repository.findById(id).get();
    }

    public List<Card> findByCustomerId(Long id) {
        return repository.findByCustomerId(id);
    }

    public Iterable<Card> findAll() {
        return repository.findAll();
    }
}