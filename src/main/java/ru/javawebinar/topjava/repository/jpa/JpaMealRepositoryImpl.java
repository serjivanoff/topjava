package ru.javawebinar.topjava.repository.jpa;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
@Repository
@Transactional
public class JpaMealRepositoryImpl implements MealRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            if(meal.getUser().getId()!=userId)
            return null;

        if(meal.isNew()){
            em.persist(meal);return meal;}else{
        return em.merge(meal);}

    }
    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE).setParameter("id",id)
                .setParameter("userId",userId).executeUpdate()!=0;
    }
    @Override
    @Transactional
    public Meal get(int id, int userId) {
        try{
        return (Meal)(em.createQuery("SELECT m FROM Meal m WHERE m.id=?1 AND m.user.id=?2")
                .setParameter(1,id).setParameter(2,userId)
                .getSingleResult());}catch(NoResultException e){throw new NotFoundException("");}
    }
    @Override
    @Transactional
    public List<Meal> getAll(int userId) {
        List<Meal>result=em.createQuery("SELECT m FROM Meal m WHERE m.user.id=?1 ORDER BY m.dateTime DESC")
                .setParameter(1,userId)
                .getResultList();
        return result;
    }
    @Override
    @Transactional
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
List<Meal>result=em.createQuery(
"SELECT m FROM Meal m WHERE m.user.id=?1 AND m.dateTime>=?2 AND m.dateTime<=?3 ORDER BY m.dateTime DESC")
        .setParameter(1,userId).setParameter(2,startDate).setParameter(3,endDate)
        .getResultList();
        return result;
    }
}