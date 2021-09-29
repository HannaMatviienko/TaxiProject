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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class LogInCommandTest {

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
        when(mockRequest.getParameter("email")).thenReturn(user.getEmail());
        when(mockRequest.getParameter("password")).thenReturn(user.getPassword());
    }

    @Test
    void executeGet() throws ServletException {
        when(mockRequest.getMethod()).thenReturn("GET");

        LogInCommand command = new LogInCommand();
        String page = command.execute(mockRequest, null);

        Assertions.assertEquals("/WEB-INF/jsp/login.jsp", page);
    }

    @Test
    void executePostAdmin() throws ServletException, SQLException, ClassNotFoundException {
        user.setRole(User.ROLE.ADMIN);
        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockDAO.checkLogin(user.getEmail(), user.getPassword())).thenReturn(user);
        when(mockRequest.getSession()).thenReturn(mockSession);

        LogInCommand command = new LogInCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("username", user.getEmail());
        verify(mockSession).setAttribute("user", user);

        Assertions.assertEquals("redirect:admin/orders", page);
    }

    @Test
    void executePostUser() throws ServletException, SQLException, ClassNotFoundException {
        user.setRole(User.ROLE.USER);
        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockDAO.checkLogin(user.getEmail(), user.getPassword())).thenReturn(user);
        when(mockRequest.getSession()).thenReturn(mockSession);

        LogInCommand command = new LogInCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("username", user.getEmail());
        verify(mockSession).setAttribute("user", user);

        Assertions.assertEquals("redirect:order", page);
    }

    @Test
    void executePostNoUser() throws ServletException, SQLException, ClassNotFoundException {
        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockDAO.checkLogin(user.getEmail(), user.getPassword())).thenReturn(null);
        when(mockRequest.getSession()).thenReturn(mockSession);

        LogInCommand command = new LogInCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("error", true);
        verify(mockRequest).setAttribute("email", user.getEmail());
        verify(mockRequest).setAttribute("password", user.getPassword());

        Assertions.assertEquals("/WEB-INF/jsp/login.jsp", page);
    }

    @Test
    void executeExceptionSQL() throws SQLException, ClassNotFoundException {
        when(mockRequest.getMethod()).thenReturn("POST");

        String message = "Error";
        doThrow(new SQLException(message)).when(mockDAO).checkLogin(user.getEmail(), user.getPassword());

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            LogInCommand command = new LogInCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(SQLException.class.getName() + ": " + message, ex.getMessage());
    }

    @Test
    void executeExceptionClassNotFound() throws SQLException, ClassNotFoundException {
        when(mockRequest.getMethod()).thenReturn("POST");

        String message = "Error";
        doThrow(new ClassNotFoundException(message)).when(mockDAO).checkLogin(user.getEmail(), user.getPassword());

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            LogInCommand command = new LogInCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(ClassNotFoundException.class.getName() + ": " + message, ex.getMessage());
    }
}