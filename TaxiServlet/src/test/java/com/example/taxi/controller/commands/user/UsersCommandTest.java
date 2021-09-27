package com.example.taxi.controller.commands.user;

import com.example.taxi.controller.commands.car.CarsCommand;
import com.example.taxi.model.dao.CarDAO;
import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.dao.UserDAO;
import com.example.taxi.model.entity.Car;
import com.example.taxi.model.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersCommandTest {

    private HttpServletRequest mockRequest;
    private UserDAO mockDAO;

    @BeforeEach
    void setUp() {
        mockDAO = mock(UserDAO.class);
        DAOFactory.setUser(mockDAO);
        mockRequest = mock(HttpServletRequest.class);
    }

    @Test
    void execute() throws ServletException, SQLException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        users.add(user);
        when(mockDAO.get()).thenReturn(users);

        UsersCommand command = new UsersCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("users", users);

        Assertions.assertEquals("/WEB-INF/jsp/users.jsp", page);
    }

    @Test
    void executeExceptionSQL() throws SQLException, ClassNotFoundException {
        String message = "Error";
        doThrow(new SQLException(message)).when(mockDAO).get();

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            UsersCommand command = new UsersCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(SQLException.class.getName() + ": " + message, ex.getMessage());
    }

    @Test
    void executeExceptionClassNotFound() throws SQLException, ClassNotFoundException {
        String message = "Error";

        doThrow(new ClassNotFoundException(message)).when(mockDAO).get();

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            UsersCommand command = new UsersCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(ClassNotFoundException.class.getName() + ": " + message, ex.getMessage());
    }
}