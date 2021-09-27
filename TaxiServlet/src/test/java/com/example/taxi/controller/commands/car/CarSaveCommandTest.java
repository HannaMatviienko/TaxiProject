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

class CarSaveCommandTest {

    private HttpServletRequest mockRequest;
    private CarDAO mockDAO;
    private Car car;

    @BeforeEach
    void setUp()
    {
        mockRequest = mock(HttpServletRequest.class);
        car = new Car();
        when(mockRequest.getParameter("id")).thenReturn(car.getId().toString());
        when(mockRequest.getParameter("model")).thenReturn(car.getModel());
        when(mockRequest.getParameter("plate")).thenReturn(car.getPlate());
        when(mockRequest.getParameter("category")).thenReturn(car.getCategory().toString());
        when(mockRequest.getParameter("passengers")).thenReturn(car.getPassengers().toString());
        when(mockRequest.getParameter("status")).thenReturn(car.getStatus().toString());

        mockDAO = mock(CarDAO.class);
        DAOFactory.setCar(mockDAO);
    }

    @Test
    void execute() throws ServletException, SQLException, ClassNotFoundException {
        CarSaveCommand command = new CarSaveCommand();
        String page = command.execute(mockRequest, null);

        verify(mockDAO).save(car);
        Assertions.assertEquals("redirect:/admin/cars", page);
    }

    @Test
    void executeExceptionSQL() throws SQLException, ClassNotFoundException {
        String message = "Error";

        doThrow(new SQLException(message)).when(mockDAO).save(car);

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            CarSaveCommand command = new CarSaveCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(SQLException.class.getName() + ": " + message, ex.getMessage());
    }


    @Test
    void executeExceptionClassNotFound() throws SQLException, ClassNotFoundException {
        String message = "Error";

        doThrow(new ClassNotFoundException(message)).when(mockDAO).save(car);

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            CarSaveCommand command = new CarSaveCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(ClassNotFoundException.class.getName() + ": " + message, ex.getMessage());
    }

    @Test
    void executeExceptionNumberFormat() {
        when(mockRequest.getParameter("id")).thenReturn("");
        Assertions.assertThrows(ServletException.class, () -> {
            CarSaveCommand command = new CarSaveCommand();
            command.execute(mockRequest, null);
        });
    }
}