package pl.beata.springbootrestapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/cars")
public class CarController {

    CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Resources<Car>> getCars() {
        List<Car> cars = carService.getCars();

        if(cars != null && !cars.isEmpty()) {
            return new ResponseEntity<>(getResources(cars), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/{carId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Resource<Car>> getCarById(@PathVariable long carId) {
        Car car = carService.getCarById(carId);

        if(car != null) {
            Link link = linkTo(CarController.class).slash(carId).withSelfRel();
            Resource<Car> carResource = new Resource<>(car, link);

            return new ResponseEntity<>(carResource, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/color/{color}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Resources<Car>> getCarsByColor(@PathVariable String color) {
        List<Car> cars = carService.getCarsByColor(color);

        if(cars != null && !cars.isEmpty()) {
            return  new ResponseEntity<>(getResources(cars), HttpStatus.OK);
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


    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> modifyCar(@RequestBody Car car) {
        boolean ismodCar = carService.modifyCar(car);

        if(ismodCar) {
            return new ResponseEntity<>("Success modified car.", HttpStatus.OK);
        }

        return new ResponseEntity<>("No data object modify or other error!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path = "/{carId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> modifyCarByParam(@PathVariable long carId, @RequestParam(required = false, defaultValue = "") String model,
                                           @RequestParam(required = false, defaultValue = "") String mark, @RequestParam(required = false, defaultValue = "") String color) {
        boolean modCar = carService.modifyCarByParam(carId, model, mark, color);

        if(modCar) {
            return new ResponseEntity<>("Success modified car.", HttpStatus.OK);
        }

        return new ResponseEntity<>("No data to modify or other error!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(path = "/{carId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity removeCar(@PathVariable  long carId) {
        boolean delCar = carService.removeCar(carId);

        if(delCar) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


   private Resources<Car> getResources(List<Car> cars) {
       cars.forEach(car -> car.add(linkTo(CarController.class).slash(car.getCarId()).withSelfRel()));
       Link link = linkTo(CarController.class).withSelfRel();
       Resources<Car> carResources = new Resources<>(cars, link);

       return carResources;
   }

}
