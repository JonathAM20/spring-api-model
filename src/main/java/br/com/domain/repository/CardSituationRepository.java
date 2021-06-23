package br.com.domain.repository;

import br.com.domain.domain.CardSituation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CardSituationRepository implements ICardSituationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CardSituation save(CardSituation cardSituation) {
        entityManager.persist(cardSituation);
        return cardSituation;
    }

    @Override
    public CardSituation update(CardSituation cardSituation) {
        entityManager.merge(cardSituation);
        return cardSituation;
    }

    @Override
    public CardSituation delete(CardSituation cardSituation) {
        if (entityManager.contains(cardSituation)) {
            entityManager.remove(cardSituation);
        } else {
            entityManager.remove(entityManager.merge(cardSituation));
        }
        return cardSituation;
    }

    @Override
    public CardSituation get(Long id) {
        return entityManager.find(CardSituation.class, id);
    }

    @Override
    public List<CardSituation> list() {
        String jpql = "select c from CardSituation c";
        Query query = entityManager.createQuery(jpql);
        return (List<CardSituation>) query.getResultList();
    }
}