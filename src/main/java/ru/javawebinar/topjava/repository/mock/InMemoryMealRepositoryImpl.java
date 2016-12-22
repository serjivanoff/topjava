package ru.javawebinar.topjava.repository.mock;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id,Integer userId) {
        if (repository.get(id) == null) return false;
        else {
            if (repository.get(id).getUserId() == userId) {
                repository.remove(id);
                return true;
            } else
                return false;
        }
    }
    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(Integer userId,LocalDate startDate, LocalDate endDate) {
        Comparator<Meal> compareByDate= (o1, o2) -> {
            int result=o2.getDate().compareTo(o1.getDate());
            if(result==0)result=o2.getTime().compareTo(o1.getTime());
             return result;
        };
        List<Meal> result=new ArrayList<>(repository.values());
        if(result.isEmpty() || result==null)return null; else{
        for(int i=0;i<result.size();i++){
            if(result.get(i).getUserId()!=userId)result.remove(i);
        }}

        if(result.isEmpty())return null;else{
        Collections.sort(result,compareByDate);
            List<Meal>sortedResult=new ArrayList();
        for(Meal m:result){
            if(m.getDate().compareTo(startDate)<0)break;
            if(m.getDate().compareTo(endDate)<=0)sortedResult.add(m);
        }
        return sortedResult;}
    }
}
