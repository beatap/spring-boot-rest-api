package pl.beata.springbootrestapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.beata.springbootrestapi.dao.CarDaoImpl;
import pl.beata.springbootrestapi.model.Car;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService {

    private CarDaoImpl carDao;

    @Autowired
    public CarService(CarDaoImpl carDao) {
        this.carDao = carDao;
    }

    public List<Car> getCars() {

        return carDao.findAll();
    }

    public List<Car> getByYear(LocalDate from, LocalDate to) {
        return carDao.findByYear(from, to);
    }

    public int addCar(Car car) {
        return carDao.saveCar(car);
    }
}
