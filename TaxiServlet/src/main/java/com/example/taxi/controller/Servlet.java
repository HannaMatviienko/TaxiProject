package com.example.taxi.controller;

import com.example.taxi.controller.commands.Command;
import com.example.taxi.controller.commands.IndexCommand;
import com.example.taxi.controller.commands.car.*;
import com.example.taxi.controller.commands.order.*;
import com.example.taxi.controller.commands.user.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Servlet", value = "/")
public class Servlet extends HttpServlet {

    private Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig servletConfig) {
        commands = new HashMap<>();
        commands.put("/user/login", new LogInCommand());
        commands.put("/user/logout", new LogOutCommand());
        commands.put("/user/signup", new SignUpCommand());

        commands.put("/order", new OrderCommand());
        commands.put("/order/checkout", new CheckOutCommand());

        commands.put("/admin/orders", new OrdersCommand());

        commands.put("/admin/cars", new CarsCommand());
        commands.put("/admin/cars/edit", new CarEditCommand());
        commands.put("/admin/cars/del", new CarDeleteCommand());
        commands.put("/admin/cars/new", new CarNewCommand());
        commands.put("/admin/cars/save", new CarSaveCommand());

        commands.put("/admin/users", new UsersCommand());
        commands.put("/admin/users/edit", new UserEditCommand());
        commands.put("/admin/users/del", new UserDeleteCommand());
        commands.put("/admin/users/new", new UserNewCommand());
        commands.put("/admin/users/save", new UserSaveCommand());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        Command command = commands.getOrDefault(path, new IndexCommand());
        String page = command.execute(request, response);
        if (page.contains("redirect:"))
            response.sendRedirect(request.getContextPath() + page.replace("redirect:", "/"));
        else
            request.getRequestDispatcher(page).forward(request, response);
    }
}