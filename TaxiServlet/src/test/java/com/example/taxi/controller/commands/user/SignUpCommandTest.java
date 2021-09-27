package com.example.taxi.controller.commands.user;

import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.dao.UserDAO;
import com.example.taxi.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignUpCommandTest {

    private HttpServletRequest mockRequest;
    private HttpSession mockSession;
    private UserDAO mockDAO;
    private User user;

    @BeforeEach
    void setUp() {
        mockDAO = mock(UserDAO.class);
        DAOFactory.setUser(mockDAO);

        mockRequest = mock(HttpServletRequest.class);
        mockSession = mock(HttpSession.class);

        user = new User();
        when(mockRequest.getParameter("firstName")).thenReturn(user.getFirstName());
        when(mockRequest.getParameter("lastName")).thenReturn(user.getLastName());
        when(mockRequest.getParameter("userName")).thenReturn(user.getLogin());
        when(mockRequest.getParameter("email")).thenReturn(user.getEmail());
        when(mockRequest.getParameter("password")).thenReturn(user.getPassword());
    }

    @Test
    void executeGet() throws ServletException {
        when(mockRequest.getMethod()).thenReturn("GET");

        SignUpCommand command = new SignUpCommand();
        String page = command.execute(mockRequest, null);

        Assertions.assertEquals("/WEB-INF/jsp/signup.jsp", page);
    }

    @Test
    void executePost() throws ServletException, SQLException, ClassNotFoundException {
        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockDAO.newUser(user.getFirstName(), user.getLastName(), user.getLogin(), user.getEmail(), user.getPassword())).thenReturn(user);
        when(mockRequest.getSession()).thenReturn(mockSession);

        SignUpCommand command = new SignUpCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("username", user.getLogin());
        verify(mockSession).setAttribute("user", user);

        Assertions.assertEquals("redirect:/order", page);
    }

    @Test
    void executePostNoUser() throws ServletException, SQLException, ClassNotFoundException {
        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockDAO.newUser(user.getFirstName(), user.getLastName(), user.getLogin(), user.getEmail(), user.getPassword())).thenReturn(null);
        when(mockRequest.getSession()).thenReturn(mockSession);

        SignUpCommand command = new SignUpCommand();
        String page = command.execute(mockRequest, null);

        String message = "Invalid email/password";
        verify(mockRequest).setAttribute("message", message);
        verify(mockRequest).setAttribute("firstName", user.getFirstName());
        verify(mockRequest).setAttribute("lastName", user.getLastName());
        verify(mockRequest).setAttribute("userName", user.getLogin());
        verify(mockRequest).setAttribute("email", user.getEmail());
        verify(mockRequest).setAttribute("password", user.getPassword());

        Assertions.assertEquals("/WEB-INF/jsp/signup.jsp", page);
    }


    @Test
    void executeExceptionSQL() throws SQLException, ClassNotFoundException {
        when(mockRequest.getMethod()).thenReturn("POST");

        String message = "Error";
        doThrow(new SQLException(message)).when(mockDAO).newUser(user.getFirstName(), user.getLastName(), user.getLogin(), user.getEmail(), user.getPassword());

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            SignUpCommand command = new SignUpCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(SQLException.class.getName() + ": " + message, ex.getMessage());
    }

    @Test
    void executeExceptionClassNotFound() throws SQLException, ClassNotFoundException {
        when(mockRequest.getMethod()).thenReturn("POST");

        String message = "Error";
        doThrow(new ClassNotFoundException(message)).when(mockDAO).newUser(user.getFirstName(), user.getLastName(), user.getLogin(), user.getEmail(), user.getPassword());

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            SignUpCommand command = new SignUpCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(ClassNotFoundException.class.getName() + ": " + message, ex.getMessage());
    }
}