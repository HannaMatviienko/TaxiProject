package com.example.taxi.model.entity;

import java.util.Objects;

public class Car {

    public Car()
    {
        id = -1;
        status = 1;
        passengers = 1;
        category = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        final Car other = (Car) obj;

        if (!Objects.equals(this.plate, other.plate))
            return false;
        if (!Objects.equals(this.model, other.model))
            return false;

        if (!Objects.equals(this.id, other.id))
            return false;
        if (!Objects.equals(this.category, other.category))
            return false;
        if (!Objects.equals(this.status, other.status))
            return false;
        if (!Objects.equals(this.passengers, other.passengers))
            return false;

        return true;
    }

    private Integer id;

    private String plate;

    private String model;

    private Integer category;

    private Integer status;

    private Integer passengers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }
}