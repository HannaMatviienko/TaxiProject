package com.example.taxi.controller.commands.car;

import com.example.taxi.controller.commands.Command;
import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.entity.Car;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class CarSaveCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Car car = new Car();
            car.setId(Integer.parseInt(request.getParameter("id")));
            car.setModel(request.getParameter("model"));
            car.setPlate(request.getParameter("plate"));
            car.setCategory(Integer.parseInt(request.getParameter("category")));
            car.setPassengers(Integer.parseInt(request.getParameter("passengers")));
            car.setStatus(Integer.parseInt(request.getParameter("status")));
            DAOFactory.getCar().save(car);
        } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
            throw new ServletException(ex);
        }
        return "redirect:/admin/cars";
    }
}
