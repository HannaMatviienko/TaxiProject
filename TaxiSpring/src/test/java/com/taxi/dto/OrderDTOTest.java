package com.taxi.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class OrderDTOTest {

    private final int intVal = 1;
    private final String stringVal = "stringVal";

    @Test
    void getDisplayDate() {
        OrderDTO cat = new OrderDTO();
        cat.setOrderDate(new Date(0));
        Assertions.assertEquals("01/01/1970", cat.getDisplayDate());
    }

    @Test
    void getCheckOutPrice() {
    }

    @Test
    void getId() {
        OrderDTO cat = new OrderDTO();
        cat.setId(intVal);
        Assertions.assertEquals(intVal, cat.getId());
    }

    @Test
    void getCars() {
        OrderDTO cat = new OrderDTO();
        cat.setCars(null);
        Assertions.assertNull(cat.getCars());
    }

    @Test
    void getUser() {
        OrderDTO cat = new OrderDTO();
        cat.setUser(null);
        Assertions.assertNull(cat.getUser());
    }

    @Test
    void getAddressFrom() {
        OrderDTO cat = new OrderDTO();
        cat.setAddressFrom(stringVal);
        Assertions.assertEquals(stringVal, cat.getAddressFrom());
    }

    @Test
    void getAddressTo() {
        OrderDTO cat = new OrderDTO();
        cat.setAddressTo(stringVal);
        Assertions.assertEquals(stringVal, cat.getAddressTo());
    }

    @Test
    void getPassengers() {
        OrderDTO cat = new OrderDTO();
        cat.setPassengers(intVal);
        Assertions.assertEquals(intVal, cat.getPassengers());
    }

    @Test
    void getCategory() {
        OrderDTO cat = new OrderDTO();
        cat.setCategory(intVal);
        Assertions.assertEquals(intVal, cat.getCategory());
    }

    @Test
    void getStatus() {
        OrderDTO cat = new OrderDTO();
        cat.setStatus(intVal);
        Assertions.assertEquals(intVal, cat.getStatus());
    }

    @Test
    void getPrice() {
        OrderDTO cat = new OrderDTO();
        double price = 1.1;
        cat.setPrice(price);
        Assertions.assertEquals(price, cat.getPrice());
    }

    @Test
    void getOrderDate() {
        Date date = new Date(0);
        OrderDTO cat = new OrderDTO();
        cat.setOrderDate(date);
        Assertions.assertEquals(date, cat.getOrderDate());
    }
}