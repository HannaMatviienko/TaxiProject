package com.taxi.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CarDTOTest {

    @Test
    void getId() {
        int id = 1;
        CarDTO cat = new CarDTO();
        cat.setId(id);
        Assertions.assertEquals(id, cat.getId());
    }

    @Test
    void getPlate() {
        String plate = "plate";
        CarDTO cat = new CarDTO();
        cat.setPlate(plate);
        Assertions.assertEquals(plate, cat.getPlate());
    }

    @Test
    void getModel() {
        String model = "model";
        CarDTO cat = new CarDTO();
        cat.setModel(model);
        Assertions.assertEquals(model, cat.getModel());
    }

    @Test
    void getCategory() {
        int category = 1;
        CarDTO cat = new CarDTO();
        cat.setCategory(category);
        Assertions.assertEquals(category, cat.getCategory());
    }

    @Test
    void getStatus() {
        int status = 1;
        CarDTO cat = new CarDTO();
        cat.setStatus(status);
        Assertions.assertEquals(status, cat.getStatus());
    }

    @Test
    void getPassengers() {
        int passengers = 1;
        CarDTO cat = new CarDTO();
        cat.setPassengers(passengers);
        Assertions.assertEquals(passengers, cat.getPassengers());
    }

    @Test
    void testToString() {
    }

    @Test
    void builder() {
    }
}