package br.com.domain.service;

import br.com.domain.domain.CardSituation;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.CardSituationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardSituationService {

    @Autowired
    private CardSituationRepository repository;

    public CardSituation save(CardSituation cardSituation) {
        CardSituation save = repository.findBySituation(cardSituation.getSituation());
        if(save != null) throw new ViolationConstraintException("Card already exist: " + cardSituation.getSituation());
        return repository.save(cardSituation);
    }

    public CardSituation update(CardSituation cardSituation) {
        CardSituation update = repository.findById(cardSituation.getId()).get();
        if(update.getSituation().equals(cardSituation.getSituation())){
            repository.save(cardSituation);
        } else {
            CardSituation validationSituation = repository.findBySituation(cardSituation.getSituation());
            if(validationSituation != null) throw new ViolationConstraintException("CardSituation already exist: " + cardSituation.getSituation());
            repository.save(cardSituation);
        }
        return cardSituation;
    }

    public CardSituation delete(Long id) {
        CardSituation cardSituation = repository.findById(id).get();
        repository.delete(cardSituation);
        return cardSituation;
    }

    public CardSituation findById(Long id) {
        return repository.findById(id).get();
    }

    public Iterable<CardSituation> findAll() {
        return repository.findAll();
    }
}