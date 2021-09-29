package com.taxi.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserDTOTest {

    private final String stringVal = "stringVal";

    @Test
    void getId() {
        UserDTO cat = new UserDTO();
        int intVal = 1;
        cat.setId(intVal);
        Assertions.assertEquals(intVal, cat.getId());
    }

    @Test
    void getEmail() {
        UserDTO cat = new UserDTO();
        cat.setEmail(stringVal);
        Assertions.assertEquals(stringVal, cat.getEmail());
    }

    @Test
    void getPassword() {
        UserDTO cat = new UserDTO();
        cat.setPassword(stringVal);
        Assertions.assertEquals(stringVal, cat.getPassword());
    }

    @Test
    void getFirstName() {
        UserDTO cat = new UserDTO();
        cat.setFirstName(stringVal);
        Assertions.assertEquals(stringVal, cat.getFirstName());
    }

    @Test
    void getLastName() {
        UserDTO cat = new UserDTO();
        cat.setLastName(stringVal);
        Assertions.assertEquals(stringVal, cat.getLastName());
    }

    @Test
    void isEnabled() {
        UserDTO cat = new UserDTO();
        cat.setEnabled(true);
        Assertions.assertTrue(cat.isEnabled());
    }

    @Test
    void getRoles() {
        UserDTO cat = new UserDTO();
        cat.setRoles(stringVal);
        Assertions.assertEquals(stringVal, cat.getRoles());
    }
}