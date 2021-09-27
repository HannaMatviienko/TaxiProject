package com.example.taxi.controller.commands.car;

import com.example.taxi.model.entity.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class CarNewCommandTest {
    @Test
    void execute() throws ServletException  {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CarNewCommand command = new CarNewCommand();
        String page = command.execute(request, null);

        verify(request).setAttribute("mode", 0);
        verify(request).setAttribute("car", new Car());

        Assertions.assertEquals("/WEB-INF/jsp/car.jsp", page);
    }
}