package ru.javawebinar.topjava.util;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
public class UserMealsUtil {

    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(13,0),2000);

//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed>result=new ArrayList<>();
        Map<Integer,Integer>calPerDay=new HashMap<Integer,Integer>();
        for(UserMeal um:mealList){
            int day=um.getDateTime().getDayOfYear(), cal=um.getCalories();
            if(!calPerDay.containsKey(day))calPerDay.put(day,cal); else {
                int oldCalories=calPerDay.get(day);
                int newCalories=oldCalories+cal;
                calPerDay.put(day,newCalories);
            }
            System.out.println(calPerDay.get(day));
        }

        List<UserMeal>userMeals=mealList.stream()
                .filter(s->(TimeUtil.isBetween(s.getDateTime().toLocalTime(),startTime,endTime))
                        &&(calPerDay.get(s.getDateTime().getDayOfYear())>caloriesPerDay))
                .collect(Collectors.toList());
        //-----------
        if(!userMeals.isEmpty()){
        for(UserMeal um:userMeals){ System.out.println(um.getDateTime()+" "+um.getCalories()+" "+
                um.getDescription()+" "+calPerDay.get(um.getDateTime().getDayOfYear()));
            result.add(new UserMealWithExceed(um.getDateTime(),um.getDescription(),um.getCalories(),true));
        }}
        //-----------
        return result;
    }
}