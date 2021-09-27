package com.taxi.controllers;

import com.taxi.dto.CarDTO;
import com.taxi.services.CarService;
import com.taxi.tools.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CarsController {
    private final CarService carService;

    @Autowired
    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/admin/cars")
    public String getCars(Model model) {
        model.addAttribute("cars", carService.listAll());
        return "cars";
    }

    @GetMapping("/admin/car/new")
    public String newCar(Model model) {
        CarDTO car = new CarDTO();
        car.setStatus(1);
        car.setCategory(0);
        model.addAttribute("car", car);
        model.addAttribute("mode", 0);
        return "car";
    }

    @PostMapping("/admin/car/save")
    public String saveCar(CarDTO car) {
        carService.save(car);
        return "redirect:/admin/cars";
    }

    @GetMapping("/admin/car/edit/{id}")
    public String editCar(@PathVariable("id") Integer id, Model model) {
        try {
            CarDTO car = carService.get(id);
            model.addAttribute("car", car);
            model.addAttribute("mode", 1);
        } catch (CarNotFoundException ignored) {

        }
        return "car";
    }

    @GetMapping("/admin/car/del/{id}")
    public String deleteCar(@PathVariable("id") Integer id) {
        try {
            carService.delete(id);
        } catch (CarNotFoundException ignored) {

        }
        return "redirect:/admin/cars";
    }
}
