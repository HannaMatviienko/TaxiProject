package com.taxi.controllers;

import com.taxi.dto.UserDTO;
import com.taxi.services.UserService;
import com.taxi.tools.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.*;

class UsersControllerTest {

    private Model mockModel;
    private UserService mockService;

    @BeforeEach
    void setUp() {
        mockModel = mock(Model.class);
        mockService = mock(UserService.class);
    }

    @Test
    void loginUser() {
        UsersController cat = new UsersController(mockService);
        String view = cat.loginUser("", "", mockModel);

        verify(mockModel).addAttribute("error", true);
        verify(mockModel).addAttribute("logout", true);

        Assertions.assertEquals("login", view);
    }

    @Test
    void signupUser() {
        AtomicReference<UserDTO> mockUser = new AtomicReference<>();
        try (MockedConstruction<UserDTO> ignored = Mockito.mockConstruction(UserDTO.class,
                (mock, context) -> mockUser.set(mock))) {

            UsersController cat = new UsersController(mockService);
            String view = cat.signupUser(mockModel);

            verify(mockModel).addAttribute("user", mockUser.get());

            Assertions.assertEquals("signup", view);
        }
    }

    @Test
    void getUsers() {
        when(mockService.listAll()).thenReturn(null);

        UsersController cat = new UsersController(mockService);
        String view = cat.getUsers(mockModel);

        verify(mockModel).addAttribute("users", null);

        Assertions.assertEquals("users", view);
    }

    @Test
    void newUser() {
        AtomicReference<UserDTO> mockUser = new AtomicReference<>();
        try (MockedConstruction<UserDTO> ignored = Mockito.mockConstruction(UserDTO.class,
                (mock, context) -> mockUser.set(mock))) {

            UsersController cat = new UsersController(mockService);
            String view = cat.newUser(mockModel);

            verify(mockUser.get()).setRoles("ROLE_USER");
            verify(mockUser.get()).setEnabled(true);

            verify(mockModel).addAttribute("user", mockUser.get());
            verify(mockModel).addAttribute("mode", 0);

            Assertions.assertEquals("user", view);
        }
    }

    @Test
    void saveUser() {

        String password = "password";

        UserDTO mockUser = mock(UserDTO.class);
        when(mockUser.getPassword()).thenReturn(password);

        try (MockedConstruction<BCryptPasswordEncoder> ignored = Mockito.mockConstruction(BCryptPasswordEncoder.class,
                (mock, context) -> when(mock.encode(mockUser.getPassword())).thenReturn(password))) {

            UsersController cat = new UsersController(mockService);
            String view = cat.saveUser(mockUser);

            verify(mockUser).setPassword(password);
            verify(mockService).save(mockUser);

            Assertions.assertEquals("redirect:/admin/users", view);
        }
    }

    @Test
    void editUser() throws UserNotFoundException {
        int id = 1;

        when(mockService.get(id)).thenReturn(null);

        UsersController cat = new UsersController(mockService);
        String view = cat.editUser(id, mockModel);

        verify(mockModel).addAttribute("user", null);
        verify(mockModel).addAttribute("mode", 1);

        Assertions.assertEquals("user", view);
    }

    @Test
    void deleteUser() throws UserNotFoundException {
        int id = 1;

        UsersController cat = new UsersController(mockService);
        String view = cat.deleteUser(id);

        verify(mockService).delete(id);

        Assertions.assertEquals("redirect:/admin/users", view);
    }
}