package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private static final Sort SORT_DATE = new Sort("dateTime");
    @Autowired
    private CrudMealRepository crudRepository;
    @Autowired
    private CrudUserRepository crudUserRepository;


    @Override
    public Meal save(Meal meal, int userId) {
        Meal result=null;
        if(!meal.isNew()&& get(meal.getId(),userId)==null){
        return result;
        }
        meal.setUser(crudUserRepository.findOne(userId));
        result=crudRepository.save(meal);
        return result;
    }

    @Override
    public boolean delete(int id, int userId) {
        boolean result=false;
        if(get(id,userId)!=null){crudRepository.delete(id);result=true;}
        return result;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result=crudRepository.findOne(id);
        if(result.getUser().getId()!=userId)return null;
            else  return result;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository
                .findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(userId,startDate,endDate);
    }
}
