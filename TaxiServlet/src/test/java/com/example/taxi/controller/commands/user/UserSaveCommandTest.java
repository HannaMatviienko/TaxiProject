package com.example.taxi.controller.commands.user;

import com.example.taxi.controller.commands.car.CarSaveCommand;
import com.example.taxi.model.dao.CarDAO;
import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.dao.UserDAO;
import com.example.taxi.model.entity.Car;
import com.example.taxi.model.entity.User;
import com.example.taxi.tools.PBKDF2Hasher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserSaveCommandTest {

    private HttpServletRequest mockRequest;
    private UserDAO mockDAO;
    private User user;

    @BeforeEach
    void setUp() {
        mockRequest = mock(HttpServletRequest.class);
        user = new User();
        when(mockRequest.getParameter("id")).thenReturn(Long.toString(user.getId()));
        when(mockRequest.getParameter("firstName")).thenReturn(user.getFirstName());
        when(mockRequest.getParameter("lastName")).thenReturn(user.getLastName());
        when(mockRequest.getParameter("password")).thenReturn(user.getPassword());
        when(mockRequest.getParameter("roles")).thenReturn(user.getRole().toString());
        when(mockRequest.getParameter("email")).thenReturn(user.getEmail());

        mockDAO = mock(UserDAO.class);
        DAOFactory.setUser(mockDAO);
    }

    @Test
    void execute()  throws ServletException, SQLException, ClassNotFoundException {
        UserSaveCommand command = new UserSaveCommand();
        String page = command.execute(mockRequest, null);

        verify(mockDAO).save(user);
        Assertions.assertEquals("redirect:/admin/users", page);
    }

    @Test
    void executeExceptionSQL() throws SQLException, ClassNotFoundException {
        String message = "Error";

        doThrow(new SQLException(message)).when(mockDAO).save(user);

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            UserSaveCommand command = new UserSaveCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(SQLException.class.getName() + ": " + message, ex.getMessage());
    }


    @Test
    void executeExceptionClassNotFound() throws SQLException, ClassNotFoundException {
        String message = "Error";

        doThrow(new ClassNotFoundException(message)).when(mockDAO).save(user);

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            UserSaveCommand command = new UserSaveCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(ClassNotFoundException.class.getName() + ": " + message, ex.getMessage());
    }

    @Test
    void executeExceptionNumberFormat() {
        when(mockRequest.getParameter("id")).thenReturn("");
        Assertions.assertThrows(ServletException.class, () -> {
            UserSaveCommand command = new UserSaveCommand();
            command.execute(mockRequest, null);
        });
    }
}