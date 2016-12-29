package ru.javawebinar.topjava;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    private ConfigurableApplicationContext springContext=
    new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
    @Autowired
    private MealService service=springContext.getBean(MealServiceImpl.class);


    public static final List<Meal>testMealAdmin= Arrays.asList(
new Meal(100003, LocalDateTime.of(2016, Month.DECEMBER,28,8,00),"завтрак",20),
new Meal(100004, LocalDateTime.of(2016, Month.DECEMBER,28,12,18),"обед",1800),
new Meal(100005, LocalDateTime.of(2016, Month.DECEMBER,28,19,18),"ужин",181)
    );
    public static final List<Meal>testMealUser= Arrays.asList(
    new Meal(100002, LocalDateTime.of(2016, Month.DECEMBER,28,12,18),"завтрак",2000));
    {
        Collections.sort(testMealAdmin, InMemoryMealRepositoryImpl.MEAL_COMPARATOR);}
    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();

    @Test
public void mealIdTest(){

    MATCHER.assertCollectionEquals(testMealAdmin,service.getAll(100001));
}


}
