package com.example.taxi.controller.commands.car;

import com.example.taxi.controller.commands.Command;
import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.entity.Car;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class CarsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            List<Car> cars =  DAOFactory.getCar().get();
            request.setAttribute("cars", cars);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }
        return "/WEB-INF/jsp/cars.jsp";
    }
}
