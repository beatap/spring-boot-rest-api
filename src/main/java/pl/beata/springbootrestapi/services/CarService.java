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

    public Car getCarById(long carId) {
        Optional<Car> car = findFirst(carId);

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
        if(findFirst(car.getCarId()).isPresent()) {
            return false;
        }

        return cars != null ? cars.add(car) : false;

    }

    public boolean modifyCar(Car car) {
        Optional<Car> findCar = findFirst(car.getCarId());
        if(findCar.isPresent()) {
            Car modCar = findCar.get();

            modCar.setMark(car.getMark());
            modCar.setModel(car.getModel());
            modCar.setColor(car.getColor());


            return true;
        }

        return false;

    }

    public boolean modifyCarByParam(long carId, String model, String mark, String color) {
        Optional<Car> car = findFirst(carId);
        if(car.isPresent()) {
            Car newCar = car.get();

            if(!model.isBlank()) {
                newCar.setModel(model);
            }

            if(!mark.isBlank()) {
                newCar.setMark(mark);
            }

            if(!color.isBlank()) {
                newCar.setColor(color);
            }

            if(!model.isBlank() || !mark.isBlank() || !color.isBlank()) {

                return true;
            }

            return false;

        }

        return false;
    }

    public boolean removeCar(long carId) {
        Optional<Car> car = findFirst(carId);

        return car.map(c -> cars.remove(c)).orElse(false);
    }

    private Optional<Car> findFirst(long carId) {

        return cars != null ? cars.stream().filter(c -> c.getCarId() == carId).findFirst() : Optional.empty();
    }
}
