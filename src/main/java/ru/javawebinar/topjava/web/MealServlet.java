package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    public static LocalDate startDate,endDate;
    public static LocalTime startTime,endTime;
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
   // private MealRepository repository;
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        startDate=LocalDate.MIN; endDate=LocalDate.MAX;
        startTime=LocalTime.MIN; endTime=LocalTime.MAX;
        super.init(config);
        //repository = new InMemoryMealRepositoryImpl();
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
        mealRestController = appCtx.getBean(MealRestController.class);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.printf(request.getParameterNames().toString());
        String id = request.getParameter("id");
        //-----------HardCodeBegin
        Integer userId=AuthorizedUser.id();
        //-----------HardCodeEnd
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),userId);
        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealRestController.create(meal);

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        //-----------HardCodeBegin
        Integer userId=AuthorizedUser.id();
        //-----------HardCodeEnd
        startDate=LocalDate.MIN; endDate=LocalDate.MAX;
        startTime=LocalTime.MIN; endTime=LocalTime.MAX;
        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("meals",
                    mealRestController.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealRestController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000,userId) :
                    new Meal(mealRestController.get(getId(request)).getDateTime(),
                            mealRestController.get(getId(request)).getDescription(),
                            mealRestController.get(getId(request)).getCalories(),
                            userId);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }else
        if("filter".equals(action)){
            if(!"".equals(request.getParameter("startDate")))startDate=LocalDate.parse(request.getParameter("startDate"));
            if(!"".equals(request.getParameter("endDate")))endDate=LocalDate.parse(request.getParameter("endDate"));
            if(!"".equals(request.getParameter("startTime")))startTime=LocalTime.parse(request.getParameter("startTime"));
            if(!"".equals(request.getParameter("endTime")))endTime=LocalTime.parse(request.getParameter("endTime"));
            request.setAttribute("meals",
                    mealRestController.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }
    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId)-1;
    }
}
