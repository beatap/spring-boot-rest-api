package pl.beata.springbootrestapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.beata.springbootrestapi.model.Car;
import pl.beata.springbootrestapi.services.CarService;

import java.time.LocalDate;

@Controller
public class CarController {

    private CarService carService;
    private static final String MODEL_CARS = "cars";

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping("/all")
    public String getCars(Model model) {
        model.addAttribute(MODEL_CARS, carService.getCars());

        return "list-cars";
    }

    @GetMapping("/search")
    public String getCarsByYear(Model model,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to) {
        model.addAttribute(MODEL_CARS, carService.getByYear(from, to));

        return "list-cars";
    }

    @GetMapping("/showAdd")
    public String showAddCar(Car car) {

        return "add-car";
    }

    @PostMapping("/add")
    public String addCar(Car car) {
        carService.addCar(car);

        return "redirect:/all";
    }

}
