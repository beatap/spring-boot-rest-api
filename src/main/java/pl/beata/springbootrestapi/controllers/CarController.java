package pl.beata.springbootrestapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.beata.springbootrestapi.model.Car;
import pl.beata.springbootrestapi.services.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Car>> getCars() {
        List<Car> cars = carService.getCars();

        if(cars != null && !cars.isEmpty()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Car car = carService.getCarById(id);

        if(car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/color/{color}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Car>> getCarsByColor(@PathVariable String color) {
        List<Car> cars = carService.getCarsByColor(color);

        if(cars != null && !cars.isEmpty()) {
            return  new ResponseEntity<>(cars, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> addCar(@RequestBody Car car) {
        boolean isNewCar = carService.addCar(car);

        if (isNewCar) {
            return new ResponseEntity<>("Success add car.", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Not create. Such a car exists or other error!", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> modifyCarByParam(@PathVariable long id, @RequestParam(required = false, defaultValue = "") String model,
                                           @RequestParam(required = false, defaultValue = "") String mark, @RequestParam(required = false, defaultValue = "") String color) {
        boolean modCar = carService.modifyCarByParam(id, model, mark, color);

        if(modCar) {
            return new ResponseEntity<>("Success modified car.", HttpStatus.OK);
        }

        return new ResponseEntity<>("No data to modify or other error!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity removeCar(@PathVariable  long id) {
        boolean delCar = carService.removeCar(id);

        if(delCar) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
