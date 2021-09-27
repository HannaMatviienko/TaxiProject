package com.example.taxi.controller.commands.order;

import com.example.taxi.controller.commands.Command;
import com.example.taxi.model.dao.DAOFactory;
import com.example.taxi.model.entity.Order;
import com.example.taxi.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class OrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getMethod().equals("GET")) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                if (user != null)
                    request.setAttribute("user", user);

                Order order = (Order) session.getAttribute("order");
                if (order == null)
                    order = new Order();
                request.setAttribute("order", order);
                return "/WEB-INF/jsp/order.jsp";
            }
            else
            {
                String address = request.getParameter("address");
                String destination = request.getParameter("destination");

                int passengers = 1;
                try {
                    passengers = Integer.parseInt(request.getParameter("passengers"));
                } catch (NumberFormatException ignored) {
                }

                int category = 0;

                try {
                    category = Integer.parseInt(request.getParameter("category"));
                } catch (NumberFormatException ignored) {

                }

                Order order = new Order();
                order.setAddressFrom(address);
                order.setAddressTo(destination);
                order.setPassengers(passengers);
                order.setCategory(category);

                order.setCars(DAOFactory.getCar().findByCategoryAndStatusAndPassengers(category, 1, passengers));

                if (order.getCars().size() > 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("order", order);
                    return "redirect:/order/checkout";

                } else {
                    request.setAttribute("order", order);
                    request.setAttribute("error", true);
                    return "/WEB-INF/jsp/order.jsp";
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            throw new ServletException(ex);
        }
    }
}
