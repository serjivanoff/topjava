package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class MealWithExceed {
    private  LocalDateTime dateTime;
    private final  int id;
    private  String description;

    private  int calories;

    private  boolean exceed;

    public MealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed,int id) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        this.id=id;
    }

    public  int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
    public boolean isExceed(){return exceed;}

}