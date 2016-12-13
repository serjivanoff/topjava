package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.*;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.meals;

public class MealServlet extends HttpServlet {
    public static volatile int count = 0;
    public static List<MealWithExceed> meal = MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, 2000);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //   LOG.debug("redirect to users");
        request.setAttribute("meals", meal);
        String forward="/meals.jsp";
        String action=request.getParameter("action");
        if("delete".equals(action)){
            int mealId=Integer.parseInt(request.getParameter("id"));
            for(int i=0;i<meals.size();i++){
                if(meals.get(i).getId()==mealId){
                    meals.remove(i);break;
                }
            }
        }else if("edit".equals(action))
            {
            int mealId=Integer.parseInt(request.getParameter("id"))-1;
                request.setAttribute("meal", meal.get(mealId));
                forward="/edition.jsp";
        }
        //request.setAttribute("meals", meal);
        meal = MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("meals", meal);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int mealId;
        if(request.getParameter("mealid")!=null)
            mealId=Integer.parseInt(request.getParameter("mealid"));else
         mealId=-1;
        String descript=request.getParameter("descr");
        String dt=request.getParameter("datetime");
        String dateTime=dt.split(" ")[0]+"T"+dt.split(" ")[1];
        LocalDateTime dtime=LocalDateTime.parse(dateTime);
        int cals=Integer.parseInt(request.getParameter("cals"));
        if(mealId>=0){
        for(int i = 0; i< meals.size(); i++)  if(mealId== meals.get(i).getId()){
            meals.get(i).setDateTime(dtime);
            meals.get(i).setDescription(descript);
            meals.get(i).setCalories(cals);
            }
            this.doGet(request,response);}
        else
            {
                meals.add(new Meal(dtime,descript,cals));
                this.doGet(request,response);}
       }
}