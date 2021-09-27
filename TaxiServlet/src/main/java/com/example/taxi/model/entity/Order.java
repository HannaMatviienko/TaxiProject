package com.example.taxi.model.entity;

import java.util.Date;
import java.util.List;

public class Order {

    public Order() {
        setPrice(0.0);
        setStatus(0);
        setPassengers(1);
        setCategory(0);
    }

    private Integer id;

    private List<Car> cars;

    private User user;

    private String addressFrom;

    private String addressTo;

    private Integer passengers;

    private Integer category;

    private Integer status;

    private Double price;

    private Date orderDate;

    public Double getCheckOutPrice() {
        if (getCars() == null || getCars().size() == 0)
            setPrice(0.0);
        else {
            double k = 1.0;
            switch (getCategory()) {
                case 1:
                    k = 1.5;
                    break;

                case 2:
                    k = 2.0;
                    break;
            }
            setPrice(getPassengers() * 100.0 * k);
        }

        return price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}