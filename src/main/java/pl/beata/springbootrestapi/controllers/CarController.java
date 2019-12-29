package pl.beata.springbootrestapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.beata.springbootrestapi.model.Car;
import pl.beata.springbootrestapi.services.CarService;

import java.util.List;

@Controller
public class CarController {

    CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping("/all")
    public String getCars(Model model) {
        List<Car> cars = carService.getCars();
        model.addAttribute("cars", cars);

        return "list-cars";
    }

    @GetMapping("/showAdd")
    public String showAddCar(Car car) {

        return "add-car";
    }

    @PostMapping("/add")
    public String addCar(Car car) {
        int size = carService.getCars().size();
        car.setCarId(size+1);

        carService.addCar(car);

        return "redirect:/all";
    }

    @GetMapping("/modify/{carId}")
    public String modifyCarById(@PathVariable long carId, Model model) {
        Car car = carService.getCarById(carId);
        model.addAttribute("car", car);

        return "update-car";
    }

    @PostMapping("/update/{carId}")
    public String updateCar(@PathVariable long carId, Car car) {

        carService.modifyCar(car);

        return "redirect:/all";
    }

    @GetMapping("/delete/{carId}")
    public String removeCar(@PathVariable  long carId) {
        carService.removeCar(carId);

        return "redirect:/all";
    }

}
