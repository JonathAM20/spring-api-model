package br.com.domain.service;

import br.com.domain.domain.Card;
import br.com.domain.exception.NotExistException;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.ICardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService implements ICardService{

    @Autowired
    private ICardRepository repository;

    @Override
    public Card save(Card card) {
        Card save = repository.findByPan(card.getPan());
        if(save != null) throw new ViolationConstraintException("Card already exist: " + card.getPan());
        return repository.save(card);
    }

    @Override
    public Card update(Card card) {
        Card update = repository.findById(card.getId());
        if(update != null){
            repository.update(card);
        } else {
            throw new NotExistException("Id: " + card.getId());
        }
        return card;
    }

    @Override
    public Card delete(Long id) {
        Card card = repository.findById(id);
        if(card == null) throw new NotExistException("Id: " + id);
        repository.delete(card);
        return card;
    }

    public Card findById(Long id) {
        Card card = repository.findById(id);
        if(card == null) throw new NotExistException("Id: " + id);
        return card;
    }

    @Override
    public List<Card> findByCustomerId(Long id) {
        return repository.findByCustomerId(id);
    }

    public List<Card> findAll() {
        return repository.findAll();
    }
}