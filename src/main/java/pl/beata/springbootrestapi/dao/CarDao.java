package pl.beata.springbootrestapi.dao;

import pl.beata.springbootrestapi.model.Car;

import java.time.LocalDate;
import java.util.List;

public interface CarDao {

    List<Car> findAll();
    List<Car> findByYear(LocalDate from, LocalDate to);
    int saveCar(Car car);
}
