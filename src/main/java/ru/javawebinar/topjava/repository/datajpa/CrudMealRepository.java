package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional
public interface CrudMealRepository extends JpaRepository<Meal,Integer>{

    List<Meal>findAll(Sort sort);
    List<Meal> findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId,LocalDateTime startDate, LocalDateTime endDate);
    List<Meal> findAllByUserIdOrderByDateTimeDesc(Integer userId);
}
