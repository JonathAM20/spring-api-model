package br.com.domain.repository;

import br.com.domain.domain.Card;
import br.com.domain.domain.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CardRepository implements ICardRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Card save(Card card) {
        entityManager.persist(card);
        return card;
    }

    @Override
    public Card update(Card card) {
        entityManager.merge(card);
        return card;
    }

    @Override
    public Card delete(Card card) {
        if (entityManager.contains(card)) {
            entityManager.remove(card);
        } else {
            entityManager.remove(entityManager.merge(card));
        }
        return card;
    }

    @Override
    public Card findById(Long id) {
        return entityManager.find(Card.class, id);
    }

    @Override
    public Card findByPan(String pan) {
        Card card = null;
        String jpql = "select c from Card c WHERE c.pan = '" + pan + "'";
        Query query = entityManager.createQuery(jpql);
        try{
            card = (Card) query.getSingleResult();
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
        }
        return card;
    }

    @Override
    public List<Card> findByCustomerId(Long id) {
        String jpql = "select c from Card c WHERE c.customer.id = " + id + "";
        Query query = entityManager.createQuery(jpql);
        return (List<Card>) query.getResultList();
    }

    @Override
    public List<Card> findAll() {
        String jpql = "select c from Card c";
        Query query = entityManager.createQuery(jpql);
        return (List<Card>) query.getResultList();
    }
}