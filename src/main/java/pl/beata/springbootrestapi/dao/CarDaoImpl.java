package pl.beata.springbootrestapi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.beata.springbootrestapi.model.Car;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CarDaoImpl  implements CarDao {

    private String sql = "";
    private static final String TABLE_NAME = "cars";
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public CarDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Car> findAll() {
        sql = "SELECT * FROM " + TABLE_NAME;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Car.class));
    }

    @Override
    public List<Car> findByYear(LocalDate from, LocalDate to) {

        if(from == null && to == null) {
            return findAll();
        }

        sql = "SELECT * FROM "+ TABLE_NAME + " WHERE year >= ? AND year <= ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Car.class), from, to);
    }

    @Override
    public int saveCar(Car car) {
        sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql, getMaxRow()+1, car.getMark(), car.getModel(), car.getColor(), car.getYear());

    }

    private int getMaxRow() {
        sql = "SELECT MAX(car_id) FROM " + TABLE_NAME;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);

        return result != null ? result : 0;
    }
}
