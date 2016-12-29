package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository

public class JdbcMealRepositoryImpl implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource) {
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("meal_id");
    }
    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map=new MapSqlParameterSource()
                .addValue("meal_id",meal.getId())
                .addValue("date_time",meal.getDateTime())
                .addValue("description",meal.getDescription())
                .addValue("calories",meal.getCalories())
                .addValue("user_id",userId);
        if(meal.isNew()){
            int newKey=(int)insertMeal.executeAndReturnKey(map);
                    meal.setId(newKey);}
        else{
            namedParameterJdbcTemplate.update
("UPDATE meals SET date_time=:date_time,description=:description,calories=:calories WHERE user_id=:user_id AND meal_id=:meal_id",map);
        }
        return meal;
    }
    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE meal_id="+id+"AND user_id=?",userId)!=0;
    }

        @Override
    public Meal get(int id, int userId) {
        String sql="SELECT * FROM meals WHERE meal_id=?";
        Meal meal=this.jdbcTemplate.queryForObject(sql,ROW_MAPPER,id);
            meal.setId(id);
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        String sql="SELECT meal_id,date_time,calories,description" +
                " FROM meals WHERE user_id=? ORDER  BY date_time DESC";
        List<Meal>meals=this.jdbcTemplate.query(sql, new Object[]{userId}, new RowMapper<Meal>() {
            @Override
            public Meal mapRow(ResultSet rs, int i) throws SQLException {
                String[] line=rs.getString("date_time").split(" ");
                String dateTime=line[0]+"T"+line[1];
                return new Meal(rs.getInt("meal_id"),
                        LocalDateTime.parse(dateTime).truncatedTo(ChronoUnit.MINUTES),
                        rs.getString("description"),rs.getInt("calories"));
            }
        });
//-----------
        for(Meal m:meals) System.out.println(m.toString());
//------------
        return meals;
    }
    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal>meal=getAll(userId);
        List<Meal>result=new ArrayList<>();
            for(Meal m:meal){
                if(m.getDateTime().compareTo(endDate)<=0){
                    if(m.getDateTime().compareTo(startDate)<0)break;
                    result.add(m);
                }
            }
        return result;
    }
}