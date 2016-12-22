package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal Meal);

    boolean delete(int id,Integer userId);

    Meal get(int id);

    List<Meal> getAll(Integer userId, LocalDate startDate, LocalDate endDate);
}