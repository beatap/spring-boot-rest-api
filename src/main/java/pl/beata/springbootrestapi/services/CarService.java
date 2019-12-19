package pl.beata.springbootrestapi.services;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.beata.springbootrestapi.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private List<Car> cars;


    @EventListener(ApplicationReadyEvent.class)
    private void init() {
        cars = new ArrayList<>();

        cars.add(new Car(1, "Mazda", "3", "blue"));
        cars.add(new Car(2, "Opel", "Astra", "gray"));
        cars.add(new Car(3, "Toyota", "Yaris", "green"));
    }

    public List<Car> getCars() {
        return cars;
    }

    public Car getCarById(long id) {
        Optional<Car> car = findFirst(id);

        if(car.isPresent()) {
            return car.get();
        }
        return car.orElse(null);
    }

    public List<Car> getCarsByColor(String color) {

        return cars != null ? cars.stream().filter(c -> c.getColor().equals(color))
                .collect(Collectors.toList()) : null;
    }

    public boolean addCar(Car car) {
        if(findFirst(car.getId()).isPresent()) {
            return false;
        }

        return cars != null ? cars.add(car) : false;
    }

    public boolean modifyCar(Car car) {

        if(findFirst(car.getId()).isPresent()) {
            cars.add(cars.indexOf(car), car);

            return true;
        }

        return false;

    }

    public boolean modifyCarByParam(long id, String model, String mark, String color) {
        Optional<Car> car = findFirst(id);
        if(car.isPresent()) {
            Car newCar = car.get();

            if(!model.equals("")) {
                newCar.setModel(model);
            }

            if(!mark.equals("")) {
                newCar.setMark(mark);
            }

            if(!color.equals("")) {
                newCar.setColor(color);
            }

            if(!model.equals("") || !mark.equals("") || !color.equals("")) {

                return true;
            }

            return false;

        }

        return false;
    }

    public boolean removeCar(long id) {
        Optional<Car> car = findFirst(id);

        if(car.isPresent()) {
            return cars.remove(car.get());
        }

        return false;
    }

    private Optional<Car> findFirst(long id) {

        return cars != null ? cars.stream().filter(c -> c.getId() == id).findFirst() : Optional.empty();
    }
}
