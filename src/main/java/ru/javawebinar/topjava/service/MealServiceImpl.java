package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.AuthorizedUser.*;
/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {
    @Autowired
    private MealRepository repository;

    public Meal save(Meal meal) {
        meal.setUserId(AuthorizedUser.id());
        return repository.save(meal);
    }

    public void delete(int id, Integer userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id,userId), id);
    }

    public MealWithExceed get(int id, Integer userId,
                              LocalDate startDate,LocalDate endDate) throws NotFoundException {
        checkNotFoundWithId(repository.get(id).getUserId()==userId,id);
        return getWithExceeded(repository.getAll(userId,startDate,endDate),getCaloriesPerDay()).get(id);
    }

    public List<MealWithExceed> getAll(Integer userId, LocalTime startTime, LocalTime endTime,
                                       LocalDate startDate,LocalDate endDate) throws NotFoundException {
        if(repository.getAll(userId,startDate,endDate)==null)throw new NotFoundException("You have no permissions to access it");
        return getFilteredWithExceeded(repository.getAll(userId,startDate,endDate),startTime,endTime,getCaloriesPerDay());
    }
}
