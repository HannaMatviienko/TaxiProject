package com.taxi.services;

import com.taxi.dto.CarDTO;
import com.taxi.repositories.CarRepository;
import com.taxi.tools.CarNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarService {

    private final CarRepository repo;

    @Autowired
    public CarService(CarRepository repo) {
        this.repo = repo;
    }

    public List<CarDTO> listAll()
    {
        return (List<CarDTO>) repo.findAll();
    }

    public List<CarDTO> findByCategoryAndStatusAndPassengers(Integer category, Integer status, Integer passengers)
    {
        List<CarDTO> list = new ArrayList<>();

        List<CarDTO> cars = repo.findByCategoryAndStatus(category, status);
        int left = passengers;
        for (CarDTO car : cars)
        {
            list.add(car);
            left = left - car.getPassengers();
            if (left < 0) break;
        }

        if (left > 0)
            list.clear();

        return list;
    }

    public void save(CarDTO car) {
        repo.save(car);
    }

    public CarDTO get(Integer id) throws CarNotFoundException {
        Optional<CarDTO> result = repo.findById(id);
        if (result.isPresent())
            return result.get();
        else
            throw new CarNotFoundException("Could not find any car with ID " + id);
    }

    public void delete(Integer id) throws CarNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count == 0) {
            throw new CarNotFoundException("Could not find any car with ID " + id);
        }
        repo.deleteById(id);
    }
}
