package com.example.taxi.controller.commands.order;

import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.dao.DateDAO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class OrdersCommandTest {

    private HttpServletRequest mockRequest;
    private HttpSession mockSession;
    private OrderDAO mockDAO;
    private DateDAO mockDateDAO;

    @BeforeEach
    void setUp() {
        mockRequest = mock(HttpServletRequest.class);
        mockSession = mock(HttpSession.class);

        mockDAO = mock(OrderDAO.class);
        DAOFactory.setOrder(mockDAO);

        mockDateDAO = mock(DateDAO.class);
        DAOFactory.setDate(mockDateDAO);
    }

    @Test
    void executeGet() throws ServletException {
        String now = "now";
        when(mockRequest.getMethod()).thenReturn("GET");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockDateDAO.getNow()).thenReturn(now);

        OrdersCommand command = new OrdersCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("dateFrom", now);
        verify(mockRequest).setAttribute("dateTo", now);
        verify(mockRequest).setAttribute("userId", 0);
        verify(mockRequest).setAttribute("pageCurrent", 1);
        verify(mockRequest).setAttribute("pageCount", 0);
        verify(mockRequest).setAttribute("sort", "order_date");
        verify(mockRequest).setAttribute("sortDir", "DESC");

        Assertions.assertEquals("/WEB-INF/jsp/orders.jsp", page);
    }

    @Test
    void executePost() throws ServletException, SQLException, ClassNotFoundException {
        String now = "now";
        int userId = 1;
        int pageCurrent = 1;
        int pageCount = 5;
        int limit = 10;
        String sort = "price";
        String sortDir = "ASC";

        when(mockRequest.getMethod()).thenReturn("POST");
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockDateDAO.getNow()).thenReturn(now);
        when(mockDAO.getPageCount(now, now, String.valueOf(userId), limit)).thenReturn(pageCount);

        when(mockRequest.getParameter("dateFrom")).thenReturn(now);
        when(mockRequest.getParameter("dateTo")).thenReturn(now);
        when(mockRequest.getParameter("userId")).thenReturn(String.valueOf(userId));
        when(mockRequest.getParameter("pageCurrent")).thenReturn(String.valueOf(pageCurrent));
        when(mockRequest.getParameter("page")).thenReturn("#" + pageCurrent);
        when(mockRequest.getParameter("sort")).thenReturn(sort);
        when(mockRequest.getParameter("sortDir")).thenReturn(sortDir);

        OrdersCommand command = new OrdersCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("userId", userId);
        verify(mockRequest).setAttribute("dateFrom", now);
        verify(mockRequest).setAttribute("dateFrom", now);
        verify(mockRequest).setAttribute("pageCurrent", pageCurrent);
        verify(mockRequest).setAttribute("pageCount", pageCount);
        verify(mockRequest).setAttribute("sort", sort);
        verify(mockRequest).setAttribute("sortDir", sortDir);

        Assertions.assertEquals("/WEB-INF/jsp/orders.jsp", page);
    }
}