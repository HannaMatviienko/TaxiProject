package com.example.taxi.controller.commands.order;

import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.dao.OrderDAO;
import com.example.taxi.model.entity.Car;
import com.example.taxi.model.entity.Order;
import com.example.taxi.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class OrderCommandTest {
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
    void executeGet() throws ServletException {
        Order order = new Order();
        User user = new User();

        when(mockRequest.getMethod()).thenReturn("GET");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(user);
        when(mockSession.getAttribute("order")).thenReturn(order);

        OrderCommand command = new OrderCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("user", user);
        verify(mockRequest).setAttribute("order", order);

        Assertions.assertEquals("/WEB-INF/jsp/order.jsp", page);
    }

    @Test
    void executeGetNoOrder() throws ServletException {
        Order order = new Order();
        User user = new User();

        when(mockRequest.getMethod()).thenReturn("GET");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockSession.getAttribute("user")).thenReturn(user);
        when(mockSession.getAttribute("order")).thenReturn(null);

        OrderCommand command = new OrderCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("user", user);
        verify(mockRequest).setAttribute(eq("order"), isA(Order.class));

        Assertions.assertEquals("/WEB-INF/jsp/order.jsp", page);
    }

    @Test
    void executePost() throws ServletException {
        String address = "";
        String destination = "";
        int passengers = 1;
        int category = 0;

        List<Car> list = new ArrayList<>();
        list.add(new Car());

        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockRequest.getSession()).thenReturn(mockSession);

        when(mockRequest.getParameter("address")).thenReturn(address);
        when(mockRequest.getParameter("destination")).thenReturn(destination);
        when(mockRequest.getParameter("passengers")).thenReturn(String.valueOf(passengers));
        when(mockRequest.getParameter("category")).thenReturn(String.valueOf(category));

        AtomicReference<Order> mockOrder = new AtomicReference<>();
        try (MockedConstruction<Order> mocked = Mockito.mockConstruction(Order.class,
                (mock, context) -> {
                    when(mock.getCars()).thenReturn(list);
                    mockOrder.set(mock);
                })) {

            OrderCommand command = new OrderCommand();
            String page = command.execute(mockRequest, null);

            verify(mockOrder.get()).setAddressFrom(address);
            verify(mockOrder.get()).setAddressTo(destination);
            verify(mockOrder.get()).setPassengers(passengers);
            verify(mockOrder.get()).setCategory(category);

            verify(mockSession).setAttribute("order", mockOrder.get());

            Assertions.assertEquals("redirect:/order/checkout", page);
        }
    }

    @Test
    void executePostNoCar() throws ServletException {
        String address = "";
        String destination = "";
        int passengers = 1;
        int category = 0;

        List<Car> list = new ArrayList<>();

        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockRequest.getSession()).thenReturn(mockSession);

        when(mockRequest.getParameter("address")).thenReturn(address);
        when(mockRequest.getParameter("destination")).thenReturn(destination);
        when(mockRequest.getParameter("passengers")).thenReturn(String.valueOf(passengers));
        when(mockRequest.getParameter("category")).thenReturn(String.valueOf(category));

        AtomicReference<Order> mockOrder = new AtomicReference<>();
        try (MockedConstruction<Order> mocked = Mockito.mockConstruction(Order.class,
                (mock, context) -> {
                    when(mock.getCars()).thenReturn(list);
                    mockOrder.set(mock);
                })) {

            OrderCommand command = new OrderCommand();
            String page = command.execute(mockRequest, null);

            verify(mockOrder.get()).setAddressFrom(address);
            verify(mockOrder.get()).setAddressTo(destination);
            verify(mockOrder.get()).setPassengers(passengers);
            verify(mockOrder.get()).setCategory(category);

            verify(mockRequest).setAttribute("error", true);
            verify(mockRequest).setAttribute("order", mockOrder.get());

            Assertions.assertEquals("/WEB-INF/jsp/order.jsp", page);
        }
    }
}