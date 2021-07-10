package br.com.domain.service;

import br.com.domain.domain.Card;
import br.com.domain.exception.ViolationConstraintException;
import br.com.domain.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public Card save(Card card) {
        Card save = repository.findByPan(card.getPan());
        if(save != null) throw new ViolationConstraintException("pan-already exist");
        return repository.save(card);
    }

    public Card update(Card card) {
        try{
            Card update = repository.findById(card.getId()).get();
            if(update.getPan().equals(card.getPan())){
                repository.save(card);
            } else {
                Card panValidation = repository.findByPan(card.getPan());
                if(panValidation != null) throw new ViolationConstraintException("pan-already exist");
                repository.save(card);
            }
        } catch (NoSuchElementException ex){
            throw new NoSuchElementException("id-invalid");
        } catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("cardSituation.id/customer.id-invalid");
        }
        return card;
    }

    public Card delete(Long id) {
        try{
            Card card = repository.findById(id).get();
            repository.delete(card);
            return card;
        } catch (NoSuchElementException ex){
            throw new NoSuchElementException("id-invalid");
        } catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("cardSituation.id/customer.id-invalid");
        }
    }

    public Card findById(Long id) {
        try{
            return repository.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new NoSuchElementException("id-invalid");
        }
    }

    public List<Card> findByCustomerId(Long id) {
        return repository.findByCustomerId(id);
    }

    public Iterable<Card> findAll() {
        return repository.findAll();
    }
}