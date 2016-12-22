package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import static ru.javawebinar.topjava.web.MealServlet.*;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class  MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        meal.setId(null);
        LOG.info("create " + meal);
        return service.save(meal);
    }
    public MealWithExceed get(int id) {
        LOG.info("get"+id);
        return service.get(id,AuthorizedUser.id(),startDate,endDate);
    }
    public void delete(int id) {
        LOG.info("delete"+id);
        service.delete(id,AuthorizedUser.id());
    }
    public List<MealWithExceed> getAll(){
        LOG.info("getAll");
        return service.getAll(AuthorizedUser.id(),startTime,endTime,startDate,endDate);
    }
    public void update(Meal meal, int id) {
        meal.setId(id);
        LOG.info("update " + meal);
        service.save(meal);
    }
}
