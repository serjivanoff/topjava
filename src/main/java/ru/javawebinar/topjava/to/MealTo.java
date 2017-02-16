package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MealTo implements Serializable{
    private static final long serialVersionUID = 1L;
    @NotEmpty
    private Integer id;

    private LocalDateTime dateTime;
    @NotEmpty
    private String description;

    @NotEmpty
    private Integer calories;

    public MealTo(){}

    public MealTo(Integer id, LocalDateTime dateTime, String description, Integer calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public boolean isNew() {
        return id == null;
    }
  }
