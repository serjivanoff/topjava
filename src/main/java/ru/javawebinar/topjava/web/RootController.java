package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;
    @Autowired
    private MealRestController mealRestController;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        model.addAttribute("users", service.getAll());
        return "users";
    }
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }
//    ------------------------
    @RequestMapping(value = "/meals",method = RequestMethod.GET)
    public String meals(Model model){
        model.addAttribute("meals",mealRestController.getAll());
        return "meals";
    }
    @RequestMapping(value="/edit",method=RequestMethod.GET)
    public String editMeal(Model model,HttpServletRequest request){
        int mealId=Integer.valueOf(request.getParameter("id"));
        Meal meal= mealRestController.get(mealId);
        request.setAttribute("meal",meal);
        return "meal";
    }
    @RequestMapping(value="/update",method=RequestMethod.GET)
    public String updateMeal(Model model,HttpServletRequest request){
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()){
            mealRestController.create(meal);
        }else{
        int mealId=Integer.valueOf(request.getParameter("id"));
        mealRestController.update(meal,mealId);}
        return meals(model);
    }
    @RequestMapping(value="/save",method=RequestMethod.GET)
    public String save(){
        return "meal";
    }
    @RequestMapping(value = "/delete",method=RequestMethod.GET)
    public String deleteMeal(Model model,HttpServletRequest request){
        int mealId=Integer.valueOf(request.getParameter("id"));
        mealRestController.delete(mealId);
        return meals(model);
    }
}