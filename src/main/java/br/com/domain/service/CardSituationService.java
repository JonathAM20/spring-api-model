package br.com.domain.service;

import br.com.domain.domain.CardSituation;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.CardSituationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CardSituationService {

    @Autowired
    private CardSituationRepository repository;

    public CardSituation save(CardSituation cardSituation) {
        CardSituation save = repository.findBySituation(cardSituation.getSituation());
        if(save != null) throw new ViolationConstraintException("situation-already exist");
        return repository.save(cardSituation);
    }

    public CardSituation update(CardSituation cardSituation) {
        try{
            CardSituation update = repository.findById(cardSituation.getId()).get();
            if(update.getSituation().equals(cardSituation.getSituation())){
                repository.save(cardSituation);
            } else {
                CardSituation validationSituation = repository.findBySituation(cardSituation.getSituation());
                if(validationSituation != null) throw new ViolationConstraintException("situation-already exist");
                repository.save(cardSituation);
            }
        } catch (NoSuchElementException ex){
            throw new NoSuchElementException("id-invalid");
        }
        return cardSituation;
    }

    public CardSituation delete(Long id) {
        try{
            CardSituation cardSituation = repository.findById(id).get();
            repository.delete(cardSituation);
            return cardSituation;
        } catch (NoSuchElementException ex){
            throw new NoSuchElementException("id-invalid");
        }
    }

    public CardSituation findById(Long id) {
        try{
            return repository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new NoSuchElementException("id-invalid");
        }
    }

    public Iterable<CardSituation> findAll() {
        return repository.findAll();
    }
}