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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class CarsCommandTest {
    private HttpServletRequest mockRequest;
    private CarDAO mockDAO;

    @BeforeEach
    void setUp()
    {
        mockDAO = mock(CarDAO.class);
        DAOFactory.setCar(mockDAO);
        mockRequest = mock(HttpServletRequest.class);
    }

    @Test
    void execute() throws ServletException, SQLException, ClassNotFoundException {
        List<Car> cars = new ArrayList<>();
        Car car = new Car();
        car.setId(1);
        cars.add(car);
        when(mockDAO.get()).thenReturn(cars);

        CarsCommand command = new CarsCommand();
        String page = command.execute(mockRequest, null);

        verify(mockRequest).setAttribute("cars", cars);

        Assertions.assertEquals("/WEB-INF/jsp/cars.jsp", page);
    }

    @Test
    void executeExceptionSQL() throws SQLException, ClassNotFoundException {
        String message = "Error";
        doThrow(new SQLException(message)).when(mockDAO).get();

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            CarsCommand command = new CarsCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(SQLException.class.getName() + ": " + message, ex.getMessage());
    }

    @Test
    void executeExceptionClassNotFound() throws SQLException, ClassNotFoundException {
        String message = "Error";

        doThrow(new ClassNotFoundException(message)).when(mockDAO).get();

        ServletException ex  = Assertions.assertThrows(ServletException.class, () -> {
            CarsCommand command = new CarsCommand();
            command.execute(mockRequest, null);
        });

        Assertions.assertEquals(ClassNotFoundException.class.getName() + ": " + message, ex.getMessage());
    }
}
