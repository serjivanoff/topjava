package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional
public class JpaMealRepositoryImpl implements MealRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if(meal.getUser().getId()!=userId)return null;
        if(meal.isNew()){em.persist(meal);return meal;}else{
//        meal.setUser(g);
        return em.merge(meal);}
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {

        return false;
    }

    @Override
    @Transactional
    public Meal get(int id, int userId) {
        return null;
    }

    @Override
    @Transactional
    public List<Meal> getAll(int userId) {
        return null;
    }

    @Override
    @Transactional
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return null;
    }
}