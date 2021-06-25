package br.com.domain.service;

import br.com.domain.domain.Card;
import br.com.domain.domain.CardSituation;
import br.com.domain.exception.NotExistException;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.ICardRepository;
import br.com.domain.repository.ICardSituationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardSituationService implements ICardSituationService {

    @Autowired
    private ICardSituationRepository repository;

    @Override
    public CardSituation save(CardSituation cardSituation) {
        CardSituation save = repository.findBySituation(cardSituation.getSituation());
        if(save != null) throw new ViolationConstraintException("Card already exist: " + cardSituation.getSituation());
        return repository.save(cardSituation);
    }

    @Override
    public CardSituation update(CardSituation cardSituation) {
        CardSituation update = repository.findById(cardSituation.getId());
        if(update != null){
            repository.update(cardSituation);
        } else {
            throw new NotExistException("Id: " + cardSituation.getId());
        }
        return cardSituation;
    }

    @Override
    public CardSituation delete(Long id) {
        CardSituation cardSituation = repository.findById(id);
        if(cardSituation == null) throw new NotExistException("Id: " + id);
        repository.delete(cardSituation);
        return cardSituation;
    }

    public CardSituation findById(Long id) {
        CardSituation cardSituation = repository.findById(id);
        if(cardSituation == null) throw new NotExistException("Id: " + id);
        return cardSituation;
    }

    public List<CardSituation> findAll() {
        return repository.findAll();
    }
}