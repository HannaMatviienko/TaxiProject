package com.example.taxi.controller.commands.car;

import com.example.taxi.controller.commands.Command;
import com.example.taxi.model.entity.Car;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CarNewCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.setAttribute("car", new Car());
        request.setAttribute("mode", 0);
        return "/WEB-INF/jsp/car.jsp";
    }
}
