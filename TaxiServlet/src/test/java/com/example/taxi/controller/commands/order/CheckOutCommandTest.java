package com.example.taxi.controller.commands.order;

import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.dao.OrderDAO;
import com.example.taxi.model.entity.Order;
import com.example.taxi.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

class CheckOutCommandTest {

    private HttpServletRequest mockRequest;
    private HttpSession mockSession;
    private OrderDAO mockDAO;

    @BeforeEach
    void setUp() {
        mockRequest = mock(HttpServletRequest.class);
        mockSession = mock(HttpSession.class);
        mockDAO = mock(OrderDAO.class);
        DAOFactory.setOrder(mockDAO);
    }

    @Test
    void executeGetOrder() throws ServletException {
        Order order = new Order();
        when(mockRequest.getMethod()).thenReturn("GET");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("order")).thenReturn(order);

        CheckOutCommand command = new CheckOutCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("order", order);

        Assertions.assertEquals("/WEB-INF/jsp/checkout.jsp", page);
    }

    @Test
    void executeGetNoOrder() throws ServletException {
        when(mockRequest.getMethod()).thenReturn("GET");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("order")).thenReturn(null);

        CheckOutCommand command = new CheckOutCommand();
        String page = command.execute(mockRequest, null);

        Assertions.assertEquals("redirect:/order", page);
    }


    @Test
    void executePostAdmin() throws ServletException, SQLException, ClassNotFoundException {
        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("order")).thenReturn(new Order());
        when(mockSession.getAttribute("user")).thenReturn(new User());

        CheckOutCommand command = new CheckOutCommand();
        String page = command.execute(mockRequest, null);

        verify(mockSession).setAttribute(eq("order"), isA(Order.class));

        Assertions.assertEquals("redirect:/order?message", page);
    }

    @Test
    void executeExceptionSQL() throws SQLException, ClassNotFoundException {
        Order order = new Order();
        User user = new User();

        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("order")).thenReturn(order);
        when(mockSession.getAttribute("user")).thenReturn(user);

        String message = "Error";
        doThrow(new SQLException(message)).when(mockDAO).save(user, order);

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            CheckOutCommand command = new CheckOutCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(SQLException.class.getName() + ": " + message, ex.getMessage());
    }

    @Test
    void executeClassNotFound() throws SQLException, ClassNotFoundException {
        Order order = new Order();
        User user = new User();

        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("order")).thenReturn(order);
        when(mockSession.getAttribute("user")).thenReturn(user);

        String message = "Error";
        doThrow(new ClassNotFoundException(message)).when(mockDAO).save(user, order);

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            CheckOutCommand command = new CheckOutCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(ClassNotFoundException.class.getName() + ": " + message, ex.getMessage());
    }

}