package com.example.taxi.controller.commands.car;

import com.example.taxi.model.dao.CarDAO;
import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.entity.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class CarEditCommandTest {

    private int id;
    private CarDAO mockDAO;
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp()
    {
        id = 1;
        mockDAO = mock(CarDAO.class);
        DAOFactory.setCar(mockDAO);

        mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getParameter("id")).thenReturn(Integer.toString(id));
    }

    @Test
    void execute() throws SQLException, ClassNotFoundException, ServletException {
        Car car = new Car();
        car.setId(id);
        when(mockDAO.get(id)).thenReturn(car);

        CarEditCommand command = new CarEditCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("mode", 1);
        verify(mockRequest).setAttribute("car", car);
        Assertions.assertEquals("/WEB-INF/jsp/car.jsp", page);
    }


    @Test
    void executeExceptionSQL() throws SQLException, ClassNotFoundException {
        String message = "Error";

        doThrow(new SQLException(message)).when(mockDAO).get(id);

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            CarEditCommand command = new CarEditCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(SQLException.class.getName() + ": " + message, ex.getMessage());
    }


    @Test
    void executeExceptionClassNotFound() throws SQLException, ClassNotFoundException {
        String message = "Error";

        doThrow(new ClassNotFoundException(message)).when(mockDAO).get(id);

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            CarEditCommand command = new CarEditCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(ClassNotFoundException.class.getName() + ": " + message, ex.getMessage());
    }

    @Test
    void executeExceptionNumberFormat() {
        when(mockRequest.getParameter("id")).thenReturn("");

        Assertions.assertThrows(ServletException.class, () -> {
            CarEditCommand command = new CarEditCommand();
            command.execute(mockRequest, null);
        });
    }
}