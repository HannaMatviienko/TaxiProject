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

public class CheckOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (request.getMethod().equals("GET")) {
            HttpSession session = request.getSession();
            Order order = (Order) session.getAttribute("order");
            if (order != null) {
                request.setAttribute("order", order);
                return "/WEB-INF/jsp/checkout.jsp";
            } else {
                return "redirect:/order";
            }
        } else {
            HttpSession session = request.getSession();
            Order order = (Order) session.getAttribute("order");
            User user = (User) session.getAttribute("user");
            if (order != null && user != null) {
                try {
                    DAOFactory.getOrder().save(user, order);
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new ServletException(ex);
                }
            }
            session.setAttribute("order", new Order());
            return "redirect:/order?message";
        }
    }
}