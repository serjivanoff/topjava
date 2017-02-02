package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
@RequestMapping("/rest")
public class MealRestController extends AbstractMealController {

    @GetMapping(value = "meal/{mealId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("mealId") int mealId) {
        return super.get(mealId);
    }

    @DeleteMapping(value = "/{mealId}")
    public void delete(@PathVariable("mealId") int mealId) {
      super.delete(mealId);
    }
    @GetMapping(value = "/meals",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll(){
        return super.getAll();
    }
    @PutMapping(value = "/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal,@PathVariable("mealId") int mealId) {
            super.update(meal,mealId);
    }
    @PutMapping(value="/save")
    public Meal create(@RequestBody Meal meal) {
        return super.create(meal);
    }
}